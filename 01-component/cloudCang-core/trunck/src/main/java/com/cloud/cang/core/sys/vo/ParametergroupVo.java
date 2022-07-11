package com.cloud.cang.core.sys.vo;


import com.cloud.cang.model.sys.Parametergroup;

public class ParametergroupVo extends Parametergroup {
	private static final long serialVersionUID = 1L;

	private String orderStr;//排序字段

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
}
