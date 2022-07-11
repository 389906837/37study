package com.cloud.cang.security.listener;

import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.service.SecUserService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
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
 * 用户登录监听器，实现shiro的AuthenticationListener接口
 * @author zhouhong
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
			String  principal = realms.iterator().next();
			AuthorizationVO auth= SessionUserUtils.getUserFormPrincipal(principal);
			AuthorizationVO authDtl= SpringContext
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
			log.error(e.getMessage());
		}
	}

	@Override
	public void onLogout(PrincipalCollection principals) {
	    Collection<String> realms = principals.asList();
        if (realms != null && !realms.isEmpty()) {
            String  principal = realms.iterator().next();
            AuthorizationVO auth=SessionUserUtils.getUserFormPrincipal(principal);
            SpringContext.getBean(SecUserService.class).logout(auth.getId());
        }
           
	}

}
