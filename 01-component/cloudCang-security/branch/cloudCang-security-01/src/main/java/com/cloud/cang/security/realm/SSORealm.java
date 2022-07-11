package com.cloud.cang.security.realm;

import com.cloud.cang.security.service.SecPurviewService;
import com.cloud.cang.security.service.SecRoleSevice;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.*;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * 自实现用户与权限查询.
 * 
 */
public class SSORealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(SSORealm.class);
	private static RestTemplate restRequest=new RestTemplate();
	private String CURRENT_SHIRO_HURBAO_REALM_NAME="37cang_realm_name";
	
	
	public SSORealm(){
		setAuthenticationTokenClass(SSOToken.class);
		super.setCredentialsMatcher(new AllowAllCredentialsMatcher());
		this.setName(CURRENT_SHIRO_HURBAO_REALM_NAME);
	}
	
	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		SSOToken token = (SSOToken) authcToken;
		if (authcToken == null)
			return null;
		log.info("TICKET：{}请求登录验证，来源IP:{}", token.getCredentials(),
				token.getHost());
		String ticket = (String) token.getCredentials();
		if (StringUtils.isEmpty(ticket)) {
			return null;
		}
		ResponseEntity<AuthenticationResult> result = restRequest.getForEntity(
				EvnUtils.getValue("sso.server.chechUrl")+"?tokenId="+ticket, AuthenticationResult.class);
		if (result == null)
			return null;
		log.debug(result.toString());
		AuthenticationResult authResult = result.getBody();
		if (!authResult.getErrorCode().equals("200")) {
			return null;
		}

		Principal principal = authResult.getPrincipal();
		// AuthorizationVO userInfo =
		// SpringContext.getBean(SecUserService.class).getUserByUserName(token);
		String principalKey = principal.getId() + "~~"
				+ principal.getUserName();
		clearCachedAuthorizationInfo(principalKey);
		// AuthorizationVO
		PrincipalCollection principalCollection = new SimplePrincipalCollection(
				principalKey, CURRENT_SHIRO_HURBAO_REALM_NAME);
		AuthorizationVO vo=new AuthorizationVO();
		vo.setAuth(principal.getIsOpenAccountAuth() != 0);
		vo.setCode(principal.getUserCode());
		vo.setEmail(principal.getEmail());
		vo.setFreeze(false);
		vo.setId(principal.getId());
		vo.setLastLoginTime(principal.getLastLoginTime());
		vo.setMemberType(principal.getMemberType());
		vo.setMobile(principal.getMobile());
		vo.setRealName(principal.getRealName());
		vo.setSex(principal.getSex());
		vo.setUserName(principal.getUserName());
		vo.setRegIp(principal.getId());
		vo.setLastLoginIp(principal.getLastLoginIp());
		SessionUserUtils.setSessionAttributeForUserDtl(vo);//把用户详细信息存放到Session中
		return new SimpleAuthenticationInfo(principalCollection, CURRENT_SHIRO_HURBAO_REALM_NAME);

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	    
		@SuppressWarnings("unchecked")
		Collection<String> realms = principals.fromRealm(CURRENT_SHIRO_HURBAO_REALM_NAME);
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
