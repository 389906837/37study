package com.cloud.cang.mgr.tp.vo;

import com.cloud.cang.model.tp.DeviceInfoTemplate;

/**
 * Created by Alex on 2019/3/4.
 */
public class DeviceInfoTemplateVo extends DeviceInfoTemplate {
    private String orderStr;//排序字段
    private String queryCondition;//查询条件

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    @Override
    public String toString() {
        return "DeviceInfoTemplateVo{" +
                "orderStr='" + orderStr + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
