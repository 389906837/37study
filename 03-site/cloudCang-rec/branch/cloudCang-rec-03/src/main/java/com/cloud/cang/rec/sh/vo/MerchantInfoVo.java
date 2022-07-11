package com.cloud.cang.rec.sh.vo;

import com.cloud.cang.model.sh.MerchantInfo;

/**
 * @version 1.0
 * @Description: 商户列表返回Vo
 * @Author: yanlingfeng
 * @Date: 2018/2/10 16:14
 */
public class MerchantInfoVo extends MerchantInfo {
    private  String iisConfWechatGzh ; //微信公众号是否配置
    private  String iisConfAlipayShh ; //支付宝生活号是否配置
    private  String iisConfWechat ; //微信支付是否配置
    private  String iisConfAlipay ; //支付宝是否配置

    public String getIisConfWechatGzh() {
        return iisConfWechatGzh;
    }

    public void setIisConfWechatGzh(String iisConfWechatGzh) {
        this.iisConfWechatGzh = iisConfWechatGzh;
    }

    public String getiisConfAlipayShh() {
        return iisConfAlipayShh;
    }

    public void setiisConfAlipayShh(String iisConfAlipayShh) {
        this.iisConfAlipayShh = iisConfAlipayShh;
    }

    public String getIisConfWechat() {
        return iisConfWechat;
    }

    public void setIisConfWechat(String iisConfWechat) {
        this.iisConfWechat = iisConfWechat;
    }

    public String getIisConfAlipay() {
        return iisConfAlipay;
    }

    public void setIisConfAlipay(String iisConfAlipay) {
        this.iisConfAlipay = iisConfAlipay;
    }

}
