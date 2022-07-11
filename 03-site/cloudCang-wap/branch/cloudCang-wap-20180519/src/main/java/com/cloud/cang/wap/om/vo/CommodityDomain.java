package com.cloud.cang.wap.om.vo;

import com.cloud.cang.common.SuperDto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 计划补货单商品参数
 * @Author: zhouhong
 * @Date: 2018/4/11 16:22
 */
public class CommodityDomain implements Serializable {
    /* 商品ID */
    private String commodityId;

    /* 商品编号 */
    private String commodityCode;

    /* 商品名称 */
    private String commodityName;

    /* 商品销售价 */
    private BigDecimal salePrice;

    /* 实付金额 */
    private BigDecimal factualAmount;

    /* 优惠总额 */
    private BigDecimal fcommodityAmount;

    /* 订单数量 */
    private Integer orderNum;

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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getFactualAmount() {
        return factualAmount;
    }

    public void setFactualAmount(BigDecimal factualAmount) {
        this.factualAmount = factualAmount;
    }

    public BigDecimal getFcommodityAmount() {
        return fcommodityAmount;
    }

    public void setFcommodityAmount(BigDecimal fcommodityAmount) {
        this.fcommodityAmount = fcommodityAmount;
    }

    @Override
    public String toString() {
        return "CommodityDomain{" +
                "commodityId='" + commodityId + '\'' +
                ", commodityCode='" + commodityCode + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", salePrice=" + salePrice +
                ", factualAmount=" + factualAmount +
                ", fcommodityAmount=" + fcommodityAmount +
                ", orderNum=" + orderNum +
                '}';
    }
}
