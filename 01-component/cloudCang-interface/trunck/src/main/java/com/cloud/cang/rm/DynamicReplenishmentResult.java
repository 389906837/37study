package com.cloud.cang.rm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Description: 动态补货返回参数
 * @Author: ChangTanchang
 * @Date: 2018/4/8 19:27
 */
public class DynamicReplenishmentResult implements Serializable {

    //计划补货单编号
    private String sreplenishmentCode;

    // 设备ID
    private String sdeviceId;

    // 设备编号
    private String sdeviceCode;

    // 设备名称
    private String sdeviceName;

    // 设备地址
    private String sdeviceAddress;

    // 补货员姓名
    private String srenewalName;

    // 补货员手机号
    private String srenewalMobile;

    /* 生成时间 */
    private Date tgenerateTime;

    /* 备注 */
    private String sremark;

    /* 前台是否能修改 */
    private boolean isUpdate;

    //商品集合
    List<CommodityResult> commodityResults;


    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public String getSdeviceAddress() {
        return sdeviceAddress;
    }

    public void setSdeviceAddress(String sdeviceAddress) {
        this.sdeviceAddress = sdeviceAddress;
    }

    public String getSrenewalName() {
        return srenewalName;
    }

    public void setSrenewalName(String srenewalName) {
        this.srenewalName = srenewalName;
    }

    public String getSrenewalMobile() {
        return srenewalMobile;
    }

    public void setSrenewalMobile(String srenewalMobile) {
        this.srenewalMobile = srenewalMobile;
    }

    public String getSreplenishmentCode() {
        return sreplenishmentCode;
    }

    public void setSreplenishmentCode(String sreplenishmentCode) {
        this.sreplenishmentCode = sreplenishmentCode;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public List<CommodityResult> getCommodityResults() {
        return commodityResults;
    }

    public void setCommodityResults(List<CommodityResult> commodityResults) {
        this.commodityResults = commodityResults;
    }

    public Date getTgenerateTime() {
        return tgenerateTime;
    }

    public void setTgenerateTime(Date tgenerateTime) {
        this.tgenerateTime = tgenerateTime;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    public String getSdeviceName() {
        return sdeviceName;
    }

    public void setSdeviceName(String sdeviceName) {
        this.sdeviceName = sdeviceName;
    }

    @Override
    public String toString() {
        return "DynamicReplenishmentResult{" +
                "sreplenishmentCode='" + sreplenishmentCode + '\'' +
                ", sdeviceId='" + sdeviceId + '\'' +
                ", sdeviceCode='" + sdeviceCode + '\'' +
                ", sdeviceName='" + sdeviceName + '\'' +
                ", sdeviceAddress='" + sdeviceAddress + '\'' +
                ", srenewalName='" + srenewalName + '\'' +
                ", srenewalMobile='" + srenewalMobile + '\'' +
                ", tgenerateTime=" + tgenerateTime +
                ", sremark='" + sremark + '\'' +
                ", isUpdate=" + isUpdate +
                ", commodityResults=" + commodityResults +
                '}';
    }
}
