package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.generic.GenericEntity;

import java.math.BigDecimal;

/**
 * 主动盘点时，实时库存商品信息
 */
public class DeviceInventoryStockDomain extends GenericEntity {

    private String commodityId;             // 商品ID
    private String commodityFullName;       // 商品名称
    private String commodityCode;           // 商品编号
    private String svrCode;                 // 商品视觉编号
    private Integer num;                    // 商品数量
    private BigDecimal fcommodityPrice;     // 商品价格

    public BigDecimal getFcommodityPrice() {
        return fcommodityPrice;
    }

    public void setFcommodityPrice(BigDecimal fcommodityPrice) {
        this.fcommodityPrice = fcommodityPrice;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getSvrCode() {
        return svrCode;
    }

    public void setSvrCode(String svrCode) {
        this.svrCode = svrCode;
    }

    public String getCommodityFullName() {
        return commodityFullName;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "DeviceInventoryStockDomain{" +
                "commodityId='" + commodityId + '\'' +
                ", commodityFullName='" + commodityFullName + '\'' +
                ", commodityCode='" + commodityCode + '\'' +
                ", fcommodityPrice=" + fcommodityPrice +
                ", svrCode='" + svrCode + '\'' +
                ", num=" + num +
                '}';
    }
}
