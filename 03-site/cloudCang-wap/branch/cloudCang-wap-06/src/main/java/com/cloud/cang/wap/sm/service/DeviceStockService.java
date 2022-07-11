package com.cloud.cang.wap.sm.service;

import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.sm.vo.DeviceStockList;

import java.util.List;

public interface DeviceStockService extends GenericService<DeviceStock, String> {

    /**
     * 根据设备编号查询设备商品库存
     *
     * @param deviceCode
     * @return
     */
    List<DeviceStockList> selectDeviceStock(String deviceCode);

}