package com.cloud.cang.mgr.sys.vo;

import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @version 1.0
 * @description: 商品销售Top 10
 * @author:Yanlingfeng
 * @time:2018-03-24 14:07:05
 */
public class CommoditySaleRankingVo {
    private String commodityId;//商品Id
    private String commodityCount;//购买商品数量
    private BigDecimal commodityAmount;//商品总销售额
    private String commodityAmountStr;//商品总销售额格式化
    private String commodityName;//商品名
    private String sbrandName;//品牌名

    private String staste;//口味

    private String ispecWeight;// 规则/重量

    private String sspecUnit;//单位

    private String commodityFullName;//商品全称

    private String spackageUnit;//最小销售包装单位

    //品牌+商品名+口味+规格+单位
    public String getCommodityFullName() {
        if (!"--".equals(commodityName)) {
            return this.sbrandName + this.commodityName + this.staste + this.ispecWeight + this.sspecUnit + "/" + this.spackageUnit;
        }
        return "--";
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

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(String commodityCount) {
        this.commodityCount = commodityCount;
    }

    public BigDecimal getCommodityAmount() {
        return commodityAmount;
    }

    public void setCommodityAmount(BigDecimal commodityAmount) {
        this.commodityAmount = commodityAmount;
    }

    public String getCommodityAmountStr() {
        if (null == this.getCommodityAmount()) {
            return "--";
        } else {
            return new DecimalFormat(",##0.##").format(this.getCommodityAmount().doubleValue());
        }
    }

    public void setCommodityAmountStr(String commodityAmountStr) {
        this.commodityAmountStr = commodityAmountStr;
    }

    @Override
    public String toString() {
        return "CommoditySaleRankingVo{" +
                "commodityId='" + commodityId + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", commodityCount='" + commodityCount + '\'' +
                ", commodityAmount=" + commodityAmount +
                ", commodityAmountStr='" + commodityAmountStr + '\'' +
                '}';
    }
}
