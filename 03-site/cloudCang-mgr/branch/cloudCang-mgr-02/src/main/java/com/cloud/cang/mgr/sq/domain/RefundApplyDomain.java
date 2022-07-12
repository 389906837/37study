package com.cloud.cang.mgr.sq.domain;

import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @version 1.0
 * @description: 申请退款记录 Domain
 * @author:Yanlingfeng
 * @time:2018-02-28 14:07:05
 */
public class RefundApplyDomain extends RefundApply {
    private String orderStr;//排序字段
    private String queryCondition;//查询条件
    private String queryFinishDatetime;//退款完成时间查询条件
    private Date finishStart;//退款完成时间开始时间
    private Date finishEnd;//退款完成时间结束时间

    public String getQueryFinishDatetime() {
        return queryFinishDatetime;
    }

    public void setQueryFinishDatetime(String queryFinishDatetime) {
        this.queryFinishDatetime = queryFinishDatetime;
    }

    public Date getFinishStart() {
        if (StringUtil.isNotBlank(this.queryFinishDatetime)) {
            return DateUtils.parseDate(queryFinishDatetime.split(" - ")[0]);
        }
        return finishStart;
    }

    public void setFinishStart(Date finishStart) {
        this.finishStart = finishStart;
    }

    public Date getFinishEnd() {
        if (StringUtil.isNotBlank(this.queryFinishDatetime)) {
            return DateUtils.parseDate(queryFinishDatetime.split(" - ")[1]);
        }
        return finishEnd;
    }

    public void setFinishEnd(Date finishEnd) {
        this.finishEnd = finishEnd;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
