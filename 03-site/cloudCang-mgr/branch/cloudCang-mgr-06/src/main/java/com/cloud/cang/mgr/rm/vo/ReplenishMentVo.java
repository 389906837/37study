package com.cloud.cang.mgr.rm.vo;


import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description: 商品补货查询 VO
 * @author:ChangTanchang
 * @time:2018-01-24 16:07:05
 * @version 1.0
 */
public class ReplenishMentVo extends ReplenishmentRecord {
    // 补货时间参数
    private String treplenishmentTimeStr;

    // 补货开始日期
    private Date toperateStartDate;

    // 补货结束日期
    private Date toperateEndDate;

    // 排序字段
    private String orderStr;

    //商户名
    private String merchantName;

    //商户编号
    private String shcode;

    //设备名称
    private String sbsname;

    //查询条件
    private String queryCondition;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.treplenishmentTimeStr)) {
            return DateUtils.parseDate(treplenishmentTimeStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.treplenishmentTimeStr)) {
            return DateUtils.parseDate(treplenishmentTimeStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
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

    public String getTreplenishmentTimeStr() {
        return treplenishmentTimeStr;
    }

    public void setTreplenishmentTimeStr(String treplenishmentTimeStr) {
        this.treplenishmentTimeStr = treplenishmentTimeStr;
    }

    @Override
    public String toString() {
        return "ReplenishMentVo{" +
                "treplenishmentTimeStr='" + treplenishmentTimeStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", orderStr='" + orderStr + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", shcode='" + shcode + '\'' +
                ", sbsname='" + sbsname + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
