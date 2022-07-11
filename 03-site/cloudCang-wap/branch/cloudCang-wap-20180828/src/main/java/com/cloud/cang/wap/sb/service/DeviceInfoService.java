package com.cloud.cang.wap.sb.service;

import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.ac.vo.DeviceVo;

import java.util.List;

public interface DeviceInfoService extends GenericService<DeviceInfo, String> {

    /**
     * 获取设备信息
     * @param deviceCode 设备编号
     */
    DeviceInfo selectByCode(String deviceCode);
    /**
     * 获取设备信息
     * @param queryCondition 设备编号
     */
    List<DeviceVo> selectUseDevice(String queryCondition);
}