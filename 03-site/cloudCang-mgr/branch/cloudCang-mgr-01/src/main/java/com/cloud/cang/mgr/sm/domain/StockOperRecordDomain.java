package com.cloud.cang.mgr.sm.domain;

import com.cloud.cang.model.sm.StockOperRecord;

/**
 * @description: 库存操作日志返回设备名称,地址,商品名称,商户名称字段
 * @author:ChangTanchang
 * @time:2018-04-25 11:38:05
 * @version 1.0
 */
public class StockOperRecordDomain extends StockOperRecord {

    // 商户名称
    private String shName;

    // 设备名称
    private String sbName;

    // 设备地址
    private String adress;

    // 商品名称
    private String spName;

    // 排序字段
    private String orderStr;

    public String getShName() {
        return shName;
    }

    public void setShName(String shName) {
        this.shName = shName;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    @Override
    public String toString() {
        return "StockOperRecordDomain{" +
                "shName='" + shName + '\'' +
                ", sbName='" + sbName + '\'' +
                ", adress='" + adress + '\'' +
                ", spName='" + spName + '\'' +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
