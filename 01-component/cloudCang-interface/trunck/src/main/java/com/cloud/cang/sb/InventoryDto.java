package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 根据商户ID----盘货
 * DTO
 * @author zengzexiong
 * @version 1.0
 * @date 2018年3月19日16:45:37
 */
public class InventoryDto extends SuperDto {

    private String merchantId;        //  商户ID  定时盘货时必填
    private String deviceId;        // 设备ID     单台设备盘货时必填

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
