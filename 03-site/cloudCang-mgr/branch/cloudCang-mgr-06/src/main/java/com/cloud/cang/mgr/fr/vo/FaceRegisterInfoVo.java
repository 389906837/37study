package com.cloud.cang.mgr.fr.vo;

import com.cloud.cang.model.fr.FaceRegisterInfo;

/**
 * @version 1.0
 * @ClassName FaceRegisterInfoVo
 * @Description 人脸信息注册搜索对象
 * @Author zengzexiong
 * @Date 2018年8月2日14:47:14
 */
public class FaceRegisterInfoVo extends FaceRegisterInfo {

    private String orderStr;//排序字段
    private String deviceName;                   /* 售货机设备名称 */
    private String deviceCode;                   /* 售货机设备编号 */
    private String merchantName;            /* 商户名称 */
    private String queryCondition;          /* 查询条件 */

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    @Override
    public String toString() {
        return "FaceRegisterInfoVo{" +
                "orderStr='" + orderStr + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
