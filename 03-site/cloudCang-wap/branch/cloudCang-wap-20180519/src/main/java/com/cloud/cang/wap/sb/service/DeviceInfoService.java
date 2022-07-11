package com.cloud.cang.wap.sb.service;

import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.generic.GenericService;

public interface DeviceInfoService extends GenericService<DeviceInfo, String> {

    /**
     * 获取设备信息
     * @param deviceCode 设备编号
     */
    DeviceInfo selectByCode(String deviceCode);
}