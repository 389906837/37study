package com.cloud.cang.mgr.sm.vo;

import com.cloud.cang.model.sm.DeviceStock;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 单机库存列表 VO
 * @author:ChangTanchang
 * @time:2018-01-15 15:29:05
 * @version 1.0
 */
public class DeviceStockVo extends DeviceStock {

    // 开始日期
    private Date toperateStartDate;

    // 结束日期
    private Date toperateEndDate;

    //商户名称
    private String merchantName;

    //设备名称
    private String sbName;

    // 排序字段
    private String orderStr;

    // 查询字段
    private String queryCondition;

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

    // 拼接设备地址
    private String adress;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getToperateStartDate() {
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

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

    @Override
    public String toString() {
        return "DeviceStockVo{" +
                "toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", merchantName='" + merchantName + '\'' +
                ", sbName='" + sbName + '\'' +
                ", orderStr='" + orderStr + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                ", spName='" + spName + '\'' +
                ", spFsaleprice=" + spFsaleprice +
                ", spsspecunit='" + spsspecunit + '\'' +
                ", spispecweight=" + spispecweight +
                ", kcistandardstock=" + kcistandardstock +
                ", adress='" + adress + '\'' +
                '}';
    }
}
