package com.cloud.cang.open.sdk.mapping;

import com.cloud.cang.open.sdk.exception.CloudCangException;

import java.util.List;

/**
 * @version 1.0
 * @Description: 自定义解析类
 * @Author: zhouhong
 * @Date: 2018/9/3 16:33
 */
public interface Reader {
    boolean hasReturnField(Object var1);

    Object getPrimitiveObject(Object var1);

    Object getObject(Object var1, Class<?> var2) throws CloudCangException;

    List<?> getListObjects(Object var1, Object var2, Class<?> var3) throws CloudCangException;
}
