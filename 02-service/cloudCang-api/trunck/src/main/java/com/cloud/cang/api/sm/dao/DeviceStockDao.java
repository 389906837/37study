package com.cloud.cang.api.sm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.api.sm.vo.StockVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.DeviceStock;
import org.apache.ibatis.annotations.Param;

/**
 * 单设备库存记录表(SM_DEVICE_STOCK)
 **/
public interface DeviceStockDao extends GenericDao<DeviceStock, String> {


    /**
     * 查询设备库存商品总重量
     *
     * @param deviceId
     * @return
     */
    BigDecimal selectTotalWeight(String deviceId);

    /**
     * 查询设备商品编号库存明细
     *
     * @param deviceId 设备ID
     */
    List<StockVo> selectStockByDeviceId(String deviceId);

    /***
     * 查询设备库存
     * @param deviceId 设备ID
     * @param commodityId 商品ID
     */
    DeviceStock selectDeviceStockByCommodityId(@Param("deviceId") String deviceId, @Param("commodityId") String commodityId);



    /**
     * 查询设备商品编号库存明细(重力识别柜)
     *
     * @param map 设备ID diffData 相差值
     */
    List<StockVo> selectGravityStockByDeviceId(Map<String, Object> map);

    /**
     * 查询匹配最近几个月内设备售出的最小重量的商品
     *
     * @param map 设备ID diffData 相差值 month 最近几个月（默认一个月）
     */
    List<StockVo> selectSellGoodsByDeviceId(Map<String, Object> map);

    /***
     * 查询设备库存
     * @param deviceId 设备ID
     * @param svrCode 商品视觉编号
     */
    DeviceStock selectDeviceStockByVrCode(@Param("deviceId") String deviceId, @Param("svrCode") String svrCode);

}