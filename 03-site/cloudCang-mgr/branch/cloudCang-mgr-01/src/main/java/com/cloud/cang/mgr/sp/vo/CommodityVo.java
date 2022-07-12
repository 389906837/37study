package com.cloud.cang.mgr.sp.vo;

import com.cloud.cang.model.sp.CommodityInfo;

/**
 * @version 1.0
 * @Description: 商品查询参数Vo
 * @Author: zhouhong
 * @Date: 2018/2/10 12:35
 */
public class CommodityVo extends CommodityInfo {

    private String orderStr;//排序字段
    private String merchantName;//商户名称
    private String queryCondition;//查询条件
    private String deviceId;//设备Id

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    @Override
    public String toString() {
        return "CommodityVo{" +
                "orderStr='" + orderStr + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
