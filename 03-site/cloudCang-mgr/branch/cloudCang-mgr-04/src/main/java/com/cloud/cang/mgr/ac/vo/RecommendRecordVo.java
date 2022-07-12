package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.RecommendRecord;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Description: 好友推荐Vo
 * @Author: ChangTanchang
 * @Date: 2018/03/19 19:06
 */
public class RecommendRecordVo extends RecommendRecord {

    // 商户名称
    private String merchantName;

    // 注册时间参数
    private String toperateStartDateStr;

    // 首单时间参数
    private String torderDatetimeStr;

    // 注册开始日期
    private Date toperateStartDate;

    // 注册结束日期
    private Date toperateEndDate;

    // 首单开始时间
    private Date torderDatetimeStart;

    // 首单结束时间
    private Date torderDatetimeEnd;

    // 首单金额(区间范围)
    private BigDecimal sorderMoneyFrom;

    private BigDecimal sorderMoneyTo;

    // 排序字段
    private String OrderStr;

    // 查询条件
    private String condition;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public BigDecimal getSorderMoneyFrom() {
        return sorderMoneyFrom;
    }

    public void setSorderMoneyFrom(BigDecimal sorderMoneyFrom) {
        this.sorderMoneyFrom = sorderMoneyFrom;
    }

    public BigDecimal getSorderMoneyTo() {
        return sorderMoneyTo;
    }

    public void setSorderMoneyTo(BigDecimal sorderMoneyTo) {
        this.sorderMoneyTo = sorderMoneyTo;
    }

    public String getOrderStr() {
        return OrderStr;
    }

    public void setOrderStr(String orderStr) {
        OrderStr = orderStr;
    }

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.toperateStartDateStr)) {
            return DateUtils.parseDate(toperateStartDateStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.toperateStartDateStr)) {
            return DateUtils.parseDate(toperateStartDateStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public String getToperateStartDateStr() {
        return toperateStartDateStr;
    }

    public void setToperateStartDateStr(String toperateStartDateStr) {
        this.toperateStartDateStr = toperateStartDateStr;
    }

    public String getTorderDatetimeStr() {
        return torderDatetimeStr;
    }

    public void setTorderDatetimeStr(String torderDatetimeStr) {
        this.torderDatetimeStr = torderDatetimeStr;
    }

    public Date getTorderDatetimeStart() {
        if (StringUtil.isNotBlank(this.torderDatetimeStr)) {
            return DateUtils.parseDate(torderDatetimeStr.split(" - ")[0]);
        }
        return torderDatetimeStart;
    }

    public void setTorderDatetimeStart(Date torderDatetimeStart) {
        this.torderDatetimeStart = torderDatetimeStart;
    }

    public Date getTorderDatetimeEnd() {
        if (StringUtil.isNotBlank(this.torderDatetimeStr)) {
            return DateUtils.parseDate(torderDatetimeStr.split(" - ")[1]);
        }
        return torderDatetimeEnd;
    }

    public void setTorderDatetimeEnd(Date torderDatetimeEnd) {
        this.torderDatetimeEnd = torderDatetimeEnd;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "RecommendRecordVo{" +
                "merchantName='" + merchantName + '\'' +
                ", toperateStartDateStr='" + toperateStartDateStr + '\'' +
                ", torderDatetimeStr='" + torderDatetimeStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", torderDatetimeStart=" + torderDatetimeStart +
                ", torderDatetimeEnd=" + torderDatetimeEnd +
                ", sorderMoneyFrom=" + sorderMoneyFrom +
                ", sorderMoneyTo=" + sorderMoneyTo +
                ", OrderStr='" + OrderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
