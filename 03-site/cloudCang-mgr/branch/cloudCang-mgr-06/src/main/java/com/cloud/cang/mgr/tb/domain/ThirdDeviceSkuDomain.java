package com.cloud.cang.mgr.tb.domain;

import com.cloud.cang.generic.GenericEntity;


/**
 * @version 1.0
 * @ClassName ThirdDeviceSkuDomain
 * @Description 第三方商户设备SKU库
 * @Author zengzexiong
 * @Date 2018年10月12日11:45:01
 */
public class ThirdDeviceSkuDomain extends GenericEntity {
    private String id;
    private String merchantName;                    /* 商户名称 */
    private String deviceName;                      /* 设备名称 */
    private String sdeviceCode;                     /* 设备编号 */
    private String sdeviceId;                       /* 设备ID */
    private String smerchantCode;                   /* 商户编号 */
    private String smerchantId;                     /* 商户ID */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    @Override
    public String toString() {
        return "ThirdDeviceSkuDomain{" +
                "id='" + id + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", sdeviceCode='" + sdeviceCode + '\'' +
                ", sdeviceId='" + sdeviceId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smerchantId='" + smerchantId + '\'' +
                '}';
    }
}
