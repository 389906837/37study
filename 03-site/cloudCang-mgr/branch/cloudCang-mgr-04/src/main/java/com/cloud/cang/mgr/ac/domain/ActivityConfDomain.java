package com.cloud.cang.mgr.ac.domain;

import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.ac.ActivityConf;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: 活动列表返回Domain
 * @Author: zhouhong
 * @Date: 2018/2/7 19:55
 */
public class ActivityConfDomain extends ActivityConf {

    private String merchantName;//商户名称
    private String scenesType;//场景分类名称
    private String discountWay;//优惠方式名称

    public String getScenesType() {
        if(null != this.getIscenesType()) {
            return GrpParaUtil.getName("SP000118", String.valueOf(this.getIscenesType()));
        }
        return scenesType;
    }

    public void setScenesType(String scenesType) {
        this.scenesType = scenesType;
    }

    public String getDiscountWay() {
        if(null != this.getIdiscountWay()) {
            return GrpParaUtil.getName("SP000113", String.valueOf(this.getIdiscountWay()));
        }
        return discountWay;
    }

    public void setDiscountWay(String discountWay) {
        this.discountWay = discountWay;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "ActivityConfDomain{" +
                "merchantName='" + merchantName + '\'' +
                ", scenesType='" + scenesType + '\'' +
                ", discountWay='" + discountWay + '\'' +
                '}';
    }
}
