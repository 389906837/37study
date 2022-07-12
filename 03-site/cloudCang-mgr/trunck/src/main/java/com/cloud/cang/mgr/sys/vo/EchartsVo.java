package com.cloud.cang.mgr.sys.vo;

import com.cloud.cang.utils.StringUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @description: 订单统计
 * @author:Yanlingfeng
 * @time:2018-03-24 14:07:05
 */
public class EchartsVo {
    private Date categories;
    private BigDecimal value;//数据
    private String name;//横坐标
    private String group;


    public Date getCategories() {
        return categories;
    }

    public void setCategories(Date categories) {
        this.categories = categories;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {

        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
