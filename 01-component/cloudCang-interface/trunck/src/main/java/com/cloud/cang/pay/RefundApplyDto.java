package com.cloud.cang.pay;


import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

public class RefundApplyDto extends SuperDto {

	private static final long serialVersionUID = -8138082486960651840L;
	//======必填=========
	private String smerchantCode;//商户编号
	private String sorderId;//订单Id
	private String sorderCode;//订单编号
	private String sauditOrderId;//退款审核订单ID
	private String sauditOrderCode;//退款审核订单编号
	private String stransactionId;//支付交易流水号
	private BigDecimal ftotalMoney;//订单实付总额
	private BigDecimal frefundMoney;//退款金额
	private BigDecimal frefundPoint;//申请退款积分
	private String smemberId;//会员ID
	private String smemberCode;//会员编号
	private String smemberName;//会员名
	private String sip;//用户IP
	/*	支付类型
	10=账户余额
	20=银行卡
	30=微信支付
	40=支付宝
	50=银联支付
	60=现金支付
	70=第三方支付
	80=其他*/
	private Integer ipayType;//退款渠道

	//======选填=========
	//一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号
	private String srefundNo;//退款编号  如果有就传
	private String operName;//操作人名称
	private String sremark;//备注


	public BigDecimal getFrefundPoint() {
		return frefundPoint;
	}

	public void setFrefundPoint(BigDecimal frefundPoint) {
		this.frefundPoint = frefundPoint;
	}

	public String getSmerchantCode() {
		return smerchantCode;
	}

	public void setSmerchantCode(String smerchantCode) {
		this.smerchantCode = smerchantCode;
	}

	public String getSorderId() {
		return sorderId;
	}

	public void setSorderId(String sorderId) {
		this.sorderId = sorderId;
	}

	public String getSorderCode() {
		return sorderCode;
	}

	public void setSorderCode(String sorderCode) {
		this.sorderCode = sorderCode;
	}

	public String getSauditOrderId() {
		return sauditOrderId;
	}

	public void setSauditOrderId(String sauditOrderId) {
		this.sauditOrderId = sauditOrderId;
	}

	public String getSauditOrderCode() {
		return sauditOrderCode;
	}

	public void setSauditOrderCode(String sauditOrderCode) {
		this.sauditOrderCode = sauditOrderCode;
	}

	public String getStransactionId() {
		return stransactionId;
	}

	public void setStransactionId(String stransactionId) {
		this.stransactionId = stransactionId;
	}

	public BigDecimal getFtotalMoney() {
		return ftotalMoney;
	}

	public void setFtotalMoney(BigDecimal ftotalMoney) {
		this.ftotalMoney = ftotalMoney;
	}

	public BigDecimal getFrefundMoney() {
		return frefundMoney;
	}

	public void setFrefundMoney(BigDecimal frefundMoney) {
		this.frefundMoney = frefundMoney;
	}

	public String getSmemberId() {
		return smemberId;
	}

	public void setSmemberId(String smemberId) {
		this.smemberId = smemberId;
	}

	public String getSmemberCode() {
		return smemberCode;
	}

	public void setSmemberCode(String smemberCode) {
		this.smemberCode = smemberCode;
	}

	public String getSmemberName() {
		return smemberName;
	}

	public void setSmemberName(String smemberName) {
		this.smemberName = smemberName;
	}

	public String getSip() {
		return sip;
	}

	public void setSip(String sip) {
		this.sip = sip;
	}

	public String getSrefundNo() {
		return srefundNo;
	}

	public void setSrefundNo(String srefundNo) {
		this.srefundNo = srefundNo;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getSremark() {
		return sremark;
	}

	public void setSremark(String sremark) {
		this.sremark = sremark;
	}

	public Integer getIpayType() {
		return ipayType;
	}

	public void setIpayType(Integer ipayType) {
		this.ipayType = ipayType;
	}

	@Override
	public String toString() {
		return "RefundApplyDto{" +
				"smerchantCode='" + smerchantCode + '\'' +
				", sorderId='" + sorderId + '\'' +
				", sorderCode='" + sorderCode + '\'' +
				", sauditOrderId='" + sauditOrderId + '\'' +
				", sauditOrderCode='" + sauditOrderCode + '\'' +
				", stransactionId='" + stransactionId + '\'' +
				", ftotalMoney=" + ftotalMoney +
				", frefundMoney=" + frefundMoney +
				", smemberId='" + smemberId + '\'' +
				", smemberCode='" + smemberCode + '\'' +
				", smemberName='" + smemberName + '\'' +
				", sip='" + sip + '\'' +
				", ipayType=" + ipayType +
				", srefundNo='" + srefundNo + '\'' +
				", operName='" + operName + '\'' +
				", sremark='" + sremark + '\'' +
				'}';
	}
}
