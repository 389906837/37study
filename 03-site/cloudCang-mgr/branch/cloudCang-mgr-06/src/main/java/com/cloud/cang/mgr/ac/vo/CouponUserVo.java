package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @version 1.0
 * @Description: 用户持有券Vo
 * @Author: ChangTanchang
 * @Date: 2018/03/20 14:33
 */
public class CouponUserVo extends CouponUser{

    // 商户名称
    private String merchantName;

    // 获券时间参数
    private String tgetDatetimeStartStr;

    // 实际使用时间参数
    private String tactualUseDatetimeStr;

    // 券生效时间参数
    private String dcouponEffectiveDateStr;

    // 券失效时间参数
    private String dcouponExpiryDateStr;

    // 获券开始时间
    private Date tgetDatetimeStart;

    // 获券结束时间
    private Date tgetDatetimeEnd;

    // 实际使用开始时间
    private Date tactualUseDatetimeStart;

    // 实际使用结束时间
    private Date tactualUseDatetimeEnd;

    // 券生效开始时间
    private Date dcouponEffectiveDateStart;

    // 券生效结束时间
    private Date dcouponEffectiveDateEnd;

    // 券失效开始时间
    private Date dcouponExpiryDateStart;

    // 券失效结束时间
    private Date dcouponExpiryDateEnd;

    // 排序
    private String orderStr;

    // 查询条件
    private String condition;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Date getTgetDatetimeStart() {
        if (StringUtil.isNotBlank(this.tgetDatetimeStartStr)) {
            return DateUtils.parseDate(tgetDatetimeStartStr.split(" - ")[0]);
        }
        return tgetDatetimeStart;
    }

    public void setTgetDatetimeStart(Date tgetDatetimeStart) {
        this.tgetDatetimeStart = tgetDatetimeStart;
    }

    public Date getTgetDatetimeEnd() {
        if (StringUtil.isNotBlank(this.tgetDatetimeStartStr)) {
            return DateUtils.parseDate(tgetDatetimeStartStr.split(" - ")[1]);
        }
        return tgetDatetimeEnd;
    }

    public void setTgetDatetimeEnd(Date tgetDatetimeEnd) {
        this.tgetDatetimeEnd = tgetDatetimeEnd;
    }

    public Date getTactualUseDatetimeStart() {
        if (StringUtil.isNotBlank(this.tactualUseDatetimeStr)) {
            return DateUtils.parseDate(tactualUseDatetimeStr.split(" - ")[0]);
        }
        return tactualUseDatetimeStart;
    }

    public void setTactualUseDatetimeStart(Date tactualUseDatetimeStart) {
        this.tactualUseDatetimeStart = tactualUseDatetimeStart;
    }

    public Date getTactualUseDatetimeEnd() {
        if (StringUtil.isNotBlank(this.tactualUseDatetimeStr)) {
            return DateUtils.parseDate(tactualUseDatetimeStr.split(" - ")[1]);
        }
        return tactualUseDatetimeEnd;
    }

    public void setTactualUseDatetimeEnd(Date tactualUseDatetimeEnd) {
        this.tactualUseDatetimeEnd = tactualUseDatetimeEnd;
    }

    public String getTgetDatetimeStartStr() {
        return tgetDatetimeStartStr;
    }

    public void setTgetDatetimeStartStr(String tgetDatetimeStartStr) {
        this.tgetDatetimeStartStr = tgetDatetimeStartStr;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getDcouponEffectiveDateStart() {
        if (StringUtil.isNotBlank(this.dcouponEffectiveDateStr)) {
            return DateUtils.parseDate(dcouponEffectiveDateStr.split(" - ")[0]);
        }
        return dcouponEffectiveDateStart;
    }

    public void setDcouponEffectiveDateStart(Date dcouponEffectiveDateStart) {
        this.dcouponEffectiveDateStart = dcouponEffectiveDateStart;
    }

    public Date getDcouponEffectiveDateEnd() {
        if (StringUtil.isNotBlank(this.dcouponEffectiveDateStr)) {
            return DateUtils.parseDate(dcouponEffectiveDateStr.split(" - ")[1]);
        }
        return dcouponEffectiveDateEnd;
    }

    public void setDcouponEffectiveDateEnd(Date dcouponEffectiveDateEnd) {
        this.dcouponEffectiveDateEnd = dcouponEffectiveDateEnd;
    }

    public Date getDcouponExpiryDateStart() {
        if (StringUtil.isNotBlank(this.dcouponExpiryDateStr)) {
            return DateUtils.parseDate(dcouponExpiryDateStr.split(" - ")[0]);
        }
        return dcouponExpiryDateStart;
    }

    public void setDcouponExpiryDateStart(Date dcouponExpiryDateStart) {
        this.dcouponExpiryDateStart = dcouponExpiryDateStart;
    }

    public Date getDcouponExpiryDateEnd() {
        if (StringUtil.isNotBlank(this.dcouponExpiryDateStr)) {
            return DateUtils.parseDate(dcouponExpiryDateStr.split(" - ")[1]);
        }
        return dcouponExpiryDateEnd;
    }

    public void setDcouponExpiryDateEnd(Date dcouponExpiryDateEnd) {
        this.dcouponExpiryDateEnd = dcouponExpiryDateEnd;
    }

    public String getTactualUseDatetimeStr() {
        return tactualUseDatetimeStr;
    }

    public void setTactualUseDatetimeStr(String tactualUseDatetimeStr) {
        this.tactualUseDatetimeStr = tactualUseDatetimeStr;
    }

    public String getDcouponEffectiveDateStr() {
        return dcouponEffectiveDateStr;
    }

    public void setDcouponEffectiveDateStr(String dcouponEffectiveDateStr) {
        this.dcouponEffectiveDateStr = dcouponEffectiveDateStr;
    }

    public String getDcouponExpiryDateStr() {
        return dcouponExpiryDateStr;
    }

    public void setDcouponExpiryDateStr(String dcouponExpiryDateStr) {
        this.dcouponExpiryDateStr = dcouponExpiryDateStr;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "CouponUserVo{" +
                "merchantName='" + merchantName + '\'' +
                ", tgetDatetimeStartStr='" + tgetDatetimeStartStr + '\'' +
                ", tactualUseDatetimeStr='" + tactualUseDatetimeStr + '\'' +
                ", dcouponEffectiveDateStr='" + dcouponEffectiveDateStr + '\'' +
                ", dcouponExpiryDateStr='" + dcouponExpiryDateStr + '\'' +
                ", tgetDatetimeStart=" + tgetDatetimeStart +
                ", tgetDatetimeEnd=" + tgetDatetimeEnd +
                ", tactualUseDatetimeStart=" + tactualUseDatetimeStart +
                ", tactualUseDatetimeEnd=" + tactualUseDatetimeEnd +
                ", dcouponEffectiveDateStart=" + dcouponEffectiveDateStart +
                ", dcouponEffectiveDateEnd=" + dcouponEffectiveDateEnd +
                ", dcouponExpiryDateStart=" + dcouponExpiryDateStart +
                ", dcouponExpiryDateEnd=" + dcouponExpiryDateEnd +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
