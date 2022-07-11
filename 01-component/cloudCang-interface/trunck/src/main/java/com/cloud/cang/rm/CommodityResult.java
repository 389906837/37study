package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 计划补货单商品参数
 * @Author: zhouhong
 * @Date: 2018/4/11 16:22
 */
public class CommodityResult extends SuperDto {

    /* 商品ID */
    private String scommodityId;

    // 商品编号
    private String scommodityCode;

    // 商品名称
    private String scommodityName;

    // 补货数量
    private Integer forderCount;

    // 商品销售单价
    private BigDecimal fcommodityPrice;

    // 商品总额
    private BigDecimal fcommodityAmount;

    //备注  批次数据
    private String sremark;

    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }

    public String getScommodityName() {
        return scommodityName;
    }

    public void setScommodityName(String scommodityName) {
        this.scommodityName = scommodityName;
    }

    public Integer getForderCount() {
        return forderCount;
    }

    public void setForderCount(Integer forderCount) {
        this.forderCount = forderCount;
    }

    public BigDecimal getFcommodityPrice() {
        return fcommodityPrice;
    }

    public void setFcommodityPrice(BigDecimal fcommodityPrice) {
        this.fcommodityPrice = fcommodityPrice;
    }

    public BigDecimal getFcommodityAmount() {
        return fcommodityAmount;
    }

    public void setFcommodityAmount(BigDecimal fcommodityAmount) {
        this.fcommodityAmount = fcommodityAmount;
    }

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    @Override
    public String toString() {
        return "CommodityResult{" +
                "scommodityId='" + scommodityId + '\'' +
                ", scommodityCode='" + scommodityCode + '\'' +
                ", scommodityName='" + scommodityName + '\'' +
                ", forderCount=" + forderCount +
                ", fcommodityPrice=" + fcommodityPrice +
                ", fcommodityAmount=" + fcommodityAmount +
                ", sremark='" + sremark + '\'' +
                '}';
    }
}
