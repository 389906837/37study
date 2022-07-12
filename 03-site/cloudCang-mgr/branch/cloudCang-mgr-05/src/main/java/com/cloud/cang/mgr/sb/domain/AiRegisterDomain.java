package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.model.sb.DeviceRegister;

/**
 * @version 1.0
 * @ClassName AiRegisterDomain
 * @Description AI设备页面域对象
 * @Author zengzexiong
 * @Date 2018年7月31日14:16:26
 */
public class AiRegisterDomain extends DeviceRegister {

    private String saiCode;                   /* 设备编号 */
    private String saiName;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */
    private String oldReqIp;               /* 之前的注册码*/
    private String deviceCode;           // 大屏编号
    private String deviceName;           // 大屏名称

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getSaiCode() {
        return saiCode;
    }

    public void setSaiCode(String saiCode) {
        this.saiCode = saiCode;
    }

    public String getSaiName() {
        return saiName;
    }

    public void setSaiName(String saiName) {
        this.saiName = saiName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getOldReqIp() {
        return oldReqIp;
    }

    public void setOldReqIp(String oldReqIp) {
        this.oldReqIp = oldReqIp;
    }

    @Override
    public String toString() {
        return "AiRegisterDomain{" +
                "saiCode='" + saiCode + '\'' +
                ", saiName='" + saiName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", oldReqIp='" + oldReqIp + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
