package com.cloud.cang.open.sdk.util;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description:
 * @Author: zhouhong
 * @Date: 2018/9/4 11:58
 */
public class SignItem implements Serializable {
    private String signSourceDate;
    private String sign;

    public SignItem() {
    }

    public String getSignSourceDate() {
        return this.signSourceDate;
    }

    public void setSignSourceDate(String signSourceDate) {
        this.signSourceDate = signSourceDate;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
