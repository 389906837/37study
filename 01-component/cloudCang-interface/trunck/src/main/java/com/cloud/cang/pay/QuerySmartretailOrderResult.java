package com.cloud.cang.pay;

import java.util.List;

/**
 * @version 1.0
 * @Description: 微信支付分查询订单返回类
 * @Author: yan
 * @Date: 2019/8/8 14:57
 */
public class QuerySmartretailOrderResult {
    private String appid;//
    private String mchid;//
    private String out_order_no;//
    private String service_id;//
    private String state;//
    private String service_start_time;//
    private String service_start_location;//
    private String service_introduction;//
    private List<FeeDto> fees;//
    private Integer risk_amount;//
    private String finish_ticket;//用于完结订单时传入,确保订单完结时数据完整. 只有单据状态为USER_ACCEPTED才返回完结凭证

    //非必填
    private String finish_type;//
    private String service_end_time;//
    private String real_service_start_time;//
    private String real_service_end_time;//
    private String pay_succ_time;//
    private String service_end_location;//
    private String real_service_end_location;//
    private List<DiscountDto> discounts;//
    private Integer total_amount;//
    private String attach;//
    private String finish_transaction_id;//

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public List<FeeDto> getFees() {
        return fees;
    }

    public void setFees(List<FeeDto> fees) {
        this.fees = fees;
    }

    public Integer getRisk_amount() {
        return risk_amount;
    }

    public void setRisk_amount(Integer risk_amount) {
        this.risk_amount = risk_amount;
    }

    public String getFinish_ticket() {
        return finish_ticket;
    }

    public void setFinish_ticket(String finish_ticket) {
        this.finish_ticket = finish_ticket;
    }

    public String getFinish_type() {
        return finish_type;
    }

    public void setFinish_type(String finish_type) {
        this.finish_type = finish_type;
    }

    public String getService_end_time() {
        return service_end_time;
    }

    public void setService_end_time(String service_end_time) {
        this.service_end_time = service_end_time;
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

    public String getPay_succ_time() {
        return pay_succ_time;
    }

    public void setPay_succ_time(String pay_succ_time) {
        this.pay_succ_time = pay_succ_time;
    }

    public String getService_end_location() {
        return service_end_location;
    }

    public void setService_end_location(String service_end_location) {
        this.service_end_location = service_end_location;
    }

    public String getReal_service_end_location() {
        return real_service_end_location;
    }

    public void setReal_service_end_location(String real_service_end_location) {
        this.real_service_end_location = real_service_end_location;
    }

    public List<DiscountDto> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDto> discounts) {
        this.discounts = discounts;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getFinish_transaction_id() {
        return finish_transaction_id;
    }

    public void setFinish_transaction_id(String finish_transaction_id) {
        this.finish_transaction_id = finish_transaction_id;
    }

    @Override
    public String toString() {
        return "QuerySmartretailOrderResult{" +
                "appid='" + appid + '\'' +
                ", mchid='" + mchid + '\'' +
                ", out_order_no='" + out_order_no + '\'' +
                ", service_id='" + service_id + '\'' +
                ", state='" + state + '\'' +
                ", service_start_time='" + service_start_time + '\'' +
                ", service_start_location='" + service_start_location + '\'' +
                ", service_introduction='" + service_introduction + '\'' +
                ", fees=" + fees +
                ", risk_amount=" + risk_amount +
                ", finish_ticket='" + finish_ticket + '\'' +
                ", finish_type='" + finish_type + '\'' +
                ", service_end_time='" + service_end_time + '\'' +
                ", real_service_start_time='" + real_service_start_time + '\'' +
                ", real_service_end_time='" + real_service_end_time + '\'' +
                ", pay_succ_time='" + pay_succ_time + '\'' +
                ", service_end_location='" + service_end_location + '\'' +
                ", real_service_end_location='" + real_service_end_location + '\'' +
                ", discounts=" + discounts +
                ", total_amount=" + total_amount +
                ", attach='" + attach + '\'' +
                ", finish_transaction_id='" + finish_transaction_id + '\'' +
                '}';
    }
}
