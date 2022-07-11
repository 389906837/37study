package com.cloud.cang.security.filter;



import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.utils.URLUtils;
import com.cloud.cang.session.SessionRepository;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author zhouhong
 * 
 */
public class ConcurrentLoginControlFilter extends AccessControlFilter {

	private String kickoutUrl; // 踢出后地址
	private boolean kickoutAfter = false; // 默认踢出之前登录的用户
	private int maxSession = 1; // 同一个帐号最大会话数 默认1

	static String KEY_PRE_USERNAME="tzb-concurrent-";
	private Cache<String, Deque<Serializable>> cache;
	private SessionRepository<com.cloud.cang.session.Session> sessionRepository;
	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
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
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}

		Session session = subject.getSession();
		String username = SessionUserUtils.getSessionUserName();
		Serializable sessionId = session.getId();
		Deque<Serializable> deque = cache.get(getCacheKey(username));
		if (deque == null) {
			deque = new ConcurrentLinkedDeque<Serializable>();
		}
		boolean isChangeCache=false;
		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId)) {
			deque.addLast(sessionId);
			isChangeCache=true;//this.cache.put(getCacheKey(username), deque);
		}
		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId = null;
			if (!kickoutAfter) { // 如果踢出前者
				kickoutSessionId = deque.removeFirst();
				isChangeCache=true;
			} else { // 否则踢出后者
				kickoutSessionId = deque.removeLast();
				isChangeCache=true;
			}
			try {
				com.cloud.cang.session.Session orlSession=sessionRepository.getSession((String)kickoutSessionId);
				orlSession.setAttribute("kickout", "out");
				sessionRepository.save(orlSession);
			} catch (Exception e) {
			}
			
		}
		if(isChangeCache){
			this.cache.put(getCacheKey(username), deque);
		}
		// 如果被踢出了，直接退出，重定向到踢出后的地址
		if (session.getAttribute("kickout") != null) {
			try {
				subject.logout();
				session.setAttribute("kickout",null);
			} catch (Exception e) {
			}
			saveRequest(request);
			//WebUtils.issueRedirect(request, response, kickoutUrl);
			URLUtils.saveRequestAndRedirectToLogin(request, response, kickoutUrl);
			return false;
		}
		return true;
	}

	private String getCacheKey(String userName){
	    return KEY_PRE_USERNAME+userName;
	}

	public SessionRepository<com.cloud.cang.session.Session> getSessionRepository() {
		return sessionRepository;
	}
	public void setCacheManager(CacheManager cacheManager) {
		this.cache = cacheManager.getCache("concurrent-login:");
	}
	public void setSessionRepository(SessionRepository<com.cloud.cang.session.Session> sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

}
