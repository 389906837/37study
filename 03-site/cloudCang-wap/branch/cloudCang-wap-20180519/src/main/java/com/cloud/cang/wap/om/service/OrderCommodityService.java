package com.cloud.cang.wap.om.service;

import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.om.vo.CommodityDomain;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface OrderCommodityService extends GenericService<OrderCommodity, String> {

    /***
     * 获取订单商品明细
     * @param sorderCode 订单编号
     * @return
     */
    List<CommodityDomain> selectByOrderCode(String sorderCode);
}