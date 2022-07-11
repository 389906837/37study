package com.cloud.cang.api.sm.service;

import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface StockDetailService extends GenericService<StockDetail, String> {

    /**
     * 查询设备库存
     * @param deviceId 设备ID
     * @return
     */
    Map<String,Integer> selectMapByDeviceId(String deviceId);
}