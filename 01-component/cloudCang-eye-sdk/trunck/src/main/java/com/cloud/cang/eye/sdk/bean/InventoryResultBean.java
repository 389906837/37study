package com.cloud.cang.eye.sdk.bean;

import java.util.ArrayList;

public class InventoryResultBean extends ResultBean {
	
	private String scode;//处理业务编号
	
	private String sextends;//扩展参数
	
	private ArrayList<CellGoodsBean> data;

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getSextends() {
		return sextends;
	}

	public void setSextends(String sextends) {
		this.sextends = sextends;
	}

	public ArrayList<CellGoodsBean> getData() {
		return data;
	}

	public void setData(ArrayList<CellGoodsBean> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "InventoryResultBean [scode=" + scode + ", sextends=" + sextends
				+ ", data=" + data + "]";
	}
	
	
}
