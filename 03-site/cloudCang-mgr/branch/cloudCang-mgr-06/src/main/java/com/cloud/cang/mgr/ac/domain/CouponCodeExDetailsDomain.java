package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.ac.CouponCodeExDetails;
import org.apache.shiro.SecurityUtils;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 券码明细列表返回Domain(商户名称字段)
 * @Author: ChangTanchang
 * @Date: 2018/5/5 15:55
 */
public class CouponCodeExDetailsDomain extends CouponCodeExDetails {

    // 商户名称
    private String merchantName;

    // 商户编号
    private String merchantCode;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    // 会员脱敏数据
    private String smemberNameDesensitize;

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("MEMBERINFO_MEMBER_NAME_DESENSITIZE")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    @Override
    public String toString() {
        return "CouponCodeExDetailsDomain{" +
                "merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", smemberNameDesensitize='" + smemberNameDesensitize + '\'' +
                '}';
    }
}
