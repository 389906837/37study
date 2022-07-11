package com.cloud.cang.api.om.vo;


import com.cloud.cang.api.common.utils.PriceUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Description: 定单商品
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

    /* 平均实付金额 */
    private BigDecimal favgActualAmount;

    /* 优惠总额 */
    private BigDecimal fcommodityAmount;

    /* 订单数量 */
    private Integer orderNum;
    /*商品图片*/
    private String scommodityImg;

    private Date orderTimeTemp;

    public Date getOrderTimeTemp() {
        return orderTimeTemp;
    }

    public void setOrderTimeTemp(Date orderTimeTemp) {
        this.orderTimeTemp = orderTimeTemp;
    }

    public String getScommodityImg() {
        return scommodityImg;
    }

    public void setScommodityImg(String scommodityImg) {
        this.scommodityImg = scommodityImg;
    }

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

    public BigDecimal getFavgActualAmount() {
        if (null != this.factualAmount && null != this.orderNum) {
            return PriceUtil.divideDown(this.factualAmount, new BigDecimal(String.valueOf(this.orderNum)));
        }
        return favgActualAmount;
    }

    public void setFavgActualAmount(BigDecimal favgActualAmount) {
        this.favgActualAmount = favgActualAmount;
    }

    @Override
    public String toString() {
        return "CommodityDomain{" +
                "commodityId='" + commodityId + '\'' +
                ", commodityCode='" + commodityCode + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", salePrice=" + salePrice +
                ", factualAmount=" + factualAmount +
                ", favgActualAmount=" + favgActualAmount +
                ", fcommodityAmount=" + fcommodityAmount +
                ", orderNum=" + orderNum +
                ", scommodityImg='" + scommodityImg + '\'' +
                ", orderTimeTemp=" + orderTimeTemp +
                '}';
    }
}
