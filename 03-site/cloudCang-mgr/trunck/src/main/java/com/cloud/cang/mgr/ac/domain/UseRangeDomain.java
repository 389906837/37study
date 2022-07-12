package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.model.ac.UseRange;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 活动引用范围明细列表返回Domain(设备名称,设备地址,商品名称,商品单价)
 * @Author: ChangTanchang
 * @Date: 2018/5/5 15:55
 */
public class UseRangeDomain extends UseRange{
    // 设备名称
    private String deviceName;

    // 设备地址
    private String adress;

    // 商品名称
    private String commodityName;

    // 商品单价
    private BigDecimal commodityFsalePrice;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getCommodityFsalePrice() {
        return commodityFsalePrice;
    }

    public void setCommodityFsalePrice(BigDecimal commodityFsalePrice) {
        this.commodityFsalePrice = commodityFsalePrice;
    }

    @Override
    public String toString() {
        return "UseRangeDomain{" +
                "deviceName='" + deviceName + '\'' +
                ", adress='" + adress + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", commodityFsalePrice=" + commodityFsalePrice +
                '}';
    }
}
