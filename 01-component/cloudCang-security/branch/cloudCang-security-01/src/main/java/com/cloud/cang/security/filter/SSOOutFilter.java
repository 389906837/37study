package com.cloud.cang.security.filter;


import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.session.Session;
import com.cloud.cang.session.SessionRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 拦截所有的用户请求，如果存在ticket进行sso验证
 * 
 * @author zhouhong
 * @version 1.0
 */
public class SSOOutFilter extends OncePerRequestFilter {

	private static Logger LOGGER = LoggerFactory.getLogger(SSOOutFilter.class);

	private static String SESSION_KEY_USER_ID_SESSION_ID = "prefix_sso_user_session_";
	public static String REQUEST_USER_ID = "userId";

	@Autowired
	private ICached cacheUtils;
	private SessionRepository<Session> sessionRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 拦截所有请有判断有tokenId
		String userId = request.getParameter(REQUEST_USER_ID);
		if (StringUtils.isNotBlank(userId)) {
			LOGGER.info("用户：{} 请求登出  来源IP:{}", userId,
					SessionUserUtils.getIpAddr(request));
			try {
				String sessionId = (String) cacheUtils
						.get(SESSION_KEY_USER_ID_SESSION_ID + userId);
				sessionRepository.delete(sessionId);
			} catch (Exception e) {
				LOGGER.error("", e);
			}
			writeToResponse(response, "{\"message\":\"true\"}");
		} else {
			writeToResponse(response, "{\"message\":\"登出参数不一致\"}");
		}
		// 判断session是否存在
		// 判断用户id是否一致
		// 调用shiro处理
	}

	private void writeToResponse(HttpServletResponse response, String msg) {
		try {
			ServletOutputStream output = response.getOutputStream();
			output.write(msg.getBytes());
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SessionRepository<Session> getSessionRepository() {
		return sessionRepository;
	}

	public void setSessionRepository(
			SessionRepository<Session> sessionRepository) {
		this.sessionRepository = sessionRepository;
	}
}
