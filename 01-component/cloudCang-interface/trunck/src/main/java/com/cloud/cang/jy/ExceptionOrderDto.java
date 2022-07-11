package com.cloud.cang.jy;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @description: 生成异常订单 Dto
 * @author:严凌峰
 * @time:2018-04-11
 */
public class ExceptionOrderDto extends SuperDto {
    //----------------------------------必填-------------------------------
    private String smerchantId;//商户ID
    private String smerchantCode;//商户Code
    private String smemberId;//会员Id
    private String smemberCode;//会员Code
    private String smemberName;//会员名
    private Integer isourceClientType;//来源客户端
    private String sdeviceId;//设备Id
    private String sdeviceCode;//设备Code
    private String sdeviceName;//设备名称
    private String sreaderSerialNumber;//设备读写器序列号
    private String sdeviceAddress;//设备地址
    private BigDecimal ftotalAmount;//订单总额
    private Integer itype;//异常审核订单审核类型
    private String shandlerRemark ;//异常审核订单管理人员备注

    private List<ExceptionOrderCommodityDto> exceptionOrderCommodityDtoList;//异常订单明细

    //----------------------------------选填-------------------------------
    private Integer isourceWay;//订单来源方式（预留）
    private Integer isourceWayCode;//来源方式编号（预留)
    private String isourceWayName;//来源方式名称（预留)
    private String sext;


    public BigDecimal getFtotalAmount() {
        return ftotalAmount;
    }

    public void setFtotalAmount(BigDecimal ftotalAmount) {
        this.ftotalAmount = ftotalAmount;
    }

    @Override
    public String toString() {
        return "ExceptionOrderDto{" +
                "smerchantId='" + smerchantId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", smemberCode='" + smemberCode + '\'' +
                ", smemberName='" + smemberName + '\'' +
                ", isourceClientType=" + isourceClientType +
                ", sdeviceId='" + sdeviceId + '\'' +
                ", sdeviceCode='" + sdeviceCode + '\'' +
                ", sdeviceName='" + sdeviceName + '\'' +
                ", sreaderSerialNumber='" + sreaderSerialNumber + '\'' +
                ", sdeviceAddress='" + sdeviceAddress + '\'' +
                ", ftotalAmount=" + ftotalAmount +
                ", itype=" + itype +
                ", exceptionOrderCommodityDtoList=" + exceptionOrderCommodityDtoList +
                ", isourceWay=" + isourceWay +
                ", isourceWayCode=" + isourceWayCode +
                ", isourceWayName='" + isourceWayName + '\'' +
                ", sext=" + sext +
                '}';
    }

    public String getShandlerRemark() {
        return shandlerRemark;
    }

    public void setShandlerRemark(String shandlerRemark) {
        this.shandlerRemark = shandlerRemark;
    }

    public List<ExceptionOrderCommodityDto> getExceptionOrderCommodityDtoList() {
        return exceptionOrderCommodityDtoList;
    }

    public String getSdeviceName() {
        return sdeviceName;
    }

    public void setSdeviceName(String sdeviceName) {
        this.sdeviceName = sdeviceName;
    }

    public void setExceptionOrderCommodityDtoList(List<ExceptionOrderCommodityDto> exceptionOrderCommodityDtoList) {
        this.exceptionOrderCommodityDtoList = exceptionOrderCommodityDtoList;
    }

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public String getSmemberCode() {
        return smemberCode;
    }

    public void setSmemberCode(String smemberCode) {
        this.smemberCode = smemberCode;
    }

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    public Integer getIsourceClientType() {
        return isourceClientType;
    }

    public void setIsourceClientType(Integer isourceClientType) {
        this.isourceClientType = isourceClientType;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public String getSreaderSerialNumber() {
        return sreaderSerialNumber;
    }

    public void setSreaderSerialNumber(String sreaderSerialNumber) {
        this.sreaderSerialNumber = sreaderSerialNumber;
    }

    public String getSdeviceAddress() {
        return sdeviceAddress;
    }

    public void setSdeviceAddress(String sdeviceAddress) {
        this.sdeviceAddress = sdeviceAddress;
    }

    public Integer getItype() {
        return itype;
    }

    public void setItype(Integer itype) {
        this.itype = itype;
    }

    public Integer getIsourceWay() {
        return isourceWay;
    }

    public void setIsourceWay(Integer isourceWay) {
        this.isourceWay = isourceWay;
    }

    public Integer getIsourceWayCode() {
        return isourceWayCode;
    }

    public void setIsourceWayCode(Integer isourceWayCode) {
        this.isourceWayCode = isourceWayCode;
    }

    public String getIsourceWayName() {
        return isourceWayName;
    }

    public void setIsourceWayName(String isourceWayName) {
        this.isourceWayName = isourceWayName;
    }

    public String getSext() {
        return sext;
    }

    public void setSext(String sext) {
        this.sext = sext;
    }
}
