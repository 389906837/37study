package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 根据商户ID获取离线设备 -- dto
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年4月16日10:29:10
 */
public class OfflineDeviceDto extends SuperDto {

    private String merchantId;//传入参数--商户ID


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

}
