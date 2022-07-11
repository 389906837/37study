package com.cloud.cang.pay.alipay.vo;


/**
 * 支付宝退款请求参数
 * Created by zhouhong on 2017/11/8.
 */

public class AliBaseData {

    //订单支付时传入的商户订单号,不能和 trade_no同时为空
    private String out_trade_no; //商户订单号

    private String trade_no; //支付宝交易号，和商户订单号不能同时为空
    private String out_request_no;//请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOut_request_no() {
        return out_request_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    @Override
    public String toString() {
        return "AliBaseData{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", out_request_no='" + out_request_no + '\'' +
                '}';
    }
}
