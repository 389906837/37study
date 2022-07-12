package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 活动列表查询VO
 * @Author: zhouhong
 * @Date: 2018/2/7 19:55
 */
public class ActivityConfVo extends ActivityConf {

    private String merchantName;//商户名称
    private String orderStr;//排序字段
    private String queryCondition;//查询条件

    private String activeStartTimeStr;//活动结束时间
    private String activeEndTimeStr;//活动开始时间

    // 活动开始时间1
    private Date activeStartTime1;

    // 活动开始时间2
    private Date activeStartTime2;

    // 活动结束时间1
    private Date activeEndTime1;

    // 活动结束时间2
    private Date activeEndTime2;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getActiveEndTimeStr() {
        return activeEndTimeStr;
    }

    public void setActiveEndTimeStr(String activeEndTimeStr) {
        this.activeEndTimeStr = activeEndTimeStr;
    }

    public String getActiveStartTimeStr() {
        return activeStartTimeStr;
    }

    public void setActiveStartTimeStr(String activeStartTimeStr) {
        this.activeStartTimeStr = activeStartTimeStr;
    }

    public Date getActiveStartTime1() {
        if (StringUtil.isNotBlank(this.activeStartTimeStr)) {
            return DateUtils.parseDate(activeStartTimeStr.split(" - ")[0]);
        }
        return activeStartTime1;
    }

    public void setActiveStartTime1(Date activeStartTime1) {
        this.activeStartTime1 = activeStartTime1;
    }

    public Date getActiveStartTime2() {
        if (StringUtil.isNotBlank(this.activeStartTimeStr)) {
            return DateUtils.parseDate(activeStartTimeStr.split(" - ")[1]);
        }
        return activeStartTime2;
    }

    public void setActiveStartTime2(Date activeStartTime2) {
        this.activeStartTime2 = activeStartTime2;
    }

    public Date getActiveEndTime1() {
        if (StringUtil.isNotBlank(this.activeEndTimeStr)) {
            return DateUtils.parseDate(activeEndTimeStr.split(" - ")[0]);
        }
        return activeEndTime1;
    }

    public void setActiveEndTime1(Date activeEndTime1) {
        this.activeEndTime1 = activeEndTime1;
    }

    public Date getActiveEndTime2() {
        if (StringUtil.isNotBlank(this.activeEndTimeStr)) {
            return DateUtils.parseDate(activeEndTimeStr.split(" - ")[1]);
        }
        return activeEndTime2;
    }

    public void setActiveEndTime2(Date activeEndTime2) {
        this.activeEndTime2 = activeEndTime2;
    }

    @Override
    public String toString() {
        return "ActivityConfVo{" +
                "merchantName='" + merchantName + '\'' +
                ", orderStr='" + orderStr + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                ", activeEndTimeStr='" + activeEndTimeStr + '\'' +
                ", activeStartTimeStr='" + activeStartTimeStr + '\'' +
                ", activeStartTime1=" + activeStartTime1 +
                ", activeStartTime2=" + activeStartTime2 +
                ", activeEndTime1=" + activeEndTime1 +
                ", activeEndTime2=" + activeEndTime2 +
                '}';
    }
}
