package com.cloud.cang.mgr.om.vo;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.om.RefundAudit;
import org.apache.shiro.SecurityUtils;

/**
 * @version 1.0
 * @description: VO
 * @author:yanlingfeng
 * @time:2018-01-24 16:07:05
 */
public class RefunAuditVo extends RefundAudit {
    //商户名
    private String merchantName;
    //设备名
    private String deviceName;
    //设备编号
    private String deviceCode;
    //设备地址
    private String deviceAddress;
    //退款渠道
    private Integer ipayType;

    private String smemberNameDesensitize;//用户名脱敏

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("REFUND_AUDIT_MEMBER_NAME_DESENSITIZE")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    public Integer getIpayType() {
        return ipayType;
    }

    public void setIpayType(Integer ipayType) {
        this.ipayType = ipayType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
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
}
