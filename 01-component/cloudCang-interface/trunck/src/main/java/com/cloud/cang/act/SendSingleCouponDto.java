package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

/**
 * 单个会员补发参数
 * @author yanlingfeng
 * @version 1.0
 */
public class SendSingleCouponDto extends SuperDto {
	
	private static final long serialVersionUID = -7699244452027092376L;
	
	/**
	 * 会员Id
	 */
	private String memberId;
	
	/**
	 * 来源单据编号
	 */
	private String sourceCode;
	
	/**
	 * 批量下发券配置id
	 */
	private String couponBatchId;
	
	/**
	 * 来源单据类型
	 */
	private Integer sourceType;
	
	/**
	 * 来源单据类型说明
	 */
	private String sourceTypeDesc;
	
	/**
	 * 发送数量
	 */
	private Integer sendCount = 1;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getCouponBatchId() {
		return couponBatchId;
	}

	public void setCouponBatchId(String couponBatchId) {
		this.couponBatchId = couponBatchId;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceTypeDesc() {
		return sourceTypeDesc;
	}

	public void setSourceTypeDesc(String sourceTypeDesc) {
		this.sourceTypeDesc = sourceTypeDesc;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	@Override
	public String toString() {
		return "SendSingleCouponDto [memberId=" + memberId + ", sourceCode="
				+ sourceCode + ", couponBatchId=" + couponBatchId
				+ ", sourceType=" + sourceType + ", sourceTypeDesc="
				+ sourceTypeDesc + ", sendCount=" + sendCount + ", serialNum="
				+ serialNum + "]";
	}

	

}
