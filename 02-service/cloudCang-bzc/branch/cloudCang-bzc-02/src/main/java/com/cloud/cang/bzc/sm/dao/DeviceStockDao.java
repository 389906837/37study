package com.cloud.cang.bzc.sm.dao;

import com.cloud.cang.bzc.sm.vo.StockVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.DeviceStock;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 单设备库存记录表(SM_DEVICE_STOCK)
 **/
public interface DeviceStockDao extends GenericDao<DeviceStock, String> {


    int selectByMap(@Param("sdeviceId") String sdeviceId, @Param("scommodityId") String scommodityId);

    int updateIstock(Map<String, Object> map);

    /**
     * 查询设备商品编号库存明细
     *
     * @param deviceId 设备ID
     */
    List<StockVo> selectStockByDeviceId(String deviceId);

    /**
     * 查询设备商品编号库存明细(重力识别柜)
     *
     * @param map 设备ID diffData 相差值
     */
    List<StockVo> selectGravityStockByDeviceId(Map<String, Object> map);

    /***
     * 查询设备库存
     * @param deviceId 设备ID
     * @param commodityId 商品ID
     */
    DeviceStock selectDeviceStockByCommodityId(@Param("deviceId") String deviceId, @Param("commodityId") String commodityId);

    /**
     * 修改设备库存
     *
     * @param addMap
     */
    int updateIstockByAdd(Map<String, String> addMap);

}