package com.cloud.cang.mgr.tb.vo;

import com.cloud.cang.model.tb.InterfaceTransferLog;


/**
 * @version 1.0
 * @ClassName InterfaceTransferLogVo
 * @Description 第三方接口调用查询条件对象
 * @Author zengzexiong
 * @Date 2018年10月12日16:04:46
 */
public class InterfaceTransferLogVo extends InterfaceTransferLog {

    private String orderStr;//排序字段
    private String queryCondition;//查询条件
    private String merchantName;//商户名称
    private String merchantCode;//商户编号
    private String sname;//设备名称

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
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

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "InterfaceTransferLogVo{" +
                "orderStr='" + orderStr + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", sname='" + sname + '\'' +
                '}';
    }
}
