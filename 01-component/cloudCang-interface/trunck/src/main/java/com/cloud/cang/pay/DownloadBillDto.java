package com.cloud.cang.pay;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 免密支付请求参数
 * @Author: zhouhong
 * @Date: 2018/3/22 14:57
 */
public class DownloadBillDto implements Serializable {

    //========必填========
    private String smerchantCode;//收款商户
    /**
     * 微信
     * ALL，返回当日所有订单信息，默认值
     * SUCCESS，返回当日成功支付的订单
     * REFUND，返回当日退款订单
     * RECHARGE_REFUND，返回当日充值退款订单
     * 支付宝
     * trade 商户基于支付宝交易收单的业务账单
     * signcustomer 基于商户支付宝余额收入及支出等资金变动的帐务账单
     */
    private String billType;//账单类型
    private String billDate;//账单日期  微信格式：20140603 支付宝格式 日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
    private Integer itype; //第三方类型 30 微信 40 支付宝

    //=====可选========
    private String tarType;//非必传参数， 压缩账单类型 默认gzip


    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getTarType() {
        return tarType;
    }

    public void setTarType(String tarType) {
        this.tarType = tarType;
    }

    public Integer getItype() {
        return itype;
    }

    public void setItype(Integer itype) {
        this.itype = itype;
    }

    @Override
    public String toString() {
        return "DownloadBillDto{" +
                "smerchantCode='" + smerchantCode + '\'' +
                ", billType='" + billType + '\'' +
                ", billDate='" + billDate + '\'' +
                ", itype=" + itype +
                ", tarType='" + tarType + '\'' +
                '}';
    }
}
