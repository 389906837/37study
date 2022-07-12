package com.cloud.cang.mgr.sys.vo;


import com.cloud.cang.security.vo.AuthorizationVO;

public class UserDetail extends AuthorizationVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8158144449525389358L;

	private Integer sex;
	
	private String mobile;
	private String  phone;
	private String mai;
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMai() {
		return mai;
	}
	public void setMai(String mai) {
		this.mai = mai;
	}
	
	
	
	
}
