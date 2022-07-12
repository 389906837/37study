package com.cloud.cang.mgr.sl.vo;

import com.cloud.cang.model.sl.OperLog;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;


import java.util.Date;

/**
 * @description: 后台访问日志 VO
 * @author:ChangTanchang
 * @time:2018-01-15 15:29:05
 * @version 1.0
 */
public class VistLogVo extends OperLog {
    // 访问日期参数
    private String toperateDateStr;

    // 开始操作日期
    private Date toperateStartDate;

    // 结束操作日期
    private Date toperateEndDate;

    // 排序字段
    private String orderStr;

    // 搜索条件
    private String condition;

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.toperateDateStr)) {
            return DateUtils.parseDate(toperateDateStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.toperateDateStr)) {
            return DateUtils.parseDate(toperateDateStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getToperateDateStr() {
        return toperateDateStr;
    }

    public void setToperateDateStr(String toperateDateStr) {
        this.toperateDateStr = toperateDateStr;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "VistLogVo{" +
                "toperateDateStr='" + toperateDateStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
