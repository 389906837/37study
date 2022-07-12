package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceUpgrade;

/**
 * @version 1.0
 * @ClassName DeviceUpgradeVo
 * @Description 设备升级记录查询条件
 * @Author zengzexiong
 * @Date 2018年6月22日15:14:54
 */
public class DeviceUpgradeVo extends DeviceUpgrade {

    private String orderStr;                //排序字段
    private String queryCondition;          /* 查询条件 */


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
        return "DeviceUpgradeVo{" +
                "orderStr='" + orderStr + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
