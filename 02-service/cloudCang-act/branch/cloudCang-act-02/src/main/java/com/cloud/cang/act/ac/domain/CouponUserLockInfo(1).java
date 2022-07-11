package com.cloud.cang.act.ac.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.cloud.cang.model.ac.CouponUser;

public class CouponUserLockInfo extends CouponUser{
	
	private static final long serialVersionUID = -9114306156236013227L;
	private String unlockId;   //主键ID
	private BigDecimal funlockUseInvestmentAmount;   //使用要求额度投资时，投资金额大于该值时说明可以使用这个券
	private Integer iunlockUserInvestmentDuration;   //使用要求投资期限
	private Integer iunlockUserPrjField;   //使用要求栏目
	private String sunlockUserPrjId;   //使用要求项目
	private String sunlockUserPrjType;   //使用要求项目类型
	private Integer iunlockUseSettlementWay;   //使用要求结息方式
	private String sunlockUserChannel;   //多个以英文逗号分割 微信,wap,android,ios,pc。全部支持填-1
	private Date tunlockEffectiveStartDate;   //规则活动开始日期
	private Date tunlockEffectiveEndDate;   //规则活动结束日期
	private String sunlockDesc;   //解锁展示说明
	private String sunlockRemark;   //备注
	private String sunlockAddUser;   //添加人
	private Date tunlockAddTime;   //添加日期
	private Integer iunlockType;   //1.充值 2.投资 3.实名认证
	public String getUnlockId() {
		return unlockId;
	}
	public void setUnlockId(String unlockId) {
		this.unlockId = unlockId;
	}
	public BigDecimal getFunlockUseInvestmentAmount() {
		return funlockUseInvestmentAmount;
	}
	public void setFunlockUseInvestmentAmount(BigDecimal funlockUseInvestmentAmount) {
		this.funlockUseInvestmentAmount = funlockUseInvestmentAmount;
	}
	public Integer getIunlockUserInvestmentDuration() {
		return iunlockUserInvestmentDuration;
	}
	public void setIunlockUserInvestmentDuration(
			Integer iunlockUserInvestmentDuration) {
		this.iunlockUserInvestmentDuration = iunlockUserInvestmentDuration;
	}
	public Integer getIunlockUserPrjField() {
		return iunlockUserPrjField;
	}
	public void setIunlockUserPrjField(Integer iunlockUserPrjField) {
		this.iunlockUserPrjField = iunlockUserPrjField;
	}
	public String getSunlockUserPrjId() {
		return sunlockUserPrjId;
	}
	public void setSunlockUserPrjId(String sunlockUserPrjId) {
		this.sunlockUserPrjId = sunlockUserPrjId;
	}
	public String getSunlockUserPrjType() {
		return sunlockUserPrjType;
	}
	public void setSunlockUserPrjType(String sunlockUserPrjType) {
		this.sunlockUserPrjType = sunlockUserPrjType;
	}
	public Integer getIunlockUseSettlementWay() {
		return iunlockUseSettlementWay;
	}
	public void setIunlockUseSettlementWay(Integer iunlockUseSettlementWay) {
		this.iunlockUseSettlementWay = iunlockUseSettlementWay;
	}
	public String getSunlockUserChannel() {
		return sunlockUserChannel;
	}
	public void setSunlockUserChannel(String sunlockUserChannel) {
		this.sunlockUserChannel = sunlockUserChannel;
	}
	public Date getTunlockEffectiveStartDate() {
		return tunlockEffectiveStartDate;
	}
	public void setTunlockEffectiveStartDate(Date tunlockEffectiveStartDate) {
		this.tunlockEffectiveStartDate = tunlockEffectiveStartDate;
	}
	public Date getTunlockEffectiveEndDate() {
		return tunlockEffectiveEndDate;
	}
	public void setTunlockEffectiveEndDate(Date tunlockEffectiveEndDate) {
		this.tunlockEffectiveEndDate = tunlockEffectiveEndDate;
	}
	public String getSunlockDesc() {
		return sunlockDesc;
	}
	public void setSunlockDesc(String sunlockDesc) {
		this.sunlockDesc = sunlockDesc;
	}
	public String getSunlockRemark() {
		return sunlockRemark;
	}
	public void setSunlockRemark(String sunlockRemark) {
		this.sunlockRemark = sunlockRemark;
	}
	public String getSunlockAddUser() {
		return sunlockAddUser;
	}
	public void setSunlockAddUser(String sunlockAddUser) {
		this.sunlockAddUser = sunlockAddUser;
	}
	public Date getTunlockAddTime() {
		return tunlockAddTime;
	}
	public void setTunlockAddTime(Date tunlockAddTime) {
		this.tunlockAddTime = tunlockAddTime;
	}
	public Integer getIunlockType() {
		return iunlockType;
	}
	public void setIunlockType(Integer iunlockType) {
		this.iunlockType = iunlockType;
	}
	
}
