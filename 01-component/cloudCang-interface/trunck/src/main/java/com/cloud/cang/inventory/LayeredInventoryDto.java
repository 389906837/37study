package com.cloud.cang.inventory;

import java.math.BigDecimal;
import java.util.List;



/**
 * Created by YLF on 2019/7/3.
 */
public class LayeredInventoryDto {
    private static final long serialVersionUID = -2913572903105378881L;
    //======必填=====
    private Integer inventoryType;//盘点类型 10 关门盘点 20 定时盘点 30 主动盘点
    private Integer closeType;//关门类型 10 购物 20 补货员 关门盘点必填
    private String deviceId;//设备编号
    //======选填=====
    private BigDecimal openWeightTotal;//开门总重量
    private BigDecimal closeWeightTotal;//关门总重量
    private String memberId;//会员ID 购物
    private Integer isourceClientType;//来源客户端
    private String sip;//操作IP
    private List<LayeredGoodsVo> openLayeredGoods;
    private List<LayeredGoodsVo> closeLayeredGoods;
    //扩展字段
    private String sext;


    public List<LayeredGoodsVo> getOpenLayeredGoods() {
        return openLayeredGoods;
    }

    public void setOpenLayeredGoods(List<LayeredGoodsVo> openLayeredGoods) {
        this.openLayeredGoods = openLayeredGoods;
    }

    public List<LayeredGoodsVo> getCloseLayeredGoods() {
        return closeLayeredGoods;
    }

    public void setCloseLayeredGoods(List<LayeredGoodsVo> closeLayeredGoods) {
        this.closeLayeredGoods = closeLayeredGoods;
    }

    public BigDecimal getCloseWeightTotal() {
        return closeWeightTotal;
    }

    public void setCloseWeightTotal(BigDecimal closeWeightTotal) {
        this.closeWeightTotal = closeWeightTotal;
    }

    public BigDecimal getOpenWeightTotal() {

        return openWeightTotal;
    }

    public void setOpenWeightTotal(BigDecimal openWeightTotal) {
        this.openWeightTotal = openWeightTotal;
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

    public String getSext() {
        return sext;
    }

    public void setSext(String sext) {
        this.sext = sext;
    }
}
