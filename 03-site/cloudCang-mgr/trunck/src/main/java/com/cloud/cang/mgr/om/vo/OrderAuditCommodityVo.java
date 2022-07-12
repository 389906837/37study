package com.cloud.cang.mgr.om.vo;

import com.cloud.cang.model.om.OrderAuditCommodity;

/**
 * Created by yan on 2018/6/20.
 */
public class OrderAuditCommodityVo extends OrderAuditCommodity {
    private String sbrandName;//品牌名

    private String staste;//口味

    private String ispecWeight;// 规则/重量

    private String sspecUnit;//单位

    private String commodityFullName;//商品全称

    private String spackageUnit;//最小销售包装单位


    //品牌+商品名+口味+规格+单位
    public String getCommodityFullName() {
        return this.sbrandName + this.getScommodityName() + this.staste + this.ispecWeight + this.sspecUnit+ "/" + this.spackageUnit;
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
}
