package com.cloud.cang.tec.sm.service;

import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.tec.sm.vo.RealtimeInventorySynVo;

import java.util.List;
import java.util.Map;

public interface DeviceStockService extends GenericService<DeviceStock, String> {
    /**
     * 查询商品实时库存 根据商户ID
     *
     * @param merchantId 商户ID
     */
    List<RealtimeInventorySynVo> selectRealtimeInventorySyn(String merchantId);
}