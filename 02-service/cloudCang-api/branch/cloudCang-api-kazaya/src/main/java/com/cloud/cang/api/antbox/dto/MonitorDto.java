package com.cloud.cang.api.antbox.dto;

import com.cloud.cang.api.antbox.constant.BoxStatus;

import java.util.List;

/**
 * Created by oyhk on 2017/8/7.
 */
public class MonitorDto {

    // 最后一次心跳时间
    private Long heartbeatTime;
    // 最后一次正常心跳时间
    private Long lastExceptionTime;
    // 当心跳超过需要处理的时间时，处理时间列表
    private List<Long> dealWithTimeList;
    // 当前状态
    private Boolean status;
    /**
     * 当前状态
     */
    private BoxStatus boxStatus;
    /**
     * 当前状态发生时间
     */
    private Long boxStatusTime;

    public BoxStatus getBoxStatus() {
        return boxStatus;
    }

    public void setBoxStatus(BoxStatus boxStatus) {
        this.boxStatus = boxStatus;
    }

    public Long getBoxStatusTime() {
        return boxStatusTime;
    }

    public void setBoxStatusTime(Long boxStatusTime) {
        this.boxStatusTime = boxStatusTime;
    }

    public Long getLastExceptionTime() {
        return lastExceptionTime;
    }

    public void setLastExceptionTime(Long lastExceptionTime) {
        this.lastExceptionTime = lastExceptionTime;
    }

    public Long getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Long heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public List<Long> getDealWithTimeList() {
        return dealWithTimeList;
    }

    public void setDealWithTimeList(List<Long> dealWithTimeList) {
        this.dealWithTimeList = dealWithTimeList;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
