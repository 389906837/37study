package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Description: 优惠券批量下发Vo
 * @Author: ChangTanchang
 * @Date: 2018/03/20 10:33
 */
public class CouponBatchVo extends CouponBatch{

    // 商户名称
    private String merchantName;

    // 券生效时间参数
    private String dcouponEffectiveDateStr;

    // 券失效时间参数
    private String dcouponExpiryDateStr;

    // 兑券有效开始时间参数
    private String texStarttimeStr;

    // 兑券有效截止时间参数
    private String texEndtimeStr;

    // 开始劵面值
    private BigDecimal fcouponValueFrom;

    // 结束劵面值
    private BigDecimal fcouponValueTo;

    //开始 审核日期
    private Date tauditDatetimeStart;

    //结束 审核日期
    private Date tauditDatetimeEnd;

    //开始 券生效日期
    private Date dcouponEffectiveDateStart;

    //结束 券生效日期
    private Date dcouponEffectiveDateEnd;

    //开始 券失效日期
    private Date dcouponExpiryDateStart;

    //结束 券失效日期
    private Date dcouponExpiryDateEnd;

    // 开始 兑券有效开始时间
    private Date texStarttimeStart;

    // 结束 兑券有效结束时间
    private Date texStarttimeEnd;

    // 开始 兑券有效截止时间
    private Date texEndtimeStart;

    // 结束 兑券有效截止时间
    private Date texEndtimeEnd;

    // 排序
    private String OrderStr;

    // 查询条件
    private String condition;

    public BigDecimal getFcouponValueFrom() {
        return fcouponValueFrom;
    }

    public void setFcouponValueFrom(BigDecimal fcouponValueFrom) {
        this.fcouponValueFrom = fcouponValueFrom;
    }

    public BigDecimal getFcouponValueTo() {
        return fcouponValueTo;
    }

    public void setFcouponValueTo(BigDecimal fcouponValueTo) {
        this.fcouponValueTo = fcouponValueTo;
    }

    public Date getTauditDatetimeStart() {
        return tauditDatetimeStart;
    }

    public void setTauditDatetimeStart(Date tauditDatetimeStart) {
        this.tauditDatetimeStart = tauditDatetimeStart;
    }

    public Date getTauditDatetimeEnd() {
        return tauditDatetimeEnd;
    }

    public void setTauditDatetimeEnd(Date tauditDatetimeEnd) {
        this.tauditDatetimeEnd = tauditDatetimeEnd;
    }

    public String getOrderStr() {
        return OrderStr;
    }

    public void setOrderStr(String orderStr) {
        OrderStr = orderStr;
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

    public String getTexStarttimeStr() {
        return texStarttimeStr;
    }

    public void setTexStarttimeStr(String texStarttimeStr) {
        this.texStarttimeStr = texStarttimeStr;
    }

    public String getTexEndtimeStr() {
        return texEndtimeStr;
    }

    public void setTexEndtimeStr(String texEndtimeStr) {
        this.texEndtimeStr = texEndtimeStr;
    }

    public Date getTexStarttimeStart() {
        if (StringUtil.isNotBlank(this.texStarttimeStr)) {
            return DateUtils.parseDate(texStarttimeStr.split(" - ")[0]);
        }
        return texStarttimeStart;
    }

    public void setTexStarttimeStart(Date texStarttimeStart) {
        this.texStarttimeStart = texStarttimeStart;
    }

    public Date getTexStarttimeEnd() {
        if (StringUtil.isNotBlank(this.texStarttimeStr)) {
            return DateUtils.parseDate(texStarttimeStr.split(" - ")[1]);
        }
        return texStarttimeEnd;
    }

    public void setTexStarttimeEnd(Date texStarttimeEnd) {
        this.texStarttimeEnd = texStarttimeEnd;
    }

    public Date getTexEndtimeStart() {
        if (StringUtil.isNotBlank(this.texEndtimeStr)) {
            return DateUtils.parseDate(texEndtimeStr.split(" - ")[0]);
        }
        return texEndtimeStart;
    }

    public void setTexEndtimeStart(Date texEndtimeStart) {
        this.texEndtimeStart = texEndtimeStart;
    }

    public Date getTexEndtimeEnd() {
        if (StringUtil.isNotBlank(this.texEndtimeStr)) {
            return DateUtils.parseDate(texEndtimeStr.split(" - ")[1]);
        }
        return texEndtimeEnd;
    }

    public void setTexEndtimeEnd(Date texEndtimeEnd) {
        this.texEndtimeEnd = texEndtimeEnd;
    }

    @Override
    public String toString() {
        return "CouponBatchVo{" +
                "merchantName='" + merchantName + '\'' +
                ", dcouponEffectiveDateStr='" + dcouponEffectiveDateStr + '\'' +
                ", dcouponExpiryDateStr='" + dcouponExpiryDateStr + '\'' +
                ", texStarttimeStr='" + texStarttimeStr + '\'' +
                ", texEndtimeStr='" + texEndtimeStr + '\'' +
                ", fcouponValueFrom=" + fcouponValueFrom +
                ", fcouponValueTo=" + fcouponValueTo +
                ", tauditDatetimeStart=" + tauditDatetimeStart +
                ", tauditDatetimeEnd=" + tauditDatetimeEnd +
                ", dcouponEffectiveDateStart=" + dcouponEffectiveDateStart +
                ", dcouponEffectiveDateEnd=" + dcouponEffectiveDateEnd +
                ", dcouponExpiryDateStart=" + dcouponExpiryDateStart +
                ", dcouponExpiryDateEnd=" + dcouponExpiryDateEnd +
                ", texStarttimeStart=" + texStarttimeStart +
                ", texStarttimeEnd=" + texStarttimeEnd +
                ", texEndtimeStart=" + texEndtimeStart +
                ", texEndtimeEnd=" + texEndtimeEnd +
                ", OrderStr='" + OrderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
