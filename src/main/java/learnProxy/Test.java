package learnProxy;

/**
 * Created by Dell on 2017-09-05.
 */
public class Test {
    public static void main(String[] args){
        //靠的是多态和反射
        UserService userService=new UserServiceImpl();
        MyInvocationHandler handler=new MyInvocationHandler(userService);//实现对原来对象的增强，持有原来对象的引用
        UserService proxy=(UserService) handler.getProxy();
        proxy.add();//动态代理使用了多态，实现要代理的接口的子类，并返回该对象

    }
}
