package com.cloud.cang.mgr.om.domain;

import com.cloud.cang.model.om.OrderAudit;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @description: 订单审核 Domain
 * @author:Yanlingfeng
 * @time:2018-04-08 14:07:05
 */
public class OrderAuditDomain extends OrderAudit {
    private BigDecimal ftotalAmountBegin;  // 订单总额区间
    private BigDecimal ftotalAmountEnd;  // 订单总额区间
    private String orderStr;//排序字段
    private String merchantName;//商户名
    private  String queryCondition;//搜索条件
    //原订单支付类型  private String ipayType;




    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
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
