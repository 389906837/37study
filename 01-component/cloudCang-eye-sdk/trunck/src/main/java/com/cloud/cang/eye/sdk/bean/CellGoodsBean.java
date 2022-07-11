package com.cloud.cang.eye.sdk.bean;

import java.util.ArrayList;

public class CellGoodsBean {
	
	private String deviceId;//设备标识
	
	private String cellid; //获取照片标识
	
	private String sextends;//扩展参数
	
	private String status;
	
	private ArrayList<GoodsBean> goods;

	public String getCellid() {
		return cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<GoodsBean> getGoods() {
		return goods;
	}

	public void setGoods(ArrayList<GoodsBean> goods) {
		this.goods = goods;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSextends() {
		return sextends;
	}

	public void setSextends(String sextends) {
		this.sextends = sextends;
	}

	@Override
	public String toString() {
		return "CellGoodsBean [deviceId=" + deviceId + ", cellid=" + cellid
				+ ", sextends=" + sextends + ", status=" + status + ", goods="
				+ goods + "]";
	}
	
	
}
