package com.cloud.cang.security.filter;



import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.SSOToken;

import com.cloud.cang.utils.MD5;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
import java.net.URLEncoder;



/**
 * 拦截所有的用户请求，如果存在ticket进行sso验证
 * 
 * @author zhouhong
 * @version 1.0
 */
public class SSOFilter extends OncePerRequestFilter {

	
	private static Logger LOGGER = LoggerFactory.getLogger(SSOFilter.class);
	@Autowired
	private ICached cacheUtils;
	
	private static String REQUEST_TOKEN_ID_NAME = "tokenId";
	private static String SESSION_KEY_USER_ID_SESSION_ID = "prefix_sso_user_session_";
	public static final String INCLUDE_CONTEXT_PATH_ATTRIBUTE = "javax.servlet.include.context_path";
	public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
	public static final String URL_SIGN_SSO = "/prelogin";
	public static final String SPLIT_TOKEN_USER_ID="~";
	public static final int REDIS_TIMEOUT=3600*5;
	public static final String LOGIN_OUT_URL ="shiro.logoutUrl";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 拦截所有请有判断有tokenId
		String tokenId = request.getParameter(REQUEST_TOKEN_ID_NAME);
		if (StringUtils.isNotBlank(tokenId)) {
			LOGGER.info("TICKET:{} 请求IP:{}",tokenId, SessionUserUtils.getIpAddr(request));
			// 判断md5值
			
			String tokenArr[]=tokenId.split(SPLIT_TOKEN_USER_ID);
			if(tokenArr==null||tokenArr.length<2){
				filterChain.doFilter(request, response);
				return;
			}
			String md5Sign = tokenArr[0].substring(0, 32);//MD5签名
			String userId=tokenArr[0].substring(32);//用户的tokenId
			String value = tokenArr[1];//加密串
			value=URLEncoder.encode(value,"UTF-8");
			String userFlag=(String) request.getSession().getAttribute(SESSION_KEY_USER_ID_SESSION_ID+userId);
			if(userFlag!=null){
				handleRedictURL(request, response);
				return;
			}
			if (md5Sign.equals(MD5.encode(value))) {
				SSOToken tokenObj=new SSOToken(tokenArr[0]+SPLIT_TOKEN_USER_ID+value,SessionUserUtils.getIpAddr(request));
				try {
					SecurityUtils.getSubject().login(tokenObj);
				} catch (Exception e) {
					LOGGER.error("登录异常", e);
					response.sendRedirect(EvnUtils.getValue(LOGIN_OUT_URL));
					return;
				}
				if(SecurityUtils.getSubject().isAuthenticated()){
					handleRedictURL(request, response);
					
					try {
						cacheUtils.put(SESSION_KEY_USER_ID_SESSION_ID+SessionUserUtils.getSessionUserId(), request.getSession().getId(), REDIS_TIMEOUT);
					} catch (Exception e) {
						LOGGER.error("TICKET:"+tokenId,e);
					}
					request.getSession().setAttribute(SESSION_KEY_USER_ID_SESSION_ID+SessionUserUtils.getSessionUserId(), "1");
					return;
				}
			}
		}
		// 判断session是否存在
		// 判断用户id是否一致
		// 调用shiro处理
		filterChain.doFilter(request, response);
	}
	private void handleRedictURL(HttpServletRequest request,HttpServletResponse response){
		String requestPath=getPathWithinApplication(request);
		if(requestPath.indexOf(URL_SIGN_SSO)!=-1){
			writeToResponse(response,"{\"result\":\"success\"}");
		}else{
			try {
				response.sendRedirect(urlResolver(request));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void writeToResponse(HttpServletResponse response,String msg) {
		try {
			ServletOutputStream output=response.getOutputStream();
			output.write(msg.getBytes());
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String urlResolver(HttpServletRequest request){
		//重新定位到原请求，去除EC参数。
		String url = request.getRequestURL().toString() + "?" + request.getQueryString();
			//如果请求中存在EC参数，则去除这个参数，重定位。
			if(url.contains(REQUEST_TOKEN_ID_NAME)){
				url = url.substring(0, url.indexOf(REQUEST_TOKEN_ID_NAME));
				//去除末尾的问号。
				if(url.endsWith("?")){
					url = url.substring(0, url.length()-1);
				}
				//去除末尾的&符号。
				if(url.endsWith("&")){
					url = url.substring(0, url.length()-1);
				}
			}
			return url;
	}
	
	public static String getContextPath(HttpServletRequest request) {
		String contextPath = (String) request
				.getAttribute(INCLUDE_CONTEXT_PATH_ATTRIBUTE);
		if (contextPath == null) {
			contextPath = request.getContextPath();
		}
		if ("/".equals(contextPath)) {
			contextPath = "";
		}
		return contextPath;
	}

	public String getRequestUri(HttpServletRequest request) {
		String uri = (String) request
				.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE);
		if (uri == null) {
			uri = request.getRequestURI();
		}
		return uri;
	}

	public String getPathWithinApplication(HttpServletRequest request) {
		String contextPath = getContextPath(request);
		String requestUri = getRequestUri(request);
		if (StringUtils.startsWithIgnoreCase(requestUri, contextPath)) {
			// Normal case: URI contains context path.
			String path = requestUri.substring(contextPath.length());
			return (StringUtils.isNotBlank(path) ? path : "/");
		} else {
			return requestUri;
		}
	}
}
