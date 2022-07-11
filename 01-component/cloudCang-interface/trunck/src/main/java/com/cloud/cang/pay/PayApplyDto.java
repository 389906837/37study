package com.cloud.cang.pay;


import com.cloud.cang.common.SuperDto;

/**
 * 生成付款单
 * @author zhouhong
 *
 */
public class PayApplyDto extends SuperDto {

	private static final long serialVersionUID = 1L;
	private String smerchantCode;//商户编号
	private String sorderId;//订单Id 多个订单号用#号隔开
	private String smemberId;//会员ID
	private String smemberCode;//会员编号
	private String smemberName;//会员名
	private String ssubject;//订单标题
	private Integer iisIgnoreStatus;//是否忽略状态 0否1是
	/* 支付类型
	10=账户余额
	20=银行卡
	30=微信支付
	40=支付宝
	50=银联支付
	60=现金支付
	70=第三方支付
	80=其他 */
	private Integer ipayWay;
	/* 支付类型中支付方式
	10：公众号支付
	20：H5支付
	30：扫码支付
	40：APP支付
	50：刷卡支付
	60：小程序支付
	70：代扣 */
	private Integer ipayType;
	//=====可选========
	private String smemberOpenId;//微信JSAPI方式支付 必填
	private String body;//订单描述
	private String sremark;//付款备注

	private String sip;//操作IP
	private String sreturnUrl;//支付返回商户地址

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

	public String getSsubject() {
		return ssubject;
	}

	public void setSsubject(String ssubject) {
		this.ssubject = ssubject;
	}

	public Integer getIisIgnoreStatus() {
		return iisIgnoreStatus;
	}

	public void setIisIgnoreStatus(Integer iisIgnoreStatus) {
		this.iisIgnoreStatus = iisIgnoreStatus;
	}

	public Integer getIpayWay() {
		return ipayWay;
	}

	public void setIpayWay(Integer ipayWay) {
		this.ipayWay = ipayWay;
	}

	public Integer getIpayType() {
		return ipayType;
	}

	public void setIpayType(Integer ipayType) {
		this.ipayType = ipayType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSremark() {
		return sremark;
	}

	public void setSremark(String sremark) {
		this.sremark = sremark;
	}

	public String getSmemberOpenId() {
		return smemberOpenId;
	}

	public void setSmemberOpenId(String smemberOpenId) {
		this.smemberOpenId = smemberOpenId;
	}

	public String getSreturnUrl() {
		return sreturnUrl;
	}

	public void setSreturnUrl(String sreturnUrl) {
		this.sreturnUrl = sreturnUrl;
	}

	public String getSip() {
		return sip;
	}

	public void setSip(String sip) {
		this.sip = sip;
	}

	@Override
	public String toString() {
		return "PayApplyDto{" +
				"smerchantCode='" + smerchantCode + '\'' +
				", sorderId='" + sorderId + '\'' +
				", smemberId='" + smemberId + '\'' +
				", smemberCode='" + smemberCode + '\'' +
				", smemberName='" + smemberName + '\'' +
				", ssubject='" + ssubject + '\'' +
				", iisIgnoreStatus=" + iisIgnoreStatus +
				", ipayWay=" + ipayWay +
				", ipayType=" + ipayType +
				", smemberOpenId='" + smemberOpenId + '\'' +
				", body='" + body + '\'' +
				", sremark='" + sremark + '\'' +
				", sip='" + sip + '\'' +
				", sreturnUrl='" + sreturnUrl + '\'' +
				'}';
	}
}