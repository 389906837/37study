package com.cloud.cang.mgr.sp.domain;

import com.cloud.cang.generic.GenericEntity;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 选择商品返回domain
 * @Author: zhouhong
 * @Date: 2018/2/10 11:14
 */
public class SelectCommodityDomain extends GenericEntity {

    private String commodityId;//商品ID
    private String commodityCode;//商品编号
    private String commodityName;//商品名称
    private BigDecimal salePrice;//销售价格

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "SelectCommodityDomain{" +
                "commodityId='" + commodityId + '\'' +
                ", commodityCode='" + commodityCode + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", salePrice=" + salePrice +
                '}';
    }
}
