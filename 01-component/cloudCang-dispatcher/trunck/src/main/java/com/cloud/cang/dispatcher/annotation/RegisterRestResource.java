package com.cloud.cang.dispatcher.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegisterRestResource {

	String value() default "";//资源名称，默认取所在的类名
	
	String rel() default "";//资源关系，默认取所在的方法名
	
	boolean isRegister() default true;//是否需要注册该资源关系
}
