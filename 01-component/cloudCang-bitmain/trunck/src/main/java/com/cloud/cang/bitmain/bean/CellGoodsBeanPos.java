package com.cloud.cang.bitmain.bean;

import java.util.ArrayList;

public class CellGoodsBeanPos {
	
	private String deviceId;//设备标识
	
	private String cellid; //获取照片标识
	
	private String sextends;//扩展参数
	
	private String status;
	
	private ArrayList<GoodsBeanPos> goods;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<GoodsBeanPos> getGoods() {
		return goods;
	}

	public void setGoods(ArrayList<GoodsBeanPos> goods) {
		this.goods = goods;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCellid() {
		return cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	public String getSextends() {
		return sextends;
	}

	public void setSextends(String sextends) {
		this.sextends = sextends;
	}

	@Override
	public String toString() {
		return "CellGoodsBeanPos [deviceId=" + deviceId + ", cellid=" + cellid
				+ ", sextends=" + sextends + ", status=" + status + ", goods="
				+ goods + "]";
	}
	
	
}
