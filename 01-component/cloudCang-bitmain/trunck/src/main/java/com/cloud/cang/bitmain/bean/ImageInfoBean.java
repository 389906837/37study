package com.cloud.cang.bitmain.bean;

import java.util.List;

public class ImageInfoBean extends BaseCellListBean {

	private int isUpload;
	private String batchNo;
	private String url;
	private List<ImageCellBean> param;


	public int getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(int isUpload) {
		this.isUpload = isUpload;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ImageCellBean> getParam() {
		return param;
	}

	public void setParam(List<ImageCellBean> param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "ImageInfoBean{" +
				"isUpload=" + isUpload +
				", batchNo='" + batchNo + '\'' +
				", url='" + url + '\'' +
				", param=" + param +
				'}';
	}
}
