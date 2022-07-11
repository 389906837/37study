package com.cloud.cang.api.sm.service;

import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.sb.InventoryDto;

public interface DeviceStockService extends GenericService<DeviceStock, String> {


    /**
     * 计算商品实时情况
     * @param deviceId
     * @return
     */
    InventoryDto calculateCommodity(String deviceId);

}