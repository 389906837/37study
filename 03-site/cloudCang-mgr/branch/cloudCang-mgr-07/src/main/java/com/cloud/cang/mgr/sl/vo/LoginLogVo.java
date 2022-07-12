package com.cloud.cang.mgr.sl.vo;

import com.cloud.cang.model.sl.LoginLog;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description: 登录日志 VO
 * @author:ChangTanchang
 * @time:2018-01-15 15:29:05
 * @version 1.0
 */
public class LoginLogVo extends LoginLog {

    // 登录时间参数
    private String tloginTimeStr;

    // 登录开始日期
    private Date tloginTimeStart;

    // 登录结束日期
    private Date tloginTimeEnd;

    // 排序字段
    private String orderStr;

    // 搜索条件
    private String condition;

    public String getTloginTimeStr() {
        return tloginTimeStr;
    }

    public void setTloginTimeStr(String tloginTimeStr) {
        this.tloginTimeStr = tloginTimeStr;
    }

    public Date getTloginTimeStart() {
        if (StringUtil.isNotBlank(this.tloginTimeStr)) {
            return DateUtils.parseDate(tloginTimeStr.split(" - ")[0]);
        }
        return tloginTimeStart;
    }

    public void setTloginTimeStart(Date tloginTimeStart) {
        this.tloginTimeStart = tloginTimeStart;
    }

    public Date getTloginTimeEnd() {
        if (StringUtil.isNotBlank(this.tloginTimeStr)) {
            return DateUtils.parseDate(tloginTimeStr.split(" - ")[1]);
        }
        return tloginTimeEnd;
    }

    public void setTloginTimeEnd(Date tloginTimeEnd) {
        this.tloginTimeEnd = tloginTimeEnd;
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
        return "LoginLogVo{" +
                "tloginTimeStr='" + tloginTimeStr + '\'' +
                ", tloginTimeStart=" + tloginTimeStart +
                ", tloginTimeEnd=" + tloginTimeEnd +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
