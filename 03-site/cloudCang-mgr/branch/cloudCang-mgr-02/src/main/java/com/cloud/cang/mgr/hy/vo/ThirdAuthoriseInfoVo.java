package com.cloud.cang.mgr.hy.vo;

import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description 会员第三方授权信息查询
 * @author ChangTanchang
 * @time 2018-2-08 19:05:32
 * @fileName MemberThreeInfoController.java
 * @version 1.0
 */
public class ThirdAuthoriseInfoVo extends ThirdAuthorise{
	// 授权时间参数
	private String tauthoriseTimeStr;

	// 开始授权日期
	private Date toperateStartDate;

	// 结束授权日期
	private Date toperateEndDate;

	// 排序字段
	private String orderStr;

	public Date getToperateStartDate() {
		if (StringUtil.isNotBlank(this.tauthoriseTimeStr)) {
			return DateUtils.parseDate(tauthoriseTimeStr.split(" - ")[0]);
		}
		return toperateStartDate;
	}

	public void setToperateStartDate(Date toperateStartDate) {
		this.toperateStartDate = toperateStartDate;
	}

	public Date getToperateEndDate() {
		if (StringUtil.isNotBlank(this.tauthoriseTimeStr)) {
			return DateUtils.parseDate(tauthoriseTimeStr.split(" - ")[1]);
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

	public String getTauthoriseTimeStr() {
		return tauthoriseTimeStr;
	}

	public void setTauthoriseTimeStr(String tauthoriseTimeStr) {
		this.tauthoriseTimeStr = tauthoriseTimeStr;
	}

	@Override
	public String toString() {
		return "ThirdAuthoriseInfoVo{" +
				"tauthoriseTimeStr='" + tauthoriseTimeStr + '\'' +
				", toperateStartDate=" + toperateStartDate +
				", toperateEndDate=" + toperateEndDate +
				", orderStr='" + orderStr + '\'' +
				'}';
	}
}
