package myAOP;

import learnProxy.UserService;

import java.io.FileNotFoundException;

/**
 * Created by Dell on 2017-09-06.
 */
public class AopFrameWorkTest {
    public static void main(String[] args) throws FileNotFoundException {
        /*
         BeanFactory,用于读取配置文件，根据配置创建相应的对象
         ProxyFactoryBean,用于生成代理对象，含有两个私有属性:目标和通知
         Advice,通知接口，用于把切面的代码以对象的形式传递给InvocationHandler的的invoke方法
         MyAdvice,Advice接口的一个实现类，打印执行方法前的时间及执行耗时
         AopFrameWorkTest，测试效果
         */
        BeanFactory factory=new BeanFactory(AopFrameWorkTest.class.getResourceAsStream("/aop.properties"));
        UserService userProxy=(UserService) factory.getBean("userProxy");
        userProxy.add();



    }
}
