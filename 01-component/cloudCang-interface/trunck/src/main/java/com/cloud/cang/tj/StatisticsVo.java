/*
 * Copyright (C) 2016 hurbao All rights reserved
 * Author: zhouhong
 * Date: 2016年4月5日
 * Description:StatisticsVo.java 
 */
package com.cloud.cang.tj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 平台数据统计
 * @author 周红
 * @version 1.0
 */
public class StatisticsVo implements Serializable {

	private static final long serialVersionUID = 6830395218747116081L;
	
	/**
	 * 总订单额
	 */
	private BigDecimal orderTotalAmount;

	/**
	 * 总订单优惠总额
	 */
	private BigDecimal discountTotalAmount;

	/**
	 * 总订单首单优惠金额
	 */
	private BigDecimal firstDiscountTotalAmount;

	/**
	 * 总订单促销优惠金额
	 */
	private BigDecimal promotionDiscountTotalAmount;

	/**
	 * 总订单优惠券抵扣金额
	 */
	private BigDecimal couponDeductionTotalAmount;

	/**
	 * 总订单积分优惠金额
	 */
	private BigDecimal pointDiscountTotalAmount;

	/**
	 * 总订单其他优惠金额
	 */
	private BigDecimal otherDiscountTotalAmount;

	/**
	 * 总订单实付金额
	 */
	private BigDecimal actualPayTotalAmount;

	/**
	 * 总订单成功支付金额
	 */
	private BigDecimal orderSuccessTotalAmount;

	/**
	 * 总订单成功支付失败金额或未付款金额
	 */
	private BigDecimal orderFailTotalAmount;

	/**
	 * 总订单笔数
	 */
	private Integer orderTotalNum;

	/**
	 * 总订单人数
	 */
	private Integer orderManTotalNum;

	/**
	 * 总退款笔数
	 */
	private Integer refundTotalNum;

	/**
	 * 总退款金额
	 */
	private BigDecimal refundTotalAmount;

	/**
	 * 总退款审核笔数
	 */
	private Integer refundAuditTotalNum;

	/**
	 * 总退款审核成功笔数
	 */
	private Integer refundAuditSuccessTotalNum;

	/**
	 * 总退款审核拒绝笔数
	 */
	private Integer refundAuditFailTotalNum;

	/**
	 * 总订单成功退款额
	 */
	private BigDecimal refundSuccessTotalAmount;

	/**
	 * 总异常订单笔数
	 */
	private Integer abnormalTotalNum;

	/**
	 * 总异常订单处理笔数
	 */
	private Integer abnormalDealwithNum;

	/**
	 * 总异常订单忽略笔数
	 */
	private Integer abnormalIgnoreNum;

	/**
	 * 总异常订单扣款笔数
	 */
	private Integer abnormalChargebackNum;

	/**
	 * 总注册人数
	 */
	private Integer regTotalNum;

	/**
	 * 总投放设备数量
	 */
	private Integer deviceTotalNum;

	/**
	 * 总访客次数（包含未购物）
	 */
	private Integer memberTotalNum;

	/**
	 * 总未购物次数
	 */
	private Integer visitorsNum;

	/**
	 * 总补货次数
	 */
	private Integer replenishmentNum;

	/**
	 * 总未生成补货单次数
	 */
	private Integer notReplenishmentTotalNum;

	/**
	 * 当天订单金额
	 */
	private BigDecimal orderTodayAmount;

	/**
	 * 当天订单优惠总额
	 */
	private BigDecimal discountTodayAmount;

	/**
	 * 当天订单首单优惠金额
	 */
	private BigDecimal firstDiscountTodayAmount;

	/**
	 * 当天订单促销优惠金额
	 */
	private BigDecimal promotionDiscountTodayAmount;

	/**
	 * 当天订单优惠券抵扣金额
	 */
	private BigDecimal couponDeductionTodayAmount;

	/**
	 * 当天订单积分优惠金额
	 */
	private BigDecimal pointDiscountTodayAmount;

	/**
	 * 当天订单其他优惠金额
	 */
	private BigDecimal otherDiscountTodayAmount;

	/**
	 * 当天订单实付金额
	 */
	private BigDecimal actualPayTodayAmount;

	/**
	 * 当天订单成功支付金额
	 */
	private BigDecimal orderSuccessTodayAmount;

	/**
	 * 当天订单成功支付失败金额或未付款金额
	 */
	private BigDecimal orderFailTodayAmount;

	/**
	 * 当天订单笔数
	 */
	private Integer orderTodayNum;

	/**
	 * 当天订单人数
	 */
	private Integer orderManTodayNum;

	/**
	 * 当天退款笔数
	 */
	private Integer refundTodayNum;

	/**
	 * 当天退款金额
	 */
	private BigDecimal refundTodayAmount;

	/**
	 * 当天退款审核笔数
	 */
	private Integer refundAuditTodayNum;

	/**
	 * 当天退款审核成功笔数
	 */
	private Integer refundAuditSuccessTodayNum;

	/**
	 * 当天退款审核拒绝笔数
	 */
	private Integer refundAuditFailTodayNum;

	/**
	 * 当天订单成功退款金额
	 */
	private BigDecimal refundSuccessTodayAmount;

	/**
	 * 当天异常订单笔数
	 */
	private Integer abnormalTodayNum;

	/**
	 * 当天异常订单处理笔数
	 */
	private Integer abnormalDealwithTodayNum;

	/**
	 * 当天异常订单忽略笔数
	 */
	private Integer abnormalIgnoreTodayNum;

	/**
	 * 当天异常订单扣款笔数
	 */
	private Integer abnormalChargebackTodayNum;

	/**
	 * 当天注册人数
	 */
	private Integer regTodayNum;

	/**
	 * 当天投放设备数量
	 */
	private Integer deviceTodayNum;

	/**
	 * 当天访客次数（包含未购物）
	 */
	private Integer memberTodayNum;

	/**
	 * 当天未购物次数
	 */
	private Integer visitorsTodayNum;

	/**
	 * 当天补货次数
	 */
	private Integer replenishmentTodayNum;

	/**
	 * 当天未生成补货单次数
	 */
	private Integer notReplenishmentTodayNum;

	/**
	 * 待付款订单笔数 包含 支付处理中
	 */
	private Integer waitPayOrderNum;

	/**
	 * 付款失败订单笔数
	 */
	private Integer payFailureOrderNum;


	/**
	 * 待付款订单金额 包含 支付处理中
	 */
	private BigDecimal waitPayOrderAmount;

	/**
	 * 付款失败订单金额
	 */
	private BigDecimal payFailureOrderAmount;

	/**
	 * 统计时间
	 */
	private Date statisticDate;

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public BigDecimal getOrderSuccessTotalAmount() {
		return orderSuccessTotalAmount;
	}

	public void setOrderSuccessTotalAmount(BigDecimal orderSuccessTotalAmount) {
		this.orderSuccessTotalAmount = orderSuccessTotalAmount;
	}

	public BigDecimal getOrderFailTotalAmount() {
		return orderFailTotalAmount;
	}

	public void setOrderFailTotalAmount(BigDecimal orderFailTotalAmount) {
		this.orderFailTotalAmount = orderFailTotalAmount;
	}

	public Integer getOrderTotalNum() {
		return orderTotalNum;
	}

	public void setOrderTotalNum(Integer orderTotalNum) {
		this.orderTotalNum = orderTotalNum;
	}

	public Integer getOrderManTotalNum() {
		return orderManTotalNum;
	}

	public void setOrderManTotalNum(Integer orderManTotalNum) {
		this.orderManTotalNum = orderManTotalNum;
	}

	public Integer getRefundTotalNum() {
		return refundTotalNum;
	}

	public void setRefundTotalNum(Integer refundTotalNum) {
		this.refundTotalNum = refundTotalNum;
	}

	public BigDecimal getRefundTotalAmount() {
		return refundTotalAmount;
	}

	public void setRefundTotalAmount(BigDecimal refundTotalAmount) {
		this.refundTotalAmount = refundTotalAmount;
	}

	public Integer getRefundAuditTotalNum() {
		return refundAuditTotalNum;
	}

	public void setRefundAuditTotalNum(Integer refundAuditTotalNum) {
		this.refundAuditTotalNum = refundAuditTotalNum;
	}

	public Integer getRefundAuditSuccessTotalNum() {
		return refundAuditSuccessTotalNum;
	}

	public void setRefundAuditSuccessTotalNum(Integer refundAuditSuccessTotalNum) {
		this.refundAuditSuccessTotalNum = refundAuditSuccessTotalNum;
	}

	public Integer getRefundAuditFailTotalNum() {
		return refundAuditFailTotalNum;
	}

	public void setRefundAuditFailTotalNum(Integer refundAuditFailTotalNum) {
		this.refundAuditFailTotalNum = refundAuditFailTotalNum;
	}

	public BigDecimal getRefundSuccessTotalAmount() {
		return refundSuccessTotalAmount;
	}

	public void setRefundSuccessTotalAmount(BigDecimal refundSuccessTotalAmount) {
		this.refundSuccessTotalAmount = refundSuccessTotalAmount;
	}

	public Integer getAbnormalTotalNum() {
		return abnormalTotalNum;
	}

	public void setAbnormalTotalNum(Integer abnormalTotalNum) {
		this.abnormalTotalNum = abnormalTotalNum;
	}

	public Integer getAbnormalDealwithNum() {
		return abnormalDealwithNum;
	}

	public void setAbnormalDealwithNum(Integer abnormalDealwithNum) {
		this.abnormalDealwithNum = abnormalDealwithNum;
	}

	public Integer getAbnormalIgnoreNum() {
		return abnormalIgnoreNum;
	}

	public void setAbnormalIgnoreNum(Integer abnormalIgnoreNum) {
		this.abnormalIgnoreNum = abnormalIgnoreNum;
	}

	public Integer getAbnormalChargebackNum() {
		return abnormalChargebackNum;
	}

	public void setAbnormalChargebackNum(Integer abnormalChargebackNum) {
		this.abnormalChargebackNum = abnormalChargebackNum;
	}

	public Integer getRegTotalNum() {
		return regTotalNum;
	}

	public void setRegTotalNum(Integer regTotalNum) {
		this.regTotalNum = regTotalNum;
	}

	public Integer getDeviceTotalNum() {
		return deviceTotalNum;
	}

	public void setDeviceTotalNum(Integer deviceTotalNum) {
		this.deviceTotalNum = deviceTotalNum;
	}

	public Integer getVisitorsNum() {
		return visitorsNum;
	}

	public void setVisitorsNum(Integer visitorsNum) {
		this.visitorsNum = visitorsNum;
	}

	public Integer getReplenishmentNum() {
		return replenishmentNum;
	}

	public void setReplenishmentNum(Integer replenishmentNum) {
		this.replenishmentNum = replenishmentNum;
	}

	public BigDecimal getOrderTodayAmount() {
		return orderTodayAmount;
	}

	public void setOrderTodayAmount(BigDecimal orderTodayAmount) {
		this.orderTodayAmount = orderTodayAmount;
	}

	public BigDecimal getOrderSuccessTodayAmount() {
		return orderSuccessTodayAmount;
	}

	public void setOrderSuccessTodayAmount(BigDecimal orderSuccessTodayAmount) {
		this.orderSuccessTodayAmount = orderSuccessTodayAmount;
	}

	public BigDecimal getOrderFailTodayAmount() {
		return orderFailTodayAmount;
	}

	public void setOrderFailTodayAmount(BigDecimal orderFailTodayAmount) {
		this.orderFailTodayAmount = orderFailTodayAmount;
	}

	public Integer getOrderTodayNum() {
		return orderTodayNum;
	}

	public void setOrderTodayNum(Integer orderTodayNum) {
		this.orderTodayNum = orderTodayNum;
	}

	public Integer getOrderManTodayNum() {
		return orderManTodayNum;
	}

	public void setOrderManTodayNum(Integer orderManTodayNum) {
		this.orderManTodayNum = orderManTodayNum;
	}

	public Integer getRefundTodayNum() {
		return refundTodayNum;
	}

	public void setRefundTodayNum(Integer refundTodayNum) {
		this.refundTodayNum = refundTodayNum;
	}

	public BigDecimal getRefundTodayAmount() {
		return refundTodayAmount;
	}

	public void setRefundTodayAmount(BigDecimal refundTodayAmount) {
		this.refundTodayAmount = refundTodayAmount;
	}

	public Integer getRefundAuditTodayNum() {
		return refundAuditTodayNum;
	}

	public void setRefundAuditTodayNum(Integer refundAuditTodayNum) {
		this.refundAuditTodayNum = refundAuditTodayNum;
	}

	public Integer getRefundAuditSuccessTodayNum() {
		return refundAuditSuccessTodayNum;
	}

	public void setRefundAuditSuccessTodayNum(Integer refundAuditSuccessTodayNum) {
		this.refundAuditSuccessTodayNum = refundAuditSuccessTodayNum;
	}

	public Integer getRefundAuditFailTodayNum() {
		return refundAuditFailTodayNum;
	}

	public void setRefundAuditFailTodayNum(Integer refundAuditFailTodayNum) {
		this.refundAuditFailTodayNum = refundAuditFailTodayNum;
	}

	public BigDecimal getRefundSuccessTodayAmount() {
		return refundSuccessTodayAmount;
	}

	public void setRefundSuccessTodayAmount(BigDecimal refundSuccessTodayAmount) {
		this.refundSuccessTodayAmount = refundSuccessTodayAmount;
	}

	public Integer getAbnormalTodayNum() {
		return abnormalTodayNum;
	}

	public void setAbnormalTodayNum(Integer abnormalTodayNum) {
		this.abnormalTodayNum = abnormalTodayNum;
	}

	public Integer getAbnormalDealwithTodayNum() {
		return abnormalDealwithTodayNum;
	}

	public void setAbnormalDealwithTodayNum(Integer abnormalDealwithTodayNum) {
		this.abnormalDealwithTodayNum = abnormalDealwithTodayNum;
	}

	public Integer getAbnormalIgnoreTodayNum() {
		return abnormalIgnoreTodayNum;
	}

	public void setAbnormalIgnoreTodayNum(Integer abnormalIgnoreTodayNum) {
		this.abnormalIgnoreTodayNum = abnormalIgnoreTodayNum;
	}

	public Integer getAbnormalChargebackTodayNum() {
		return abnormalChargebackTodayNum;
	}

	public void setAbnormalChargebackTodayNum(Integer abnormalChargebackTodayNum) {
		this.abnormalChargebackTodayNum = abnormalChargebackTodayNum;
	}

	public Integer getRegTodayNum() {
		return regTodayNum;
	}

	public void setRegTodayNum(Integer regTodayNum) {
		this.regTodayNum = regTodayNum;
	}

	public Integer getDeviceTodayNum() {
		return deviceTodayNum;
	}

	public void setDeviceTodayNum(Integer deviceTodayNum) {
		this.deviceTodayNum = deviceTodayNum;
	}

	public Integer getVisitorsTodayNum() {
		return visitorsTodayNum;
	}

	public void setVisitorsTodayNum(Integer visitorsTodayNum) {
		this.visitorsTodayNum = visitorsTodayNum;
	}

	public Integer getReplenishmentTodayNum() {
		return replenishmentTodayNum;
	}

	public void setReplenishmentTodayNum(Integer replenishmentTodayNum) {
		this.replenishmentTodayNum = replenishmentTodayNum;
	}

	public Integer getWaitPayOrderNum() {
		return waitPayOrderNum;
	}

	public void setWaitPayOrderNum(Integer waitPayOrderNum) {
		this.waitPayOrderNum = waitPayOrderNum;
	}

	public Integer getPayFailureOrderNum() {
		return payFailureOrderNum;
	}

	public void setPayFailureOrderNum(Integer payFailureOrderNum) {
		this.payFailureOrderNum = payFailureOrderNum;
	}

	public Date getStatisticDate() {
		return statisticDate;
	}

	public void setStatisticDate(Date statisticDate) {
		this.statisticDate = statisticDate;
	}

	public BigDecimal getWaitPayOrderAmount() {
		return waitPayOrderAmount;
	}

	public void setWaitPayOrderAmount(BigDecimal waitPayOrderAmount) {
		this.waitPayOrderAmount = waitPayOrderAmount;
	}

	public BigDecimal getPayFailureOrderAmount() {
		return payFailureOrderAmount;
	}

	public void setPayFailureOrderAmount(BigDecimal payFailureOrderAmount) {
		this.payFailureOrderAmount = payFailureOrderAmount;
	}

	public BigDecimal getDiscountTotalAmount() {
		return discountTotalAmount;
	}

	public void setDiscountTotalAmount(BigDecimal discountTotalAmount) {
		this.discountTotalAmount = discountTotalAmount;
	}

	public BigDecimal getFirstDiscountTotalAmount() {
		return firstDiscountTotalAmount;
	}

	public void setFirstDiscountTotalAmount(BigDecimal firstDiscountTotalAmount) {
		this.firstDiscountTotalAmount = firstDiscountTotalAmount;
	}

	public BigDecimal getPromotionDiscountTotalAmount() {
		return promotionDiscountTotalAmount;
	}

	public void setPromotionDiscountTotalAmount(BigDecimal promotionDiscountTotalAmount) {
		this.promotionDiscountTotalAmount = promotionDiscountTotalAmount;
	}

	public BigDecimal getCouponDeductionTotalAmount() {
		return couponDeductionTotalAmount;
	}

	public void setCouponDeductionTotalAmount(BigDecimal couponDeductionTotalAmount) {
		this.couponDeductionTotalAmount = couponDeductionTotalAmount;
	}

	public BigDecimal getPointDiscountTotalAmount() {
		return pointDiscountTotalAmount;
	}

	public void setPointDiscountTotalAmount(BigDecimal pointDiscountTotalAmount) {
		this.pointDiscountTotalAmount = pointDiscountTotalAmount;
	}

	public BigDecimal getOtherDiscountTotalAmount() {
		return otherDiscountTotalAmount;
	}

	public void setOtherDiscountTotalAmount(BigDecimal otherDiscountTotalAmount) {
		this.otherDiscountTotalAmount = otherDiscountTotalAmount;
	}

	public BigDecimal getActualPayTotalAmount() {
		return actualPayTotalAmount;
	}

	public void setActualPayTotalAmount(BigDecimal actualPayTotalAmount) {
		this.actualPayTotalAmount = actualPayTotalAmount;
	}

	public Integer getMemberTotalNum() {
		return memberTotalNum;
	}

	public void setMemberTotalNum(Integer memberTotalNum) {
		this.memberTotalNum = memberTotalNum;
	}

	public BigDecimal getDiscountTodayAmount() {
		return discountTodayAmount;
	}

	public void setDiscountTodayAmount(BigDecimal discountTodayAmount) {
		this.discountTodayAmount = discountTodayAmount;
	}

	public BigDecimal getFirstDiscountTodayAmount() {
		return firstDiscountTodayAmount;
	}

	public void setFirstDiscountTodayAmount(BigDecimal firstDiscountTodayAmount) {
		this.firstDiscountTodayAmount = firstDiscountTodayAmount;
	}

	public BigDecimal getPromotionDiscountTodayAmount() {
		return promotionDiscountTodayAmount;
	}

	public void setPromotionDiscountTodayAmount(BigDecimal promotionDiscountTodayAmount) {
		this.promotionDiscountTodayAmount = promotionDiscountTodayAmount;
	}

	public BigDecimal getCouponDeductionTodayAmount() {
		return couponDeductionTodayAmount;
	}

	public void setCouponDeductionTodayAmount(BigDecimal couponDeductionTodayAmount) {
		this.couponDeductionTodayAmount = couponDeductionTodayAmount;
	}

	public BigDecimal getPointDiscountTodayAmount() {
		return pointDiscountTodayAmount;
	}

	public void setPointDiscountTodayAmount(BigDecimal pointDiscountTodayAmount) {
		this.pointDiscountTodayAmount = pointDiscountTodayAmount;
	}

	public BigDecimal getOtherDiscountTodayAmount() {
		return otherDiscountTodayAmount;
	}

	public void setOtherDiscountTodayAmount(BigDecimal otherDiscountTodayAmount) {
		this.otherDiscountTodayAmount = otherDiscountTodayAmount;
	}

	public BigDecimal getActualPayTodayAmount() {
		return actualPayTodayAmount;
	}

	public void setActualPayTodayAmount(BigDecimal actualPayTodayAmount) {
		this.actualPayTodayAmount = actualPayTodayAmount;
	}

	public Integer getMemberTodayNum() {
		return memberTodayNum;
	}

	public void setMemberTodayNum(Integer memberTodayNum) {
		this.memberTodayNum = memberTodayNum;
	}

	public Integer getNotReplenishmentTotalNum() {
		return notReplenishmentTotalNum;
	}

	public void setNotReplenishmentTotalNum(Integer notReplenishmentTotalNum) {
		this.notReplenishmentTotalNum = notReplenishmentTotalNum;
	}

	public Integer getNotReplenishmentTodayNum() {
		return notReplenishmentTodayNum;
	}

	public void setNotReplenishmentTodayNum(Integer notReplenishmentTodayNum) {
		this.notReplenishmentTodayNum = notReplenishmentTodayNum;
	}

	@Override
	public String toString() {
		return "StatisticsVo{" +
				"orderTotalAmount=" + orderTotalAmount +
				", discountTotalAmount=" + discountTotalAmount +
				", firstDiscountTotalAmount=" + firstDiscountTotalAmount +
				", promotionDiscountTotalAmount=" + promotionDiscountTotalAmount +
				", couponDeductionTotalAmount=" + couponDeductionTotalAmount +
				", pointDiscountTotalAmount=" + pointDiscountTotalAmount +
				", otherDiscountTotalAmount=" + otherDiscountTotalAmount +
				", actualPayTotalAmount=" + actualPayTotalAmount +
				", orderSuccessTotalAmount=" + orderSuccessTotalAmount +
				", orderFailTotalAmount=" + orderFailTotalAmount +
				", orderTotalNum=" + orderTotalNum +
				", orderManTotalNum=" + orderManTotalNum +
				", refundTotalNum=" + refundTotalNum +
				", refundTotalAmount=" + refundTotalAmount +
				", refundAuditTotalNum=" + refundAuditTotalNum +
				", refundAuditSuccessTotalNum=" + refundAuditSuccessTotalNum +
				", refundAuditFailTotalNum=" + refundAuditFailTotalNum +
				", refundSuccessTotalAmount=" + refundSuccessTotalAmount +
				", abnormalTotalNum=" + abnormalTotalNum +
				", abnormalDealwithNum=" + abnormalDealwithNum +
				", abnormalIgnoreNum=" + abnormalIgnoreNum +
				", abnormalChargebackNum=" + abnormalChargebackNum +
				", regTotalNum=" + regTotalNum +
				", deviceTotalNum=" + deviceTotalNum +
				", memberTotalNum=" + memberTotalNum +
				", visitorsNum=" + visitorsNum +
				", replenishmentNum=" + replenishmentNum +
				", notReplenishmentTotalNum=" + notReplenishmentTotalNum +
				", orderTodayAmount=" + orderTodayAmount +
				", discountTodayAmount=" + discountTodayAmount +
				", firstDiscountTodayAmount=" + firstDiscountTodayAmount +
				", promotionDiscountTodayAmount=" + promotionDiscountTodayAmount +
				", couponDeductionTodayAmount=" + couponDeductionTodayAmount +
				", pointDiscountTodayAmount=" + pointDiscountTodayAmount +
				", otherDiscountTodayAmount=" + otherDiscountTodayAmount +
				", actualPayTodayAmount=" + actualPayTodayAmount +
				", orderSuccessTodayAmount=" + orderSuccessTodayAmount +
				", orderFailTodayAmount=" + orderFailTodayAmount +
				", orderTodayNum=" + orderTodayNum +
				", orderManTodayNum=" + orderManTodayNum +
				", refundTodayNum=" + refundTodayNum +
				", refundTodayAmount=" + refundTodayAmount +
				", refundAuditTodayNum=" + refundAuditTodayNum +
				", refundAuditSuccessTodayNum=" + refundAuditSuccessTodayNum +
				", refundAuditFailTodayNum=" + refundAuditFailTodayNum +
				", refundSuccessTodayAmount=" + refundSuccessTodayAmount +
				", abnormalTodayNum=" + abnormalTodayNum +
				", abnormalDealwithTodayNum=" + abnormalDealwithTodayNum +
				", abnormalIgnoreTodayNum=" + abnormalIgnoreTodayNum +
				", abnormalChargebackTodayNum=" + abnormalChargebackTodayNum +
				", regTodayNum=" + regTodayNum +
				", deviceTodayNum=" + deviceTodayNum +
				", memberTodayNum=" + memberTodayNum +
				", visitorsTodayNum=" + visitorsTodayNum +
				", replenishmentTodayNum=" + replenishmentTodayNum +
				", notReplenishmentTodayNum=" + notReplenishmentTodayNum +
				", waitPayOrderNum=" + waitPayOrderNum +
				", payFailureOrderNum=" + payFailureOrderNum +
				", waitPayOrderAmount=" + waitPayOrderAmount +
				", payFailureOrderAmount=" + payFailureOrderAmount +
				", statisticDate=" + statisticDate +
				'}';
	}
}
