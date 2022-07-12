package com.cloud.cang.mgr.sl.domain;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.sl.SystemAlarm;
import org.apache.shiro.SecurityUtils;

/**
 * Created by Administrator on 2018/6/15.
 */
public class SystemAlarmDomain extends SystemAlarm{

    // 会员脱敏数据
    private String smemberNameDesensitize;

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("MEMBERINFO_MEMBER_NAME_DESENSITIZE_SA")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    @Override
    public String toString() {
        return "SystemAlarmDomain{" +
                "smemberNameDesensitize='" + smemberNameDesensitize + '\'' +
                '}';
    }
}
