package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.ActivityConf;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @version 1.0
 * @Description: 促销活动保存Vo
 * @Author: zhouhong
 * @Date: 2018/2/10 16:06
 */
public class PromotionsVo extends ActivityConf {

    private Integer irangeCommodity;//活动商品类型
    private Integer irangeDevice;//活动设备类型

    private String[] deviceIds;//设备Id
    private String[] commodityIds;//商品Id
    private String sdescription;  /* 描述 */

    //首单参数
    private BigDecimal firstTargetMoney;//目标金额
    private BigDecimal firstDiscountAmount;//优惠金额

    //折扣参数
    private Integer discountSelect;//折扣类型
    private BigDecimal discountTargetMoney;//目标金额
    private Integer discountTargetNum;//目标件数
    private BigDecimal fdiscount;//优惠折扣

    //满减参数
    private Integer fullReductionSelect;//满减类型
    private BigDecimal fullReductionTargetMoney;//目标金额
    private Integer fullReductionTargetNum;//目标件数
    private BigDecimal fullReductionDiscountMoney;//优惠金额
    private BigDecimal fdiscountLimit;//优惠上限
    private BigDecimal fullReductionTargetMoney1;//目标金额
    private BigDecimal fullReductionDiscountMoney1;//优惠金额
    private BigDecimal fullReductionTargetMoney2;//目标金额
    private BigDecimal fullReductionDiscountMoney2;//优惠金额

    //返现参数
    private BigDecimal cashBackTargetMoney;//目标金额
    private BigDecimal fcashBackMoney;//返现金额

    public Integer getIrangeCommodity() {
        return irangeCommodity;
    }

    public void setIrangeCommodity(Integer irangeCommodity) {
        this.irangeCommodity = irangeCommodity;
    }

    public Integer getIrangeDevice() {
        return irangeDevice;
    }

    public void setIrangeDevice(Integer irangeDevice) {
        this.irangeDevice = irangeDevice;
    }

    public String[] getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String[] deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String[] getCommodityIds() {
        return commodityIds;
    }

    public void setCommodityIds(String[] commodityIds) {
        this.commodityIds = commodityIds;
    }

    public BigDecimal getFirstTargetMoney() {
        return firstTargetMoney;
    }

    public void setFirstTargetMoney(BigDecimal firstTargetMoney) {
        this.firstTargetMoney = firstTargetMoney;
    }

    public BigDecimal getFirstDiscountAmount() {
        return firstDiscountAmount;
    }

    public void setFirstDiscountAmount(BigDecimal firstDiscountAmount) {
        this.firstDiscountAmount = firstDiscountAmount;
    }

    public Integer getDiscountSelect() {
        return discountSelect;
    }

    public void setDiscountSelect(Integer discountSelect) {
        this.discountSelect = discountSelect;
    }

    public BigDecimal getDiscountTargetMoney() {
        return discountTargetMoney;
    }

    public void setDiscountTargetMoney(BigDecimal discountTargetMoney) {
        this.discountTargetMoney = discountTargetMoney;
    }

    public Integer getDiscountTargetNum() {
        return discountTargetNum;
    }

    public void setDiscountTargetNum(Integer discountTargetNum) {
        this.discountTargetNum = discountTargetNum;
    }

    public BigDecimal getFdiscount() {
        return fdiscount;
    }

    public void setFdiscount(BigDecimal fdiscount) {
        this.fdiscount = fdiscount;
    }

    public Integer getFullReductionSelect() {
        return fullReductionSelect;
    }

    public void setFullReductionSelect(Integer fullReductionSelect) {
        this.fullReductionSelect = fullReductionSelect;
    }

    public BigDecimal getFullReductionTargetMoney() {
        return fullReductionTargetMoney;
    }

    public void setFullReductionTargetMoney(BigDecimal fullReductionTargetMoney) {
        this.fullReductionTargetMoney = fullReductionTargetMoney;
    }

    public Integer getFullReductionTargetNum() {
        return fullReductionTargetNum;
    }

    public void setFullReductionTargetNum(Integer fullReductionTargetNum) {
        this.fullReductionTargetNum = fullReductionTargetNum;
    }

    public BigDecimal getFullReductionDiscountMoney() {
        return fullReductionDiscountMoney;
    }

    public void setFullReductionDiscountMoney(BigDecimal fullReductionDiscountMoney) {
        this.fullReductionDiscountMoney = fullReductionDiscountMoney;
    }

    public BigDecimal getFdiscountLimit() {
        return fdiscountLimit;
    }

    public void setFdiscountLimit(BigDecimal fdiscountLimit) {
        this.fdiscountLimit = fdiscountLimit;
    }

    public BigDecimal getFullReductionTargetMoney1() {
        return fullReductionTargetMoney1;
    }

    public void setFullReductionTargetMoney1(BigDecimal fullReductionTargetMoney1) {
        this.fullReductionTargetMoney1 = fullReductionTargetMoney1;
    }

    public BigDecimal getFullReductionDiscountMoney1() {
        return fullReductionDiscountMoney1;
    }

    public void setFullReductionDiscountMoney1(BigDecimal fullReductionDiscountMoney1) {
        this.fullReductionDiscountMoney1 = fullReductionDiscountMoney1;
    }

    public BigDecimal getFullReductionTargetMoney2() {
        return fullReductionTargetMoney2;
    }

    public void setFullReductionTargetMoney2(BigDecimal fullReductionTargetMoney2) {
        this.fullReductionTargetMoney2 = fullReductionTargetMoney2;
    }

    public BigDecimal getFullReductionDiscountMoney2() {
        return fullReductionDiscountMoney2;
    }

    public void setFullReductionDiscountMoney2(BigDecimal fullReductionDiscountMoney2) {
        this.fullReductionDiscountMoney2 = fullReductionDiscountMoney2;
    }

    public BigDecimal getCashBackTargetMoney() {
        return cashBackTargetMoney;
    }

    public void setCashBackTargetMoney(BigDecimal cashBackTargetMoney) {
        this.cashBackTargetMoney = cashBackTargetMoney;
    }

    public BigDecimal getFcashBackMoney() {
        return fcashBackMoney;
    }

    public void setFcashBackMoney(BigDecimal fcashBackMoney) {
        this.fcashBackMoney = fcashBackMoney;
    }

    @Override
    public String getSdescription() {
        return sdescription;
    }

    @Override
    public void setSdescription(String sdescription) {
        this.sdescription = sdescription;
    }

    @Override
    public String toString() {
        return "PromotionsVo{" +
                "irangeCommodity=" + irangeCommodity +
                ", irangeDevice=" + irangeDevice +
                ", deviceIds=" + Arrays.toString(deviceIds) +
                ", commodityIds=" + Arrays.toString(commodityIds) +
                ", sdescription='" + sdescription + '\'' +
                ", firstTargetMoney=" + firstTargetMoney +
                ", firstDiscountAmount=" + firstDiscountAmount +
                ", discountSelect=" + discountSelect +
                ", discountTargetMoney=" + discountTargetMoney +
                ", discountTargetNum=" + discountTargetNum +
                ", fdiscount=" + fdiscount +
                ", fullReductionSelect=" + fullReductionSelect +
                ", fullReductionTargetMoney=" + fullReductionTargetMoney +
                ", fullReductionTargetNum=" + fullReductionTargetNum +
                ", fullReductionDiscountMoney=" + fullReductionDiscountMoney +
                ", fdiscountLimit=" + fdiscountLimit +
                ", fullReductionTargetMoney1=" + fullReductionTargetMoney1 +
                ", fullReductionDiscountMoney1=" + fullReductionDiscountMoney1 +
                ", fullReductionTargetMoney2=" + fullReductionTargetMoney2 +
                ", fullReductionDiscountMoney2=" + fullReductionDiscountMoney2 +
                ", cashBackTargetMoney=" + cashBackTargetMoney +
                ", fcashBackMoney=" + fcashBackMoney +
                '}';
    }
}
