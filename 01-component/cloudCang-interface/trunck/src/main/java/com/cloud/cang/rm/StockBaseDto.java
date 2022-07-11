package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.inventory.CommodityDiffVo;

import java.util.List;

/**
 * @version 1.0
 * @Description:生成补货单服务参数
 * @Author: zhouhong
 * @Date: 2018/4/13 12:02
 */
public class StockBaseDto extends SuperDto {

    //====必填====
    //商户信息
    private String smerchantId;//商户ID
    private String smerchantCode;//商户编号

    //设备信息
    private String sdeviceId;//设备ID
    private String sdeviceCode;//设备编号
    private String sreaderSerialNumber;//设备读写器序列号
    private String sdeviceAddress;//设备地址


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

    @Override
    public String toString() {
        return "StockBaseDto{" +
                "smerchantId='" + smerchantId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", sdeviceId='" + sdeviceId + '\'' +
                ", sdeviceCode='" + sdeviceCode + '\'' +
                ", sreaderSerialNumber='" + sreaderSerialNumber + '\'' +
                ", sdeviceAddress='" + sdeviceAddress + '\'' +
                '}';
    }
}
