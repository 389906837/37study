package com.cloud.cang.bzc.sm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.model.sm.StockDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/** 设备实时库存明细(SM_STOCK_DETAIL) **/
public interface StockDetailDao extends GenericDao<StockDetail, String> {
    /**
     * 查询设备库存
     * @param deviceId 设备ID
     * @return
     */
    List<Map<String,Object>> selectMapByDeviceId(String deviceId);

}