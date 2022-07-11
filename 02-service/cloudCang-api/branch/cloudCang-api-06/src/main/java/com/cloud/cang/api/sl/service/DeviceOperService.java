package com.cloud.cang.api.sl.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sl.DeviceOper;

import java.util.Map;

public interface DeviceOperService extends GenericService<DeviceOper, String> {


    String selectLastOpLog(Map<String, String> map);

    /**
     * 查询最后一个开门人ID
     * @param deviceCode
     * @return
     */
    String selectLastUserByDeviceCode(String deviceCode);


}