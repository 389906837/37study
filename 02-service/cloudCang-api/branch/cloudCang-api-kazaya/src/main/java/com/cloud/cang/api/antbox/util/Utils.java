package com.cloud.cang.api.antbox.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by oyhk on 2017/4/14.
 */
public class Utils {


    /**
     * json 解释器
     */
    public static final ObjectMapper objectMapper = Mappers.objectMapper;

    /**
     * xml 解析器
     */
    public static final XmlMapper xmlMapper = Mappers.xmlMapper;

    /**
     * copy list property.
     */
    public static void copy(List<?> sourceList, String sourceId, String sourceProps, List<?> targetList, String targetIdField, String targetProps) {
        if (targetList == null || targetList.isEmpty() || sourceList == null || sourceList.isEmpty()) return;
        String cks[] = sourceProps.split(",");
        String aks[] = targetProps.split(",");
        //CheckHelper.check(cks.length == aks.length, "source properties:" + sourceProps + ", target properties:" + targetProps + " not equals!");
        for (Object bean : sourceList) {
            BeanWrapper sourceWrapper = new BeanWrapperImpl(bean);
            Object fromId = sourceWrapper.getPropertyValue(sourceId);
            if (fromId == null) continue;
            for (Object b : targetList) {
                BeanWrapper targetWrapper = new BeanWrapperImpl(b);
                Object applyId = targetWrapper.getPropertyValue(targetIdField);
                if (applyId == null) continue;
                if (fromId.toString().equals(applyId.toString())) {
                    for (int i = 0; i < aks.length; i++) {
                        String p = aks[i];
                        Object v = sourceWrapper.getPropertyValue(cks[i]);
                        targetWrapper.setPropertyValue(p, v);
                    }
                }
            }
        }
    }

    /**
     * copy property
     */
    public static void copy(Object source, String sourceProps, Object target, String targetProps) {
        if (source == null || target == null) return;
        String cks[] = sourceProps.split(",");
        String aks[] = targetProps.split(",");
       // CheckHelper.check(cks.length == aks.length, "source properties:" + sourceProps + ", target properties:" + targetProps + " not equals!");
        BeanWrapper sourceWrapper = new BeanWrapperImpl(source);
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);
        for (int i = 0; i < aks.length; i++) {
            String p = aks[i];
            Object v = sourceWrapper.getPropertyValue(cks[i]);
            targetWrapper.setPropertyValue(p, v);
        }
    }

    /**
     * copy property
     */
    public static void copy(Object source, String sourceProps, Object target) {
        copy(source, sourceProps, target, sourceProps);
    }

    /**
     * copy property as class
     */
    public static <T> T copy(Object source, String sourceProps, Class<T> classType) {
        return copy(source, sourceProps, classType, sourceProps);
    }


    /**
     * copy property as class
     */
    public static <T> T copy(Object source, String sourceProps, Class<T> classType, String targetProps) {
        try {
            T target = classType.newInstance();
            copy(source, sourceProps, target, targetProps);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void set(Object target, String targetProps, Object values) {
        if (target == null || values == null) return;
        String cks[] = targetProps.split(",");
        BeanWrapper targetWrapper = new BeanWrapperImpl(target);
        for (int i = 0; i < cks.length; i++) {
            String p = cks[i];
            if (targetWrapper.isReadableProperty(p)) {
                targetWrapper.setPropertyValue(p, values);
            }
        }
    }

    /**
     * null转换.
     */
    public static <X> X nvl(X o, X overrideNullValue) {
        if (o == null) {
            return (X) overrideNullValue;
        } else {
            return (X) o;
        }
    }

    /**
     * null转换.
     */
    public static String nvlS(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    /**
     * null转换.
     */
    public static Long nvlL(Long l) {
        if (l == null) {
            return 0L;
        } else {
            return l;
        }
    }

    /**
     * null转换.
     */
    public static Integer nvlI(Integer i) {
        if (i == null) {
            return 0;
        } else {
            return i;
        }
    }

    /**
     * create random uuid
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 读取图像数据
     *
     * @param imageUrl 图像URL
     * @return 图像文件字节数组
     */
    public static byte[] readImageFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try (InputStream inputStream = url.openStream()) {
                int n = 0;
                byte[] buf = new byte[1024];
                while (-1 != (n = inputStream.read(buf))) {
                    outputStream.write(buf, 0, n);
                }
            }
            return outputStream.toByteArray();
        }
    }

    /**
     * 获取日期毫秒数
     *
     * @param date
     * @return
     */
    public static Long getMillis(Date date) {
        return date == null ? 0L : date.getTime();
    }
}
