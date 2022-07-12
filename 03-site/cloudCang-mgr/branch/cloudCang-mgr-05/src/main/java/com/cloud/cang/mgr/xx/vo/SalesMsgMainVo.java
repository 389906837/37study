package com.cloud.cang.mgr.xx.vo;

import com.cloud.cang.model.xx.SalesMsgMain;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * 营销短信信息表 Vo
 * @author ChangTanchang
 * @time:2018-01-19 10:07:00
 * @version 1.0
 */
public class SalesMsgMainVo extends SalesMsgMain{
    // 添加时时间参数
    private String taddTimeStr;

    // 开始添加日期
    private Date toperateStartDate;

    // 开始结束日期
    private Date toperateEndDate;

    // 排序字段
    private String orderStr;

    // 消息供应商名称
    private String supplierInfoSname;

    // 商户名称
    private String merchantName;

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

    public String getSupplierInfoSname() {
        return supplierInfoSname;
    }

    public void setSupplierInfoSname(String supplierInfoSname) {
        this.supplierInfoSname = supplierInfoSname;
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

    @Override
    public String toString() {
        return "SalesMsgMainVo{" +
                "taddTimeStr='" + taddTimeStr + '\'' +
                ", toperateStartDate=" + toperateStartDate +
                ", toperateEndDate=" + toperateEndDate +
                ", orderStr='" + orderStr + '\'' +
                ", supplierInfoSname='" + supplierInfoSname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
