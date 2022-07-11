package com.cloud.cang.core.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: 37cang-云平台
 * @description: Server酱  http://sc.ftqq.com/3.version
 * @author: qzg
 * @create: 2019-11-25 16:23
 **/
public class ServerChanUtil {
    private static final Logger logger = LoggerFactory.getLogger(ServerChanUtil.class);
    public static void send(String text,String content){
        try {
            HttpUtil.post("https://sc.ftqq.com/SCU67111T8ac37b04765aa7881b382d489a7a346a5dd9f79e5697f.send",
                    MapUtil .<String,Object>builder()
                            .put("text",text)
                            .put("desp",content)
                            .map());
        } catch (Exception e) {
            logger.error("Server酱发送失败！",e);
        }
    }
}
