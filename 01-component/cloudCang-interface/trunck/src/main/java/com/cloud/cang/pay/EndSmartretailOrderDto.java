package com.cloud.cang.pay;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Description: 微信支付分完结订单
 * @Author: yanlingfeng
 * @Date: 2019/8/8 14:57
 */
public class EndSmartretailOrderDto implements Serializable {
    //必填
    private String out_order_no;//商户系统内部服务订单号（不是交易单号）
   // private String appid;//微信公众平台分配的与传入的商户号建立了支付绑定关系的appid，可在公众平台查看绑定关系.需要在本系统先进行配置.
   // private String service_id;//该服务ID有本接口对应产品的权限
    private Integer finish_type;//标识用户订单使用情况：1 未使用服务，取消订单；2 完成服务使用，结束订单
    private int total_amount;//大于等于0的数字，单位为分 需满足：总金额=付费项目金额之和-商户优惠项目金额之和<=订单风险金额 未使用服务，取消订单时，该字段必须填0.
    private String finish_ticket;//用于完结订单时传入
    /* 用于完结订单时传入（每次获取到的字段内容可能变化，但之前获取的字段始终有效，可一直使用）,请确保数据完整性，无需对字段再做处理。
             （注意：该字段可通过查询订单或者用户确认使用服务回调里获取finish_ticket，finish_ticket是支付分用来校验的参数，商户不可自定义）
     总长度不大于512字符,超出报错处理.*/
    private boolean profit_sharing;//是否指定服务商分账，true-需要分账，false-不需要分账
    private String smerchantCode;//商户编号
    private String smemberId;//会员id
    private String orderId;//订单iD
    private String sdeviceId;
    //非必填
    private String cancel_reason;//不超过30个字符，超出报错处理；取消订单时必填，说明取消订单的原因
    private String real_service_start_time;//实际服务开始时间
    private String real_service_end_time;//服务结束时间
    private String real_service_end_location;//不超过20个字符，超出报错处理 实际结束使用服务的地点
    private List<FeeDto> fees;//后付费，描述付费项目列表，最多包含100条付费项目（含创建订单已传入的付费项目）。
    private List<DiscountDto> discounts;//商户优惠，最多包含5条商户优惠 （含创建订单已传入的商户优惠）

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    @Override
    public String toString() {
        return "EndSmartretailOrderDto{" +
                "out_order_no='" + out_order_no + '\'' +

                ", finish_type=" + finish_type +
                ", total_amount=" + total_amount +
                ", finish_ticket='" + finish_ticket + '\'' +
                ", profit_sharing=" + profit_sharing +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", cancel_reason='" + cancel_reason + '\'' +
                ", real_service_start_time='" + real_service_start_time + '\'' +
                ", real_service_end_time='" + real_service_end_time + '\'' +
                ", real_service_end_location='" + real_service_end_location + '\'' +
                ", fees=" + fees +
                ", discounts=" + discounts +
                '}';
    }


    public Integer getFinish_type() {
        return finish_type;
    }

    public void setFinish_type(Integer finish_type) {
        this.finish_type = finish_type;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getFinish_ticket() {
        return finish_ticket;
    }

    public void setFinish_ticket(String finish_ticket) {
        this.finish_ticket = finish_ticket;
    }

    public boolean isProfit_sharing() {
        return profit_sharing;
    }

    public void setProfit_sharing(boolean profit_sharing) {
        this.profit_sharing = profit_sharing;
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

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getReal_service_start_time() {
        return real_service_start_time;
    }

    public void setReal_service_start_time(String real_service_start_time) {
        this.real_service_start_time = real_service_start_time;
    }

    public String getReal_service_end_time() {
        return real_service_end_time;
    }

    public void setReal_service_end_time(String real_service_end_time) {
        this.real_service_end_time = real_service_end_time;
    }

    public String getReal_service_end_location() {
        return real_service_end_location;
    }

    public void setReal_service_end_location(String real_service_end_location) {
        this.real_service_end_location = real_service_end_location;
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
}
