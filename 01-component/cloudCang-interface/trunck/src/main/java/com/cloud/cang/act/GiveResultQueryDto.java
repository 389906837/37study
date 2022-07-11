/*
 * Copyright (C) 2016 cloud.cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月24日
 * Description:GiveResultQueryDto.java 
 */
package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

/**
 * @author yanlingefng
 * @version 1.0
 */
public class GiveResultQueryDto extends SuperDto {
	
	private static final long serialVersionUID = 4344103223355849746L;

	/**
	 * 来源单据号
	 */
	private String sourceCode;
	
	/**
	 * 来源类型
	 */
	private Integer sourceType;
	
	/**
	 * 会员Id
	 */
	private String memberId;

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "GiveResultQueryDto [sourceCode=" + sourceCode + ", sourceType="
				+ sourceType + ", memberId=" + memberId + "]";
	}

}
