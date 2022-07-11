package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.rm.ReplenishmentResult;

import java.math.BigDecimal;
import java.util.List;


/**
 * 实时订单返回结果
 * @author zhouhong
 * @version 1.0
 */
public class RealTimeOrderResult extends SuperDto {
	

	private BigDecimal ftotalAmount;//订单总金额
	private BigDecimal fdiscountAmount;//订单优惠总额
	private BigDecimal ffirstDiscountAmount;//订单首单优惠金额
	private BigDecimal fpromotionDiscountAmount;//订单促销优惠金额
	private BigDecimal fcouponDeductionAmount;//订单优惠券抵扣金额
	private BigDecimal fpointDiscountAmount;//订单积分优惠金额
	private BigDecimal fotherDiscountAmount;//订单其他优惠金额
	private BigDecimal factualPayAmount;//实付金额

	private List<RealTimeCommodityResult> results;//订单商品集合

	public BigDecimal getFtotalAmount() {
		return ftotalAmount;
	}

	public void setFtotalAmount(BigDecimal ftotalAmount) {
		this.ftotalAmount = ftotalAmount;
	}

	public BigDecimal getFdiscountAmount() {
		return fdiscountAmount;
	}

	public void setFdiscountAmount(BigDecimal fdiscountAmount) {
		this.fdiscountAmount = fdiscountAmount;
	}

	public BigDecimal getFfirstDiscountAmount() {
		return ffirstDiscountAmount;
	}

	public void setFfirstDiscountAmount(BigDecimal ffirstDiscountAmount) {
		this.ffirstDiscountAmount = ffirstDiscountAmount;
	}

	public BigDecimal getFpromotionDiscountAmount() {
		return fpromotionDiscountAmount;
	}

	public void setFpromotionDiscountAmount(BigDecimal fpromotionDiscountAmount) {
		this.fpromotionDiscountAmount = fpromotionDiscountAmount;
	}

	public BigDecimal getFcouponDeductionAmount() {
		return fcouponDeductionAmount;
	}

	public void setFcouponDeductionAmount(BigDecimal fcouponDeductionAmount) {
		this.fcouponDeductionAmount = fcouponDeductionAmount;
	}

	public BigDecimal getFpointDiscountAmount() {
		return fpointDiscountAmount;
	}

	public void setFpointDiscountAmount(BigDecimal fpointDiscountAmount) {
		this.fpointDiscountAmount = fpointDiscountAmount;
	}

	public BigDecimal getFotherDiscountAmount() {
		return fotherDiscountAmount;
	}

	public void setFotherDiscountAmount(BigDecimal fotherDiscountAmount) {
		this.fotherDiscountAmount = fotherDiscountAmount;
	}

	public BigDecimal getFactualPayAmount() {
		return factualPayAmount;
	}

	public void setFactualPayAmount(BigDecimal factualPayAmount) {
		this.factualPayAmount = factualPayAmount;
	}

	public List<RealTimeCommodityResult> getResults() {
		return results;
	}

	public void setResults(List<RealTimeCommodityResult> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "RealTimeOrderResult{" +
				"ftotalAmount=" + ftotalAmount +
				", fdiscountAmount=" + fdiscountAmount +
				", ffirstDiscountAmount=" + ffirstDiscountAmount +
				", fpromotionDiscountAmount=" + fpromotionDiscountAmount +
				", fcouponDeductionAmount=" + fcouponDeductionAmount +
				", fpointDiscountAmount=" + fpointDiscountAmount +
				", fotherDiscountAmount=" + fotherDiscountAmount +
				", factualPayAmount=" + factualPayAmount +
				", results=" + results +
				'}';
	}
}
