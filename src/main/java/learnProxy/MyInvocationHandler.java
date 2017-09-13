package learnProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 实现自己的 调用处理器
 * Created by Dell on 2017-09-05.
 */
public class MyInvocationHandler implements InvocationHandler {
    // 目标对象
    private Object target;
    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    /**
     * 这是动态代理的核心，生成的代理对象的method中转调这个实现的
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------before--------");//实现的增强
        Object result=method.invoke(target,args);//转调原本的
        System.out.println("------after--------");
        return result;
    }
    public Object getProxy(){
        //proxy获取代理对象需要有类加载器，需要代理对象的接口，调用处理器的handler
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),target.getClass().getInterfaces(),this);
    }
}
