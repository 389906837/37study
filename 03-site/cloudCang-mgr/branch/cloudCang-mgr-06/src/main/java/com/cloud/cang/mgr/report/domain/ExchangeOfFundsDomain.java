package com.cloud.cang.mgr.report.domain;

import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * 资金往来报表 Domain
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class ExchangeOfFundsDomain {
    private String merchantId;//商户ID
    private String queryTimeCondition;//查询时间条件
    private Date queryTimeStart;//查询开始时间
    private Date queryTimeEnd;//查询结束时间
    private String orderStr;//排序条件
    private Integer type;//资金来往类型1:收款 2:付款

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getQueryTimeCondition() {
        return queryTimeCondition;
    }

    public void setQueryTimeCondition(String queryTimeCondition) {
        this.queryTimeCondition = queryTimeCondition;
    }

    public Date getQueryTimeStart() {
        if (StringUtil.isNotBlank(this.queryTimeCondition)) {
            return DateUtils.parseDate(queryTimeCondition.split(" - ")[0]);
        }
        return queryTimeStart;
    }

    public void setQueryTimeStart(Date queryTimeStart) {
        this.queryTimeStart = queryTimeStart;
    }

    public Date getQueryTimeEnd() {
        if (StringUtil.isNotBlank(this.queryTimeCondition)) {
            return DateUtils.parseDate(queryTimeCondition.split(" - ")[1]);
        }
        return queryTimeEnd;
    }

    public void setQueryTimeEnd(Date queryTimeEnd) {
        this.queryTimeEnd = queryTimeEnd;
    }
}
