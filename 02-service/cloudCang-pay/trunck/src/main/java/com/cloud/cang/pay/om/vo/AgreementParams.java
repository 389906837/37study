package com.cloud.cang.pay.om.vo;

import java.io.Serializable;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description:
 * @Author: zhouhong
 * @Date: 2018/3/27 10:29
 */
public class AgreementParams implements Serializable {
   private String agreement_no;
   private String apply_token;
   private String auth_confirm_no;

    public String getAgreement_no() {
        return agreement_no;
    }

    public void setAgreement_no(String agreement_no) {
        this.agreement_no = agreement_no;
    }

    public String getApply_token() {
        return apply_token;
    }

    public void setApply_token(String apply_token) {
        this.apply_token = apply_token;
    }

    public String getAuth_confirm_no() {
        return auth_confirm_no;
    }

    public void setAuth_confirm_no(String auth_confirm_no) {
        this.auth_confirm_no = auth_confirm_no;
    }

    @Override
    public String toString() {
        return "AgreementParams{" +
                "agreement_no='" + agreement_no + '\'' +
                ", apply_token='" + apply_token + '\'' +
                ", auth_confirm_no='" + auth_confirm_no + '\'' +
                '}';
    }
}
