/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年8月15日
 */
package com.gzjy.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author xuewenlong
 * @updated 2018年8月15日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
    /**
     * 
     * @return  允许访问的最大次数
     * RequestLimit.java
     */
    int count() default Integer.MAX_VALUE;
    /**
     * 时间段，单位毫秒，默认值一分钟
     * @return
     * RequestLimit.java
     */
    long time() default 60000;

}
