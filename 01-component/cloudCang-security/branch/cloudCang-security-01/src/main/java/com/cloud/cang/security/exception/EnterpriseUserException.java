/**
 * 
 */
package com.cloud.cang.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author zhouhong
 *
 */
public class EnterpriseUserException  extends AuthenticationException{

	private static final long serialVersionUID = 1255512853928720687L;

		public EnterpriseUserException() {
	        super();
	    }
	    
	    public EnterpriseUserException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    
	    public EnterpriseUserException(String message) {
	        super(message);
	    }
	    
	    public EnterpriseUserException(Throwable cause) {
	        super(cause);
	    }
}
