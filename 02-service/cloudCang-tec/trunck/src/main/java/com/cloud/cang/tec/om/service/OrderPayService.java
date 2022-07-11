package com.cloud.cang.tec.om.service;

import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface OrderPayService extends GenericService<OrderPay, String> {


    /**
     * 根据订单编号查询付款编号
     * @param orderCode
     * @return
     */
    String selectOutTradeNoByOrderCode(String orderCode);

    /**
     * 更新数据
     *
     * @param map
     */
    void updateDataByMap(Map<String, Object> map);
}