package com.cloud.cang.mgr.sl.vo;

import com.cloud.cang.model.sl.SystemAlarm;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * Created by Administrator on 2018/6/15.
 */
public class SystemAlarmVo extends SystemAlarm {

    // 报警时间参数
    private String talarmTimeStr;

    // 报警开始时间
    private Date talarmTimeStart;

    // 报警结束时间
    private Date talarmTimeEnd;

    // 排序字段
    private String orderStr;

    // 搜索条件
    private String condition;

    public String getTalarmTimeStr() {
        return talarmTimeStr;
    }

    public void setTalarmTimeStr(String talarmTimeStr) {
        this.talarmTimeStr = talarmTimeStr;
    }

    public Date getTalarmTimeStart() {
        if (StringUtil.isNotBlank(this.talarmTimeStr)) {
            return DateUtils.parseDate(talarmTimeStr.split(" - ")[0]);
        }
        return talarmTimeStart;
    }

    public void setTalarmTimeStart(Date talarmTimeStart) {
        this.talarmTimeStart = talarmTimeStart;
    }

    public Date getTalarmTimeEnd() {
        if (StringUtil.isNotBlank(this.talarmTimeStr)) {
            return DateUtils.parseDate(talarmTimeStr.split(" - ")[1]);
        }
        return talarmTimeEnd;
    }

    public void setTalarmTimeEnd(Date talarmTimeEnd) {
        this.talarmTimeEnd = talarmTimeEnd;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "SystemAlarmVo{" +
                "talarmTimeStr='" + talarmTimeStr + '\'' +
                ", talarmTimeStart='" + talarmTimeStart + '\'' +
                ", talarmTimeEnd='" + talarmTimeEnd + '\'' +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
