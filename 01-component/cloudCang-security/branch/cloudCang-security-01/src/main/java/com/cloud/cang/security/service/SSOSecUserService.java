/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月18日
 * Description:SSOSecUserService.java 
 */
package com.cloud.cang.security.service;

/**
 * @author zhouhong
 * @version 1.0
 */
public interface SSOSecUserService {
	
	void loginSuccess(String userId, String host);
	
	/**
	 * 登录出错时调用，
	 * @param userName 登录时输入的手机号或用户名
	 * @param host
	 */
    void loginError(String userName, String host);
	
	/**
	 * 
	 * @param userId
	 */
    void logout(String userId);

}
