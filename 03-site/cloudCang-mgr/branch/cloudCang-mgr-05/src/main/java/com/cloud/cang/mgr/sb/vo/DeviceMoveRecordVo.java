package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceMoveRecord;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * Created by Administrator on 2018/6/13.
 */
public class DeviceMoveRecordVo extends DeviceMoveRecord{
    // 计划搬迁时间参数
    private String tplanMoveTimeStr;

    // 搬迁时间参数
    private String tmoveTimeStr;

    // 审核时间参数
    private String tauditTimeStr;

    // 申请时间参数
    private String tapplyTimeStr;

    // 计划搬迁开始时间
    private Date tplanMoveTimeStart;

    // 计划搬迁结束时间
    private Date tplanMoveTimeEnd;

    // 搬迁开始时间
    private Date tmoveTimeStart;

    // 搬迁结束时间
    private Date tmoveTimeEnd;

    // 审核开始时间
    private Date tauditTimeStart;

    // 审核结束时间
    private Date tauditTimeEnd;

    // 申请开始时间
    private Date tapplyTimeStart;

    // 申请结束时间
    private Date tapplyTimeEnd;

    // 商户名称
    private String merchantName;

    // 设备名称
    private String sbName;

    // 设备地址
    private String address;

    // 排序字段
    private String orderStr;

    // 搜索条件
    private String condition;

    public String getTplanMoveTimeStr() {
        return tplanMoveTimeStr;
    }

    public void setTplanMoveTimeStr(String tplanMoveTimeStr) {
        this.tplanMoveTimeStr = tplanMoveTimeStr;
    }

    public String getTmoveTimeStr() {
        return tmoveTimeStr;
    }

    public void setTmoveTimeStr(String tmoveTimeStr) {
        this.tmoveTimeStr = tmoveTimeStr;
    }

    public String getTauditTimeStr() {
        return tauditTimeStr;
    }

    public void setTauditTimeStr(String tauditTimeStr) {
        this.tauditTimeStr = tauditTimeStr;
    }

    public String getTapplyTimeStr() {
        return tapplyTimeStr;
    }

    public void setTapplyTimeStr(String tapplyTimeStr) {
        this.tapplyTimeStr = tapplyTimeStr;
    }

    public Date getTplanMoveTimeStart() {
        if (StringUtil.isNotBlank(this.tplanMoveTimeStr)) {
            return DateUtils.parseDate(tplanMoveTimeStr.split(" - ")[0]);
        }
        return tplanMoveTimeStart;
    }

    public void setTplanMoveTimeStart(Date tplanMoveTimeStart) {
        this.tplanMoveTimeStart = tplanMoveTimeStart;
    }

    public Date getTplanMoveTimeEnd() {
        if (StringUtil.isNotBlank(this.tplanMoveTimeStr)) {
            return DateUtils.parseDate(tplanMoveTimeStr.split(" - ")[1]);
        }
        return tplanMoveTimeEnd;
    }

    public void setTplanMoveTimeEnd(Date tplanMoveTimeEnd) {
        this.tplanMoveTimeEnd = tplanMoveTimeEnd;
    }

    public Date getTmoveTimeStart() {
        if (StringUtil.isNotBlank(this.tmoveTimeStr)) {
            return DateUtils.parseDate(tmoveTimeStr.split(" - ")[0]);
        }
        return tmoveTimeStart;
    }

    public void setTmoveTimeStart(Date tmoveTimeStart) {
        this.tmoveTimeStart = tmoveTimeStart;
    }

    public Date getTmoveTimeEnd() {
        if (StringUtil.isNotBlank(this.tmoveTimeStr)) {
            return DateUtils.parseDate(tmoveTimeStr.split(" - ")[1]);
        }
        return tmoveTimeEnd;
    }

    public void setTmoveTimeEnd(Date tmoveTimeEnd) {
        this.tmoveTimeEnd = tmoveTimeEnd;
    }

    public Date getTauditTimeStart() {
        if (StringUtil.isNotBlank(this.tauditTimeStr)) {
            return DateUtils.parseDate(tauditTimeStr.split(" - ")[0]);
        }
        return tauditTimeStart;
    }

    public void setTauditTimeStart(Date tauditTimeStart) {
        this.tauditTimeStart = tauditTimeStart;
    }

    public Date getTauditTimeEnd() {
        if (StringUtil.isNotBlank(this.tauditTimeStr)) {
            return DateUtils.parseDate(tauditTimeStr.split(" - ")[1]);
        }
        return tauditTimeEnd;
    }

    public void setTauditTimeEnd(Date tauditTimeEnd) {
        this.tauditTimeEnd = tauditTimeEnd;
    }

    public Date getTapplyTimeStart() {
        if (StringUtil.isNotBlank(this.tapplyTimeStr)) {
            return DateUtils.parseDate(tapplyTimeStr.split(" - ")[0]);
        }
        return tapplyTimeStart;
    }

    public void setTapplyTimeStart(Date tapplyTimeStart) {
        this.tapplyTimeStart = tapplyTimeStart;
    }

    public Date getTapplyTimeEnd() {
        if (StringUtil.isNotBlank(this.tapplyTimeStr)) {
            return DateUtils.parseDate(tapplyTimeStr.split(" - ")[1]);
        }
        return tapplyTimeEnd;
    }

    public void setTapplyTimeEnd(Date tapplyTimeEnd) {
        this.tapplyTimeEnd = tapplyTimeEnd;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "DeviceMoveRecordVo{" +
                "tplanMoveTimeStr='" + tplanMoveTimeStr + '\'' +
                ", tmoveTimeStr='" + tmoveTimeStr + '\'' +
                ", tauditTimeStr='" + tauditTimeStr + '\'' +
                ", tapplyTimeStr='" + tapplyTimeStr + '\'' +
                ", tplanMoveTimeStart=" + tplanMoveTimeStart +
                ", tplanMoveTimeEnd=" + tplanMoveTimeEnd +
                ", tmoveTimeStart=" + tmoveTimeStart +
                ", tmoveTimeEnd=" + tmoveTimeEnd +
                ", tauditTimeStart=" + tauditTimeStart +
                ", tauditTimeEnd=" + tauditTimeEnd +
                ", tapplyTimeStart=" + tapplyTimeStart +
                ", tapplyTimeEnd=" + tapplyTimeEnd +
                ", merchantName='" + merchantName + '\'' +
                ", sbName='" + sbName + '\'' +
                ", address='" + address + '\'' +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
