package com.cloud.cang.jy;

import com.cloud.cang.act.GiveActivityDto;
import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @description: 订单 Dto
 * @author:严凌峰
 * @time:
 */
public class OrderCommDto extends SuperDto {

    private List<OrderCommodityDto> comms;//同一个订单下的商品
    private OrderDto orderDto;//商品订单表

    @Override
    public String toString() {
        return "OrderCommDto{" +
                "comms=" + comms +
                ", orderDto=" + orderDto +
                '}';
    }

    public List<OrderCommodityDto> getComms() {
        return comms;
    }

    public void setComms(List<OrderCommodityDto> comms) {
        this.comms = comms;
    }

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }


}
