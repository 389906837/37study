/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月1日
 * Description:Principal.java 
 */
package com.cloud.cang.security.vo;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author zhouhong
 * @version 1.0
 */
public class Principal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6803995418557011833L;
	protected String id;
	protected String userCode;
	protected String userName;
	protected String mobile;
	protected String email;
	protected int sex;
	protected String realName;
	protected String lastLoginTime;
	protected boolean isauth = false;
	private int isOpenAccountAuth=0;
	private Integer memberType=1;
	private long credentialCreateTime;
	protected String lastLoginIp;
	protected String ip;
	protected Map<String, Object> attributes = Maps.newHashMap();

	public Principal() {
	}

	public Principal(String id, Map<String, Object> attr) {
		this.id = id;
		this.attributes = attr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tziba.sso.core.authentication.Principal#getAttributes()
	 */
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tziba.sso.core.authentication.Principal#getId()
	 */

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public boolean isIsauth() {
		return isauth;
	}

	public void setIsauth(boolean isauth) {
		this.isauth = isauth;
	}

	public int getIsOpenAccountAuth() {
		return isOpenAccountAuth;
	}

	public void setIsOpenAccountAuth(int isOpenAccountAuth) {
		this.isOpenAccountAuth = isOpenAccountAuth;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public long getCredentialCreateTime() {
		return credentialCreateTime;
	}

	public void setCredentialCreateTime(long credentialCreateTime) {
		this.credentialCreateTime = credentialCreateTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
