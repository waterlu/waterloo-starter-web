package cn.lu.web.annotation;

import java.lang.annotation.*;

/**
 * @author lu
 * @date 2018/5/11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableWaterlooWeb {
}
