package com.cloud.cang.wap.common.vo;


import com.cloud.cang.generic.GenericEntity;

public class RequestVo extends GenericEntity {

	private static final long serialVersionUID = 1L;
	private String memId;//会员ID 修改用户信息用到
	private String mobileNumber;//手机号
	private String txCode;//图形验证码
	private Integer types;//操作类型
	private Integer bindType;//绑定类型 10 微信 20 支付宝

	//验证码参数
	private String challenge;
	private String validate;
	private String seccode;

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	public Integer getBindType() {
		return bindType;
	}

	public void setBindType(Integer bindType) {
		this.bindType = bindType;
	}


	public String getChallenge() {
		return challenge;
	}

	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getSeccode() {
		return seccode;
	}

	public void setSeccode(String seccode) {
		this.seccode = seccode;
	}

	@Override
	public String toString() {
		return "RequestVo{" +
				"memId='" + memId + '\'' +
				", mobileNumber='" + mobileNumber + '\'' +
				", txCode='" + txCode + '\'' +
				", types=" + types +
				", bindType=" + bindType +
				", challenge='" + challenge + '\'' +
				", validate='" + validate + '\'' +
				", seccode='" + seccode + '\'' +
				'}';
	}
}
