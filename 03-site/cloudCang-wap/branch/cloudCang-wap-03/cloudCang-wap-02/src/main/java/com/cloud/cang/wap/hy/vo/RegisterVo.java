package com.cloud.cang.wap.hy.vo;


import com.cloud.cang.generic.GenericEntity;

/**
 * @author zhouhong
 * 注册Vo
 */
public class RegisterVo extends GenericEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String merchantCode;//商户编号
	private String mobileNumber;//手机号
	private String captcha;//图片验证码
	private String msgCode;//短信验证码
	private String loginPwd;//登录密码
	private String recommonCode;//邀请码
	private Integer itype;//注册类型 10 微信 20 支付宝
	/*private String headImg;//头像
	private Integer clientType;//注册来源
	private Integer sex;//性别
	private String deviceCode;//设备编号*/
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getRecommonCode() {
		return recommonCode;
	}
	public void setRecommonCode(String recommonCode) {
		this.recommonCode = recommonCode;
	}

	public Integer getItype() {
		return itype;
	}

	public void setItype(Integer itype) {
		this.itype = itype;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	@Override
	public String toString() {
		return "RegisterVo{" +
				"mobileNumber='" + mobileNumber + '\'' +
				", captcha='" + captcha + '\'' +
				", msgCode='" + msgCode + '\'' +
				", loginPwd='" + loginPwd + '\'' +
				", recommonCode='" + recommonCode + '\'' +
				", itype=" + itype +
				'}';
	}
}
