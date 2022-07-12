package com.cloud.cang.mgr.sys.vo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @version 1.0
 * @description: 会员统计 Vo
 * @author:Yanlingfeng
 * @time:2018-03-24 14:07:05
 */
public class MemberStatisticsVo {
    private BigDecimal thisMonthMemberNum;//本月会员数
    private BigDecimal thisWeekMemberNum;//本周会员数
    private Map thisMonthRateOfChange;//本月同比改变率
    private Map thisWeekRateOfChange;//本周同比改变率

    public BigDecimal getThisMonthMemberNum() {
        return thisMonthMemberNum;
    }

    public void setThisMonthMemberNum(BigDecimal thisMonthMemberNum) {
        this.thisMonthMemberNum = thisMonthMemberNum;
    }

    public BigDecimal getThisWeekMemberNum() {
        return thisWeekMemberNum;
    }

    public void setThisWeekMemberNum(BigDecimal thisWeekMemberNum) {
        this.thisWeekMemberNum = thisWeekMemberNum;
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
