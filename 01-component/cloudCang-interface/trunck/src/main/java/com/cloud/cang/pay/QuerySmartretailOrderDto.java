package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 微信支付分查询订单
 * @Author: yanlingfeng
 * @Date: 2019/8/8 14:57
 */
public class QuerySmartretailOrderDto implements Serializable {
    //必填
    private String out_order_no;//
    private String smerchantCode;//

    //非必填
    private String service_id;//
    private String appid;//
    private String smemberId;//会员ID
    private String sdeviceId;

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    @Override
    public String toString() {
        return "QuerySmartretailOrderDto{" +
                "service_id='" + service_id + '\'' +
                ", out_order_no='" + out_order_no + '\'' +
                ", appid='" + appid + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", sdeviceId='" + sdeviceId + '\'' +
                '}';
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
