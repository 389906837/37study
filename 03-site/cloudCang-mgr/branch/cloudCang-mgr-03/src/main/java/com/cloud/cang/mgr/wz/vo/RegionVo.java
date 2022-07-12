package com.cloud.cang.mgr.wz.vo;

import com.cloud.cang.model.wz.Region;

import java.util.Date;

/**
 * @description 运营区域管理VO
 * @author ChangTanchang
 * @time 2018-03-29 14:17:15
 * @fileName RegionVo.java
 * @version 1.0
 */
public class RegionVo extends Region{

    // 开始数量
    private Integer icountFrom;

    // 结束数量
    private Integer icountTo;

    // 开始添加日期
    private Date taddTimeStart;

    // 结束添加日期
    private Date taddTimeEnd;

    // 排序
    private String orderStr;

    // 编号和名称
    private String codeAndname;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Integer getIcountFrom() {
        return icountFrom;
    }

    public void setIcountFrom(Integer icountFrom) {
        this.icountFrom = icountFrom;
    }

    public Integer getIcountTo() {
        return icountTo;
    }

    public void setIcountTo(Integer icountTo) {
        this.icountTo = icountTo;
    }

    public Date getTaddTimeStart() {
        return taddTimeStart;
    }

    public void setTaddTimeStart(Date taddTimeStart) {
        this.taddTimeStart = taddTimeStart;
    }

    public Date getTaddTimeEnd() {
        return taddTimeEnd;
    }

    public void setTaddTimeEnd(Date taddTimeEnd) {
        this.taddTimeEnd = taddTimeEnd;
    }

    public String getCodeAndname() {
        return codeAndname;
    }

    public void setCodeAndname(String codeAndname) {
        this.codeAndname = codeAndname;
    }

    @Override
    public String toString() {
        return "RegionVo{" +
                "icountFrom=" + icountFrom +
                ", icountTo=" + icountTo +
                ", taddTimeStart=" + taddTimeStart +
                ", taddTimeEnd=" + taddTimeEnd +
                ", orderStr='" + orderStr + '\'' +
                ", codeAndname='" + codeAndname + '\'' +
                '}';
    }
}
