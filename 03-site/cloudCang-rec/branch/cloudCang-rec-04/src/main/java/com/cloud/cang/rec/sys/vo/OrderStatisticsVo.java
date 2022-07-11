package com.cloud.cang.rec.sys.vo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @version 1.0
 * @description: 订单统计 Vo
 * @author:Yanlingfeng
 * @time:2018-03-24 14:07:05
 */
public class OrderStatisticsVo {
    private BigDecimal thisMonthOrderNum;//本月订单数
    private BigDecimal thisWeekOrderNum;//本周订单数
    private Map thisMonthRateOfChange;//本月同比改变率
    private Map thisWeekRateOfChange;//本周同比改变率

    public Map getThisMonthRateOfChange() {
        return thisMonthRateOfChange;
    }

    public void setThisMonthRateOfChange(Map thisMonthRateOfChange) {
        this.thisMonthRateOfChange = thisMonthRateOfChange;
    }

    public BigDecimal getThisMonthOrderNum() {
        return thisMonthOrderNum;
    }

    public void setThisMonthOrderNum(BigDecimal thisMonthOrderNum) {
        this.thisMonthOrderNum = thisMonthOrderNum;
    }

    public BigDecimal getThisWeekOrderNum() {
        return thisWeekOrderNum;
    }

    public void setThisWeekOrderNum(BigDecimal thisWeekOrderNum) {
        this.thisWeekOrderNum = thisWeekOrderNum;
    }

    public Map getThisWeekRateOfChange() {
        return thisWeekRateOfChange;
    }

    public void setThisWeekRateOfChange(Map thisWeekRateOfChange) {
        this.thisWeekRateOfChange = thisWeekRateOfChange;
    }
}
