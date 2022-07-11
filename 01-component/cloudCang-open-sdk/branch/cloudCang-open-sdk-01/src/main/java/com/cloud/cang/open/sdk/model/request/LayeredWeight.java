package com.cloud.cang.open.sdk.model.request;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by YLF on 2019/6/26.
 */
public class LayeredWeight implements Serializable {
    private BigDecimal layeredWeight;     //分层重量
    private String weightIp;            //重力Ip

    public BigDecimal getLayeredWeight() {
        return layeredWeight;
    }

    public void setLayeredWeight(BigDecimal layeredWeight) {
        this.layeredWeight = layeredWeight;
    }

    public String getWeightIp() {
        return weightIp;
    }

    public void setWeightIp(String weightIp) {
        this.weightIp = weightIp;
    }
}
