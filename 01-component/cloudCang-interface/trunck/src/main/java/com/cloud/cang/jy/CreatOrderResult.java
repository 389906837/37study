package com.cloud.cang.jy;


import com.cloud.cang.common.SuperDto;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;

import java.util.List;

/**
 * @version 1.0
 * @Description: 生成订单返回对象
 * @Author: yanlingfeng
 */
public class CreatOrderResult extends SuperDto {
    private OrderRecord orderRecord;//订单表
    private List<OrderCommodity> orderCommodityList;//订单商品明细表

    public OrderRecord getOrderRecord() {
        return orderRecord;
    }

    public void setOrderRecord(OrderRecord orderRecord) {
        this.orderRecord = orderRecord;
    }

    public List<OrderCommodity> getOrderCommodityList() {
        return orderCommodityList;
    }

    public void setOrderCommodityList(List<OrderCommodity> orderCommodityList) {
        this.orderCommodityList = orderCommodityList;
    }
}
