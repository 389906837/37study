package com.cloud.cang.security.exception;

import org.apache.shiro.authc.AuthenticationException;
/**
 * 
 * Apache Shiro 验证码验证异常
 * 
 * @author  zhouhong
 */
public class IncorrectCaptchaException extends AuthenticationException {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = -7750158768056272362L;
    
    public IncorrectCaptchaException() {
        super();
    }
    
    public IncorrectCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public IncorrectCaptchaException(String message) {
        super(message);
    }
    
    public IncorrectCaptchaException(Throwable cause) {
        super(cause);
    }
}