package com.cloud.cang.open.sdk.mapping;

import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.response.BaseResponse;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version 1.0
 * @Description: 自定义解析器
 * @Author: zhouhong
 * @Date: 2018/9/3 16:24
 */
public class Converters {
    
    public static boolean isCheckJsonType = false;
    private static final Set<String> baseFields = new HashSet();
    private static final Set<String> excludeFields = new HashSet();
    private static final Set<String> overideFields = new HashSet();

    private Converters() {
    }

    public static <T> T convert(Class<T> clazz, Reader reader) throws CloudCangException {
        Object rsp = null;

        try {
            rsp = clazz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            boolean isResponseClazz = BaseResponse.class.isAssignableFrom(clazz);
            PropertyDescriptor[] var6 = pds;
            int var7 = pds.length;

            label189:
            for(int var8 = 0; var8 < var7; ++var8) {
                PropertyDescriptor pd = var6[var8];
                Method writeMethod = pd.getWriteMethod();
                if(writeMethod != null) {
                    String itemName = pd.getName();
                    String listName = null;
                    if(!isResponseClazz || !excludeFields.contains(itemName)) {
                        List<BaseFieldMethod> baseFieldMethods = new ArrayList();
                        Field field;
                        BaseFieldMethod baseFieldMethod;
                        if(baseFields.contains(itemName) && isResponseClazz) {
                            field = BaseResponse.class.getDeclaredField(itemName);
                            baseFieldMethod = new BaseFieldMethod();
                            baseFieldMethod.setField(field);
                            if(writeMethod.getDeclaringClass().getName().contains("BaseResponse")) {
                                baseFieldMethod.setMethod(writeMethod);
                            } else {
                                writeMethod = tryGetSetMethod(BaseResponse.class, field, writeMethod.getName());
                                if(writeMethod == null) {
                                    continue;
                                }

                                baseFieldMethod.setMethod(writeMethod);
                            }

                            baseFieldMethods.add(baseFieldMethod);
                            if(overideFields.contains(itemName)) {
                                field = tryGetFieldWithoutExp(clazz, itemName);
                                if(field != null) {
                                    writeMethod = tryGetSetMethod(clazz, field, writeMethod.getName());
                                    if(writeMethod == null) {
                                        continue;
                                    }

                                    baseFieldMethod = new BaseFieldMethod();
                                    baseFieldMethod.setField(field);
                                    baseFieldMethod.setMethod(writeMethod);
                                    baseFieldMethods.add(baseFieldMethod);
                                }
                            }
                        } else {
                            field = clazz.getDeclaredField(itemName);
                            baseFieldMethod = new BaseFieldMethod();
                            baseFieldMethod.setField(field);
                            baseFieldMethod.setMethod(writeMethod);
                            baseFieldMethods.add(baseFieldMethod);
                        }

                        Iterator var27 = baseFieldMethods.iterator();

                        while(true) {
                            Method method;
                            do {
                                if(!var27.hasNext()) {
                                    continue label189;
                                }

                                baseFieldMethod = (BaseFieldMethod)var27.next();
                                field = baseFieldMethod.getField();
                                method = baseFieldMethod.getMethod();
                                BaseField jsonField = (BaseField)field.getAnnotation(BaseField.class);
                                if(jsonField != null) {
                                    itemName = jsonField.value();
                                }

                                BaseListField jsonListField = (BaseListField)field.getAnnotation(BaseListField.class);
                                if(jsonListField != null) {
                                    listName = jsonListField.value();
                                }
                            } while(!reader.hasReturnField(itemName) && (listName == null || !reader.hasReturnField(listName)));

                            Class<?> typeClass = field.getType();
                            Object obj;
                            if(String.class.isAssignableFrom(typeClass)) {
                                obj = reader.getPrimitiveObject(itemName);
                                if(obj instanceof String) {
                                    method.invoke(rsp, new Object[]{obj.toString()});
                                } else {
                                    if(isCheckJsonType && obj != null) {
                                        throw new CloudCangException(itemName + " is not a String");
                                    }

                                    if(obj != null) {
                                        method.invoke(rsp, new Object[]{obj.toString()});
                                    } else {
                                        method.invoke(rsp, new Object[]{""});
                                    }
                                }
                            } else if(Long.class.isAssignableFrom(typeClass)) {
                                obj = reader.getPrimitiveObject(itemName);
                                if(obj instanceof Long) {
                                    method.invoke(rsp, new Object[]{(Long)obj});
                                } else {
                                    if(isCheckJsonType && obj != null) {
                                        throw new CloudCangException(itemName + " is not a Number(Long)");
                                    }

                                    if(StringUtils.isNumeric(obj)) {
                                        method.invoke(rsp, new Object[]{Long.valueOf(obj.toString())});
                                    }
                                }
                            } else if(Integer.class.isAssignableFrom(typeClass)) {
                                obj = reader.getPrimitiveObject(itemName);
                                if(obj instanceof Integer) {
                                    method.invoke(rsp, new Object[]{(Integer)obj});
                                } else {
                                    if(isCheckJsonType && obj != null) {
                                        throw new CloudCangException(itemName + " is not a Number(Integer)");
                                    }

                                    if(StringUtils.isNumeric(obj)) {
                                        method.invoke(rsp, new Object[]{Integer.valueOf(obj.toString())});
                                    }
                                }
                            } else if(Boolean.class.isAssignableFrom(typeClass)) {
                                obj = reader.getPrimitiveObject(itemName);
                                if(obj instanceof Boolean) {
                                    method.invoke(rsp, new Object[]{(Boolean)obj});
                                } else {
                                    if(isCheckJsonType && obj != null) {
                                        throw new CloudCangException(itemName + " is not a Boolean");
                                    }

                                    if(obj != null) {
                                        method.invoke(rsp, new Object[]{Boolean.valueOf(obj.toString())});
                                    }
                                }
                            } else if(Double.class.isAssignableFrom(typeClass)) {
                                obj = reader.getPrimitiveObject(itemName);
                                if(obj instanceof Double) {
                                    method.invoke(rsp, new Object[]{(Double)obj});
                                } else if(isCheckJsonType && obj != null) {
                                    throw new CloudCangException(itemName + " is not a Double");
                                }
                            } else if(Number.class.isAssignableFrom(typeClass)) {
                                obj = reader.getPrimitiveObject(itemName);
                                if(obj instanceof Number) {
                                    method.invoke(rsp, new Object[]{(Number)obj});
                                } else if(isCheckJsonType && obj != null) {
                                    throw new CloudCangException(itemName + " is not a Number");
                                }
                            } else if(Date.class.isAssignableFrom(typeClass)) {
                                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                                Object value = reader.getPrimitiveObject(itemName);
                                if(value instanceof String) {
                                    method.invoke(rsp, new Object[]{format.parse(value.toString())});
                                }
                            } else if(List.class.isAssignableFrom(typeClass)) {
                                Type fieldType = field.getGenericType();
                                if(fieldType instanceof ParameterizedType) {
                                    ParameterizedType paramType = (ParameterizedType)fieldType;
                                    Type[] genericTypes = paramType.getActualTypeArguments();
                                    if(genericTypes != null && genericTypes.length > 0 && genericTypes[0] instanceof Class) {
                                        Class<?> subType = (Class)genericTypes[0];
                                        List<?> listObjs = reader.getListObjects(listName, itemName, subType);
                                        if(listObjs != null) {
                                            method.invoke(rsp, new Object[]{listObjs});
                                        }
                                    }
                                }
                            } else {
                                obj = reader.getObject(itemName, typeClass);
                                if(obj != null) {
                                    method.invoke(rsp, new Object[]{obj});
                                }
                            }
                        }
                    }
                }
            }

            return (T) rsp;
        } catch (Exception var26) {
            throw new CloudCangException(var26);
        }
    }

    private static Field tryGetFieldWithoutExp(Class<?> clazz, String itemName) {
        try {
            return clazz.getDeclaredField(itemName);
        } catch (Exception var3) {
            return null;
        }
    }

    private static <T> Method tryGetSetMethod(Class<T> clazz, Field field, String methodName) {
        try {
            return clazz.getDeclaredMethod(methodName, new Class[]{field.getType()});
        } catch (Exception var4) {
            return null;
        }
    }

    static {
        baseFields.add("code");
        baseFields.add("msg");
        baseFields.add("subCode");
        baseFields.add("subMsg");
        baseFields.add("body");
        baseFields.add("params");
        baseFields.add("success");
        excludeFields.add("errorCode");
        overideFields.add("code");
        overideFields.add("msg");
    }
}
