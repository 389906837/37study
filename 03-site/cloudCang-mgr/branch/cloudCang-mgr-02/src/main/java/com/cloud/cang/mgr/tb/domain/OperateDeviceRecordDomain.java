package com.cloud.cang.mgr.tb.domain;

import com.cloud.cang.model.tb.OperateDeviceRecord;

/**
 * @version 1.0
 * @ClassName OperateDeviceRecordDomain
 * @Description 第三方商户订单页面展示对象
 * @Author zengzexiong
 * @Date 2018年10月10日10:28:42
 */
public class OperateDeviceRecordDomain extends OperateDeviceRecord {
    private String sname;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
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

    @Override
    public String toString() {
        return "OperateDeviceRecordDomain{" +
                "sname='" + sname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                '}';
    }
}
