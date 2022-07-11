package com.cang.os.security.core;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6386649400031487356L;
	private String captcha;
	private String loginForm;//登录来源，默认为为页面登录

	// 省略 getter 和 setter 方法

	public CaptchaUsernamePasswordToken(String username, String password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

    public String getLoginForm() {
        return loginForm;
    }

    /**
     * 为Y是，不用密码实现 登录，为其它时用MD5后的密码
     * 
     * @param loginForm
     */
    public void setLoginForm(String loginForm) {
        this.loginForm = loginForm;
    }
}