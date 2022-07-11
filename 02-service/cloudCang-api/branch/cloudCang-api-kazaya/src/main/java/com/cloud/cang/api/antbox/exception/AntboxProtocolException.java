package com.cloud.cang.api.antbox.exception;

/**
 * 通讯层异常基类
 *
 * @Author chipun.cheng
 * @Date 2017年3月25日 下午10:42:28
 */
public class AntboxProtocolException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AntboxProtocolException(String message, Throwable cause) {
        super(message, cause);

    }

    public AntboxProtocolException(String message) {
        super(message);

    }

    public AntboxProtocolException(Throwable cause) {
        super(cause);

    }

}
