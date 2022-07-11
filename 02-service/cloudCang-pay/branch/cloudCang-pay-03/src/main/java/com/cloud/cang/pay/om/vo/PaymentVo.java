package com.cloud.cang.pay.om.vo;



import com.alipay.api.domain.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 免密付款订单查询、关闭、撤销model
 * @Author: zhouhong
 * @Date: 2018/3/17 17:14
 */
public class PaymentVo implements Serializable {

    private String out_trade_no;//商户订单号
    private String trade_no;//支付宝交易号
    private String operator_id;//商户自定义的的操作员 ID

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

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    @Override
    public String toString() {
        return "PaymentVo{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", operator_id='" + operator_id + '\'' +
                '}';
    }
}
