package com.cloud.cang.mgr.common.utils;

import com.cloud.cang.utils.SpringContext;

public class MessageSourceUtils {

    /**
     * 获取国际化文件中国际化字段信息
     * @param key 资源key
     * @param args
     * @return 国际化资源
     */
    public static String getMessageByKey(String key, Object[] args) {
        SpringMessageUtils utils = SpringContext.getBean(SpringMessageUtils.class);
        return utils.getMessageByKey(key, args);
    }

    /**
     * 获取国际化文件中国际化字段信息
     * @param key 资源key
     * @return 国际化资源
     */
    public static String getMessageByKey(String key) {
        SpringMessageUtils utils = SpringContext.getBean(SpringMessageUtils.class);
        return utils.getMessageByKey(key, null);
    }
}
