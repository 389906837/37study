package com.cloud.cang.security.service;

import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.vo.AuthorizationVO;

/**
 * 用户管理接口
 * @author zhouhong
 *
 */
public interface SecUserService {

	AuthorizationVO getUserByUserName(CaptchaUsernamePasswordToken loginObj);
	
	/**
	 * 登录成功后，登录监听会回调该方法，取得详细的用户数据并存放在Session中
	 * @param userId
	 * @return
	 */
    AuthorizationVO getUserDetail(String userId);
	
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
