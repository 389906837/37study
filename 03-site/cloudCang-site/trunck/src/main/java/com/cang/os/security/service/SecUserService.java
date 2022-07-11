package com.cang.os.security.service;

import com.cang.os.security.core.CaptchaUsernamePasswordToken;
import com.cang.os.security.vo.AuthorizationVO;

/**
 * 用户管理接口
 * @author jili
 *
 */
public interface SecUserService {

	public AuthorizationVO getUserByUserName(CaptchaUsernamePasswordToken loginObj);
	
	/**
	 * 登录成功后，登录监听会回调该方法，取得详细的用户数据并存放在Session中
	 * @param userId
	 * @return
	 */
	public AuthorizationVO getUserDetail(String userId);
	
	public void loginSuccess(String userId,String host);
	
	/**
	 * 登录出错时调用，
	 * @param userName 登录时输入的手机号或用户名
	 * @param host
	 */
	public void loginError(String userName,String host);
	
	/**
	 * 
	 * @param userId
	 */
	public void logout(String userId);
}
