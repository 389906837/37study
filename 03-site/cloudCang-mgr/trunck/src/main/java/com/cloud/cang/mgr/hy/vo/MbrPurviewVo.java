package com.cloud.cang.mgr.hy.vo;

import com.cloud.cang.model.hy.MbrPurview;
import com.cloud.cang.model.hy.MbrRole;

import java.util.Date;

/**
 * @description 会员权限码管理表
 * @author ChangTanchang
 * @time 2018-02-07 14:31:00
 * @fileName MbrRoleController.java
 * @version 1.0
 */
public class MbrPurviewVo extends MbrPurview {

	// 开始日期
	private Date toperateStartDate;

	// 结束日期
	private Date toperateEndDate;

	// 角色ID
	private String roleId;

	// 排序字段
	private String orderStr;

	//是否已有角色 0:没有 1:有
	private  String  isSelect;

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Date getToperateStartDate() {
		return toperateStartDate;
	}

	public void setToperateStartDate(Date toperateStartDate) {
		this.toperateStartDate = toperateStartDate;
	}

	public Date getToperateEndDate() {
		return toperateEndDate;
	}

	public void setToperateEndDate(Date toperateEndDate) {
		this.toperateEndDate = toperateEndDate;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}
}
