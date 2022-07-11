package com.cloud.cang.api.antbox.dto;

import java.util.Set;

/**
 * Created by oyhk on 2017/5/20.
 * <p>
 * 用户购物推送订单中心实体
 */
public class DistributionOrderRfidDto {

    private Long customerId;
    private Long boxId;
    /**
     * 上架 rfid list
     */
    private Set<String> upRfidList;
    /**
     * 下架 rfid list
     */
    private Set<String> downRfidList;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Set<String> getUpRfidList() {
        return upRfidList;
    }

    public void setUpRfidList(Set<String> upRfidList) {
        this.upRfidList = upRfidList;
    }

    public Set<String> getDownRfidList() {
        return downRfidList;
    }

    public void setDownRfidList(Set<String> downRfidList) {
        this.downRfidList = downRfidList;
    }
}
