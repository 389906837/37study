package com.cloud.cang.bitmain.bean;

import java.util.ArrayList;

public class CellGoodsBean {
	
	private String serverIp;//获取照片IP
	
	private String cameraCode; //获取照片标识
	
	private String imageUrl;//图片地址 可访问
	
	private String status;//状态 0 成功 其他失败
	
	private ArrayList<GoodsBean> goods;//商品信息

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getCameraCode() {
		return cameraCode;
	}

	public void setCameraCode(String cameraCode) {
		this.cameraCode = cameraCode;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	@Override
	public String toString() {
		return "CellGoodsBean{" +
				"serverIp='" + serverIp + '\'' +
				", cameraCode='" + cameraCode + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", status='" + status + '\'' +
				", goods=" + goods +
				'}';
	}
}
