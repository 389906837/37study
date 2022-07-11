package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 关门商品数量差对象
 * @author zengzexiong
 * @version 1.0
 */
public class InventoryDiffDto extends SuperDto {
    private static final long serialVersionUID = -2913572903105378881L;
    //======必填=====
    private Integer inventoryType;//盘点类型 10 关门盘点 20 定时盘点 30 主动盘点
    private Integer closeType;//关门类型 10 购物 20 补货员 关门盘点必填
    private String deviceId;//设备编号

    //======选填=====
    private String memberId;//会员ID 购物
    private List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList;//盘点商品集合
    private List<LayerGravityVo> layerGravityVoList;                    // 商品重力差值
    private Integer isourceClientType;//来源客户端
    private String sip;//操作IP

    public List<LayerGravityVo> getLayerGravityVoList() {
        return layerGravityVoList;
    }

    public void setLayerGravityVoList(List<LayerGravityVo> layerGravityVoList) {
        this.layerGravityVoList = layerGravityVoList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public List<InventoryCommodityDiffVo> getInventoryCommodityDiffVoList() {
        return inventoryCommodityDiffVoList;
    }

    public void setInventoryCommodityDiffVoList(List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList) {
        this.inventoryCommodityDiffVoList = inventoryCommodityDiffVoList;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    @Override
    public String toString() {
        return "InventoryDiffDto{" +
                "inventoryType=" + inventoryType +
                ", closeType=" + closeType +
                ", deviceId='" + deviceId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", inventoryCommodityDiffVoList=" + inventoryCommodityDiffVoList +
                ", layerGravityVoList=" + layerGravityVoList +
                ", isourceClientType=" + isourceClientType +
                ", sip='" + sip + '\'' +
                '}';
    }
}
