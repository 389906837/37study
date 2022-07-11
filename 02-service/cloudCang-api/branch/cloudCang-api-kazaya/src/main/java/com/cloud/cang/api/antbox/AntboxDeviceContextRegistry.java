package com.cloud.cang.api.antbox;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 售货机连接的上下文注册中心
 *
 * @Author chipun.cheng
 * @Date 2017年3月19日 下午5:15:44
 */
public class AntboxDeviceContextRegistry {

    private static Map<Long, BoxContext> dict = new ConcurrentHashMap<>();

    /**
     * 注册售货机连接的上下文
     *
     * @param boxSn 售货机唯一标识
     * @param ctx   连接的上下文
     */
    public static void register(long boxSn, BoxContext ctx) {
        dict.put(boxSn, ctx);
    }

    /**
     * 根据售货机唯一标识获取连接的上下文
     *
     * @param boxSn 售货机唯一标识
     * @return
     */
    public static BoxContext lookup(long boxSn) {
        return dict.get(boxSn);
    }
}
