package myAOP;

import java.lang.reflect.Method;

/**
 * Created by Dell on 2017-09-05.
 */
public interface Advice {
    void beforeMethod(Method method);
    void afterMethod(Method method);
}
