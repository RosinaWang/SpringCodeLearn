package myIOC.test;

import myIOC.container.Container;
import myIOC.container.SampleContainer;

/**
 * Created by Dell on 2017-08-15.
 */
public class Test {
    private static Container container = new SampleContainer();

    public static void baseTest() throws InstantiationException, IllegalAccessException {
        container.registerBean(Hero.class);
        // 初始化注入
        container.initWired();

        Hero h = container.getBean(Hero.class);
        h.say();
    }
//
//    public static void iocClassTest(){
//        container.registerBean(Lol2.class);
//        // 初始化注入
//        container.initWired();
//
//        Lol2 lol = container.getBean(Lol2.class);
//        lol.work();
//    }
//
    public static void iocNameTest() throws InstantiationException, IllegalAccessException {
        container.registerBean("String","fds");
        container.registerBean(Hero.class);
        // 初始化注入
        container.initWired();

        Hero lol = container.getBean(Hero.class);
        lol.say();
    }
//
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
//        baseTest();
//        iocClassTest();
        iocNameTest();
    }
}
