package com.cloud.cang.mgr.tec.vo;

import com.cloud.cang.model.tec.ScheduleLog;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description: 定时任务执行日志 VO
 * @author:ChangTanchang
 * @time:2018-01-15 15:29:05
 * @version 1.0
 */
public class ScheduleLogVo extends ScheduleLog {
    // 执行时间参数
    private String texecuteTimeStr;

    // 开始执行日期
    private Date toperateStartDate;

    // 结束执行日期
    private Date toperateEndDate;

    // 排序字段
    private String orderStr;

    // 搜索条件
    private String condition;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.texecuteTimeStr)) {
            return DateUtils.parseDate(texecuteTimeStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.texecuteTimeStr)) {
            return DateUtils.parseDate(texecuteTimeStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public String getTexecuteTimeStr() {
        return texecuteTimeStr;
    }

    public void setTexecuteTimeStr(String texecuteTimeStr) {
        this.texecuteTimeStr = texecuteTimeStr;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "ScheduleLogVo{" +
                "texecuteTimeStr='" + texecuteTimeStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
