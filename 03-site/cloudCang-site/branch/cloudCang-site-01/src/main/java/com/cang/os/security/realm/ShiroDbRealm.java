package com.cang.os.security.realm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cang.os.security.core.CaptchaUsernamePasswordToken;
import com.cang.os.security.vo.AuthorizationVO;
import com.cang.os.security.service.SecPurviewService;
import com.cang.os.security.service.SecUserService;
import com.cang.os.security.vo.RoleVO;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.
		SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cang.os.common.utils.SpringContext;
import com.cang.os.security.service.SecRoleSevice;
import com.cang.os.security.utils.SessionUserUtils;
import com.cang.os.security.vo.PurviewVO;

/**
 * 自实现用户与权限查询.
 * 
 */
public class ShiroDbRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		log.info("账号：{}请求登录验证，来源IP:{}",token.getUsername(),token.getHost());
		AuthorizationVO userInfo = SpringContext.getBean(SecUserService.class).getUserByUserName(token);
		if (userInfo == null)
			return null;
		if(userInfo.isFreeze()){
			throw new LockedAccountException("用户账号已经被锁定");
		}
		String principal=userInfo.getId()+"~~"+userInfo.getUserName();
		clearCachedAuthorizationInfo(principal);
		// AuthorizationVO
		return new SimpleAuthenticationInfo(userInfo.getId()+"~~"+userInfo.getUserName(), userInfo.getPassword(),
				getName());

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
	    
		@SuppressWarnings("unchecked")
		Collection<String> realms = principals.fromRealm(getName());
		String principal=realms.iterator().next();
		
		AuthorizationVO auth=SessionUserUtils.getUserFormPrincipal(principal);
		log.debug("{}用户进行授权",auth.getUserName());
		List<String> roleList = new ArrayList<String>();
		List<String> permissionList = new ArrayList<String>();
		List<RoleVO> _roleList = new ArrayList<RoleVO>();
		_roleList = SpringContext.getBean(SecRoleSevice.class).queryRoleByUserId(auth.getId());
		// 获取用户角色权限
		if (_roleList != null && _roleList.size() > 0) {
			for (RoleVO role : _roleList) {
				roleList.add(role.getRoleCode());
			}
		} 
		List<PurviewVO> _purviewList = new ArrayList<PurviewVO>();
		_purviewList = SpringContext.getBean(SecPurviewService.class).queryByUserId(auth.getId());
		if(_purviewList!=null)
			for (PurviewVO purview :_purviewList) {
				permissionList.add(purview.getPurviewCode());
			}
		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRoles(roleList);
		simpleAuthorInfo.addStringPermissions(permissionList);
		return simpleAuthorInfo;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

}
