package com.cloud.cang.rec.sys.vo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @version 1.0
 * @description: 销售统计 Vo
 * @author:Yanlingfeng
 * @time:2018-03-27 14:07:05
 */
public class SalesAmountStatisticsVo {
    private BigDecimal thisMonthSalesAmount;//本月销售额
    private BigDecimal thisWeekMemberSalesAmount;//本周销售额
    private Map thisMonthRateOfChange;//本月同比改变率
    private Map thisWeekRateOfChange;//本周同比改变率
    public BigDecimal getThisMonthSalesAmount() {
        return thisMonthSalesAmount;
    }

    public void setThisMonthSalesAmount(BigDecimal thisMonthSalesAmount) {
        this.thisMonthSalesAmount = thisMonthSalesAmount;
    }

    public BigDecimal getThisWeekMemberSalesAmount() {
        return thisWeekMemberSalesAmount;
    }

    public void setThisWeekMemberSalesAmount(BigDecimal thisWeekMemberSalesAmount) {
        this.thisWeekMemberSalesAmount = thisWeekMemberSalesAmount;
    }

    public Map getThisMonthRateOfChange() {
        return thisMonthRateOfChange;
    }

    public void setThisMonthRateOfChange(Map thisMonthRateOfChange) {
        this.thisMonthRateOfChange = thisMonthRateOfChange;
    }

    public Map getThisWeekRateOfChange() {
        return thisWeekRateOfChange;
    }

    public void setThisWeekRateOfChange(Map thisWeekRateOfChange) {
        this.thisWeekRateOfChange = thisWeekRateOfChange;
    }


}
