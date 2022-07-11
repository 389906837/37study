package com.cloud.cang.api.antbox.exception;

/**
 * 根据deviceId找不到上下文时，抛出此异常
 *
 * @Author chipun.cheng
 * @Date 2017年3月19日 上午11:42:08
 */
public class BoxNotFoundException extends AntboxServerException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BoxNotFoundException(long boxId) {
        super("Device[" + boxId + "] not found.");
    }

}
