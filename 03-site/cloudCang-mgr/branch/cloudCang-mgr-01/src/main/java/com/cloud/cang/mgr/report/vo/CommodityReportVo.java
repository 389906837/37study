package com.cloud.cang.mgr.report.vo;

import java.math.BigDecimal;

/**
 * 商品报表VO
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class CommodityReportVo {
    private String commodityId;//商品Id
    private String commodityName;//商品名
    private String sbrandName;//品牌名

    private String staste;//口味

    private String ispecWeight;// 规则/重量

    private String sspecUnit;//单位

    private String spackageUnit;//最小销售包装单位

    private String commodityFullName;//商品全称
    private BigDecimal commoditySaleNum;//商品销售总数量
    private BigDecimal commodityFcost;//总商品成本价
    private BigDecimal commodityFtaxPoint;//商品税点
    private BigDecimal commoditySalesVolume;//商品销售额(实付金额)
    private BigDecimal commodityGrossProfit;//商品总毛利润(实付金额 - 成本价)
    private BigDecimal commodityNetProfit;//商品总净利润(实付金额 - 成本价 -税率)
    private String grossInterestRate;//毛利率
    private Integer maxSaleMounth;//销售量最高时段/月份


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

    public Integer getMaxSaleMounth() {
        return maxSaleMounth;
    }

    public void setMaxSaleMounth(Integer maxSaleMounth) {
        this.maxSaleMounth = maxSaleMounth;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public BigDecimal getCommodityFcost() {
        return commodityFcost;
    }

    public void setCommodityFcost(BigDecimal commodityFcost) {
        this.commodityFcost = commodityFcost;
    }

    public BigDecimal getCommodityFtaxPoint() {
        return commodityFtaxPoint;
    }

    public void setCommodityFtaxPoint(BigDecimal commodityFtaxPoint) {
        this.commodityFtaxPoint = commodityFtaxPoint;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getCommoditySaleNum() {
        return commoditySaleNum;
    }

    public void setCommoditySaleNum(BigDecimal commoditySaleNum) {
        this.commoditySaleNum = commoditySaleNum;
    }

    public BigDecimal getCommoditySalesVolume() {
        return commoditySalesVolume;
    }

    public void setCommoditySalesVolume(BigDecimal commoditySalesVolume) {
        this.commoditySalesVolume = commoditySalesVolume;
    }

    public BigDecimal getCommodityGrossProfit() {
        return commodityGrossProfit;
    }

    public void setCommodityGrossProfit(BigDecimal commodityGrossProfit) {
        this.commodityGrossProfit = commodityGrossProfit;
    }

    public BigDecimal getCommodityNetProfit() {
        return commodityNetProfit;
    }

    public void setCommodityNetProfit(BigDecimal commodityNetProfit) {
        this.commodityNetProfit = commodityNetProfit;
    }

    public String getGrossInterestRate() {
        return grossInterestRate;
    }

    public void setGrossInterestRate(String grossInterestRate) {
        this.grossInterestRate = grossInterestRate;
    }
}
