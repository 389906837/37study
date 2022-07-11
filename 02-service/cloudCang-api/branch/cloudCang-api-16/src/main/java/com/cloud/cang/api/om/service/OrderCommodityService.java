package com.cloud.cang.api.om.service;

import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface OrderCommodityService extends GenericService<OrderCommodity, String> {


    /**
     * 根据订单Id查询订单商品
     *
     * @param orderId
     * @return
     */
    List<OrderCommodity> selectByOrderId(String orderId);
}