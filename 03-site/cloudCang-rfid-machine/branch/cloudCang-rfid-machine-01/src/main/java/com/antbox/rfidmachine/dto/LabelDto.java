package com.antbox.rfidmachine.dto;

/**
 * RFID标签Bean
 *
 * Created by DK on 17/5/9.
 */
public class LabelDto {
	private int num;
	private String icon;
	private String tag;
	private String productName;

	public LabelDto() {

	}

	public LabelDto(int num, String icon, String tag, String productName) {
		super();
		this.num = num;
		this.icon = icon;
		this.tag = tag;
		this.productName = productName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
