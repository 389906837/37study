package com.cloud.cang.mgr.om.service;

import com.cloud.cang.mgr.om.domain.OrderAuditCommodityDomain;
import com.cloud.cang.mgr.om.domain.OrderCommodityDomain;
import com.cloud.cang.mgr.om.vo.OrderAuditCommodityVo;
import com.cloud.cang.mgr.om.vo.OrderCommodityVo;
import com.cloud.cang.model.om.OrderAuditCommodity;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.om.OrderCommodity;
import com.github.pagehelper.Page;

import java.util.List;

public interface OrderAuditCommodityService extends GenericService<OrderAuditCommodity, String> {

    /**
     * 查看异常订单详情
     *
     * @param orderAuditCommodityDomain
     * @return
     */
    Page<OrderAuditCommodityVo> queryDetails(Page<OrderAuditCommodityVo> page, OrderAuditCommodityDomain orderAuditCommodityDomain);

    /**
     * 编辑异常订单详情
     *
     * @param orderAuditCommodity 异常订单
     * @return OrderAuditCommodityVo
     */
    List<OrderAuditCommodityVo> selectOrderAuditDetail(OrderAuditCommodity orderAuditCommodity);

    /**
     * 根据异常订单Id删除从表
     *
     * @param orderId
     */
    void delectByOrderId(String orderId);
}