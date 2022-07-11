package com.cloud.cang.model;

import java.math.BigDecimal;

/**
 * Created by YLF on 2019/6/26.
 */
public class LayeredWeight {
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
