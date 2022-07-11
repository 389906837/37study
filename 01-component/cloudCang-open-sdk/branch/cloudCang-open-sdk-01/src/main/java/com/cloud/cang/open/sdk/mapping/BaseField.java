package com.cloud.cang.open.sdk.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 1.0
 * @Description: 自定义属性
 * @Author: zhouhong
 * @Date: 2018/9/3 16:28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BaseField {
    String value() default "";
}
