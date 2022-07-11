/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年1月30日
 * Description:SessionUserUtils.java 
 */
package com.cloud.cang.security.utils;

import com.cloud.cang.security.vo.AuthorizationVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 类功能描述: Session用户操作工具类
 * @author zhouhong
 * @version 1.0
 */
public class SessionUserUtils {
	public static final String SESSION_KEY_TJ="SESSION_KEY_USER_TJ";//会员推荐编号 sessionKey
	public static final String SESSION_KEY_DETAIL="SESSION_KEY_USER";//用户详细信息 sessionKey
	public static final String SESSION_KEY_SOURCE="SESSION_KEY_SOURCE";//用户来源 sessionKey
	public static final String SESSION_KEY_SMERCHANT_ID="SESSION_KEY_SMERCHANT_ID";//后台商户ID sessionKey
	public static final String SESSION_KEY_SMERCHANT_CODE="SESSION_KEY_SMERCHANT_CODE";//后台商户编号 sessionKey
	public static final String SESSION_KEY_ROOT_MERCHANT="SESSION_KEY_ROOT_MERCHANT";//后台是否根商户 sessionKey
	
    /**
     * 取得session用户登录名
     * 
     * @return
     */
    public static String getSessionUserName()
    {
         AuthorizationVO v =  getSessionAttributeForUserDtl();
        return v==null?null:v.getUserName();
    }
    /**
     * 取得session对象
     * 
     * @return
     */
    public static Session getSession(){
    	Subject subject = SecurityUtils.getSubject();
    	
        if (SecurityUtils.getSubject() == null)
            return null;
        return subject.getSession();
    }
    /**
	 * 注入用户详情信息到Session中
	 * 
	 * @return
	 */
	public static void setSessionAttributeForUserDtl(AuthorizationVO userObj) {
		SecurityUtils.getSubject().getSession().setAttribute(SESSION_KEY_DETAIL,userObj);
	}
	/**
	 * Session中取得用户详情信息
	 * 
	 * @return
	 */
	public static AuthorizationVO getSessionAttributeForUserDtl() {
		return (AuthorizationVO)SecurityUtils.getSubject().getSession().getAttribute(SESSION_KEY_DETAIL);
	}
	/**
	 * 获取会话中的值
	 * 
	 * @return
	 */
	public static Object getSessionAttribute(String key) {
		try {
			return SecurityUtils.getSubject().getSession().getAttribute(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断用户是否已经登录
	 * 
	 * @return
	 */
	public static boolean isAuthenticated() {
		try {
			return SecurityUtils.getSubject().isAuthenticated();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除会话中的值
	 * 
	 * @param key
	 */
	public static void removeSessionAttribute(String key) {
		try {
			SecurityUtils.getSubject().getSession().removeAttribute(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 取得sessionId
	 * @return
	 */
    public static String getShiroSessionId()
    {
        Subject subject = SecurityUtils.getSubject();
        if (SecurityUtils.getSubject() == null)
            return null;
        return subject.getSession().getId().toString();
    }
    
    /**
     * 取得用户id
     * 
     * @return
     */
    public static String getSessionUserId()
    {
    	AuthorizationVO obj=getSessionAttributeForUserDtl();
    	if(obj==null)return null;
        return obj.getId();
    }
    
    
    /**
     * 判断是否有权限
     * 
     * @param role
     * @return
     */
    public static boolean hasRole(String role)
    {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.hasRole(role);
    }
    /**
     * 取得用户的IP地址
     * 
     * @return
     */
    public static String getRequestIp(){
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
    	return getIpAddr(request);
    	
    }

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
	
	/**
	 * 根据shiro的principal分出用户id和用户名
	 * @param principal
	 * @return
	 */
	public static AuthorizationVO getUserFormPrincipal(String principal){
	
		AuthorizationVO vo=new AuthorizationVO();
		String[] name=principal.split("~~");
		vo.setId(name[0]);
		vo.setUserName(name[1]);
		return vo;
	}
}
