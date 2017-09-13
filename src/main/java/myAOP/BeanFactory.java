package myAOP;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件，获取要获取的bean，
 * 如果给定的类是ProxyFactoryBean的子类，设置Advice，设置被代理的类，创建Proxy并返回
 * Created by Dell on 2017-09-05.
 */
public class BeanFactory {
    Properties properties = new Properties();
    public BeanFactory(InputStream inputStream){
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  Object getBean(String name){
        String className = properties.getProperty(name);
        Object bean = null;//bean是代理对象的实例
        try {
            Class clazz = Class.forName(className);
            bean = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (bean instanceof ProxyFactoryBean){
            ProxyFactoryBean proxyFactoryBean = (ProxyFactoryBean)bean;
            Advice advice = null;
            Object target = null;
            try {
                advice = (Advice) Class.forName(properties.getProperty(name+".advice")).newInstance();
                target = Class.forName(properties.getProperty(name+".target")).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            proxyFactoryBean.setAdvice(advice);//通知
            proxyFactoryBean.setTarget(target);//被代理的对象
            Object proxy = ((ProxyFactoryBean) bean).getProxy();
            return proxy;
        }
        return bean;
    }
}
