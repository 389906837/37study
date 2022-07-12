package com.cloud.cang.mgr.om.domain;

import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @description: 订单 Domain
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 */
public class OrderRecordDomain extends OrderRecord {

    private BigDecimal ftotalAmountBegin;  // 订单总额区间
    private BigDecimal ftotalAmountEnd;  // 订单总额区间
    private String orderStr;//排序字段
    private String merchantName;//商户名
    private String isAudit;//订单状态
    private String queryCondition;//查询条件

    private String queryTimeCondition;//订单时间区间
    private Date toperateStartDate;// 订单查询开始日期
    private Date toperateEndDate;  // 订单查询结束日期


    private String payTimeCondition;//支付完成时间区间
    private Date payStartDate;//支付完成时间区间开始时间
    private Date payEndDate;//支付完成时间区间结束时间


    public String getPayTimeCondition() {
        return payTimeCondition;
    }

    public void setPayTimeCondition(String payTimeCondition) {
        this.payTimeCondition = payTimeCondition;
    }

    public Date getPayStartDate() {
        if (StringUtil.isNotBlank(this.payTimeCondition)) {
            return DateUtils.parseDate(payTimeCondition.split(" - ")[0]);
        }
        return payStartDate;
    }

    public void setPayStartDate(Date payStartDate) {
        this.payStartDate = payStartDate;
    }

    public Date getPayEndDate() {
        if (StringUtil.isNotBlank(this.payTimeCondition)) {
            return DateUtils.parseDate(payTimeCondition.split(" - ")[1]);
        }
        return payEndDate;
    }

    public void setPayEndDate(Date payEndDate) {
        this.payEndDate = payEndDate;
    }

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

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(String isAudit) {
        this.isAudit = isAudit;
    }

    public BigDecimal getFtotalAmountBegin() {
        return ftotalAmountBegin;
    }

    public void setFtotalAmountBegin(BigDecimal ftotalAmountBegin) {
        this.ftotalAmountBegin = ftotalAmountBegin;
    }

    public BigDecimal getFtotalAmountEnd() {
        return ftotalAmountEnd;
    }

    public void setFtotalAmountEnd(BigDecimal ftotalAmountEnd) {
        this.ftotalAmountEnd = ftotalAmountEnd;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
