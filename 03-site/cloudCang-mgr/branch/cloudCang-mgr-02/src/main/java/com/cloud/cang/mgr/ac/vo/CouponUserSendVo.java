package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.CouponUserSend;

/**
 * @version 1.0
 * @Description: 优惠券批量下发用户表Vo
 * @Author: ChangTanchang
 * @Date: 2018/03/20 14:33
 */
public class CouponUserSendVo extends CouponUserSend{

    // 排序
    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
