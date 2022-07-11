package com.cloud.cang.pay;

import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.domain.VoucherDetail;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Description: 免密支付返回参数
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class QueryWechatFreePayResult implements Serializable {

    private String appid;//请求appid

    private String mch_id;//商户号

    private String device_info;//设备号

    private String openid;//用户标识

    private String is_subscribe;//是否关注公众账号

    private String trade_type;//交易类型
    /**
     *
     SUCCESS—支付成功
     REFUND—转入退款
     NOTPAY—未支付
     CLOSED—已关闭
     ACCEPT—已接收，等待扣款
     PAY_FAIL--支付失败(其他原因，如银行返回失败)
     */
    private String trade_state;

    private String bank_type;//银行类型

    private String total_fee;//订单总金额，单位为分

    private String fee_type;//货币类型 默认人民币：CNY

    private String cash_fee;//现金支付金额订单现金支付金额

    private String cash_fee_type;//现金支付货币类型

    private String coupon_fee;//代金券或立减优惠金额

    private String coupon_count;//代金券或立减优惠使用数量

    private String coupon_id_$n;//代金券或立减优惠ID

    private String coupon_fee_$n;//单个代金券或立减优惠支付金额

    private String transaction_id;//微信支付订单号

    private String out_trade_no;//商户订单号

    private String attach;//附加数据

    private String time_end;//支付完成时间

    private String trade_state_desc;//交易状态描述

    private String contract_id;//委托代扣协议id

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(String coupon_count) {
        this.coupon_count = coupon_count;
    }

    public String getCoupon_id_$n() {
        return coupon_id_$n;
    }

    public void setCoupon_id_$n(String coupon_id_$n) {
        this.coupon_id_$n = coupon_id_$n;
    }

    public String getCoupon_fee_$n() {
        return coupon_fee_$n;
    }

    public void setCoupon_fee_$n(String coupon_fee_$n) {
        this.coupon_fee_$n = coupon_fee_$n;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    @Override
    public String toString() {
        return "QueryWechatFreePayResult{" +
                "appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", device_info='" + device_info + '\'' +
                ", openid='" + openid + '\'' +
                ", is_subscribe='" + is_subscribe + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", trade_state='" + trade_state + '\'' +
                ", bank_type='" + bank_type + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", cash_fee='" + cash_fee + '\'' +
                ", cash_fee_type='" + cash_fee_type + '\'' +
                ", coupon_fee='" + coupon_fee + '\'' +
                ", coupon_count='" + coupon_count + '\'' +
                ", coupon_id_$n='" + coupon_id_$n + '\'' +
                ", coupon_fee_$n='" + coupon_fee_$n + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", attach='" + attach + '\'' +
                ", time_end='" + time_end + '\'' +
                ", trade_state_desc='" + trade_state_desc + '\'' +
                ", contract_id='" + contract_id + '\'' +
                '}';
    }
}
