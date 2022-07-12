package com.cloud.cang.mgr.sp.domain;

import com.cloud.cang.model.sp.Category;

/**
 * Created by Alex on 2018/2/22.
 */
public class CategoryDomain extends Category{

    private static final long serialVersionUID = 1L;

    private String merchantName;            /* 商户名称 */

    private String orderStr;//排序字段

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    @Override
    public String toString() {
        return "CategoryDomain{" +
                "merchantName='" + merchantName + '\'' +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
