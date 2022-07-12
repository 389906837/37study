package com.cloud.cang.mgr.wz.vo;

import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description 广告区域管理VO
 * @author ChangTanchang
 * @time 2018-03-29 14:17:15
 * @fileName RegionVo.java
 * @version 1.0
 */
public class AdvertisVo extends Advertis{

    // 商户名称
    private String merchantname;

    //开始 开始日期
    private Date tstartDateStart;

    //结束 开始日期
    private Date tstartDateEnd;

    //开始 结束日期
    private Date tendDateStart;

    //结束 结束日期
    private Date tendDateEnd;

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

    private String condition;//
    // 设备编号
    private String deviceCode;
    // 设备ID
    private String deviceId;
    // 运营区域名称
    private String sregionName;
    // 运营区域Code
    private String sregionCode;
    private String startTimeStr;
    private String endTimeStr;

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getSregionCode() {
        return sregionCode;
    }

    public void setSregionCode(String sregionCode) {
        this.sregionCode = sregionCode;
    }

    public String getSregionName() {
        return sregionName;
    }

    public void setSregionName(String sregionName) {
        this.sregionName = sregionName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getTstartDateStart() {
        if (StringUtil.isNotBlank(this.startTimeStr)) {
            return DateUtils.parseDate(startTimeStr.split(" - ")[0]);
        }
        return tstartDateStart;
    }

    public void setTstartDateStart(Date tstartDateStart) {
        this.tstartDateStart = tstartDateStart;
    }

    public Date getTstartDateEnd() {
        if (StringUtil.isNotBlank(this.startTimeStr)) {
            return DateUtils.parseDate(startTimeStr.split(" - ")[1]);
        }
        return tstartDateEnd;
    }

    public void setTstartDateEnd(Date tstartDateEnd) {
        this.tstartDateEnd = tstartDateEnd;
    }

    public Date getTendDateStart() {
        if (StringUtil.isNotBlank(this.endTimeStr)) {
            return DateUtils.parseDate(endTimeStr.split(" - ")[0]);
        }
        return tendDateStart;
    }

    public void setTendDateStart(Date tendDateStart) {
        this.tendDateStart = tendDateStart;
    }

    public Date getTendDateEnd() {
        if (StringUtil.isNotBlank(this.endTimeStr)) {
            return DateUtils.parseDate(endTimeStr.split(" - ")[1]);
        }
        return tendDateEnd;
    }

    public void setTendDateEnd(Date tendDateEnd) {
        this.tendDateEnd = tendDateEnd;
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

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    @Override
    public String toString() {
        return "AdvertisVo{" +
                "merchantname='" + merchantname + '\'' +
                ", tstartDateStart=" + tstartDateStart +
                ", tstartDateEnd=" + tstartDateEnd +
                ", tendDateStart=" + tendDateStart +
                ", tendDateEnd=" + tendDateEnd +
                ", isortFrom=" + isortFrom +
                ", isortTo=" + isortTo +
                ", taddTimeStart=" + taddTimeStart +
                ", taddTimeEnd=" + taddTimeEnd +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", sregionName='" + sregionName + '\'' +
                ", sregionCode='" + sregionCode + '\'' +
                ", startTimeStr='" + startTimeStr + '\'' +
                ", endTimeStr='" + endTimeStr + '\'' +
                '}';
    }
}
