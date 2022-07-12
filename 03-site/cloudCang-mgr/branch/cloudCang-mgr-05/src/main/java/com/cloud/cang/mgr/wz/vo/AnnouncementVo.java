package com.cloud.cang.mgr.wz.vo;

import com.cloud.cang.model.wz.Announcement;

import java.util.Date;

/**
 * @description 系统广告区域管理VO
 * @author ChangTanchang
 * @time 2018-03-29 14:17:15
 * @fileName RegionVo.java
 * @version 1.0
 */
public class AnnouncementVo extends Announcement{

    // 商户名称
    private String merchantname;

    // 发布开始时间
    private Date tpublishDateStart;

    // 发布结束时间
    private Date tpublishDateEnd;

    //开始排序号
    private Integer isortFrom;

    //结束排序号
    private Integer isortTo;

    //开始 添加日期
    private Date taddTimeStart;

    //结束 添加日期
    private Date taddTimeEnd;

    // 排序
    private String orderStr;

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public Date getTpublishDateStart() {
        return tpublishDateStart;
    }

    public void setTpublishDateStart(Date tpublishDateStart) {
        this.tpublishDateStart = tpublishDateStart;
    }

    public Date getTpublishDateEnd() {
        return tpublishDateEnd;
    }

    public void setTpublishDateEnd(Date tpublishDateEnd) {
        this.tpublishDateEnd = tpublishDateEnd;
    }

    public Integer getIsortFrom() {
        return isortFrom;
    }

    public void setIsortFrom(Integer isortFrom) {
        this.isortFrom = isortFrom;
    }

    public Integer getIsortTo() {
        return isortTo;
    }

    public void setIsortTo(Integer isortTo) {
        this.isortTo = isortTo;
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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    @Override
    public String toString() {
        return "AnnouncementVo{" +
                "merchantname='" + merchantname + '\'' +
                ", tpublishDateStart=" + tpublishDateStart +
                ", tpublishDateEnd=" + tpublishDateEnd +
                ", isortFrom=" + isortFrom +
                ", isortTo=" + isortTo +
                ", taddTimeStart=" + taddTimeStart +
                ", taddTimeEnd=" + taddTimeEnd +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
