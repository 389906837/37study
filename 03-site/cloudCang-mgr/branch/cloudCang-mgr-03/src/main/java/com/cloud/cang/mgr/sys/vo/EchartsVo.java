package com.cloud.cang.mgr.sys.vo;

import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
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
        if (StringUtil.isNotBlank(this.name))
        if (this.name.indexOf("周日") != -1) {
            this.name = this.name.replace("周日", MessageSourceUtils.getMessageByKey("main.sun",null));
        } else if (this.name.indexOf("周六") != -1) {
            this.name = this.name.replace("周六", MessageSourceUtils.getMessageByKey("main.sat",null));
        } else if (this.name.indexOf("周五") != -1) {
            this.name = this.name.replace("周五", MessageSourceUtils.getMessageByKey("main.fri",null));
        } else if (this.name.indexOf("周四") != -1) {
            this.name = this.name.replace("周四", MessageSourceUtils.getMessageByKey("main.thr",null));
        } else if (this.name.indexOf("周三") != -1) {
            this.name = this.name.replace("周三", MessageSourceUtils.getMessageByKey("main.wed",null));
        } else if (this.name.indexOf("周二") != -1) {
            this.name = this.name.replace("周二", MessageSourceUtils.getMessageByKey("main.tue",null));
        } else if (this.name.indexOf("周一") != -1) {
            this.name = this.name.replace("周一", MessageSourceUtils.getMessageByKey("main.mon",null));
        }
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
