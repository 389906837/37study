package com.cloud.cang.rmp.sm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.DeviceStock;
import org.apache.ibatis.annotations.Param;

/** 单设备库存记录表(SM_DEVICE_STOCK) **/
public interface DeviceStockDao extends GenericDao<DeviceStock, String> {

    int selectByMap(@Param("sdeviceId") String sdeviceId, @Param("scommodityId") String scommodityId);


    DeviceStock selectByDeviceStock(String sdeviceId);

    /***
     * 查询设备库存
     * @param deviceId 设备ID
     * @param commodityId 商品ID
     */
    DeviceStock selectDeviceStockByCommodityId(@Param("deviceId") String deviceId, @Param("commodityId") String commodityId);
    /**
     *  根据主键 锁定设备商品库存数据
     * @param id 设备商品库存ID
     * @return
     */
    DeviceStock selectByPrimaryKeyForUpdate(String id);
}