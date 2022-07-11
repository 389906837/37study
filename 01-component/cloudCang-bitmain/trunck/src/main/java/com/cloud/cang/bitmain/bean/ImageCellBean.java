package com.cloud.cang.bitmain.bean;


public class ImageCellBean {
	
	private String serverIp;//获取照片IP

	private int port;//获取照片端口
	
	private String cameraCode; //获取照片标识
	
	private String imageUrl;//图片地址 可访问

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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "ImageCellBean{" +
				"serverIp='" + serverIp + '\'' +
				", port=" + port +
				", cameraCode='" + cameraCode + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				'}';
	}
}
