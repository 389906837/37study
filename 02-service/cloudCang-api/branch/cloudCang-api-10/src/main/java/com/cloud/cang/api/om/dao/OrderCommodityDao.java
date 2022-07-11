package com.cloud.cang.api.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.OrderCommodity;

/** 订单商品明细表(OM_ORDER_COMMODITY) **/
public interface OrderCommodityDao extends GenericDao<OrderCommodity, String> {


    /**
     * 根据订单Id查询订单商品
     *
     * @param orderId
     * @return
     */
    List<OrderCommodity> selectByOrderId(String orderId);
}