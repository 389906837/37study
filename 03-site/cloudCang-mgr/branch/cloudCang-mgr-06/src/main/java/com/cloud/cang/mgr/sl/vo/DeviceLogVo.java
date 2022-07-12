package com.cloud.cang.mgr.sl.vo;

import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description: 后台设备操作日志 VO
 * @author:ChangTanchang
 * @time:2018-01-15 15:29:05
 * @version 1.0
 */
public class DeviceLogVo extends DeviceOper {
    // 开门时间参数
    private String topenTimeStr;

    // 关门时间参数
    private String tcloseTimeStr;

    // 开门开始日期
    private Date toperateStartDate;

    // 开门结束日期
    private Date toperateEndDate;

    // 关门开始时间
    private Date topenTime;

    // 关门结束时间
    private Date tcloseTime;

    // 设备名称
    private String sbName;

    // 设备地址
    private String adress;

    // 排序字段
    private String orderStr;

    // 搜索条件
    private String condition;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.topenTimeStr)) {
            return DateUtils.parseDate(topenTimeStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.topenTimeStr)) {
            return DateUtils.parseDate(topenTimeStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTopenTimeStr() {
        return topenTimeStr;
    }

    public void setTopenTimeStr(String topenTimeStr) {
        this.topenTimeStr = topenTimeStr;
    }

    public String getTcloseTimeStr() {
        return tcloseTimeStr;
    }

    public void setTcloseTimeStr(String tcloseTimeStr) {
        this.tcloseTimeStr = tcloseTimeStr;
    }

    @Override
    public Date getTopenTime() {
        if (StringUtil.isNotBlank(this.tcloseTimeStr)) {
            return DateUtils.parseDate(tcloseTimeStr.split(" - ")[0]);
        }
        return topenTime;
    }

    @Override
    public void setTopenTime(Date topenTime) {
        this.topenTime = topenTime;
    }

    @Override
    public Date getTcloseTime() {
        if (StringUtil.isNotBlank(this.tcloseTimeStr)) {
            return DateUtils.parseDate(tcloseTimeStr.split(" - ")[1]);
        }
        return tcloseTime;
    }

    @Override
    public void setTcloseTime(Date tcloseTime) {
        this.tcloseTime = tcloseTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "DeviceLogVo{" +
                "topenTimeStr='" + topenTimeStr + '\'' +
                ", tcloseTimeStr='" + tcloseTimeStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", topenTime=" + topenTime +
                ", tcloseTime=" + tcloseTime +
                ", sbName='" + sbName + '\'' +
                ", adress='" + adress + '\'' +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
