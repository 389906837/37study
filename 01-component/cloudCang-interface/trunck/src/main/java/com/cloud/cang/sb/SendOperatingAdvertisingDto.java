package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

/**
 * 发送运营位广告 -- dto
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年5月16日16:46:17
 */
public class SendOperatingAdvertisingDto extends SuperDto {

    private String deviceIds;    // 设备ID（必填）
    private String position;    // 运营位置（必填）
    private String merchantCode;    // 商户编号（必填）

    private String userId;      // 操作员ID

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SendOperatingAdvertisingDto{" +
                "deviceIds='" + deviceIds + '\'' +
                ", position='" + position + '\'' +
                ", userId='" + userId + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                '}';
    }
}
