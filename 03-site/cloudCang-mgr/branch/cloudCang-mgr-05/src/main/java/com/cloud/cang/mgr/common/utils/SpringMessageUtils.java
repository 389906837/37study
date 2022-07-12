package com.cloud.cang.mgr.common.utils;

import com.cloud.cang.cache.redis.ICached;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class SpringMessageUtils {

    @Autowired
    private ICached iCached;
    private static final Logger logger = LoggerFactory.getLogger(SpringMessageUtils.class);

    /**
     * 获取国际化文件中国际化字段信息
     * @param key 资源key
     * @param args
     * @return 国际化资源
     */
    public String getMessageByKey(String key, Object[] args) {
        // 读取国际化文件
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 国际化文件位置
        messageSource.setBasename("message/language");
        messageSource.setDefaultEncoding("utf-8");
        String result = "";
        try {
            // 获取当前国际化标识
            Locale locale = (Locale) iCached.get("currentLocale");
            // 获取国际化key-value
            result = messageSource.getMessage(key, args, "", locale);
        } catch (Exception e) {
            logger.error("获取国际化资源异常：{}",e);
        }
        return result;
    }
}
