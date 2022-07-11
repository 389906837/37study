package com.cloud.cang.pay.alipay.vo;


import java.math.BigDecimal;

/**
 * 支付宝请求参数
 * Created by Administrator on 2017/11/8.
 */

public class AliReqData {

    private String out_trade_no; //商户订单号

    private String subject; //订单标题

    private BigDecimal total_amount; //订单金额

    private String product_code; //销售产品码

    private String body; //对一笔交易的具体描述信息

    private String timeout_express; //该笔订单允许的最晚付款时间，逾期将关闭交易

    private String time_expire; //绝对超时时间

    private String auth_token; //标识用户授权关系

    private String goods_type; //商品主类型：0—虚拟类商品，1—实物类商

    private String passback_params; //公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数

    private String promo_params; //优惠参数注：仅与支付宝协商后可用

    private String extend_params; //业务扩展参数

    private String enable_pay_channels; //可用渠道，用户只能在指定渠道范围内支付当有多个渠道时用“,”分隔

    private String disable_pay_channels; //禁用渠道，用户不可用指定渠道支付当有多个渠道时用“,”分隔注

    private String store_id; //商户门店编号

    private String quit_url; //添加该参数后在h5支付收银台会出现返回按钮，可用于用户付款中途退出并返回到该参数指定的商户网站地址

    private String ext_user_info; //外部指定买家

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getPassback_params() {
        return passback_params;
    }

    public void setPassback_params(String passback_params) {
        this.passback_params = passback_params;
    }

    public String getPromo_params() {
        return promo_params;
    }

    public void setPromo_params(String promo_params) {
        this.promo_params = promo_params;
    }

    public String getExtend_params() {
        return extend_params;
    }

    public void setExtend_params(String extend_params) {
        this.extend_params = extend_params;
    }

    public String getEnable_pay_channels() {
        return enable_pay_channels;
    }

    public void setEnable_pay_channels(String enable_pay_channels) {
        this.enable_pay_channels = enable_pay_channels;
    }

    public String getDisable_pay_channels() {
        return disable_pay_channels;
    }

    public void setDisable_pay_channels(String disable_pay_channels) {
        this.disable_pay_channels = disable_pay_channels;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getQuit_url() {
        return quit_url;
    }

    public void setQuit_url(String quit_url) {
        this.quit_url = quit_url;
    }

    public String getExt_user_info() {
        return ext_user_info;
    }

    public void setExt_user_info(String ext_user_info) {
        this.ext_user_info = ext_user_info;
    }

    public AliReqData(String out_trade_no, String subject, BigDecimal total_amount, String product_code) {
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.total_amount = total_amount;
        this.product_code = product_code;
    }

    @Override
    public String toString() {
        return "支付宝AliReqData请求的参数:{" +
                "out_trade_no商户订单号='" + out_trade_no + '\'' +
                ", subject订单标题='" + subject + '\'' +
                ", total_amount总金额=" + total_amount +
                ", product_code产品销售码='" + product_code + '\'' +
                ", body交易描述='" + body + '\'' +
                ", timeout_express='" + timeout_express + '\'' +
                ", time_expire='" + time_expire + '\'' +
                ", auth_token='" + auth_token + '\'' +
                ", goods_type='" + goods_type + '\'' +
                ", passback_params='" + passback_params + '\'' +
                ", promo_params='" + promo_params + '\'' +
                ", extend_params='" + extend_params + '\'' +
                ", enable_pay_channels='" + enable_pay_channels + '\'' +
                ", disable_pay_channels='" + disable_pay_channels + '\'' +
                ", store_id='" + store_id + '\'' +
                ", quit_url='" + quit_url + '\'' +
                ", ext_user_info='" + ext_user_info + '\'' +
                '}';
    }
}
