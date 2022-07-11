package com.cloud.cang.api.antbox.dto;

/**
 * Created by oyhk on 2017/5/9.
 */
public class BoxDto extends Box {

    private String reqId;
    // 用户id
    private Long customerId;
    // 是否补货开门
    private Boolean distribution;
    private BoxOpenDoorSource openDoorSource;

    public BoxOpenDoorSource getOpenDoorSource() {
        return openDoorSource;
    }

    public void setOpenDoorSource(BoxOpenDoorSource openDoorSource) {
        this.openDoorSource = openDoorSource;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean getDistribution() {
        return distribution;
    }

    public void setDistribution(Boolean distribution) {

        this.distribution = distribution;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
}
