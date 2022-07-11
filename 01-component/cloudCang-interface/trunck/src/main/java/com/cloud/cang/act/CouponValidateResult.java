/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月8日
 * Description:CouponValidateResult.java 
 */
package com.cloud.cang.act;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 校验后返回的结果
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class CouponValidateResult {


    private String couponId;//券ID

    private String couponCode;//券编号

    private Integer couponType;//劵类型

    private String couponTypeName;//劵类型名称

    private String activityInstruction;//券说明

    private String sourceInstruction;//来源说明

    private Date couponEffectiveDate;//券生效日期

    private Date couponExpiryDate;//劵失效日期

    private BigDecimal couponValue;//券面金额

    private BigDecimal orderAmount;//订单金额

    private BigDecimal fdeductibleAmount;//抵扣金额

    private BigDecimal fuseInvestmentAmount;//券起投金额

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public BigDecimal getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(BigDecimal couponValue) {
        this.couponValue = couponValue;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setFdeductibleAmount(BigDecimal fdeductibleAmount) {
        this.fdeductibleAmount = fdeductibleAmount;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public void setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
    }

    public String getActivityInstruction() {
        return activityInstruction;
    }

    public void setActivityInstruction(String activityInstruction) {
        this.activityInstruction = activityInstruction;
    }

    public String getSourceInstruction() {
        return sourceInstruction;
    }

    public void setSourceInstruction(String sourceInstruction) {
        this.sourceInstruction = sourceInstruction;
    }

    public Date getCouponEffectiveDate() {
        return couponEffectiveDate;
    }

    public void setCouponEffectiveDate(Date couponEffectiveDate) {
        this.couponEffectiveDate = couponEffectiveDate;
    }

    public Date getCouponExpiryDate() {
        return couponExpiryDate;
    }

    public void setCouponExpiryDate(Date couponExpiryDate) {
        this.couponExpiryDate = couponExpiryDate;
    }

    public BigDecimal getFdeductibleAmount() {
        return fdeductibleAmount;
    }

    public BigDecimal getFuseInvestmentAmount() {
        return fuseInvestmentAmount;
    }

    public void setFuseInvestmentAmount(BigDecimal fuseInvestmentAmount) {
        this.fuseInvestmentAmount = fuseInvestmentAmount;
    }

    @Override
    public String toString() {
        return "CouponValidateResult [couponId=" + couponId + ", couponCode="
                + couponCode + ", couponType=" + couponType
                + ", couponTypeName=" + couponTypeName
                + ", activityInstruction=" + activityInstruction
                + ", sourceInstruction=" + sourceInstruction
                + ", couponEffectiveDate=" + couponEffectiveDate
                + ", couponExpiryDate=" + couponExpiryDate + ", couponValue="
                + couponValue + ", orderAmount=" + orderAmount
                + ", fdeductibleAmount=" + fdeductibleAmount
                + ", fuseInvestmentAmount=" + fuseInvestmentAmount + "]";
    }

}
