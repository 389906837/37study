package com.cloud.cang.mgr.tb.domain;

import com.cloud.cang.generic.GenericEntity;
import com.cloud.cang.model.tb.OperateDeviceDetail;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @ClassName ThirdOrderCommodityDomain
 * @Description 第三方商户订单页面详情展示对象
 * @Author zengzexiong
 * @Date 2018年10月10日10:28:42
 */
public class ThirdOrderCommodityDomain extends OperateDeviceDetail{

    private BigDecimal iweight;         //  视觉商品重量

    public BigDecimal getIweight() {
        return iweight;
    }

    public void setIweight(BigDecimal iweight) {
        this.iweight = iweight;
    }

    @Override
    public String toString() {
        return "ThirdOrderCommodityDomain{" +
                "iweight=" + iweight +
                '}';
    }
}
