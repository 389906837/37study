package com.cloud.cang.mgr.report.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金往来报表明细 VO
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class ExchangeOfFundsDetailVo {
    private String commodityCode;//商品编号

    private Integer commodityTotal;//商品总数

    private BigDecimal commodityFactualAmount;//总金额(实付金额)

    private String sbrandName;//品牌名

    private String staste;//口味

    private String ispecWeight;// 规则/重量

    private String sspecUnit;//单位

    private String spackageUnit;//最小销售包装单位

    private String commodityFullName;//商品全称

    private String commodityName;//商品名

    public String getCommodityFullName() {
        return this.sbrandName + this.commodityName + this.staste + this.ispecWeight + this.sspecUnit + "/" + this.spackageUnit;
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

    public String getSpackageUnit() {
        return spackageUnit;
    }

    public void setSpackageUnit(String spackageUnit) {
        this.spackageUnit = spackageUnit;
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

    public Integer getCommodityTotal() {
        return commodityTotal;
    }

    public void setCommodityTotal(Integer commodityTotal) {
        this.commodityTotal = commodityTotal;
    }

    public BigDecimal getCommodityFactualAmount() {
        return commodityFactualAmount;
    }

    public void setCommodityFactualAmount(BigDecimal commodityFactualAmount) {
        this.commodityFactualAmount = commodityFactualAmount;
    }
}
