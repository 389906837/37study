package com.cloud.cang.mgr.xx.vo;

import com.cloud.cang.model.xx.MsgTask;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * 消息任务表 Vo
 * @author ChangTanchang
 * @time:2018-01-19 10:07:00
 * @version 1.0
 */
public class MsgTaskVo extends MsgTask{
    // 发送时间参数
    private String tbeginSendDatetimeStr;

    // 增加时间参数
    private String taddTimeStr;

    // 开始发送日期
    private Date toperateStartDate;

    // 开始结束日期
    private Date toperateEndDate;

    // 开始增加时间
    private Date taddtimeStart;

    // 结束增加时间
    private Date taddtimeEnd;

    // 开始发送类别 1：单发 2：群发
    private Integer isenderTypeFrom;

    // 结束发送类别 1：单发 2：群发
    private Integer isenderTypeTo;

    // 排序字段
    private String orderStr;

    // 商户名称
    private String merchantName;

    // 查询条件
    private String condition;

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.tbeginSendDatetimeStr)) {
            return DateUtils.parseDate(tbeginSendDatetimeStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.tbeginSendDatetimeStr)) {
            return DateUtils.parseDate(tbeginSendDatetimeStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public Date getTaddtimeStart() {
        if (StringUtil.isNotBlank(this.taddTimeStr)) {
            return DateUtils.parseDate(taddTimeStr.split(" - ")[0]);
        }
        return taddtimeStart;
    }

    public void setTaddtimeStart(Date taddtimeStart) {
        this.taddtimeStart = taddtimeStart;
    }

    public Date getTaddtimeEnd() {
        if (StringUtil.isNotBlank(this.taddTimeStr)) {
            return DateUtils.parseDate(taddTimeStr.split(" - ")[1]);
        }
        return taddtimeEnd;
    }

    public void setTaddtimeEnd(Date taddtimeEnd) {
        this.taddtimeEnd = taddtimeEnd;
    }

    public Integer getIsenderTypeFrom() {
        return isenderTypeFrom;
    }

    public void setIsenderTypeFrom(Integer isenderTypeFrom) {
        this.isenderTypeFrom = isenderTypeFrom;
    }

    public Integer getIsenderTypeTo() {
        return isenderTypeTo;
    }

    public void setIsenderTypeTo(Integer isenderTypeTo) {
        this.isenderTypeTo = isenderTypeTo;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getTbeginSendDatetimeStr() {
        return tbeginSendDatetimeStr;
    }

    public void setTbeginSendDatetimeStr(String tbeginSendDatetimeStr) {
        this.tbeginSendDatetimeStr = tbeginSendDatetimeStr;
    }

    public String getTaddTimeStr() {
        return taddTimeStr;
    }

    public void setTaddTimeStr(String taddTimeStr) {
        this.taddTimeStr = taddTimeStr;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "MsgTaskVo{" +
                "tbeginSendDatetimeStr='" + tbeginSendDatetimeStr + '\'' +
                ", taddTimeStr='" + taddTimeStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", taddtimeStart=" + taddtimeStart +
                ", taddtimeEnd=" + taddtimeEnd +
                ", isenderTypeFrom=" + isenderTypeFrom +
                ", isenderTypeTo=" + isenderTypeTo +
                ", orderStr='" + orderStr + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
