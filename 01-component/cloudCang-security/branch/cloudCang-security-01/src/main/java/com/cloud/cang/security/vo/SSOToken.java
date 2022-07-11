
package com.cloud.cang.security.vo;

import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * 用于存放用户验证的ticket
 * @since 1.0
 */
public class SSOToken implements RememberMeAuthenticationToken {
    private static final long serialVersionUID = 8587329689973009598L;
    
    private String ticket = null;
    
    private String userId = null;
    private boolean isRememberMe = false;
    
    private String host;
    
    public SSOToken(String ticket,String  host) {
        this.ticket = ticket;
        this.host=host;
    }
    
    public Object getPrincipal() {
        return userId;
    }
    
    public Object getCredentials() {
        return ticket;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public boolean isRememberMe() {
        return isRememberMe;
    }
    
    public void setRememberMe(boolean isRememberMe) {
        this.isRememberMe = isRememberMe;
    }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
}
