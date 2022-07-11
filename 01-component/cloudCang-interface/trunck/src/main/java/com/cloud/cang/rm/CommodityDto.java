package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 计划补货单商品参数
 * @Author: zhouhong
 * @Date: 2018/4/11 16:22
 */
public class CommodityDto extends SuperDto {
    //==========必填==========
    /* 商品ID */
    private String scommodityId;
    // 补货数量
    private Integer forderCount;

    //==========选填==========
    //批次备注
    private String sremark;

    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    public Integer getForderCount() {
        return forderCount;
    }

    public void setForderCount(Integer forderCount) {
        this.forderCount = forderCount;
    }

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    @Override
    public String toString() {
        return "CommodityDto{" +
                "scommodityId='" + scommodityId + '\'' +
                ", forderCount=" + forderCount +
                ", sremark='" + sremark + '\'' +
                '}';
    }
}
