package com.cloud.cang.mgr.fr.domain;

import com.cloud.cang.model.fr.FaceRegisterInfo;

/**
 * @version 1.0
 * @ClassName FaceRegisterInfoDomain
 * @Description 人脸信息注册页面域对象
 * @Author zengzexiong
 * @Date 2018年8月2日14:48:05
 */
public class FaceRegisterInfoDomain extends FaceRegisterInfo {
    private String deviceCode;                   /* 售货机设备编号 */
    private String deviceName;                   /* 售货机设备编号 */
    private String merchantName;            /* 商户名称 */

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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "FaceRegisterInfoDomain{" +
                "deviceCode='" + deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
