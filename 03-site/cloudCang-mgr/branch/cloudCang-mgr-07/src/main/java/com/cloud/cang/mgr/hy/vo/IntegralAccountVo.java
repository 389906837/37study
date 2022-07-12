package com.cloud.cang.mgr.hy.vo;

import com.cloud.cang.model.hy.IntegralAccount;

import java.util.Date;

/**
 * @description 会员积分表
 * @author ChangTanchang
 * @time 2018-02-10 14:31:00
 * @fileName MbrRoleController.java
 * @version 1.0
 */
public class IntegralAccountVo extends IntegralAccount {

	// 可用积分(区间范围)
	private Integer iusablePointsBegin;

	// 可用积分(区间范围)
	private Integer iusablePointsEnd;

	// 已使用积分(区间范围)
	private Integer iusedPointsBegin;

	// 已使用积分(区间范围)
	private Integer iusedPointsEnd;

	// 备注
	private String sreMark;

	// 排序字段
	private String orderStr;

	// (积分)增加-减少
	private Integer addLess;

	// 查询条件
	private String condition;

	public Integer getAddLess() {
		return addLess;
	}

	public void setAddLess(Integer addLess) {
		this.addLess = addLess;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public Integer getIusablePointsBegin() {
		return iusablePointsBegin;
	}

	public void setIusablePointsBegin(Integer iusablePointsBegin) {
		this.iusablePointsBegin = iusablePointsBegin;
	}

	public Integer getIusablePointsEnd() {
		return iusablePointsEnd;
	}

	public void setIusablePointsEnd(Integer iusablePointsEnd) {
		this.iusablePointsEnd = iusablePointsEnd;
	}

	public Integer getIusedPointsBegin() {
		return iusedPointsBegin;
	}

	public void setIusedPointsBegin(Integer iusedPointsBegin) {
		this.iusedPointsBegin = iusedPointsBegin;
	}

	public Integer getIusedPointsEnd() {
		return iusedPointsEnd;
	}

	public void setIusedPointsEnd(Integer iusedPointsEnd) {
		this.iusedPointsEnd = iusedPointsEnd;
	}

	public String getSreMark() {
		return sreMark;
	}

	public void setSreMark(String sreMark) {
		this.sreMark = sreMark;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "IntegralAccountVo{" +
				"iusablePointsBegin=" + iusablePointsBegin +
				", iusablePointsEnd=" + iusablePointsEnd +
				", iusedPointsBegin=" + iusedPointsBegin +
				", iusedPointsEnd=" + iusedPointsEnd +
				", sreMark='" + sreMark + '\'' +
				", orderStr='" + orderStr + '\'' +
				", addLess=" + addLess +
				", condition='" + condition + '\'' +
				'}';
	}
}
