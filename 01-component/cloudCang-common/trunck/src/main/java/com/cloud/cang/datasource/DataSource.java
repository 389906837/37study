package com.cloud.cang.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
 * RUNTIME  
 * 编译器将把注释记录在类文件中，在运行时 VM 将保留注释，因此可以反射性地读取。  
 *  
 */  
@Target({ElementType.METHOD,ElementType.TYPE})  
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataSource {  
    String value();  
}  