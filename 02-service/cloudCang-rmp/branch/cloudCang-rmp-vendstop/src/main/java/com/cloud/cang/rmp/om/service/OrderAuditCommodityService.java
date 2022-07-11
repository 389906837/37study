package com.cloud.cang.rmp.om.service;

import com.cloud.cang.model.om.OrderAuditCommodity;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface OrderAuditCommodityService extends GenericService<OrderAuditCommodity, String> {

    /**
     * 更新审核订单商品信息
     * @param updateMap
     */
    void updateByOrderId(Map<String, Object> updateMap);
}