package com.cloud.cang.pay.om.service;

import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.om.OrderRecord;

import java.util.Map;

public interface OrderPayService extends GenericService<OrderPay, String> {


    /**
     * 新增订单支付信息数据
     * @param orderRecord 订单数据
     * @param payType 支付方式 10 微信 20 支付宝
     * @return
     */
    OrderPay insertOrderPay(OrderRecord orderRecord, Integer payType) throws Exception;

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