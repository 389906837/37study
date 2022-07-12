package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.model.sb.GroupManage;

/**
 * Created by Alex on 2018/4/25.
 */
public class GroupManageDomain extends GroupManage{
    private String merchantName;            /* 商户名称 */

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

}
