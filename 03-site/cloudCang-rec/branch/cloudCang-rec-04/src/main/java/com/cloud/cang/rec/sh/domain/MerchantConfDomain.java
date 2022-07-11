package com.cloud.cang.rec.sh.domain;

import com.cloud.cang.model.sh.MerchantConf;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 商户微信/支付宝配置 domain
 * @Author: yanlingfeng
 * @Date: 2018/3/3 16:14
 */
public class MerchantConfDomain extends MerchantConf {
    private String WCId;//微信Id
    private String WCscallBackUrl;//微信授权回调URL
    private String WCsappId;//APPID
    private String WCsappSecret;//MD5
    private BigDecimal WCfpayLimitSingle;//单笔支付限额
    private BigDecimal WCfpayLimitDay;//单日支付限额
    private BigDecimal WCfpayLimitMonth;//每月支付限额
    private BigDecimal WCfpayLimitMoney;//单笔多大金额短信确认
    private String WCspId;//微信商户号
    //微信公钥   private String WCspublicKey;
    //微信应用私钥  private String WCsprivateKey;
    //微信应用公钥 private String WCsappPublicKey;
    private String WCspayCallBackUrl;//微信支付回调地址
    private String WCsplanId;//微信代扣模板ID
    private String IISWC;//是否微信配置  0否;1是

    public String getWCspId() {
        return WCspId;
    }

    public void setWCspId(String WCspId) {
        this.WCspId = WCspId;
    }

    public String getWCsplanId() {
        return WCsplanId;
    }

    public void setWCsplanId(String WCsplanId) {
        this.WCsplanId = WCsplanId;
    }

    public String getWCspayCallBackUrl() {
        return WCspayCallBackUrl;
    }

    public void setWCspayCallBackUrl(String WCspayCallBackUrl) {
        this.WCspayCallBackUrl = WCspayCallBackUrl;
    }

    public String getWCId() {
        return WCId;
    }

    public String getIISWC() {
        return IISWC;
    }

    public void setIISWC(String IISWC) {
        this.IISWC = IISWC;
    }


    public String getWCscallBackUrl() {
        return WCscallBackUrl;
    }

    public void setWCscallBackUrl(String WCscallBackUrl) {
        this.WCscallBackUrl = WCscallBackUrl;
    }

    public String getWCsappId() {
        return WCsappId;
    }

    public void setWCsappId(String WCsappId) {
        this.WCsappId = WCsappId;
    }


    public void setWCId(String WCId) {
        this.WCId = WCId;
    }

    public String getWCsappSecret() {
        return WCsappSecret;
    }

    public void setWCsappSecret(String WCsappSecret) {
        this.WCsappSecret = WCsappSecret;
    }

    public BigDecimal getWCfpayLimitSingle() {
        return WCfpayLimitSingle;
    }

    public void setWCfpayLimitSingle(BigDecimal WCfpayLimitSingle) {
        this.WCfpayLimitSingle = WCfpayLimitSingle;
    }

    public BigDecimal getWCfpayLimitDay() {
        return WCfpayLimitDay;
    }

    public void setWCfpayLimitDay(BigDecimal WCfpayLimitDay) {
        this.WCfpayLimitDay = WCfpayLimitDay;
    }

    public BigDecimal getWCfpayLimitMonth() {
        return WCfpayLimitMonth;
    }

    public void setWCfpayLimitMonth(BigDecimal WCfpayLimitMonth) {
        this.WCfpayLimitMonth = WCfpayLimitMonth;
    }

    public BigDecimal getWCfpayLimitMoney() {
        return WCfpayLimitMoney;
    }

    public void setWCfpayLimitMoney(BigDecimal WCfpayLimitMoney) {
        this.WCfpayLimitMoney = WCfpayLimitMoney;
    }

}
