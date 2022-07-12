package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.CouponCodeExDetails;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @version 1.0
 * @Description: 券码兑换明细Vo
 * @Author: ChangTanchang
 * @Date: 2018/03/20 14:33
 */
public class CouponCodeExDetailsVo extends CouponCodeExDetails {

    // 商户名称
    private String merchantName;

    // 商户编号
    private String merchantCode;

    // 兑换时间参数
    private String texTimeStr;

    // 兑换失效时间参数
    private String texEndtimeStr;

    // 兑换开始时间
    private Date texTimeStart;

    // 兑换结束时间
    private Date texTimeEnd;

    // 兑换失效开始时间
    private Date texEndtimeStart;

    // 兑换失效结束时间
    private Date texEndtimeEnd;

    // 排序
    private String orderStr;

    // 查询条件
    private String condition;

    public Date getTexTimeStart() {
        if (StringUtil.isNotBlank(this.texTimeStr)) {
            return DateUtils.parseDate(texTimeStr.split(" - ")[0]);
        }
        return texTimeStart;
    }

    public void setTexTimeStart(Date texTimeStart) {
        this.texTimeStart = texTimeStart;
    }

    public Date getTexTimeEnd() {
        if (StringUtil.isNotBlank(this.texTimeStr)) {
            return DateUtils.parseDate(texTimeStr.split(" - ")[1]);
        }
        return texTimeEnd;
    }

    public void setTexTimeEnd(Date texTimeEnd) {
        this.texTimeEnd = texTimeEnd;
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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTexTimeStr() {
        return texTimeStr;
    }

    public void setTexTimeStr(String texTimeStr) {
        this.texTimeStr = texTimeStr;
    }

    public String getTexEndtimeStr() {
        return texEndtimeStr;
    }

    public void setTexEndtimeStr(String texEndtimeStr) {
        this.texEndtimeStr = texEndtimeStr;
    }

    @Override
    public String toString() {
        return "CouponCodeExDetailsVo{" +
                "merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", texTimeStr='" + texTimeStr + '\'' +
                ", texEndtimeStr='" + texEndtimeStr + '\'' +
                ", texTimeStart=" + texTimeStart +
                ", texTimeEnd=" + texTimeEnd +
                ", texEndtimeStart=" + texEndtimeStart +
                ", texEndtimeEnd=" + texEndtimeEnd +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
