package com.cloud.cang.rec.sys.domain;

import com.cloud.cang.model.cs.Currenttradedate;

public class CurrenttradedateDomain extends Currenttradedate{
	/**
	 * @description: 节假日 Domain
	 * @author:Yanlingfeng
	 * @time:2018-02-22 14:07:05
	 * @version 1.0
	 */
    private static final long serialVersionUID = 7424946081831784607L;

    private String syear;

    private String smonth;

	private String orderStr;//排序字段

	public String getSyear() {
		return syear;
	}

	public void setSyear(String syear) {
		this.syear = syear;
	}

	public String getSmonth() {
		return smonth;
	}

	public void setSmonth(String smonth) {
		this.smonth = smonth;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
    

}
