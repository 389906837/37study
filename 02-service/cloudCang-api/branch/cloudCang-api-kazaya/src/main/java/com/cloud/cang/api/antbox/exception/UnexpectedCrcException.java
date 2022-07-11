package com.cloud.cang.api.antbox.exception;

/**
 * CRC不一致异常
 *
 * @author yong.ma
 * @see 0.0.1
 */
public class UnexpectedCrcException extends AntboxProtocolException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UnexpectedCrcException(String message) {
        super(message);

    }

}
