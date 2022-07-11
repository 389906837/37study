package com.cloud.cang.eye.sdk.bean;


public class ImageCellBean {
	
	private String deviceId;//设备标识
	
	private String cellid; //获取照片标识
	
	private String filePath;//图片地址 可访问
	
	private String imageData;//图片base64

	private String sextends;//扩展参数

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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public String getSextends() {
		return sextends;
	}

	public void setSextends(String sextends) {
		this.sextends = sextends;
	}

	@Override
	public String toString() {
		return "ImageCellBean [deviceId=" + deviceId + ", cellid=" + cellid
				+ ", filePath=" + filePath + ", imageData=" + imageData
				+ ", sextends=" + sextends + "]";
	}
	
	
}
