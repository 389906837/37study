package com.cloud.cang.pay.hy.vo;



import com.alipay.api.domain.*;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 签约model
 * @Author: zhouhong
 * @Date: 2018/3/17 17:14
 */
public class SignVo implements Serializable {
    /*当前用户签约请求的协议有效周期。
    整形数字加上时间单位的协议有效期，从发起签约请求的时间开始算起。
    目前支持的时间单位： 1. d：天 2. m：月
    如果未传入，默认为长期有效*/
    private String sign_validity_period;
    private String product_code;//销售产品码，商户签约的支付宝合同所对应的产品码
    private String external_logon_id;//户网站的登录账号
    private String personal_product_code;//个人签约产品码，商户和支付宝签约时确定，商户可咨询技术支持
    private String sign_scene;//协议签约场景
    private String external_agreement_no;//商户签约号
    private String third_party_type;//签约第三方主体类型
    private ZmAuthParams zm_auth_params;//芝麻授权信息
    private ProdParams prod_params;//签约产品属性
    private AccessParams access_params;//接入方式信息
    private DeviceParams device_params;//设备信息参数
    private String merchant_process_url;//跳转商户处理url
    private IdentityParams identity_params;//用户实名信息参数
    private String agreement_effect_type;//协议生效类型 DIRECT: 立即生效. NOTICE: 商户通知生效, 需要再次调用

    public String getSign_validity_period() {
        return sign_validity_period;
    }

    public void setSign_validity_period(String sign_validity_period) {
        this.sign_validity_period = sign_validity_period;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getExternal_logon_id() {
        return external_logon_id;
    }

    public void setExternal_logon_id(String external_logon_id) {
        this.external_logon_id = external_logon_id;
    }

    public String getPersonal_product_code() {
        return personal_product_code;
    }

    public void setPersonal_product_code(String personal_product_code) {
        this.personal_product_code = personal_product_code;
    }

    public String getSign_scene() {
        return sign_scene;
    }

    public void setSign_scene(String sign_scene) {
        this.sign_scene = sign_scene;
    }

    public String getExternal_agreement_no() {
        return external_agreement_no;
    }

    public void setExternal_agreement_no(String external_agreement_no) {
        this.external_agreement_no = external_agreement_no;
    }

    public String getThird_party_type() {
        return third_party_type;
    }

    public void setThird_party_type(String third_party_type) {
        this.third_party_type = third_party_type;
    }

    public ZmAuthParams getZm_auth_params() {
        return zm_auth_params;
    }

    public void setZm_auth_params(ZmAuthParams zm_auth_params) {
        this.zm_auth_params = zm_auth_params;
    }

    public ProdParams getProd_params() {
        return prod_params;
    }

    public void setProd_params(ProdParams prod_params) {
        this.prod_params = prod_params;
    }

    public AccessParams getAccess_params() {
        return access_params;
    }

    public void setAccess_params(AccessParams access_params) {
        this.access_params = access_params;
    }

    public DeviceParams getDevice_params() {
        return device_params;
    }

    public void setDevice_params(DeviceParams device_params) {
        this.device_params = device_params;
    }

    public String getMerchant_process_url() {
        return merchant_process_url;
    }

    public void setMerchant_process_url(String merchant_process_url) {
        this.merchant_process_url = merchant_process_url;
    }

    public IdentityParams getIdentity_params() {
        return identity_params;
    }

    public void setIdentity_params(IdentityParams identity_params) {
        this.identity_params = identity_params;
    }

    public String getAgreement_effect_type() {
        return agreement_effect_type;
    }

    public void setAgreement_effect_type(String agreement_effect_type) {
        this.agreement_effect_type = agreement_effect_type;
    }

    @Override
    public String toString() {
        return "SignVo{" +
                "sign_validity_period='" + sign_validity_period + '\'' +
                ", product_code='" + product_code + '\'' +
                ", external_logon_id='" + external_logon_id + '\'' +
                ", personal_product_code='" + personal_product_code + '\'' +
                ", sign_scene='" + sign_scene + '\'' +
                ", external_agreement_no='" + external_agreement_no + '\'' +
                ", third_party_type='" + third_party_type + '\'' +
                ", zm_auth_params=" + zm_auth_params +
                ", prod_params=" + prod_params +
                ", access_params=" + access_params +
                ", device_params=" + device_params +
                ", merchant_process_url='" + merchant_process_url + '\'' +
                ", identity_params=" + identity_params +
                ", agreement_effect_type='" + agreement_effect_type + '\'' +
                '}';
    }
}
