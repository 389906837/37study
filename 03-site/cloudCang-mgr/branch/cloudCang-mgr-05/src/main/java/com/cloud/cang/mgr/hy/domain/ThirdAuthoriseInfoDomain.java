package com.cloud.cang.mgr.hy.domain;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.hy.ThirdAuthorise;
import org.apache.shiro.SecurityUtils;

/**
 * Created by Administrator on 2018/6/12.
 */
public class ThirdAuthoriseInfoDomain extends ThirdAuthorise {

    // 会员脱敏数据
    private String smemberNameDesensitize;

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("MEMBERINFO_MEMBER_NAME_DESENSITIZE_TA")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    @Override
    public String toString() {
        return "ThirdAuthoriseInfoDomain{" +
                "smemberNameDesensitize='" + smemberNameDesensitize + '\'' +
                '}';
    }
}
