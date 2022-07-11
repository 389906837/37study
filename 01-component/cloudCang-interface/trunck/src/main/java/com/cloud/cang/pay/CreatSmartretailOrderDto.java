package com.cloud.cang.pay;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Description: 微信支付分创建订单
 * @Author: yanlingfeng
 * @Date: 2019/8/1 14:57
 */
public class CreatSmartretailOrderDto implements Serializable {
    //必填
    // private String appid;//微信公众平台分配的与传入的商户号建立了支付绑定关系的appid
    private String smerchantCode;//商户编号
    private String smemberId;//会员ID
    private String smemberCode;//会员编号
    private String out_order_no;//商户系统内部服务订单号（不是交易单号）
    // private String service_id;//该服务ID有本接口对应产品的权限
    private String service_start_time;//用户下单时确认的服务开始时间
    private String service_start_location;//开始使用服务的地点
    private String service_introduction;//服务信息，用于介绍本订单所提供的服务，不超过20个字符
    private Integer risk_amount;//该笔订单会产生的最大金额，不能大于服务风险金额大于0的数字，单位为分
    private String deviceCode;
    private String deviceId;

    //非必填
    private String orderId;//订单iD
    private String service_end_time;//用户下单时确定的预计服务结束时间，如果超出此时间服务方还未完结订单，用户的本订单将会进入待处理状态,可不填写
    private String service_end_location;//预计服务结束的地点，用户下单时未确认服务结束地点时，可不填写。
    private List<FeeDto> fees;//	后付费，最多包含100条付费项目。
    private List<DiscountDto> discounts;//	商户优惠，最多包含5条商户优惠。
    private String attach;//	商户数据包,可存放本订单所需信息.
    private boolean need_user_confirm;//	true：使用需用户确认订单类型；false：使用免确认订单类型；默认为true.
    //private String openid;//微信用户在商户对应appid下的唯一标识，使用免确认订单类型必须填写，使用需用户确认订单类型不允许填写
    private String sremark;//备注
    private Integer total_amount;//结算订单金额

    public String getSmemberCode() {
        return smemberCode;
    }

    public void setSmemberCode(String smemberCode) {
        this.smemberCode = smemberCode;
    }
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CreatSmartretailOrderDto{" +
                "smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", out_order_no='" + out_order_no + '\'' +
                ", service_start_time='" + service_start_time + '\'' +
                ", service_start_location='" + service_start_location + '\'' +
                ", service_introduction='" + service_introduction + '\'' +
                ", risk_amount=" + risk_amount +
                ", service_end_time='" + service_end_time + '\'' +
                ", service_end_location='" + service_end_location + '\'' +
                ", fees=" + fees +
                ", discounts=" + discounts +
                ", attach='" + attach + '\'' +
                ", need_user_confirm=" + need_user_confirm +
                '}';
    }

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

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }


    public String getService_start_time() {
        return service_start_time;
    }

    public void setService_start_time(String service_start_time) {
        this.service_start_time = service_start_time;
    }

    public String getService_start_location() {
        return service_start_location;
    }

    public void setService_start_location(String service_start_location) {
        this.service_start_location = service_start_location;
    }

    public String getService_introduction() {
        return service_introduction;
    }

    public void setService_introduction(String service_introduction) {
        this.service_introduction = service_introduction;
    }

    public Integer getRisk_amount() {
        return risk_amount;
    }

    public void setRisk_amount(Integer risk_amount) {
        this.risk_amount = risk_amount;
    }

    public String getService_end_time() {
        return service_end_time;
    }

    public void setService_end_time(String service_end_time) {
        this.service_end_time = service_end_time;
    }

    public String getService_end_location() {
        return service_end_location;
    }

    public void setService_end_location(String service_end_location) {
        this.service_end_location = service_end_location;
    }

    public List<FeeDto> getFees() {
        return fees;
    }

    public void setFees(List<FeeDto> fees) {
        this.fees = fees;
    }

    public List<DiscountDto> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDto> discounts) {
        this.discounts = discounts;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public boolean isNeed_user_confirm() {
        return need_user_confirm;
    }

    public void setNeed_user_confirm(boolean need_user_confirm) {
        this.need_user_confirm = need_user_confirm;
    }
}
