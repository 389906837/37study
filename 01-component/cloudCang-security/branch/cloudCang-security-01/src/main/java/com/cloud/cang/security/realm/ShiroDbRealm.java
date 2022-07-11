package com.cloud.cang.security.realm;

import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.service.SecPurviewService;
import com.cloud.cang.security.service.SecRoleSevice;
import com.cloud.cang.security.service.SecUserService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.security.vo.PurviewVO;
import com.cloud.cang.security.vo.RoleVO;
import com.cloud.cang.utils.SpringContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 自实现用户与权限查询.
 * 
 */
public class ShiroDbRealm extends AuthorizingRealm {
	private String CURRENT_SHIRO_HURBAO_REALM_NAME="37cang_realm_name";
	
	
	public ShiroDbRealm(){
		this.setName(CURRENT_SHIRO_HURBAO_REALM_NAME);
	}
	private static final Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);
	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
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
		String pass= userInfo.getPassword();
		if(token.getLoginForm()!=null && token.getLoginForm().equalsIgnoreCase("y")){
			pass=DigestUtils.sha1Hex(pass);
		}
		return new SimpleAuthenticationInfo(userInfo.getId()+"~~"+userInfo.getUserName(),pass,CURRENT_SHIRO_HURBAO_REALM_NAME);

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
	    
		@SuppressWarnings("unchecked")
		Collection<String> realms = principals.fromRealm(CURRENT_SHIRO_HURBAO_REALM_NAME);
		String principal=realms.iterator().next();
		
		AuthorizationVO auth= SessionUserUtils.getUserFormPrincipal(principal);
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
				principal, CURRENT_SHIRO_HURBAO_REALM_NAME);
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
