package com.cloud.cang.mgr.tb.vo;

import com.cloud.cang.model.tb.ThirdDeviceSku;


/**
 * @version 1.0
 * @ClassName ThirdDeviceSkuVo
 * @Description 第三方商户设备SKU
 * @Author zengzexiong
 * @Date 2018年10月9日15:12:51
 */
public class ThirdDeviceSkuVo extends ThirdDeviceSku {
    private String orderStr;//排序字段
    private String queryCondition;//查询条件
    private String merchantName;//商户名称
    private String deviceName;//设备名称

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
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
}
