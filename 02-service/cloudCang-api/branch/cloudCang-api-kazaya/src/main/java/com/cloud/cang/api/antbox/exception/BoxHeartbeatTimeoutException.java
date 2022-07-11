package com.cloud.cang.api.antbox.exception;

/**
 * 售货机上一次心跳距离现在超出最大间隔时间时，抛出此异常
 *
 * @Author chipun.cheng
 * @Date 2017年3月19日 上午11:43:03
 */
public class BoxHeartbeatTimeoutException extends AntboxServerException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BoxHeartbeatTimeoutException() {
        super("Device heartbeat timeout.");
    }

}
