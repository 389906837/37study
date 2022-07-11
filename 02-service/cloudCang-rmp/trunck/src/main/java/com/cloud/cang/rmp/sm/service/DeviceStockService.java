package com.cloud.cang.rmp.sm.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.inventory.CommodityDiffVo;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rm.ReplenishmentDto;
import com.cloud.cang.rm.ReplenishmentResult;
import com.cloud.cang.rm.StockBaseDto;
import com.cloud.cang.rm.StockOperDto;

import java.util.Map;

public interface DeviceStockService extends GenericService<DeviceStock, String> {

    /**
     * 查询库存
     * @param sdeviceId
     * @param commodityId
     * @return
     */
    int selectByMap(String sdeviceId, String commodityId);

    /**
     * 生成补货单
     * @param replenishmentDto 补货单参数
     * @return
     */
    ResponseVo<ReplenishmentResult> generateReplenishmentRecord(ReplenishmentDto replenishmentDto) throws Exception;

    /**
     * 商品库存变更操作
     * @param diffVo 操作商品信息
     * @param baseDto 商户 设备数据
     * @param itype 操作类型 10 补货下架 20 商品出售 30 定时盘点
     * @param orderId 订单ID
     * @return
     */
    Map<String,Object> changeCommoditySotck(Integer itype, CommodityDiffVo diffVo, StockBaseDto baseDto, String orderId) throws Exception;

    /***
     * 查询设备库存
     * @param deviceId 设备ID
     * @param commodityId 商品ID
     */
    DeviceStock selectDeviceStockByCommodityId(String deviceId, String commodityId);

    /**
     *  根据主键 锁定设备商品库存数据
     * @param id 设备商品库存ID
     * @return
     */
    DeviceStock selectByPrimaryKeyForUpdate(String id);

    /**
     * 库存操作服务
     * @param stockOperDto 服务参数
     * @return
     */
    ResponseVo<String> stockOper(StockOperDto stockOperDto) throws Exception;
}