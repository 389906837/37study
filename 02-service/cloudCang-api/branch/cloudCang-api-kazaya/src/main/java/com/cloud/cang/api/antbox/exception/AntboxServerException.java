package com.cloud.cang.api.antbox.exception;

/**
 * 服务异常基类
 *
 * @Author chipun.cheng
 * @Date 2017年3月25日 下午10:43:06
 */
public class AntboxServerException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AntboxServerException(String message, Throwable cause) {
        super(message, cause);

    }

    public AntboxServerException(String message) {
        super(message);

    }

    public AntboxServerException(Throwable cause) {
        super(cause);

    }

}
