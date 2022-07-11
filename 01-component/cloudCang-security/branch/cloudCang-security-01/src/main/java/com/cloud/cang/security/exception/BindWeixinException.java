package com.cloud.cang.security.exception;

import org.apache.shiro.authc.AuthenticationException;
/**
 * Apache Shiro 已经绑定微信异常
 * @author  zhouhong
 */
public class BindWeixinException extends AuthenticationException {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -7750158768056272362L;
    
    public BindWeixinException() {
        super();
    }
    
    public BindWeixinException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BindWeixinException(String message) {
        super(message);
    }
    
    public BindWeixinException(Throwable cause) {
        super(cause);
    }
}