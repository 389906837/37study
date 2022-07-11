package com.cloud.cang.inventory;

import java.util.List;

/**
 * 盘点操作参数
 * @author zengzexiong
 * @version 1.0
 */
public class ReplenishRealTimeOrderDto {

    //======必填=====
    private String deviceId;//设备编号
    private Integer isourceClientType;//来源客户端 10=传统 20=RFID射频 30=视觉
    private String memberId;//会员ID 购物

    //======选填=====
    private List<CommodityVo> commodityVos;//盘点商品集合

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getIsourceClientType() {
        return isourceClientType;
    }

    public void setIsourceClientType(Integer isourceClientType) {
        this.isourceClientType = isourceClientType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<CommodityVo> getCommodityVos() {
        return commodityVos;
    }

    public void setCommodityVos(List<CommodityVo> commodityVos) {
        this.commodityVos = commodityVos;
    }

    @Override
    public String toString() {
        return "ReplenishRealTimeOrderDto{" +
                "deviceId='" + deviceId + '\'' +
                ", isourceClientType=" + isourceClientType +
                ", memberId='" + memberId + '\'' +
                ", commodityVos=" + commodityVos +
                '}';
    }
}
