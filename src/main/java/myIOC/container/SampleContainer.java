package myIOC.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dell on 2017-08-15.
 */
@SuppressWarnings("unchecked")
public class SampleContainer implements Container {

    /**
     * 保存所有bean对象，格式为 com.xxx.Person : @52x2xa
     */
    private Map<String, Object> beans;

    /**
     * 存储bean和name的关系
     */
    private Map<String, String> beanKeys;

    public SampleContainer() {
        this.beans = new ConcurrentHashMap<String, Object>();
        this.beanKeys = new ConcurrentHashMap<String, String>();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        String name = clazz.getName();
        Object object = beans.get(name);
        if(null != object){
            return (T) object;
        }
        return null;
    }

    @Override
    public <T> T getBeanByName(String name) {
        String className = beanKeys.get(name);
        Object object = beans.get(className);
        if(null != object){
            return (T) object;
        }
        return null;
    }

    @Override
    public Object registerBean(Object bean) {
        String name = bean.getClass().getName();
        beanKeys.put(name, name);
        beans.put(name, bean);
        return bean;
    }

    @Override
    public Object registerBean(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        String name = clazz.getName();
        beanKeys.put(name, name);
        Object bean = ReflectUtil.newInstance(clazz);
        beans.put(name, bean);
        return bean;
    }

    @Override
    public Object registerBean(String name, Object bean) {
        String className = bean.getClass().getName();
        beanKeys.put(name, className);
        beans.put(className, bean);
        return bean;
    }

    @Override
    public Set<String> getBeanNames() {
        return beanKeys.keySet();
    }

    @Override
    public void remove(Class<?> clazz) {
        String className = clazz.getName();
        if(null != className && !className.equals("")){
            beanKeys.remove(className);
            beans.remove(className);
        }
    }

    @Override
    public void removeByName(String name) {
        String className = beanKeys.get(name);
        if(null != className && !className.equals("")){
            beanKeys.remove(name);
            beans.remove(className);
        }
    }

    /**
     * 使用此方法前需要将Beans注册到map中，该方法会将需要注入的属性注入到该对象中
     */
    @Override
    public void initWired(){
        Iterator<Map.Entry<String, Object>> it = beans.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            Object object = entry.getValue();
//            injection(object);
            injectionByConstruct(entry.getKey(),object);
        }
    }

    private void injectionByConstruct(String key,Object object) {
        Constructor[] cs=object.getClass().getDeclaredConstructors();
//        Object res=null;
        for(Constructor c:cs){
            //构造器
            AutoWired autoWired= (AutoWired) c.getAnnotation(AutoWired.class);
            if(autoWired!=null){
                Type[] types=c.getGenericParameterTypes();
                Object[] objects=new Object[types.length];
                for(int i=0;i<types.length;i++){
                    objects[i]=beans.get(types[i]);
                }
                try {
                    beans.put(key,c.newInstance(objects));
//                    System.out.println("ccccccccc    "+object.toString());
                } catch (InstantiationException e) {e.printStackTrace();
                } catch (IllegalAccessException e) {e.printStackTrace();
                } catch (InvocationTargetException e) {e.printStackTrace();}
            }
        }
    }

    /**
     * 注入对象
     * @param object
     */
    public void injection(Object object) {
        // 所有字段
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                // 需要注入的字段
                AutoWired autoWired = field.getAnnotation(AutoWired.class);
                if (null != autoWired) {//获取到有该注解的属性

                    // 要注入的属性
                    Object autoWiredField = null;

                    String name = autoWired.name();//注入的属性的名称
                    //此if-else代表两种注入类型，byName和byType
                    if(!name.equals("")){
                        String className = beanKeys.get(name);//判断是否是其他bean，
                        if(null != className && !className.equals("")){//是其他bean
                            autoWiredField = beans.get(className);//注入为其他bean的对象
                        }
                        if (null == autoWiredField) {//不是其他bean,找不到，异常
                            throw new RuntimeException("Unable to load " + name);
                        }
                    } else {//注入的属性名为空，
                        if(autoWired.value() == Class.class){//通过属性的类型注入
                            autoWiredField = recursiveAssembly(field.getType());
                        } else {
                            // 指定装配的属性的类型
                            autoWiredField = this.getBean(autoWired.value());
                            if (null == autoWiredField) {
                                autoWiredField = recursiveAssembly(autoWired.value());
                            }
                        }
                    }

                    if (null == autoWiredField) {
                        throw new RuntimeException("Unable to load " + field.getType().getCanonicalName());
                    }
                    //看不懂这里，将对象，注入的属性set到field的用意
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(object, autoWiredField);
                    field.setAccessible(accessible);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private Object recursiveAssembly(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        if(null != clazz){
            return this.registerBean(clazz);
        }
        return null;
    }

}
