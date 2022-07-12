package com.cloud.cang.mgr.sh.vo;

import com.cloud.cang.model.sh.DomainConf;

/**
 * @version 1.0
 * @Description: 域名管理列表返回Vo
 * @Author: yanlingfeng
 * @Date: 2018/2/10 16:14
 */
public class DomainConfVo extends DomainConf {
 private  String merchantCode;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    private  String  merchantName;


}
