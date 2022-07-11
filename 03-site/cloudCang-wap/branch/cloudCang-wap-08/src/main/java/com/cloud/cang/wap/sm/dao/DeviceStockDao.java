package com.cloud.cang.wap.sm.dao;

import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.wap.sm.vo.DeviceStockList;

/**
 * 单设备库存记录表(SM_DEVICE_STOCK)
 **/
public interface DeviceStockDao extends GenericDao<DeviceStock, String> {


    /**
     * 根据设备编号查询设备商品库存
     *
     * @param deviceCode
     * @return
     */
    List<DeviceStockList> selectDeviceStock(String deviceCode);
}