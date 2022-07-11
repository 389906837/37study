package com.cloud.cang.tec.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.OrderPay;

/** 订单支付商户号管理(OM_ORDER_PAY) **/
public interface OrderPayDao extends GenericDao<OrderPay, String> {


    /**
     * 根据订单编号查询付款编号
     * @param orderCode
     * @return
     */
    String selectOutTradeNoByOrderCode(String orderCode);
    /**
     * 更新数据
     * @param map
     */
    void updateDataByMap(Map<String, Object> map);
}