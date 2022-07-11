/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月3日
 * Description:URLUtils.java 
 */
package com.cloud.cang.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 对URL处理工具类
 * 
 * @author zhouhong
 * @version 1.0
 */
public class URLUtils {

	/**
	 * 生成登录成功后的请示地址
	 * @param request
	 * @return
	 */
	public static String handleLoginUrl(HttpServletRequest request) {
		String backUrl = request.getParameter("backUrl");
		if (StringUtils.isBlank(backUrl))
			backUrl = getDefaultBackUrl(WebUtils.toHttp(request));
		return backUrl;
	}

	/**
	 * 跳转到登录页面
	 * @param request
	 * @param response
	 * @param backUrl
	 * @param loginUrl
	 * @throws IOException
	 */
	public static void redirectToLogin(ServletRequest request,
			ServletResponse response, String backUrl, String loginUrl)
			throws IOException {
		WebUtils.issueRedirect(request, response, loginUrl + "?service="
				+ URLEncoder.encode(backUrl, "utf-8"));
	}

	public static void saveRequestAndRedirectToLogin(ServletRequest request,
			ServletResponse response, String loginUrl) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		String backUrl = handleLoginUrl(httpRequest);
		SavedRequest savedRequest = new ClientSavedRequest(httpRequest, backUrl);
		session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
		redirectToLogin(httpRequest, response, backUrl, loginUrl);
	}

	private static String getDefaultBackUrl(HttpServletRequest request) {
		String scheme = request.getScheme();
		String domain = request.getServerName();
		int port = request.getServerPort();
		StringBuilder backUrl = new StringBuilder(scheme);
		backUrl.append("://");
		backUrl.append(domain);
		if ("http".equalsIgnoreCase(scheme) && port != 80) {
			backUrl.append(":").append(String.valueOf(port));
		} else if ("https".equalsIgnoreCase(scheme) && port != 443) {
			backUrl.append(":").append(String.valueOf(port));
		}
		backUrl.append(request.getRequestURI());
		if (StringUtils.isNotBlank(request.getQueryString())) {
			backUrl.append("?" + request.getQueryString());
		}
		return backUrl.toString();
	}

}
