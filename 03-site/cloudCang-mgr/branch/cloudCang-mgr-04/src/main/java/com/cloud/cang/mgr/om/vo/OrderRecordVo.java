package com.cloud.cang.mgr.om.vo;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.om.OrderRecord;
import org.apache.shiro.SecurityUtils;


/**
 * @version 1.0
 * @description: 订单列表 VO
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 */
public class OrderRecordVo extends OrderRecord {
    private String merchantName;//商户名
    private String deviceName;//设备名

    private String smemberNameDesensitize;//用户名脱敏

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("ORDER_MEMBER_NAME_DESENSITIZE")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
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
