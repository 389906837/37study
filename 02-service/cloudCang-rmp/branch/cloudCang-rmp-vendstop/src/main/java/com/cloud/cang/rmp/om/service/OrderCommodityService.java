package com.cloud.cang.rmp.om.service;

import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface OrderCommodityService extends GenericService<OrderCommodity, String> {

    /**
     * 更新订单商品信息
     * @param updateMap
     */
    void updateByOrderId(Map<String, Object> updateMap);
}