package com.cloud.cang.wap.ac.vo;

import java.math.BigDecimal;

/**
 * Created by yan on 2018/5/30.
 */
public class CommodityVo {

    private BigDecimal fsalePrice;//单价

    private String sname;//商品名

    private Integer ispecWeight;//规格/重量

    private String sspecUnit;//规格单位

    private String sbrandName;//品牌名

    private String staste;//口味

    private String commodityFullName;//商品全称

    private String spackageUnit;//最小销售包装单位

    //品牌+商品名+口味+规格+单位
    public String getCommodityFullName() {
        return this.sbrandName + this.sname + this.staste + this.ispecWeight + this.sspecUnit + "/" + this.spackageUnit;
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


    public void setCommodityFullName(String commodityFullName) {
        this.commodityFullName = commodityFullName;
    }

    public String getSpackageUnit() {
        return spackageUnit;
    }

    public void setSpackageUnit(String spackageUnit) {
        this.spackageUnit = spackageUnit;
    }

    public Integer getIspecWeight() {
        return ispecWeight;
    }

    public void setIspecWeight(Integer ispecWeight) {
        this.ispecWeight = ispecWeight;
    }

    public String getSspecUnit() {
        return sspecUnit;
    }

    public void setSspecUnit(String sspecUnit) {
        this.sspecUnit = sspecUnit;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public BigDecimal getFsalePrice() {
        return fsalePrice;
    }

    public void setFsalePrice(BigDecimal fsalePrice) {
        this.fsalePrice = fsalePrice;
    }
}
