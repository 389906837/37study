package com.cloud.cang.mgr.report.vo;

import java.math.BigDecimal;

/**
 * 入库汇总报表 VO
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class EnterWarehouseVo {
    private String commodityCode;//商品编号
    private String commodityName;//商品名

    private Integer orderCount;//入库数量
    private BigDecimal commodityPrice;//商品单价
    private BigDecimal commodityAmount;//商品总额
    private String merchantName;//商户名

    private String sbrandName;//品牌名

    private String staste;//口味

    private String ispecWeight;// 规则/重量

    private String sspecUnit;//单位

    private String spackageUnit;//最小销售包装单位

    private String commodityFullName;//商品全称


    //品牌+商品名+口味+规格+单位
    public String getCommodityFullName() {
        return this.sbrandName + this.commodityName + this.staste + this.ispecWeight + this.sspecUnit + "/" + this.spackageUnit;
    }

    public String getSpackageUnit() {
        return spackageUnit;
    }

    public void setSpackageUnit(String spackageUnit) {
        this.spackageUnit = spackageUnit;
    }

    public String getSbrandName() {
        return sbrandName;
    }

    public void setSbrandName(String sbrandName) {
        this.sbrandName = sbrandName;
    }

    public String getStaste() {
        return staste;
    }

    public void setStaste(String staste) {
        this.staste = staste;
    }

    public String getIspecWeight() {
        return ispecWeight;
    }

    public void setIspecWeight(String ispecWeight) {
        this.ispecWeight = ispecWeight;
    }

    public String getSspecUnit() {
        return sspecUnit;
    }

    public void setSspecUnit(String sspecUnit) {
        this.sspecUnit = sspecUnit;
    }


    public void setCommodityFullName(String commodityFullName) {
        this.commodityFullName = commodityFullName;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(BigDecimal commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public BigDecimal getCommodityAmount() {
        return commodityAmount;
    }

    public void setCommodityAmount(BigDecimal commodityAmount) {
        this.commodityAmount = commodityAmount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

}
