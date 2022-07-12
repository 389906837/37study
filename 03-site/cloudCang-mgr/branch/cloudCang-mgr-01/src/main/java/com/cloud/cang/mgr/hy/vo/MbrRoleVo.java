package com.cloud.cang.mgr.hy.vo;

import com.cloud.cang.model.hy.MbrRole;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @version 1.0
 * @description: 会员角色表 VO
 * @author:ChangTanchang
 * @time:2018-03-05 16:57:57
 */
public class MbrRoleVo extends MbrRole {
    // 添加时间参数
    private String daddDateStr;

    //开始排序号
    private Integer isortNoFrom;

    //结束排序号
    private Integer isortNoTo;

    //开始 添加日期
    private Date daddDateStart;

    //结束 添加日期
    private Date daddDateEnd;

    //开始 修改日期
    private Date dmodifyDateStart;

    //结束 修改日期
    private Date dmodifyDateEnd;

    // 排序字段
    private String orderStr;

    public Integer getIsortNoFrom() {
        return isortNoFrom;
    }

    public void setIsortNoFrom(Integer isortNoFrom) {
        this.isortNoFrom = isortNoFrom;
    }

    public Integer getIsortNoTo() {
        return isortNoTo;
    }

    public void setIsortNoTo(Integer isortNoTo) {
        this.isortNoTo = isortNoTo;
    }

    public Date getDaddDateStart() {
        if (StringUtil.isNotBlank(this.daddDateStr)) {
            return DateUtils.parseDate(daddDateStr.split(" - ")[0]);
        }
        return daddDateStart;
    }

    public void setDaddDateStart(Date daddDateStart) {
        this.daddDateStart = daddDateStart;
    }

    public Date getDaddDateEnd() {
        if (StringUtil.isNotBlank(this.daddDateStr)) {
            return DateUtils.parseDate(daddDateStr.split(" - ")[1]);
        }
        return daddDateEnd;
    }

    public void setDaddDateEnd(Date daddDateEnd) {
        this.daddDateEnd = daddDateEnd;
    }

    public Date getDmodifyDateStart() {
        return dmodifyDateStart;
    }

    public void setDmodifyDateStart(Date dmodifyDateStart) {
        this.dmodifyDateStart = dmodifyDateStart;
    }

    public Date getDmodifyDateEnd() {
        return dmodifyDateEnd;
    }

    public void setDmodifyDateEnd(Date dmodifyDateEnd) {
        this.dmodifyDateEnd = dmodifyDateEnd;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getDaddDateStr() {
        return daddDateStr;
    }

    public void setDaddDateStr(String daddDateStr) {
        this.daddDateStr = daddDateStr;
    }

    @Override
    public String toString() {
        return "MbrRoleVo{" +
                "daddDateStr='" + daddDateStr + '\'' +
                ", isortNoFrom=" + isortNoFrom +
                ", isortNoTo=" + isortNoTo +
                ", daddDateStart=" + daddDateStart +
                ", daddDateEnd=" + daddDateEnd +
                ", dmodifyDateStart=" + dmodifyDateStart +
                ", dmodifyDateEnd=" + dmodifyDateEnd +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
