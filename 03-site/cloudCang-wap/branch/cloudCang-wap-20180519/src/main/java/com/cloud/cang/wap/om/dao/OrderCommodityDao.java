package com.cloud.cang.wap.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.wap.om.vo.CommodityDomain;

/** 订单商品明细表(OM_ORDER_COMMODITY) **/
public interface OrderCommodityDao extends GenericDao<OrderCommodity, String> {
    /***
     * 获取订单商品明细
     * @param sorderCode 订单编号
     * @return
     */
    List<CommodityDomain> selectByOrderCode(String sorderCode);

}