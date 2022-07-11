package com.cloud.cang.api.sl.service;

import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface DeviceOperService extends GenericService<DeviceOper, String> {


    String selectLastOpLog(Map<String, String> map);

}