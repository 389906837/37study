package com.cloud.cang.bitmain.bean;

import java.util.List;

public class CellListBean {
	
	private String serverCode;//服务器编号
	
	private String scode;//处理业务编号
	
	private List<ImageCellBean> imgs;

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public List<ImageCellBean> getImgs() {
		return imgs;
	}

	public void setImgs(List<ImageCellBean> imgs) {
		this.imgs = imgs;
	}

	@Override
	public String toString() {
		return "CellListBean [serverCode=" + serverCode + ", scode=" + scode
				+ ", imgs=" + imgs + "]";
	}
	
	
}
