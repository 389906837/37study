package com.cloud.cang.wap.sm.vo;

/**
 * Created by yan on 2019/2/18.
 */
public class DeviceStockList {
    private String commodityName;//商品名称
    private String fcostAmount;//成本价
    private Integer istock;//库存
    /* 商品全名称 = 品牌+商品名+口味+规格+单位*/
    private String scommodityFullName;
    private String sbrandName;//品牌名

    private String staste;//口味

    private String ispecWeight;// 规则/重量

    private String sspecUnit;//单位

    private String spackageUnit;//最小销售包装单位

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

    public String getScommodityFullName() {
            return this.sbrandName + this.commodityName + this.staste + this.ispecWeight + this.sspecUnit + "/" + this.spackageUnit;
    }

    public void setScommodityFullName(String scommodityFullName) {
        this.scommodityFullName = scommodityFullName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getFcostAmount() {
        return fcostAmount;
    }

    public void setFcostAmount(String fcostAmount) {
        this.fcostAmount = fcostAmount;
    }

    public Integer getIstock() {
        return istock;
    }

    public void setIstock(Integer istock) {
        this.istock = istock;
    }
}
