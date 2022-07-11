package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;

/**
 * @Description: 动态补货查询参数  内部服务之间的model
 * @Author: ChangTanchang
 * @Date: 2018年01月10日
 * @version 1.0
 */
public class DynamicReplenishmentDto extends SuperDto {

    private static final long serialVersionUID = 1L;

    // 设备ID
    private String sdeviceId;// (必填参数)

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    @Override
    public String toString() {
        return "DynamicReplenishmentDto{" +
                "sdeviceId='" + sdeviceId + '\'' +
                '}';
    }
}
