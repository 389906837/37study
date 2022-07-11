package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 微信支付分撤销订单返回类
 * @Author: yan
 * @Date: 2019/7/23 14:57
 */
public class CancelSmartretailOrderResult implements Serializable {
    private String appid;
    private String mchid;
    private String out_order_no;
    private String service_id;
    private String order_id;



    @Override
    public String toString() {
        return "CancelSmartretailOrderResult{" +
                "appid='" + appid + '\'' +
                ", mchid='" + mchid + '\'' +
                ", out_order_no='" + out_order_no + '\'' +
                ", service_id='" + service_id + '\'' +
                ", order_id='" + order_id + '\'' +
                '}';
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
