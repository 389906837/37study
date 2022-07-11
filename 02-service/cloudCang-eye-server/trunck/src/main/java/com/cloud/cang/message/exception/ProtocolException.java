package com.cloud.cang.message.exception;

import com.cloud.cang.exception.UserException;

/**
 * 协议异常
 * @author zhouhong
 * @version 1.0
 */
public class ProtocolException extends UserException {

    public ProtocolException(String msg) {
	super(msg);
    }

    private static final long serialVersionUID = -4397087977741336814L;

}
