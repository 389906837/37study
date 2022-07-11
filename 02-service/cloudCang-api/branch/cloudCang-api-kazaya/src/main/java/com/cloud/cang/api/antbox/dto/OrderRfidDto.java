package com.cloud.cang.api.antbox.dto;

import java.util.Set;

/**
 * Created by oyhk on 2017/5/20.
 * <p>
 * 用户购物推送订单中心实体
 */
public class OrderRfidDto {

    private Long customerId;
    private Long lastCustomerId;
    private Long boxId;
    private Set<String> rfidList;

    private BoxOpenDoorSource boxOpenDoorSource;

    public Long getLastCustomerId() {
        return lastCustomerId;
    }

    public void setLastCustomerId(Long lastCustomerId) {
        this.lastCustomerId = lastCustomerId;
    }

    public BoxOpenDoorSource getBoxOpenDoorSource() {
        return boxOpenDoorSource;
    }

    public void setBoxOpenDoorSource(BoxOpenDoorSource boxOpenDoorSource) {
        this.boxOpenDoorSource = boxOpenDoorSource;
    }

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

    public Set<String> getRfidList() {
        return rfidList;
    }

    public void setRfidList(Set<String> rfidList) {
        this.rfidList = rfidList;
    }

}