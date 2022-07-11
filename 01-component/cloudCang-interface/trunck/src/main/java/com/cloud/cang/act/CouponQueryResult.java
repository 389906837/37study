package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 券查询结果
 *
 * @author zhouhong
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CouponQueryResult extends SuperDto {

    private String couponId;//券ID

    private String couponCode;//券编号

    private Integer couponType;//劵类型

    private String couponTypeName;//劵类型名称

    private String activityInstruction;//券说明

    private String sourceInstruction;//来源说明

    private Date couponEffectiveDate;//券生效日期

    private Date couponExpiryDate;//劵失效日期

    private BigDecimal couponValue;//券面金额

    private BigDecimal fuseAmount;//券要求金额

    private String suseLimitCategory;//订单商品分类使用限制（空不限制）多个用，隔开

    private String suseLimitBrand;//订单商品品牌使用限制（空不限制）多个用，隔开

    private String suseLimitCommodity;//订单商品限使用制（空不限制）多个用，隔开

    private String suseLimitDevice;//订单设备使用限制（空不限制）多个用，隔开

    public BigDecimal getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(BigDecimal couponValue) {
        this.couponValue = couponValue;
    }

    public BigDecimal getFuseAmount() {
        return fuseAmount;
    }

    public void setFuseAmount(BigDecimal fuseAmount) {
        this.fuseAmount = fuseAmount;
    }

    public String getSuseLimitCategory() {
        return suseLimitCategory;
    }

    public void setSuseLimitCategory(String suseLimitCategory) {
        this.suseLimitCategory = suseLimitCategory;
    }

    public String getSuseLimitBrand() {
        return suseLimitBrand;
    }

    public void setSuseLimitBrand(String suseLimitBrand) {
        this.suseLimitBrand = suseLimitBrand;
    }

    public String getSuseLimitCommodity() {
        return suseLimitCommodity;
    }

    public void setSuseLimitCommodity(String suseLimitCommodity) {
        this.suseLimitCommodity = suseLimitCommodity;
    }

    public String getSuseLimitDevice() {
        return suseLimitDevice;
    }

    public void setSuseLimitDevice(String suseLimitDevice) {
        this.suseLimitDevice = suseLimitDevice;
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


}
