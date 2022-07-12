package com.cloud.cang.mgr.sm.domain;

import com.cloud.cang.model.sm.StockDetail;

import java.math.BigDecimal;

/**
 * 拼接设备地址=省份+城市+区县+详细地址
 * @description: 单机库存 Domain
 * @author:ChangTanchang
 * @time:2018-02-28 10:23:05
 * @version 1.0
 */
public class StockDetailDomain extends StockDetail {

    //商户名称
    private String merchantName;

    //设备名称
    private String sbCode;

    //设备编号
    private String sbName;

    // 拼接设备地址
    private String adress;

    // 商品名称
    private String spName;

    // 商品销售单价
    private BigDecimal spFsaleprice;

    // 商品规格
    private String spsspecunit;

    // 商品重量
    private Integer spispecweight;

    // 标准库存
    private Integer kcistandardstock;

    // 排序字段
    private String orderStr;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public BigDecimal getSpFsaleprice() {
        return spFsaleprice;
    }

    public void setSpFsaleprice(BigDecimal spFsaleprice) {
        this.spFsaleprice = spFsaleprice;
    }

    public String getSpsspecunit() {
        return spsspecunit;
    }

    public void setSpsspecunit(String spsspecunit) {
        this.spsspecunit = spsspecunit;
    }

    public Integer getSpispecweight() {
        return spispecweight;
    }

    public void setSpispecweight(Integer spispecweight) {
        this.spispecweight = spispecweight;
    }

    public Integer getKcistandardstock() {
        return kcistandardstock;
    }

    public void setKcistandardstock(Integer kcistandardstock) {
        this.kcistandardstock = kcistandardstock;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getSbCode() {
        return sbCode;
    }

    public void setSbCode(String sbCode) {
        this.sbCode = sbCode;
    }

    @Override
    public String toString() {
        return "StockDetailDomain{" +
                "merchantName='" + merchantName + '\'' +
                ", sbCode='" + sbCode + '\'' +
                ", sbName='" + sbName + '\'' +
                ", adress='" + adress + '\'' +
                ", spName='" + spName + '\'' +
                ", spFsaleprice=" + spFsaleprice +
                ", spsspecunit='" + spsspecunit + '\'' +
                ", spispecweight=" + spispecweight +
                ", kcistandardstock=" + kcistandardstock +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
