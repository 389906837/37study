package com.cloud.cang.pay.om.vo;




import com.alipay.api.domain.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 免密付款model
 * @Author: zhouhong
 * @Date: 2018/3/17 17:14
 */
public class FreePaymentVo implements Serializable {

    private String out_trade_no;//商户订单号
    private String scene;//支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code
    private String auth_code;//支付授权码
    private String product_code;//销售产品码 订单业务类型(product_code)设为GENERAL_WITHHOLDING；
    private String subject;//订单标题
    private String buyer_id;//买家支付宝用户ID
    private String seller_id;//默认为商户签约账号对应的支付宝用户ID
    private BigDecimal total_amount;//订单总金额
    private BigDecimal discountable_amount;//参与优惠计算的金额
    private BigDecimal undiscountable_amount;//不参与优惠计算的金额
    private String body;//订单描述
    private GoodsDetail[] goods_detail;//商品列表信息
    private String operator_id;//商户操作员编号
    private String store_id;//商户门店编号


    private String terminal_id;//商户机具终端编号
    private String alipay_store_id;//支付宝的店铺编号
    private ExtendParams extend_params;//业务扩展参数
    private RoyaltyInfo royalty_info;//分账信息
    private AgreementParams agreement_params;//代扣业务协议信息
    private SubMerchant sub_merchant;//间连受理商户信息体
    private String disable_pay_channels;//禁用支付渠道
    private String merchant_order_no;//商户的原始订单号
    private String auth_no;//预授权号
    private ExtUserInfo ext_user_info;//外部指定买家
    private String business_params;//商户传入业务信息

    //======签约并支付参数
    private AgreementSignParams agreement_sign_params;//签约参数

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public BigDecimal getDiscountable_amount() {
        return discountable_amount;
    }

    public void setDiscountable_amount(BigDecimal discountable_amount) {
        this.discountable_amount = discountable_amount;
    }

    public BigDecimal getUndiscountable_amount() {
        return undiscountable_amount;
    }

    public void setUndiscountable_amount(BigDecimal undiscountable_amount) {
        this.undiscountable_amount = undiscountable_amount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public GoodsDetail[] getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(GoodsDetail[] goods_detail) {
        this.goods_detail = goods_detail;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getAlipay_store_id() {
        return alipay_store_id;
    }

    public void setAlipay_store_id(String alipay_store_id) {
        this.alipay_store_id = alipay_store_id;
    }

    public ExtendParams getExtend_params() {
        return extend_params;
    }

    public void setExtend_params(ExtendParams extend_params) {
        this.extend_params = extend_params;
    }

    public RoyaltyInfo getRoyalty_info() {
        return royalty_info;
    }

    public void setRoyalty_info(RoyaltyInfo royalty_info) {
        this.royalty_info = royalty_info;
    }

    public AgreementParams getAgreement_params() {
        return agreement_params;
    }

    public void setAgreement_params(AgreementParams agreement_params) {
        this.agreement_params = agreement_params;
    }

    public SubMerchant getSub_merchant() {
        return sub_merchant;
    }

    public void setSub_merchant(SubMerchant sub_merchant) {
        this.sub_merchant = sub_merchant;
    }

    public String getDisable_pay_channels() {
        return disable_pay_channels;
    }

    public void setDisable_pay_channels(String disable_pay_channels) {
        this.disable_pay_channels = disable_pay_channels;
    }

    public String getMerchant_order_no() {
        return merchant_order_no;
    }

    public void setMerchant_order_no(String merchant_order_no) {
        this.merchant_order_no = merchant_order_no;
    }

    public String getAuth_no() {
        return auth_no;
    }

    public void setAuth_no(String auth_no) {
        this.auth_no = auth_no;
    }

    public ExtUserInfo getExt_user_info() {
        return ext_user_info;
    }

    public void setExt_user_info(ExtUserInfo ext_user_info) {
        this.ext_user_info = ext_user_info;
    }

    public String getBusiness_params() {
        return business_params;
    }

    public void setBusiness_params(String business_params) {
        this.business_params = business_params;
    }

    public AgreementSignParams getAgreement_sign_params() {
        return agreement_sign_params;
    }

    public void setAgreement_sign_params(AgreementSignParams agreement_sign_params) {
        this.agreement_sign_params = agreement_sign_params;
    }

    @Override
    public String toString() {
        return "FreePaymentVo{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", scene='" + scene + '\'' +
                ", auth_code='" + auth_code + '\'' +
                ", product_code='" + product_code + '\'' +
                ", subject='" + subject + '\'' +
                ", buyer_id='" + buyer_id + '\'' +
                ", seller_id='" + seller_id + '\'' +
                ", total_amount=" + total_amount +
                ", discountable_amount=" + discountable_amount +
                ", undiscountable_amount=" + undiscountable_amount +
                ", body='" + body + '\'' +
                ", goods_detail=" + Arrays.toString(goods_detail) +
                ", operator_id='" + operator_id + '\'' +
                ", store_id='" + store_id + '\'' +
                ", terminal_id='" + terminal_id + '\'' +
                ", alipay_store_id='" + alipay_store_id + '\'' +
                ", extend_params=" + extend_params +
                ", royalty_info=" + royalty_info +
                ", agreement_params=" + agreement_params +
                ", sub_merchant=" + sub_merchant +
                ", disable_pay_channels='" + disable_pay_channels + '\'' +
                ", merchant_order_no='" + merchant_order_no + '\'' +
                ", auth_no='" + auth_no + '\'' +
                ", ext_user_info=" + ext_user_info +
                ", business_params='" + business_params + '\'' +
                ", agreement_sign_params=" + agreement_sign_params +
                '}';
    }
}
