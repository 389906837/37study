package com.cloud.cang.mgr.hy.vo;

import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description 会员权限码管理表
 * @author ChangTanchang
 * @time 2018-02-07 14:31:00
 * @fileName MbrRoleController.java
 * @version 1.0
 */
public class MemberInfoVo extends MemberInfo{
	// 注册时间参数
	private String tregisterTimeStr;

	// 开始注册日期
	private Date toperateStartDate;

	// 结束注册日期
	private Date toperateEndDate;

	// 出生年月日(开始)
	private Date dbirthdateBeginData;

	// 出生年月日(结束)
	private Date dbirthdateEndData;

	// 商户名称
	private String merchantName;

	// 排序字段
	private String orderStr;

	// 查询条件
	private String queryCondition;

	public Date getToperateStartDate() {
		if (StringUtil.isNotBlank(this.tregisterTimeStr)) {
			return DateUtils.parseDate(tregisterTimeStr.split(" - ")[0]);
		}
		return toperateStartDate;
	}

	public void setToperateStartDate(Date toperateStartDate) {
		this.toperateStartDate = toperateStartDate;
	}

	public Date getToperateEndDate() {
		if (StringUtil.isNotBlank(this.tregisterTimeStr)) {
			return DateUtils.parseDate(tregisterTimeStr.split(" - ")[1]);
		}
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

	public Date getDbirthdateBeginData() {
		return dbirthdateBeginData;
	}

	public void setDbirthdateBeginData(Date dbirthdateBeginData) {
		this.dbirthdateBeginData = dbirthdateBeginData;
	}

	public Date getDbirthdateEndData() {
		return dbirthdateEndData;
	}

	public void setDbirthdateEndData(Date dbirthdateEndData) {
		this.dbirthdateEndData = dbirthdateEndData;
	}

	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTregisterTimeStr() {
		return tregisterTimeStr;
	}

	public void setTregisterTimeStr(String tregisterTimeStr) {
		this.tregisterTimeStr = tregisterTimeStr;
	}
}
