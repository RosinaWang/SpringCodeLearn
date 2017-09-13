package myIOC.container;

import java.util.Set;

/**
 * Created by Dell on 2017-08-15.
 */
public interface Container {
    /**
     * 根据Class获取Bean
     * @param clazz
     * @return
     */
    public <T> T getBean(Class<T> clazz);

    /**
     * 根据名称获取Bean
     * @param name
     * @return
     */
    public <T> T getBeanByName(String name);

    /**
     * 注册一个Bean到容器中
     * @param bean
     */
    public Object registerBean(Object bean);

    /**
     * 注册一个Class到容器中
     * @param clazz
     */
    public Object registerBean(Class<?> clazz) throws IllegalAccessException, InstantiationException;

    /**
     * 注册一个带名称的Bean到容器中
     * @param name
     * @param bean
     */
    public Object registerBean(String name, Object bean);

    /**
     * 删除一个bean
     * @param clazz
     */
    public void remove(Class<?> clazz);

    /**
     * 根据名称删除一个bean
     * @param name
     */
    public void removeByName(String name);

    /**
     * @return	返回所有bean对象名称
     */
    public Set<String> getBeanNames();

    /**
     * 初始化装配
     */
    public void initWired();
}
