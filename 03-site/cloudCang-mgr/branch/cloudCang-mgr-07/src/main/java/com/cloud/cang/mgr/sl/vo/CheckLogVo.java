package com.cloud.cang.mgr.sl.vo;

import com.cloud.cang.model.sl.CheckLog;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description: 后台对账日志 VO
 * @author:ChangTanchang
 * @time:2018-01-15 15:29:05
 * @version 1.0
 */
public class CheckLogVo extends CheckLog {
    // 对账开始时间参数
    private String tbeginDatetimeStr;

    // 对账结束时间参数
    private String tendDatetimeStr;

    // 排序字段
    private String orderStr;

    // 对账开始日期
    private Date toperateStartDate;

    // 对账开始结束日期
    private Date toperateEndDate;

    // 对账结束开始日期
    private Date tbeginDatetimeStart;

    // 对账结束 结束日期
    private Date tendDatetimeEnd;

    // 搜索条件
    private String condition;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.tbeginDatetimeStr)) {
            return DateUtils.parseDate(tbeginDatetimeStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.tbeginDatetimeStr)) {
            return DateUtils.parseDate(tbeginDatetimeStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public String getTbeginDatetimeStr() {
        return tbeginDatetimeStr;
    }

    public void setTbeginDatetimeStr(String tbeginDatetimeStr) {
        this.tbeginDatetimeStr = tbeginDatetimeStr;
    }

    public String getTendDatetimeStr() {
        return tendDatetimeStr;
    }

    public void setTendDatetimeStr(String tendDatetimeStr) {
        this.tendDatetimeStr = tendDatetimeStr;
    }

    public Date getTbeginDatetimeStart() {
        if (StringUtil.isNotBlank(this.tendDatetimeStr)) {
            return DateUtils.parseDate(tendDatetimeStr.split(" - ")[0]);
        }
        return tbeginDatetimeStart;
    }

    public void setTbeginDatetimeStart(Date tbeginDatetimeStart) {
        this.tbeginDatetimeStart = tbeginDatetimeStart;
    }

    public Date getTendDatetimeEnd() {
        if (StringUtil.isNotBlank(this.tendDatetimeStr)) {
            return DateUtils.parseDate(tendDatetimeStr.split(" - ")[1]);
        }
        return tendDatetimeEnd;
    }

    public void setTendDatetimeEnd(Date tendDatetimeEnd) {
        this.tendDatetimeEnd = tendDatetimeEnd;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "CheckLogVo{" +
                "tbeginDatetimeStr='" + tbeginDatetimeStr + '\'' +
                ", tendDatetimeStr='" + tendDatetimeStr + '\'' +
                ", orderStr='" + orderStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", tbeginDatetimeStart=" + tbeginDatetimeStart +
                ", tendDatetimeEnd=" + tendDatetimeEnd +
                ", condition='" + condition + '\'' +
                '}';
    }
}
