package com.cloud.cang.mgr.report.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金往来报表列表页脚总合计
 * Created by yan on 2018/7/13.
 */
public class ExchangeOfFundsMoneyStaVo {

    private BigDecimal totalAmount;
    private Integer type;
    private Date dateResult;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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
