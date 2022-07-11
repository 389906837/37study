/*
 * Copyright (C) 2016 cloud.cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月9日
 * Description:ExchangeCouponDto.java 
 */
package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

/**
 * 券码兑换参数
 * @author yanlingfeng
 * @version 1.0
 */
public class ExchangeCouponDto extends SuperDto {

	private static final long serialVersionUID = -5851193931474657943L;
	
	/**
	 * 兑换券码 必填
	 */
	private String exCouponCode;
	
	/**
	 * 兑换会员Id 必填
	 */
	private String memberId;
	
	/**
	 * 兑换会员编号 必填
	 */
	private String memberCode;
	
	/**
	 * 来源客户端 必填
	 */
	private Integer clientType;

	public String getExCouponCode() {
		return exCouponCode;
	}

	public void setExCouponCode(String exCouponCode) {
		this.exCouponCode = exCouponCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	
	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	@Override
	public String toString() {
		return "ExchangeCouponDto [exCouponCode=" + exCouponCode
				+ ", memberId=" + memberId + ", memberCode=" + memberCode
				+ ", clientType=" + clientType + ", serialNum=" + serialNum
				+ "]";
	}
	

}
