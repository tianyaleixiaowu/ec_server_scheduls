package com.mindata.ecserver.global.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * zookeeper分布式锁是否启用的注解
 *
 * Created by wuweifeng on 17/5/19.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableZookeeperLockAnnotation {
    boolean enable() default true;

    /**
     * zookeeper的node名
     * @return
     * node
     */
    String nodeName() default "";
}
