package com.cloud.cang.open.sdk.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @version 1.0
 * @Description: 解析目标
 * @Author: zhouhong
 * @Date: 2018/9/3 16:26
 */
public class BaseFieldMethod {

    private Field field;
    private Method method;

    public BaseFieldMethod() {
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
