package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @ClassName DeviceOperatingDto
 * @Description mgr后台操作设备DTO
 * @Author zengzexiong
 * @Date 2019年1月17日09:41:00
 */
public class DeviceOperatingDto extends SuperDto {

    private String data;                // 操作参数
    private String method;              // 操作方法
    private String operator;            // 操作员
    private String smerchantId;         // 商户ID
    private String smerchantCode;       // 商户编号

    public DeviceOperatingDto() {
    }

    public DeviceOperatingDto(String data) {
        this.data = data;
    }

    public DeviceOperatingDto(String data, String method) {
        this.data = data;
        this.method = method;
    }

    public DeviceOperatingDto(String data, String method, String operator) {
        this.data = data;
        this.method = method;
        this.operator = operator;
    }

    public DeviceOperatingDto(String data, String method, String operator, String smerchantId, String smerchantCode) {
        this.data = data;
        this.method = method;
        this.operator = operator;
        this.smerchantId = smerchantId;
        this.smerchantCode = smerchantCode;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "DeviceOperatingDto{" +
                "data='" + data + '\'' +
                ", method='" + method + '\'' +
                ", operator='" + operator + '\'' +
                ", smerchantId='" + smerchantId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                '}';
    }
}
