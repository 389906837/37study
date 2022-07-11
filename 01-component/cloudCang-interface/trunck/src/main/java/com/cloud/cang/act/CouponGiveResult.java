package com.cloud.cang.act;

import java.io.Serializable;
import java.util.Date;

/**
 * 券发放结果
 *
 * @author zhouhong
 * @version 1.0
 */
public class CouponGiveResult implements Serializable {

    private static final long serialVersionUID = -8543150351146990522L;

    private String activityCode;//活动编号

    private String sbriefDesc;//活动描述

    private String activityName;//活动名称

    private Integer count;//券的数量

    private Integer couponType;//劵类型

    private String couponTypeName;//劵类型名称

    private String activityInstruction;//券说明

    private String sourceInstruction;//来源说明

    private Double couponValue;//券面金额

    private Double exchangeRatio;//抵用兑换比例(现金券赠送比例)

    private Date effectiveDate;//生效日期

    private Date expiryDate;//失效日期

    public String getSbriefDesc() {
        return sbriefDesc;
    }

    public void setSbriefDesc(String sbriefDesc) {
        this.sbriefDesc = sbriefDesc;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public Double getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(Double couponValue) {
        this.couponValue = couponValue;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Double getExchangeRatio() {
        return exchangeRatio;
    }

    public void setExchangeRatio(Double exchangeRatio) {
        this.exchangeRatio = exchangeRatio;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSourceInstruction() {
        return sourceInstruction;
    }

    public void setSourceInstruction(String sourceInstruction) {
        this.sourceInstruction = sourceInstruction;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "CouponGiveResult{" +
                "activityCode='" + activityCode + '\'' +
                ", sbriefDesc='" + sbriefDesc + '\'' +
                ", activityName='" + activityName + '\'' +
                ", count=" + count +
                ", couponType=" + couponType +
                ", couponTypeName='" + couponTypeName + '\'' +
                ", activityInstruction='" + activityInstruction + '\'' +
                ", sourceInstruction='" + sourceInstruction + '\'' +
                ", couponValue=" + couponValue +
                ", exchangeRatio=" + exchangeRatio +
                ", effectiveDate=" + effectiveDate +
                ", expiryDate=" + expiryDate +
                '}';
    }

}
