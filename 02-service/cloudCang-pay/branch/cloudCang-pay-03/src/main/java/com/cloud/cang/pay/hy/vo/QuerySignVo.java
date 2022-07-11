package com.cloud.cang.pay.hy.vo;



import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 解约model
 * @Author: zhouhong
 * @Date: 2018/3/17 17:14
 */
public class QuerySignVo implements Serializable {

    private String alipay_user_id;//支付宝账号以2088 开头
    private String alipay_logon_id;//支付宝登录账号
    private String personal_product_code;//个人签约产品码，商户和支付宝签约时确定，商户可咨询技术支持
    private String sign_scene;//协议签约场景
    private String external_agreement_no;//商户签约号
    private String third_party_type;//签约第三方主体类型
    private String agreement_no;//支付宝系统唯一标识

    public String getAlipay_user_id() {
        return alipay_user_id;
    }

    public void setAlipay_user_id(String alipay_user_id) {
        this.alipay_user_id = alipay_user_id;
    }

    public String getAlipay_logon_id() {
        return alipay_logon_id;
    }

    public void setAlipay_logon_id(String alipay_logon_id) {
        this.alipay_logon_id = alipay_logon_id;
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

    public String getAgreement_no() {
        return agreement_no;
    }

    public void setAgreement_no(String agreement_no) {
        this.agreement_no = agreement_no;
    }



    @Override
    public String toString() {
        return "UnsignVo{" +
                "alipay_user_id='" + alipay_user_id + '\'' +
                ", alipay_logon_id='" + alipay_logon_id + '\'' +
                ", personal_product_code='" + personal_product_code + '\'' +
                ", sign_scene='" + sign_scene + '\'' +
                ", external_agreement_no='" + external_agreement_no + '\'' +
                ", third_party_type='" + third_party_type + '\'' +
                ", agreement_no='" + agreement_no + '\'' +
                '}';
    }
}
