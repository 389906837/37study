package com.cloud.cang.inventory;

import java.math.BigDecimal;
import java.util.List;

/**
 * 重力关门
 * Created by YLF on 2019/5/31.
 */
public class GravityCloseDto {

    private BigDecimal totalWeight;     //盘货总重量
    private BigDecimal stockTotalWeight;//库存总重量
    //======必填=====
    private Integer inventoryType;//盘点类型 10 关门盘点 20 定时盘点 30 主动盘点
    private Integer closeType;//关门类型 10 购物 20 补货员 关门盘点必填
    private String deviceId;//设备编号

    //======选填=====
    private List<CommodityVo> commodityVos;//盘点商品集合
    private String memberId;//会员ID 购物
    private Integer isourceClientType;//来源客户端
    private String sip;//操作IP




    public BigDecimal getStockTotalWeight() {
        return stockTotalWeight;
    }

    public void setStockTotalWeight(BigDecimal stockTotalWeight) {
        this.stockTotalWeight = stockTotalWeight;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Integer getCloseType() {
        return closeType;
    }

    public void setCloseType(Integer closeType) {
        this.closeType = closeType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<CommodityVo> getCommodityVos() {
        return commodityVos;
    }

    public void setCommodityVos(List<CommodityVo> commodityVos) {
        this.commodityVos = commodityVos;
    }

    public Integer getIsourceClientType() {
        return isourceClientType;
    }

    public void setIsourceClientType(Integer isourceClientType) {
        this.isourceClientType = isourceClientType;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }
}
