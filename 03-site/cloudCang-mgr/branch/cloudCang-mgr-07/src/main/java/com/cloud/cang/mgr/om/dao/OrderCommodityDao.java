package com.cloud.cang.mgr.om.dao;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.vo.OrderCommodityVo;
import com.cloud.cang.model.om.OrderCommodity;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 订单商品明细表(OM_ORDER_COMMODITY)
 **/
public interface OrderCommodityDao extends GenericDao<OrderCommodity, String> {

    Page<OrderCommodityVo> queryDetailsByDomain(OrderCommodityDomain orderCommodityDomain);

    List<OrderCommodityVo> selectOrderDetail(OrderCommodityDomain orderCommodityDomain);

    /**
     * 根据订单Id和商品ID查询订单商品明细
     *
     * @param map { : }      orderId订单ID : commodityId 商品ID
     * @return OrderCommodity
     */
    OrderCommodity selectByOrderIdAndComId(Map<String, String> map);

    /**
     * 根据订单Id删除订单从表
     *
     * @param orderId 订单ID
     * @return OrderCommodity
     */
    void delectByOrderId(String orderId);

}