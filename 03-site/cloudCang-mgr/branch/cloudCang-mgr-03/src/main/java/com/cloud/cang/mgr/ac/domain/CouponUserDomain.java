package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.model.ac.CouponUserSend;
import org.apache.shiro.SecurityUtils;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 用户持有券列表返回Domain(商户名称字段)
 * @Author: ChangTanchang
 * @Date: 2018/5/5 15:55
 */
public class CouponUserDomain extends CouponUser{

    // 商户名称
    private String merchantName;

    // 会员脱敏数据
    private String smemberNameDesensitize;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("MEMBERINFO_MEMBER_NAME_DESENSITIZE_CU")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    @Override
    public String toString() {
        return "CouponUserDomain{" +
                "merchantName='" + merchantName + '\'' +
                ", smemberNameDesensitize='" + smemberNameDesensitize + '\'' +
                '}';
    }
}
