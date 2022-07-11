package com.cloud.cang.eye.sdk.bean;

import java.util.ArrayList;

public class InventoryResultBeanPos extends ResultBean {

	private String scode;//处理业务编号
	
	private String sextends;//扩展参数
	
	private ArrayList<CellGoodsBeanPos> data;
	
	

	
	public ArrayList<CellGoodsBeanPos> getData() {
		return data;
	}

	public void setData(ArrayList<CellGoodsBeanPos> data) {
		this.data = data;
	}

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

	@Override
	public String toString() {
		return "InventoryResultBeanPos [scode=" + scode + ", sextends="
				+ sextends + ", data=" + data + "]";
	}
	
	
	
}
