package com.cloud.cang.mgr.sq.vo;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.sq.RefundApply;
import org.apache.shiro.SecurityUtils;

/**
 * Created by yan on 2018/6/12.
 */
public class RefundApplyVo extends RefundApply {
    private String smemberNameDesensitize;//用户名脱敏

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("REFUNDAPPLY_MEMBER_NAME_DESENSITIZE")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }
}
