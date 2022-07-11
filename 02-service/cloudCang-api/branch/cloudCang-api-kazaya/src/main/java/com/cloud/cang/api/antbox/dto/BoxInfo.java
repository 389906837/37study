package com.cloud.cang.api.antbox.dto;

import com.cloud.cang.api.antbox.constant.BoxStatus;

import java.util.Date;

/**
 * 蚂蚁盒子信息
 */
public class BoxInfo {

    public BoxInfo() {
    }
    /**
     * SN：5个字节，系统出厂序列号。
     */
    private Long boxId;

    private volatile BoxStatus status = BoxStatus.INIT;

    /**
     * 售货机本地时间
     */
    private Date boxLocalTime;

    private Long openDoorTime;

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public synchronized BoxStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(BoxStatus status) {
        this.status = status;
    }

    public Date getBoxLocalTime() {
        return boxLocalTime;
    }

    public void setBoxLocalTime(Date boxLocalTime) {
        this.boxLocalTime = boxLocalTime;
    }

    public Long getOpenDoorTime() {
        return openDoorTime;
    }

    public void setOpenDoorTime(Long openDoorTime) {
        this.openDoorTime = openDoorTime;
    }
}
