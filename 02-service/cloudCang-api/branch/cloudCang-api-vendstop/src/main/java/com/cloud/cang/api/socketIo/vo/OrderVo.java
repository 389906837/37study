package com.cloud.cang.api.socketIo.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @ClassName: OrderVo
 * @Description: 返回给手机页面的订单对象
 * @Author: zengzexiong
 * @Date: 2018年4月18日19:05:39
 */
public class OrderVo implements Serializable {

    List<String> orderCodeList;

    public List<String> getOrderCodeList() {
        return orderCodeList;
    }

    public void setOrderCodeList(List<String> orderCodeList) {
        this.orderCodeList = orderCodeList;
    }
}
