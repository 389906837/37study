/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月18日
 * Description:SSOLoginListener.java 
 */
package com.cloud.cang.security.listener;

import com.cloud.cang.security.service.SSOSecUserService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.security.vo.SSOToken;
import com.cloud.cang.utils.SpringContext;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;



/**
 * @author zhouhong
 * @version 1.0
 */
public class SSOLoginListener implements AuthenticationListener{
	
	private static final Logger log = LoggerFactory
			.getLogger(SSOLoginListener.class);

	@Override
	public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
		SSOToken ssoToken = (SSOToken) token;
		@SuppressWarnings("unchecked")
		Collection<String> realms = info.getPrincipals().asList();
		if (realms != null && !realms.isEmpty()) {
			String  principal = realms.iterator().next();
			AuthorizationVO auth= SessionUserUtils.getUserFormPrincipal(principal);
			SpringContext.getBean(SSOSecUserService.class).loginSuccess(
					auth.getId(), ssoToken.getHost());//调用 登录成功处理函数
			log.info(auth.getUserName() + " 登录成功 " + ssoToken.getHost());
		}
	}

	@Override
	public void onFailure(AuthenticationToken token, AuthenticationException ae) {
		
		try {
			SSOToken ssoToken = (SSOToken) token;
			SpringContext.getBean(SSOSecUserService.class).loginError(
					ssoToken.getPrincipal().toString(),ssoToken.getHost());
			log.error("用户名：{}本次登录失败", ssoToken.getPrincipal());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}

	@Override
	public void onLogout(PrincipalCollection principals) {

	    Collection<String> realms = principals.asList();
        if (realms != null && !realms.isEmpty()) {
            String  principal = realms.iterator().next();
            AuthorizationVO auth=SessionUserUtils.getUserFormPrincipal(principal);
            SpringContext.getBean(SSOSecUserService.class).logout(auth.getId());
        }
           
	
		
	}

}
