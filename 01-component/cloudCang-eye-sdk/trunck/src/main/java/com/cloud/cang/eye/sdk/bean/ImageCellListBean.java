package com.cloud.cang.eye.sdk.bean;

import java.util.List;

public class ImageCellListBean extends BaseCellListBean {
	
	
	private List<ImageCellBean> imgs;

	

	public List<ImageCellBean> getImgs() {
		return imgs;
	}

	public void setImgs(List<ImageCellBean> imgs) {
		this.imgs = imgs;
	}

	@Override
	public String toString() {
		return "CellListBean [imgs=" + imgs + "]";
	}
	
	
}
