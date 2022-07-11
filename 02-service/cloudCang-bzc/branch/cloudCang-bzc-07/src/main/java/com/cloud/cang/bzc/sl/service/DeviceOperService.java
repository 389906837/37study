package com.cloud.cang.bzc.sl.service;

import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.generic.GenericService;

public interface DeviceOperService extends GenericService<DeviceOper, String> {

    /**
     * 获取最近一个会员的开门日志
     * @param deviceCode 设备编号
     */
    DeviceOper selectLastByDeviceId(String deviceCode);
}