package com.cloud.cang.mgr.sl.domain;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.sl.LoginLog;
import org.apache.shiro.SecurityUtils;

/**
 * Created by Administrator on 2018/6/12.
 */
public class LoginDomain extends LoginLog {

    // 会员脱敏数据
    private String smemberNameDesensitize;

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("MEMBERINFO_MEMBER_NAME_DESENSITIZE_LL")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    @Override
    public String toString() {
        return "LoginDomain{" +
                "smemberNameDesensitize='" + smemberNameDesensitize + '\'' +
                '}';
    }
}
