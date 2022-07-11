package com.cloud.cang.api.common.utils;

import java.util.UUID;

/**
 * Created by Alex on 2018/1/26.
 */
public class UuidUtils {
    //生成32位唯一UUID
    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
