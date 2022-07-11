package com.cloud.cang.pay;

/**
 * @version 1.0
 * @Description: 微信支付分完结订单返回类
 * @Author: yan
 * @Date: 2019/8/8 14:57
 */

public class EndSmartretailOrderResult {

    //必填
    private String appid;//调用接口提交的公众账号ID
    private String mchid;//微信支付分配的商户号
    private String out_order_no;//调用接口提交的商户服务订单号
    private String service_id;//调用该接口提交的服务ID
    private String order_id;//微信支付服务订单号 每个微信支付服务订单号与商户号下对应的商户服务订单号一一对应


    @Override
    public String toString() {
        return "EndSmartretailOrderResult{" +
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
