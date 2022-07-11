package com.cloud.cang.rec.common.vo;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;


/**
 * 日期VO
 * @author zhouhong
 */
public class DateTimeVo extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date beginDate;
	private String beginDateTime;
	private Date endDate;
	private String endDateTime;
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public String getBeginDateTime() {
		return beginDateTime;
	}
	public void setBeginDateTime(String beginDateTime) {
		this.beginDateTime = beginDateTime;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
