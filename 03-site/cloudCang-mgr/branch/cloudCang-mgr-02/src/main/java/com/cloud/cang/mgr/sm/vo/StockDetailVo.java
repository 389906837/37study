package com.cloud.cang.mgr.sm.vo;

import com.cloud.cang.model.sm.StockDetail;

import java.util.Date;

/**
 * @description: 总库存列表 VO
 * @author:ChangTanchang
 * @time:2018-01-15 15:29:05
 * @version 1.0
 */
public class StockDetailVo extends StockDetail {

    // 开始日期
    private Date toperateStartDate;

    // 结束日期
    private Date toperateEndDate;

    // 排序字段
    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getToperateStartDate() {
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }
}
