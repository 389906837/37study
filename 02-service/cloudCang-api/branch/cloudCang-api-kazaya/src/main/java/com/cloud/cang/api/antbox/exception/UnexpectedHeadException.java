package com.cloud.cang.api.antbox.exception;

/**
 * CRC不一致异常
 *
 * @author yong.ma
 * @see 0.0.1
 */
public class UnexpectedHeadException extends AntboxProtocolException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UnexpectedHeadException(String message) {
        super(message);

    }

}
