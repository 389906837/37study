package com.cang.os.security.filter;

import java.io.Serializable;
import java.util.Deque;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * @author Administrator
 * 
 */
public class ConcurrentLoginControlFilter extends AccessControlFilter {

	private String kickoutUrl; // 踢出后地址
	private boolean kickoutAfter = false; // 默认踢出之前登录的用户
	private int maxSession = 1; // 同一个帐号最大会话数 默认1

	static String KEY_PRE_USERNAME="tzb-concurrent-";
	private SessionManager sessionManager;
	private Cache<String, Deque<Serializable>> cache;
	private AbstractSessionDAO sessionDAO;
	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("concurrent-login:");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax
	 * .servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.shiro.web.filter.AccessControlFilter#onAccessDenied(javax.
	 * servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		return true;
	}

	private String getCacheKey(String userName){
	    return KEY_PRE_USERNAME+userName;
	}

    public AbstractSessionDAO getSessionDAO() {
        return sessionDAO;
    }

    public void setSessionDAO(AbstractSessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }
}
