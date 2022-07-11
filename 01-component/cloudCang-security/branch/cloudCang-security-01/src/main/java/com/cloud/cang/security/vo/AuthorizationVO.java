package com.cloud.cang.security.vo;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 保存用户的数据，存储到Session中
 * @author zhouhong
 *
 */
public class AuthorizationVO implements Serializable {

	private String smerchantId;//商户ID
	private String smerchantCode;//商户编号
	private String smerchantName;//商户名称
	private String sshortName;//商户简称
	private String smerchantLogo;//商户logo
	private String smerchantLoginLogo;//商户登录logo
	private String id;//会员ID
	private String userName; //会员名称
	private String code;//会员编号
	private boolean freeze;//冻结状态
	private String password;//密码
	private String realName;//真实姓名
	private String mobile;//手机号
	private String email;//邮件
	private Integer bisdAdmin;//是否超级管理员
	private String lastLoginTime;//最后登录时间
	private boolean isAuth=false;//是否认证
	private boolean isReplenishment=false;//是否补货员
	private Integer memberType=10;//会员类型
	private Integer sex;//性别
	protected String lastLoginIp;//登录IP
	protected String regIp;//注册IP
	protected String regDeviceCode;//注册设备编号
	protected String regPlatform;//注册平台
	private Date registerTime;//注册时间
	private String imageIco;//头像地址
	private Integer memberLevel;//会员等级
	private Integer isOrder;// 是否有过下单
	private Date birthDay;//生日
	private String fundAccountId;//资金账户ID
	private String fundAccountCode;//资金账户编号
	private String trusteeId;//托管ID
	private String trusteeAccountCode;//第三方账户编号
	private Integer isSetTradePass = 0;//是否设置了交易密码
	private String sidNo;//身份证号
	private String realNameTime;//实名认证时间
	private Integer iwechatOpen;//微信支付免密是否开通
	private Integer iaipayOpen;//支付宝免密是否开通



	private Map<String, Object> ext= Maps.newHashMap();
	private static final long serialVersionUID = -197569891751143211L;
	public AuthorizationVO() {
	}
	public AuthorizationVO(String operatorID, String userName,
			String code) {
		super();
		this.id = operatorID;
		this.userName = userName;
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isFreeze() {
		return freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public boolean isAuth() {
		return isAuth;
	}

	public void setAuth(boolean auth) {
		isAuth = auth;
	}

	public boolean isReplenishment() {
		return isReplenishment;
	}

	public void setReplenishment(boolean replenishment) {
		isReplenishment = replenishment;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getRegDeviceCode() {
		return regDeviceCode;
	}

	public void setRegDeviceCode(String regDeviceCode) {
		this.regDeviceCode = regDeviceCode;
	}

	public String getRegPlatform() {
		return regPlatform;
	}

	public void setRegPlatform(String regPlatform) {
		this.regPlatform = regPlatform;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getImageIco() {
		return imageIco;
	}

	public void setImageIco(String imageIco) {
		this.imageIco = imageIco;
	}

	public Integer getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Integer getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getFundAccountId() {
		return fundAccountId;
	}

	public void setFundAccountId(String fundAccountId) {
		this.fundAccountId = fundAccountId;
	}

	public String getFundAccountCode() {
		return fundAccountCode;
	}

	public void setFundAccountCode(String fundAccountCode) {
		this.fundAccountCode = fundAccountCode;
	}

	public String getTrusteeId() {
		return trusteeId;
	}

	public void setTrusteeId(String trusteeId) {
		this.trusteeId = trusteeId;
	}

	public String getTrusteeAccountCode() {
		return trusteeAccountCode;
	}

	public void setTrusteeAccountCode(String trusteeAccountCode) {
		this.trusteeAccountCode = trusteeAccountCode;
	}

	public Integer getIsSetTradePass() {
		return isSetTradePass;
	}

	public void setIsSetTradePass(Integer isSetTradePass) {
		this.isSetTradePass = isSetTradePass;
	}

	public String getSidNo() {
		return sidNo;
	}

	public void setSidNo(String sidNo) {
		this.sidNo = sidNo;
	}

	public String getRealNameTime() {
		return realNameTime;
	}

	public void setRealNameTime(String realNameTime) {
		this.realNameTime = realNameTime;
	}

	public Map<String, Object> getExt() {
		return ext;
	}

	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}

	public String getSmerchantId() {
		return smerchantId;
	}

	public void setSmerchantId(String smerchantId) {
		this.smerchantId = smerchantId;
	}

	public String getSmerchantName() {
		return smerchantName;
	}

	public void setSmerchantName(String smerchantName) {
		this.smerchantName = smerchantName;
	}

	public String getSmerchantLogo() {
		return smerchantLogo;
	}

	public void setSmerchantLogo(String smerchantLogo) {
		this.smerchantLogo = smerchantLogo;
	}

	public String getSshortName() {
		return sshortName;
	}

	public void setSshortName(String sshortName) {
		this.sshortName = sshortName;
	}

	public Integer getBisdAdmin() {
		return bisdAdmin;
	}

	public void setBisdAdmin(Integer bisdAdmin) {
		this.bisdAdmin = bisdAdmin;
	}

	public String getSmerchantCode() {
		return smerchantCode;
	}

	public void setSmerchantCode(String smerchantCode) {
		this.smerchantCode = smerchantCode;
	}

	public String getSmerchantLoginLogo() {
		return smerchantLoginLogo;
	}

	public void setSmerchantLoginLogo(String smerchantLoginLogo) {
		this.smerchantLoginLogo = smerchantLoginLogo;
	}

	public Integer getIwechatOpen() {
		return iwechatOpen;
	}

	public void setIwechatOpen(Integer iwechatOpen) {
		this.iwechatOpen = iwechatOpen;
	}

	public Integer getIaipayOpen() {
		return iaipayOpen;
	}

	public void setIaipayOpen(Integer iaipayOpen) {
		this.iaipayOpen = iaipayOpen;
	}

	@Override
	public String toString() {
		return "AuthorizationVO{" +
				"smerchantId='" + smerchantId + '\'' +
				", smerchantCode='" + smerchantCode + '\'' +
				", smerchantName='" + smerchantName + '\'' +
				", sshortName='" + sshortName + '\'' +
				", smerchantLogo='" + smerchantLogo + '\'' +
				", smerchantLoginLogo='" + smerchantLoginLogo + '\'' +
				", id='" + id + '\'' +
				", userName='" + userName + '\'' +
				", code='" + code + '\'' +
				", freeze=" + freeze +
				", password='" + password + '\'' +
				", realName='" + realName + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				", bisdAdmin=" + bisdAdmin +
				", lastLoginTime='" + lastLoginTime + '\'' +
				", isAuth=" + isAuth +
				", isReplenishment=" + isReplenishment +
				", memberType=" + memberType +
				", sex=" + sex +
				", lastLoginIp='" + lastLoginIp + '\'' +
				", regIp='" + regIp + '\'' +
				", regDeviceCode='" + regDeviceCode + '\'' +
				", regPlatform='" + regPlatform + '\'' +
				", registerTime=" + registerTime +
				", imageIco='" + imageIco + '\'' +
				", memberLevel=" + memberLevel +
				", isOrder=" + isOrder +
				", birthDay=" + birthDay +
				", fundAccountId='" + fundAccountId + '\'' +
				", fundAccountCode='" + fundAccountCode + '\'' +
				", trusteeId='" + trusteeId + '\'' +
				", trusteeAccountCode='" + trusteeAccountCode + '\'' +
				", isSetTradePass=" + isSetTradePass +
				", sidNo='" + sidNo + '\'' +
				", realNameTime='" + realNameTime + '\'' +
				", iwechatOpen=" + iwechatOpen +
				", iaipayOpen=" + iaipayOpen +
				", ext=" + ext +
				'}';
	}
}
