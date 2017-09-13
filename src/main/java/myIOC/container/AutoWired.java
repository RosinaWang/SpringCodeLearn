package myIOC.container;

import java.lang.annotation.*;

/**
 * Created by Dell on 2017-08-15.
 */


@Target({ElementType.TYPE,ElementType.FIELD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoWired{
    //name是另一个bean的名称
    public String name() default "";

    public Class value() default Class.class;
}
