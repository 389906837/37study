package com.cloud.cang.mgr.om.service;

import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.vo.OrderCommodityVo;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;

public interface OrderCommodityService extends GenericService<OrderCommodity, String> {

    /**
     * 查看订单详情
     *
     * @param orderCommodityDomain
     * @return
     */
    Page<OrderCommodityVo> queryDetails(Page<OrderCommodityVo> page, OrderCommodityDomain orderCommodityDomain);

    /**
     * 根据订单Id和商品ID查询订单商品明细
     *
     * @param orderId     订单ID
     * @param commodityId 商品ID
     * @return OrderCommodity
     */
    OrderCommodity selectByOrderIdAndComId(String orderId, String commodityId);

    /**
     * 根据订单Id删除订单从表
     *
     * @param orderId 订单ID
     * @return OrderCommodity
     */
    void delectByOrderId(String orderId);

    /**
     * 编辑订单订单详情
     *
     * @param orderCommodityDomain 订单
     * @return OrderCommodityVo
     */
    List<OrderCommodityVo> selectOrderDetail(OrderCommodityDomain orderCommodityDomain);


}