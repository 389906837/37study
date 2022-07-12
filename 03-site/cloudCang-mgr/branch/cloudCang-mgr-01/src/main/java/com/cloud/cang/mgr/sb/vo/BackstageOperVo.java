package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.BackstageOper;


/**
 * @version 1.0
 * @ClassName BackstageOperVo
 * @Description 后台操作记录查询对象
 * @Author zengzexiong
 * @Date 2018年12月6日11:06:19
 */
public class BackstageOperVo extends BackstageOper {

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
}
