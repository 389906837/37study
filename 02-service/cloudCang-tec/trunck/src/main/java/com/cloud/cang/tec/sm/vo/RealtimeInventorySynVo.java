package com.cloud.cang.tec.sm.vo;

/**
 * 商品实时库存更新
 */
public class RealtimeInventorySynVo {
    private String id;//单设备库存ID
    private String deviceId;//设备id
    private String commodityId;//商品id
    private Integer istock;//单设备商品库存数量
    private Integer realTsimeNum;//商品实时数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getIstock() {
        return istock;
    }

    public void setIstock(Integer istock) {
        this.istock = istock;
    }

    public Integer getRealTsimeNum() {
        return realTsimeNum;
    }

    public void setRealTsimeNum(Integer realTsimeNum) {
        this.realTsimeNum = realTsimeNum;
    }
}
