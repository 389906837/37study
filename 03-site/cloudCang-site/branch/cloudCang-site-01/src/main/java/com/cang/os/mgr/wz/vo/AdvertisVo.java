/**
 * 
 */
package com.cang.os.mgr.wz.vo;

import java.util.Date;

import com.cang.os.bean.wz.Advertis;

/**
 * @author zhouhong
 *
 */
public class AdvertisVo extends Advertis {
	/**
	 * 发布开始日期
	 */
	private Date startDate;
	
	/**
	 * 发布结束日期
	 */
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
