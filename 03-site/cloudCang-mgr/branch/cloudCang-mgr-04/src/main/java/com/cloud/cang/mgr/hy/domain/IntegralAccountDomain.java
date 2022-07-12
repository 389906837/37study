package com.cloud.cang.mgr.hy.domain;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.hy.IntegralAccount;
import org.apache.shiro.SecurityUtils;

/**
 * 积分调整
 */
public class IntegralAccountDomain extends IntegralAccount {

    // (积分)增加-减少
    private Integer addLess;

    // 会员脱敏数据
    private String smemberNameDesensitize;

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("MEMBERINFO_MEMBER_NAME_DESENSITIZE_IA")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    public Integer getAddLess() {
        return addLess;
    }

    public void setAddLess(Integer addLess) {
        this.addLess = addLess;
    }

    @Override
    public String toString() {
        return "IntegralAccountDomain{" +
                "addLess=" + addLess +
                ", smemberNameDesensitize='" + smemberNameDesensitize + '\'' +
                '}';
    }
}
