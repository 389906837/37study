package com.cloud.cang.rmp.sm.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sm.StockDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface StockDetailService extends GenericService<StockDetail, String> {

    /**
     * 获取商品明细列表
     * @param sstockId 设备商品库存ID
     * @return
     */
    List<StockDetail> selectByMapForUpdate(String sstockId);

    /**
     * 获取商品库存明细
     * @param sid 库存唯一标识
     * @return
     */
    StockDetail selectBySid(String sid);

    /***
     * 获取库存商品异常商品数据
     * @param sstockId 设备商品库存ID
     * @return
     */
    Map<String, BigDecimal> selectByAbnormalDetails(String sstockId);

    /**
     * 获取设备商品 异常库存明细
     * @param sstockId 设备商品库存ID
     * @param unitStr 商品最小销售单位
     * @return
     */
    String getAbnormalMap(String sstockId, String unitStr);
}