package com.cloud.cang.mgr.report.vo;

import java.math.BigDecimal;

/**
 * 库存报表 VO
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class StockReportVo {
    private String commodityName;//商品名
    private String sbrandName;//品牌名

    private String staste;//口味

    private String ispecWeight;// 规则/重量

    private String sspecUnit;//单位
    private String spackageUnit;//最小销售包装单位


    private String commodityFullName;//商品全称
    private Integer batchTotal;//总批次数
    private BigDecimal fcostAmount;//商品总成本
    private Integer lossNum;//已损耗数量
    private Integer expiredNum;//已过期数

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

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getBatchTotal() {
        return batchTotal;
    }

    public void setBatchTotal(Integer batchTotal) {
        this.batchTotal = batchTotal;
    }

    public BigDecimal getFcostAmount() {
        return fcostAmount;
    }

    public void setFcostAmount(BigDecimal fcostAmount) {
        this.fcostAmount = fcostAmount;
    }

    public Integer getLossNum() {
        return lossNum;
    }

    public void setLossNum(Integer lossNum) {
        this.lossNum = lossNum;
    }

    public Integer getExpiredNum() {
        return expiredNum;
    }

    public void setExpiredNum(Integer expiredNum) {
        this.expiredNum = expiredNum;
    }
}
