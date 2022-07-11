package com.cloud.cang.tec.sb.service;

import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.generic.GenericService;

public interface DeviceInfoService extends GenericService<DeviceInfo, String> {

    /**
     * 查看商户所有设备
     *
     * @param merchantId 商户ID
     */
    String selectDevice(String merchantId);
}