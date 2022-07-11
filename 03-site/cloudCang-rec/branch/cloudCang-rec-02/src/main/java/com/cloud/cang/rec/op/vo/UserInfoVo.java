package com.cloud.cang.rec.op.vo;

import com.cloud.cang.model.op.UserInfo;

/**
 * Created by yan on 2018/9/28.
 */
public class UserInfoVo extends UserInfo {
    private String merchantName;//商户名
    private String sname;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
