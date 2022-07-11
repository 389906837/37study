package com.cloud.cang.api.antbox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Antbox配置
 *
 * @author yong.ma
 * @since 0.0.1
 */
public class AntboxConfig {
    public static ExecutorService nettyCommandSenderExecutorService = Executors.newCachedThreadPool();
    public static final int INVENTORY_COUNT = 1;
}
