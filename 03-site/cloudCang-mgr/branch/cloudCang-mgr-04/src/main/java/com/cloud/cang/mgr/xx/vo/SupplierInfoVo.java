package com.cloud.cang.mgr.xx.vo;

import com.cloud.cang.model.xx.MsgTemplateMain;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * 消息供应商信息表 Vo
 * @author ChangTanchang
 * @time:2018-01-19 10:07:00
 * @version 1.0
 */
public class SupplierInfoVo extends SupplierInfo{
    // 添加时间参数
    private String taddTimeStr;

    // 开始添加日期
    private Date taddTimeStart;

    // 开始结束日期
    private Date taddTimeEnd;

    // 排序字段
    private String orderStr;

    // 商户名称
    private String merchantName;

    // 查询条件
    private String condition;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getTaddTimeStart() {
        if (StringUtil.isNotBlank(this.taddTimeStr)) {
            return DateUtils.parseDate(taddTimeStr.split(" - ")[0]);
        }
        return taddTimeStart;
    }

    public void setTaddTimeStart(Date taddTimeStart) {
        this.taddTimeStart = taddTimeStart;
    }

    public Date getTaddTimeEnd() {
        if (StringUtil.isNotBlank(this.taddTimeStr)) {
            return DateUtils.parseDate(taddTimeStr.split(" - ")[1]);
        }
        return taddTimeEnd;
    }

    public void setTaddTimeEnd(Date taddTimeEnd) {
        this.taddTimeEnd = taddTimeEnd;
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
        return "SupplierInfoVo{" +
                "taddTimeStr='" + taddTimeStr + '\'' +
                ", taddTimeStart=" + taddTimeStart +
                ", taddTimeEnd=" + taddTimeEnd +
                ", orderStr='" + orderStr + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
