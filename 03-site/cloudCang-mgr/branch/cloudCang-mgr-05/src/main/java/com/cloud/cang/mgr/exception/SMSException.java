package com.cloud.cang.mgr.exception;


import com.cloud.cang.exception.UserException;

/**
 * SMS send Error
 * @author zhouhong
 * @version 1.0
 */
public class SMSException extends UserException {
    
    private static final long serialVersionUID = 8339246342012328024L;

    public SMSException(String msg) {
	super(msg);
    }
    
    public SMSException(Throwable e) {
	super(e);
    }
   
}
