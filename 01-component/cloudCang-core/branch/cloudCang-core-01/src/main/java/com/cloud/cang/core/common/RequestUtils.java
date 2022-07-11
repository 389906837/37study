/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月4日
 * Description:RequestUtils.java 
 */
package com.cloud.cang.core.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouhong
 * @version 1.0
 */
public class RequestUtils {

	 /***
		 * 获取IP（如果是多级代理，则得到的是一串IP值）
		 */
		public static String getIpAddr(HttpServletRequest request) {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			if (ip != null && ip.length() > 0) {
				String[] ips = ip.split(",");
				for (int i = 0; i < ips.length; i++) {
					if (!"unknown".equalsIgnoreCase(ips[i])) {
						ip = ips[i];
						break;
					}
				}
			}
			return ip;
		}
}
