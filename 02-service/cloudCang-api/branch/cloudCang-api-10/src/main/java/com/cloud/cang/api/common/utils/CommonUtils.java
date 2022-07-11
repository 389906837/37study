package com.cloud.cang.api.common.utils;

import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.NettyConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alex on 2018/3/16.
 */
public class CommonUtils {
    /**
     * str字符串切割成List
     * @param str 客户端ip字符串，拼接方式为str+","+str
     * @return str按照“，”分割后的list集合
     */
    public static List<String> stringToList(String str) {
        if (StringUtils.isNotBlank(str)) {
            String[] array = str.split(",");
            List<String> list = Arrays.asList(array);
            return list;
        }
        return null;
    }

    /**
     * 异步修改设备门状态信息
     *
     * @param deviceId 设备ID
     * @param iCached  redis缓存
     * @param logger   日志工具类
     */
    public static void asynChangeDoorStatusToClose(final String deviceId, final ICached iCached, final Logger logger) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);
                    if (clientVo != null) {
                        Integer doorStatus = clientVo.getDoor();     // 门的状态 10关 20开
                        if (doorStatus.equals(20)) {
                            iCached.hremove(NettyConst.SYN_CLIENT_MAP, deviceId);   // 移除老的
                            clientVo.setDoor(10);
                            iCached.hset(NettyConst.SYN_CLIENT_MAP, deviceId, clientVo); // 加入新的
                        }
                    }
                } catch (Exception e) {
                    logger.error("修改设备状态信息出现异常：{}", e);
                }
            }
        });
    }
}
