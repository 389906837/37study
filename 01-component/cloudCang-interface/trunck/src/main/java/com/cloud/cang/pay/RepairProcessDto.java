package com.cloud.cang.pay;

import com.cloud.cang.common.SuperDto;

/**
 * 补处理接口参数
 * @author zhouhong
 *
 */
public class RepairProcessDto extends SuperDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5495615166799644845L;

	private String smerchantCode;//商户编号
	private String sorderId;//订单Id
	private String sordercode;//订单编号
	private String smemberId;//会员ID
	
	private Integer itype;//补处理类型  10 支付 20 退款
	
	//支付补处理、退款补处理
	private String spaySerialNumber;//支付交易流水号  如果有就传
	//退款补处理
	private String srefundNo;//退款编号  如果有就传
	private String srefundSerialNumber;//退款流水号  如果有就传
	private String srefundId;//退款必传
	private Integer ipayType;//订单支付方式 30 微信 40 支付宝


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
	public String getSpaySerialNumber() {
		return spaySerialNumber;
	}
	public void setSpaySerialNumber(String spaySerialNumber) {
		this.spaySerialNumber = spaySerialNumber;
	}
	public String getSrefundNo() {
		return srefundNo;
	}
	public void setSrefundNo(String srefundNo) {
		this.srefundNo = srefundNo;
	}
	public String getSrefundSerialNumber() {
		return srefundSerialNumber;
	}
	public void setSrefundSerialNumber(String srefundSerialNumber) {
		this.srefundSerialNumber = srefundSerialNumber;
	}
	public Integer getItype() {
		return itype;
	}
	public void setItype(Integer itype) {
		this.itype = itype;
	}
	public String getSrefundId() {
		return srefundId;
	}
	public void setSrefundId(String srefundId) {
		this.srefundId = srefundId;
	}
	public Integer getIpayType() {
		return ipayType;
	}
	public void setIpayType(Integer ipayType) {
		this.ipayType = ipayType;
	}
	public String getSordercode() {
		return sordercode;
	}
	public void setSordercode(String sordercode) {
		this.sordercode = sordercode;
	}

	@Override
	public String toString() {
		return "RepairProcessDto{" +
				"smerchantCode='" + smerchantCode + '\'' +
				", sorderId='" + sorderId + '\'' +
				", sordercode='" + sordercode + '\'' +
				", smemberId='" + smemberId + '\'' +
				", itype=" + itype +
				", spaySerialNumber='" + spaySerialNumber + '\'' +
				", srefundNo='" + srefundNo + '\'' +
				", srefundSerialNumber='" + srefundSerialNumber + '\'' +
				", srefundId='" + srefundId + '\'' +
				", ipayType=" + ipayType +
				'}';
	}
}
