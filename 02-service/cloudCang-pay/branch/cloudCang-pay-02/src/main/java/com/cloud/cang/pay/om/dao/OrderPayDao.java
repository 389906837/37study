package com.cloud.cang.pay.om.dao;

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
     * 查询订单号
     * @param outTradePayNo 商户订单支付编号
     * @return
     */
    String selectOrderCodeByPayNo(String outTradePayNo);
    /**
     * 更新数据
     * @param map
     */
    void updateDataByMap(Map<String, Object> map);
    /**
     * 查询商户订单支付编号
     * @param outTradeNo 商户订单编号
     * @return
     */
    String selectOutTradeNoByOrderCode(String outTradeNo);
}