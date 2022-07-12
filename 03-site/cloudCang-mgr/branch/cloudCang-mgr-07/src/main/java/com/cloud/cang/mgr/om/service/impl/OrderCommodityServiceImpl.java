package com.cloud.cang.mgr.om.service.impl;

import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.vo.OrderCommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.mgr.om.dao.OrderCommodityDao;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.mgr.om.service.OrderCommodityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderCommodityServiceImpl extends GenericServiceImpl<OrderCommodity, String> implements
        OrderCommodityService {

    @Autowired
    OrderCommodityDao orderCommodityDao;


    @Override
    public GenericDao<OrderCommodity, String> getDao() {
        return orderCommodityDao;
    }


    /**
     * 查看订单详情
     *
     * @param orderCommodityDomain
     * @return
     */
    @Override
    public Page<OrderCommodityVo> queryDetails(Page<OrderCommodityVo> page, OrderCommodityDomain orderCommodityDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<OrderCommodityVo>) orderCommodityDao.queryDetailsByDomain(orderCommodityDomain);
    }

    /**
     * 根据订单Id和商品ID查询订单商品明细
     *
     * @param orderId     订单ID
     * @param commodityId 商品ID
     * @return OrderCommodity
     */
    @Override
    public OrderCommodity selectByOrderIdAndComId(String orderId, String commodityId) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("commodityId", commodityId);
        return orderCommodityDao.selectByOrderIdAndComId(map);
    }

    /**
     * 根据订单Id删除订单从表
     *
     * @param orderId 订单ID
     * @return OrderCommodity
     */
    @Override
    public void delectByOrderId(String orderId) {
        orderCommodityDao.delectByOrderId(orderId);
    }

    /**
     * 编辑订单订单详情
     *
     * @param orderCommodityDomain 订单ID
     * @return OrderCommodity
     */
    @Override
    public List<OrderCommodityVo> selectOrderDetail(OrderCommodityDomain orderCommodityDomain) {
        return orderCommodityDao.selectOrderDetail(orderCommodityDomain);
    }
}