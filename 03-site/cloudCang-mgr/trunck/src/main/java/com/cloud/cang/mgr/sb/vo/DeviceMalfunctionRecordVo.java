package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceMalfunctionRecord;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * Created by Administrator on 2018/6/13.
 */
public class DeviceMalfunctionRecordVo extends DeviceMalfunctionRecord {
    // 商户名称
    private String merchantName;

    // 设备名称
    private String sbName;

    // 设备地址
    private String address;

    // 申报时间参数
    private String tdeclareTimeStr;

    // 处理时间参数
    private String sdealwithTimeStr;

    // 申报开始时间
    private Date tdeclareTimeStart;

    // 申报结束时间
    private Date tdeclareTimeEnd;

    // 处理开始时间
    private Date sdealwithTimeStart;

    // 处理结束时间
    private Date sdealwithTimeEnd;

    // 排序字段
    private String orderStr;

    // 搜索条件
    private String condition;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTdeclareTimeStr() {
        return tdeclareTimeStr;
    }

    public void setTdeclareTimeStr(String tdeclareTimeStr) {
        this.tdeclareTimeStr = tdeclareTimeStr;
    }

    public String getSdealwithTimeStr() {
        return sdealwithTimeStr;
    }

    public void setSdealwithTimeStr(String sdealwithTimeStr) {
        this.sdealwithTimeStr = sdealwithTimeStr;
    }

    public Date getTdeclareTimeStart() {
        if (StringUtil.isNotBlank(this.tdeclareTimeStr)) {
            return DateUtils.parseDate(tdeclareTimeStr.split(" - ")[0]);
        }
        return tdeclareTimeStart;
    }

    public void setTdeclareTimeStart(Date tdeclareTimeStart) {
        this.tdeclareTimeStart = tdeclareTimeStart;
    }

    public Date getTdeclareTimeEnd() {
        if (StringUtil.isNotBlank(this.tdeclareTimeStr)) {
            return DateUtils.parseDate(tdeclareTimeStr.split(" - ")[1]);
        }
        return tdeclareTimeEnd;
    }

    public void setTdeclareTimeEnd(Date tdeclareTimeEnd) {
        this.tdeclareTimeEnd = tdeclareTimeEnd;
    }

    public Date getSdealwithTimeStart() {
        if (StringUtil.isNotBlank(this.sdealwithTimeStr)) {
            return DateUtils.parseDate(sdealwithTimeStr.split(" - ")[0]);
        }
        return sdealwithTimeStart;
    }

    public void setSdealwithTimeStart(Date sdealwithTimeStart) {
        this.sdealwithTimeStart = sdealwithTimeStart;
    }

    public Date getSdealwithTimeEnd() {
        if (StringUtil.isNotBlank(this.sdealwithTimeStr)) {
            return DateUtils.parseDate(sdealwithTimeStr.split(" - ")[1]);
        }
        return sdealwithTimeEnd;
    }

    public void setSdealwithTimeEnd(Date sdealwithTimeEnd) {
        this.sdealwithTimeEnd = sdealwithTimeEnd;
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

    @Override
    public String toString() {
        return "DeviceMalfunctionRecordVo{" +
                "merchantName='" + merchantName + '\'' +
                ", sbName='" + sbName + '\'' +
                ", address='" + address + '\'' +
                ", tdeclareTimeStr='" + tdeclareTimeStr + '\'' +
                ", sdealwithTimeStr='" + sdealwithTimeStr + '\'' +
                ", tdeclareTimeStart=" + tdeclareTimeStart +
                ", tdeclareTimeEnd=" + tdeclareTimeEnd +
                ", sdealwithTimeStart=" + sdealwithTimeStart +
                ", sdealwithTimeEnd=" + sdealwithTimeEnd +
                '}';
    }
}
