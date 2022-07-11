package com.cloud.cang.api.sm.service;

import com.cloud.cang.api.sm.vo.StockVo;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.sb.InventoryDto;

import java.math.BigDecimal;
import java.util.List;

public interface DeviceStockService extends GenericService<DeviceStock, String> {


    /**
     * 计算商品实时情况
     *
     * @param deviceId
     * @return
     */
    /*InventoryDto calculateCommodity(String deviceId);*/


    /**
     * 查询设备库存商品总重量
     *
     * @param deviceId
     * @return
     */
    BigDecimal selectTotalWeight(String deviceId);

    /**
     * 查询设备商品编号库存明细
     * @param deviceId 设备ID
     */
    List<StockVo> selectStockByDeviceId(String deviceId);

    /***
     * 查询设备库存
     * @param deviceId 设备ID
     * @param commodityId 商品ID
     */
    DeviceStock selectDeviceStockByCommodityId(String deviceId, String commodityId);

}