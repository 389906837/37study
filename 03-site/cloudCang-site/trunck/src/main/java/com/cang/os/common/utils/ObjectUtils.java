/**
 * ObjectUtils.java create on 2011-01-01 Copyright 2015 todaysteel All Rights Reserved.
 */
package com.cang.os.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Bean相关操作处理工具类
 * 
 * @version V1.0, 2015-1-15
 */
@SuppressWarnings("rawtypes")
public class ObjectUtils {
    
    /**
     * 根据传入的cls复制整个对象的属性值到另外一个对象的对应的同名和同类型的属性 （只复制cls存在的且属同名和同类型的属性值，名为sguid的属性不拷贝）
     * 
     * @param vo 源值对象
     * @param target 目标对象
     * @param cls 被拷贝值的对象类型
     * @param not_copy 用于指定不拷贝值的属性，可传多个属性名，之间用逗号隔开
     * @param isCopyNull 不做拷贝的属性
     */
    private static void copyObjectValue(Object vo, Object target, Class cls, String not_copy, boolean isCopyNull) {
        int flag = 0;
        if (StringUtils.isNotBlank(not_copy)) {
            not_copy = "," + not_copy + ",";// 前后加逗号是为了后面能够准确的判断所包含的属性名称
        }
        
        try {
            String sname = "";
            Field[] fields = cls.getDeclaredFields();
            
            for (int i = 0; i < fields.length; i++) {
                sname = fields[i].getName();
                
                // 如果属性为id或属性名存在于not_copy指的属性名范围中，则不拷贝访属性值
                if (sname.toLowerCase().equals("id")
                    || (StringUtils.isNotBlank(not_copy) && not_copy.indexOf("," + sname + ",") != -1)) {
                    continue;
                }
                
                if (fields[i].getType().toString().startsWith("class")
                    && !fields[i].getType().getName().equals("java.lang.String")) {
                    // 对象类型字段值拷贝
                    try {
                        String _getParame = "get" + sname.substring(0, 1).toUpperCase() + sname.substring(1);
                        Object object = MethodUtils.invokeMethod(vo, _getParame, null);
                        BeanUtils.setProperty(target, sname, object);
                    } catch (Exception ne) {
                        flag = 1;
                        continue;
                    }
                } else {
                    // 基本类型字段值拷贝
                    try {
                        if (isCopyNull == false && BeanUtils.getProperty(vo, sname) == null) {
                            continue;
                        } else {
                            BeanUtils.setProperty(target, sname, BeanUtils.getProperty(vo, sname));
                        }
                    } catch (Exception ne) {
                        flag = 1;
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            flag = 1;
            e.printStackTrace();
        }
        
        if (flag == 1) {
            flag = 0;
            System.gc();
        }
    }
    
    /**
     * 复制整个对象的属性值到另外一个对象的对应的同名和同类型的属性
     * 
     * @param vo 源值对象
     * @param target 目标对象
     * @return void
     */
    public static void copyObjValue(Object vo, Object target) {
        Class cls = vo.getClass();
        while (!cls.getName().equals("java.lang.Object")) {
            copyObjectValue(vo, target, cls, null, false);
            cls = cls.getSuperclass();
        }
    }
    
    /**
     * 复制整个对象的属性值到另外一个对象的对应的同名和同类型的属性
     * 
     * @param vo 源值对象
     * @param target 目标对象
     * @param isCopyNull 是否拷贝NULL值
     * @return void
     */
    public static void copyObjValue(Object vo, Object target, boolean isCopyNull) {
        Class cls = vo.getClass();
        while (!cls.getName().equals("java.lang.Object")) {
            copyObjectValue(vo, target, cls, null, isCopyNull);
            cls = cls.getSuperclass();
        }
    }
    
    /**
     * 复制整个对象的属性值到另外一个对象的对应的同名和同类型的属性，但名为sguid的属性不拷贝
     * 
     * @param vo 源值对象
     * @param target 目标对象
     * @param not_copy 用于指定不拷贝值的属性，可传多个属性名，之间用逗号隔开
     * @return void
     */
    public static void copyObjValue(Object vo, Object target, String not_copy) {
        Class cls = vo.getClass();
        while (!cls.getName().equals("java.lang.Object")) {
            copyObjectValue(vo, target, cls, not_copy, false);
            cls = cls.getSuperclass();
        }
    }
    
    /**
     * 复制整个对象的属性值到另外一个对象的对应的同名和同类型的属性，但名为sguid的属性不拷贝
     * 
     * @param vo 源值对象
     * @param target 目标对象
     * @param not_copy 用于指定不拷贝值的属性，可传多个属性名，之间用逗号隔开
     * @param isCopyNull 是否拷贝NULL值
     * @return void
     */
    public static void copyObjValue(Object vo, Object target, String not_copy, boolean isCopyNull) {
        Class cls = vo.getClass();
        while (!cls.getName().equals("java.lang.Object")) {
            copyObjectValue(vo, target, cls, not_copy, isCopyNull);
            cls = cls.getSuperclass();
        }
    }
    
    /**
     * 
     * @param vo
     * @param target
     * @param copy_property
     */
    public static void copyObjValueForPorperty(Object vo, Object target, String copy_property) {
        for (String property : copy_property.split(",")) {
            try {
                BeanUtils.setProperty(target, property, BeanUtils.getProperty(vo, property));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
