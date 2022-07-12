package com.cloud.cang.mgr.xx.vo;

import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.model.xx.MsgTemplateMain;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * 消息/协议 Vo
 * @author ChangTanchang
 * @time:2018-01-22 10:07:00
 * @version 1.0
 */
public class MsgTemplateVo extends MsgTemplate{
    // 添加时间参数
    private String taddTimeStr;

    // 开始添加日期
    private Date toperateStartDate;

    // 开始结束日期
    private Date toperateEndDate;

    // 排序字段
    private String orderStr;

    // 供应商名称
    private String sname;

    // 商户名称
    private String merchantName;

    // 商户编号
    private String shCode;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getToperateStartDate() {
        if (StringUtil.isNotBlank(this.taddTimeStr)) {
            return DateUtils.parseDate(taddTimeStr.split(" - ")[0]);
        }
        return toperateStartDate;
    }

    public void setToperateStartDate(Date toperateStartDate) {
        this.toperateStartDate = toperateStartDate;
    }

    public Date getToperateEndDate() {
        if (StringUtil.isNotBlank(this.taddTimeStr)) {
            return DateUtils.parseDate(taddTimeStr.split(" - ")[1]);
        }
        return toperateEndDate;
    }

    public void setToperateEndDate(Date toperateEndDate) {
        this.toperateEndDate = toperateEndDate;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    public String getShCode() {
        return shCode;
    }

    public void setShCode(String shCode) {
        this.shCode = shCode;
    }

    @Override
    public String toString() {
        return "MsgTemplateVo{" +
                "taddTimeStr='" + taddTimeStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", orderStr='" + orderStr + '\'' +
                ", sname='" + sname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", shCode='" + shCode + '\'' +
                '}';
    }
}
