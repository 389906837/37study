package com.cloud.cang.api.om.dao;

import com.cloud.cang.api.om.vo.CommodityDomain;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.model.om.OrderCommodity;

import java.util.List;
import java.util.Map;

/** 订单商品明细表(OM_ORDER_COMMODITY) **/
public interface OrderCommodityDao extends GenericDao<OrderCommodity, String> {
    /***
     * 获取订单商品明细
     * @param sorderCode 订单编号
     * @return
     */
    List<CommodityDomain> selectByOrderCode(String sorderCode);
    /**
     * 查询订单商品明细列表
     * @param params 查询参数
     * @return
     */
    List<CommodityVo> selectByMap(Map<String, Object> params);
}