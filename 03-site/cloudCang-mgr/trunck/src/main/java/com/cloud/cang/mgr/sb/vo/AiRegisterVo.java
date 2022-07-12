package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceRegister;


/**
 * @version 1.0
 * @ClassName AiRegisterVo
 * @Description AI设备搜索对象
 * @Author zengzexiong
 * @Date 2018年7月31日14:34:25
 */
public class AiRegisterVo extends DeviceRegister {
    private String orderStr;                //排序字段
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */
    private String queryCondition;          /* 查询条件 */
    private String deviceCode;          /* 大屏编号 */
    private String deviceName;          /* 大屏名称 */

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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
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

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    @Override
    public String toString() {
        return "AiRegisterVo{" +
                "orderStr='" + orderStr + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }
}
