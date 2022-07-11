package com.cang.os.security.listener;

import java.util.Collection;

import com.cang.os.security.core.CaptchaUsernamePasswordToken;
import com.cang.os.security.vo.AuthorizationVO;
import com.cang.os.security.service.SecUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cang.os.common.utils.SpringContext;
import com.cang.os.security.utils.SessionUserUtils;

/**
 * 用户登录监听器，实现shiro的AuthenticationListener接口
 * @author jili
 *
 */
public class LoginListener implements AuthenticationListener {
	private static final Logger log = LoggerFactory
			.getLogger(LoginListener.class);

	@Override
	public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
		CaptchaUsernamePasswordToken authcToken = (CaptchaUsernamePasswordToken) token;
		@SuppressWarnings("unchecked")
		Collection<String> realms = info.getPrincipals().asList();
		if (realms != null && !realms.isEmpty()) {
			String  principal = (String) realms.iterator().next();
			AuthorizationVO auth=SessionUserUtils.getUserFormPrincipal(principal);
			AuthorizationVO authDtl=SpringContext
		            .getBean(SecUserService.class)
		            .getUserDetail(auth.getId());
            if(authDtl==null){
                SessionUserUtils.setSessionAttributeForUserDtl(auth);//把用户详细信息存放到Session中
            }else{
                SessionUserUtils.setSessionAttributeForUserDtl(authDtl);//把用户详细信息存放到Session中
            }
			SpringContext.getBean(SecUserService.class).loginSuccess(
					auth.getId(), authcToken.getHost());//调用 登录成功处理函数
			
			
			log.info(auth.getUserName() + " 登录成功 " + authcToken.getHost());
		}
	}

	@Override
	public void onFailure(AuthenticationToken token, AuthenticationException ae) {
		try {
			CaptchaUsernamePasswordToken authcToken = (CaptchaUsernamePasswordToken) token;
			SpringContext.getBean(SecUserService.class).loginError(
					authcToken.getUsername(), authcToken.getHost());
			log.error("用户名：{}本次登录失败", authcToken.getUsername());
		} catch (Exception e) {
			log.error("",e);
		}
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
	    Collection<String> realms = principals.asList();
        if (realms != null && !realms.isEmpty()) {
            String  principal = (String) realms.iterator().next();
            AuthorizationVO auth=SessionUserUtils.getUserFormPrincipal(principal);
            SpringContext.getBean(SecUserService.class).logout(auth.getId());
        }
           
	}

}
