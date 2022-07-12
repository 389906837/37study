package com.cloud.cang.mgr.report.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金往来报表 VO
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class ExchangeOfFundsVo {
    private Date dateResult;
    private BigDecimal amount;
    private Integer type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getDateResult() {
        return dateResult;
    }

    public void setDateResult(Date dateResult) {
        this.dateResult = dateResult;
    }
}
