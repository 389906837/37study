package com.cloud.cang.rec.sh.domain;

import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @version 1.0
 * @Description: 商户domain
 * @Author: yanlingfeng
 * @Date: 2018/2/10 16:14
 */
public class MerchantInfoDomain extends MerchantInfo {
    private static final long serialVersionUID = 1L;
    private String queryTimeCondition;
    // 开始日期
    private Date toperateStartDate;
    // 结束日期
    private Date toperateEndDate;
    private String orderStr;//排序字段
    private String condition;//管理商户类型条件


    public String getQueryTimeCondition() {
        return queryTimeCondition;
    }

    public void setQueryTimeCondition(String queryTimeCondition) {
        this.queryTimeCondition = queryTimeCondition;
    }

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.queryTimeCondition)) {
            return DateUtils.parseDate(queryTimeCondition.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.queryTimeCondition)) {
            return DateUtils.parseDate(queryTimeCondition.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
