package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceCommodity;

/**
 * @version 1.0
 * @ClassName DeviceCommodityVo
 * @Description 设备商品bean信息
 * @Author zengzexiong
 * @Date 2018年1月24日21:10:20
 */
public class DeviceCommodityVo  extends DeviceCommodity{
    //    private String sdeviceCode;             /* 设备编号 */
//    private String sdeviceId;               /* 设备ID */
//    private String scommodityId;            /* 商品ID */
//    private String scommodityCode;          /* 商品编号 */
//    private Integer istatus;                /* 10=在售    20=下架 */
//    private String supdateUser;             /* 修改人 */
//    private Date taddTime;                  /* 添加日期 */
//    private Date tupdateTime;               /* 修改日期 */

    private String orderStr;//排序字段
    private String sname;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */
    private String queryCondition;          /* 查询条件 */
    private String commodityName;           /* 商品名称 */

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

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

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    @Override
    public String toString() {
        return "DeviceCommodityVo{" +
                "orderStr='" + orderStr + '\'' +
                ", sname='" + sname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                ", commodityName='" + commodityName + '\'' +
                '}';
    }
}
