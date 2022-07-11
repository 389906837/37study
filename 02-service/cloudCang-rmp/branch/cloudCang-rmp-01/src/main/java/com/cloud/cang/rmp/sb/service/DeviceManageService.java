package com.cloud.cang.rmp.sb.service;

import com.cloud.cang.model.sb.DeviceManage;
import com.cloud.cang.generic.GenericService;

public interface DeviceManageService extends GenericService<DeviceManage, String> {


    /**
     * 根据设备查询 设备管理人员信息
     * @param sdeviceId
     * @return
     */
    DeviceManage selectByDeviceId(String sdeviceId);
}