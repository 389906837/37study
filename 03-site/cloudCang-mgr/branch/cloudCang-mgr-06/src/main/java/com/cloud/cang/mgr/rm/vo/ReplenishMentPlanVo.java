package com.cloud.cang.mgr.rm.vo;


import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description: 商品计划补货查询 VO
 * @author:ChangTanchang
 * @time:2018-01-24 16:07:05
 * @version 1.0
 */
public class ReplenishMentPlanVo extends ReplenishmentPlan {

    //时间参数
    private String toperateStartDateStr;

    // 开始日期
    private Date toperateStartDate;

    // 结束日期
    private Date toperateEndDate;

    //商户名
    private String merchantName;

    //商户编号
    private String shcode;

    //设备名称
    private String sbsname;

    // 排序字段
    private String orderStr;

    //查询条件
    private String queryCondition;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.toperateStartDateStr)) {
           return DateUtils.parseDate(toperateStartDateStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.toperateStartDateStr)) {
            return DateUtils.parseDate(toperateStartDateStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
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

    public String getShcode() {
        return shcode;
    }

    public void setShcode(String shcode) {
        this.shcode = shcode;
    }

    public String getSbsname() {
        return sbsname;
    }

    public void setSbsname(String sbsname) {
        this.sbsname = sbsname;
    }

    public String getToperateStartDateStr() {
        return toperateStartDateStr;
    }

    public void setToperateStartDateStr(String toperateStartDateStr) {
        this.toperateStartDateStr = toperateStartDateStr;
    }

    @Override
    public String toString() {
        return "ReplenishMentPlanVo{" +
                "toperateStartDateStr='" + toperateStartDateStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", merchantName='" + merchantName + '\'' +
                ", shcode='" + shcode + '\'' +
                ", sbsname='" + sbsname + '\'' +
                ", orderStr='" + orderStr + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
