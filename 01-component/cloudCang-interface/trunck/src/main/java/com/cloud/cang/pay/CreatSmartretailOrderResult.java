package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 微信支付分创建订单返回类
 * @Author: yan
 * @Date: 2019/7/23 14:57
 */

public class CreatSmartretailOrderResult implements Serializable {
    private String appid;//调用接口提交的公众账号ID
    private String mchid;//调用接口提交的商户号
    private String out_order_no;//调用接口提交的商户服务订单号
    private String service_id;//调用该接口提交的服务ID
    private String order_id;//微信支付服务订单号 每个微信支付服务订单号与商户号下对应的商户服务订单号一一对应
    private String miniprogram_businesstype;//小程序跳转businessType,在商户小程序跳转到微信侧小程序流程需要用户
    private String miniprogram_appid;//小程序跳转appid，在商户小程序跳转到微信侧小程序流程需要用到
    private String miniprogram_path;//小程序跳转路径
    private String miniprogram_username;//小程序跳转username，在商户APP跳转微信侧小程序流程需要用到
    private String miniprogram_package;//用于跳转到微信侧小程序订单数据,跳转到微信侧小程序传入

    private String smerchantCode;
    private String smemberId;

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
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

    public String getMiniprogram_businesstype() {
        return miniprogram_businesstype;
    }

    public void setMiniprogram_businesstype(String miniprogram_businesstype) {
        this.miniprogram_businesstype = miniprogram_businesstype;
    }

    public String getMiniprogram_appid() {
        return miniprogram_appid;
    }

    public void setMiniprogram_appid(String miniprogram_appid) {
        this.miniprogram_appid = miniprogram_appid;
    }

    public String getMiniprogram_path() {
        return miniprogram_path;
    }

    public void setMiniprogram_path(String miniprogram_path) {
        this.miniprogram_path = miniprogram_path;
    }

    public String getMiniprogram_username() {
        return miniprogram_username;
    }

    public void setMiniprogram_username(String miniprogram_username) {
        this.miniprogram_username = miniprogram_username;
    }

    public String getMiniprogram_package() {
        return miniprogram_package;
    }

    public void setMiniprogram_package(String miniprogram_package) {
        this.miniprogram_package = miniprogram_package;
    }


    @Override
    public String toString() {
        return "CreatSmartretailOrderResult{" +
                "appid='" + appid + '\'' +
                ", mchid='" + mchid + '\'' +
                ", out_order_no='" + out_order_no + '\'' +
                ", service_id='" + service_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", miniprogram_businesstype='" + miniprogram_businesstype + '\'' +
                ", miniprogram_appid='" + miniprogram_appid + '\'' +
                ", miniprogram_path='" + miniprogram_path + '\'' +
                ", miniprogram_username='" + miniprogram_username + '\'' +
                ", miniprogram_package='" + miniprogram_package + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                '}';
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }
}
