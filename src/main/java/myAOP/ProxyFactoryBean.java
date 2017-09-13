package myAOP;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * Created by Dell on 2017-09-05.
 */
public class ProxyFactoryBean {
    private Object target;//真实的对象
    private Advice advice;//通知，也就是要增强的

    //获取代理对象，原理同JDK动态代理,Proxy的newProxyInstance会生成被代理接口的子类代理类的字节码，代理类拥有和被代理对象同样的接口，但是前后有增强，用真实对象去转调方法
    public Object getProxy(){//使用时注意要先将target和advice赋值，否则无法达到增强和代理的目的
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                advice.beforeMethod(method);
                Object retVal=method.invoke(target,args);
                advice.afterMethod(method);
                return retVal;
            }
        });
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
