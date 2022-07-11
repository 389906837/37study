package com.cloud.cang.api.antbox.dto;

/**
 * @author lwmuk
 * @version 1.0.0 2018-03-23
 */
public class BoxOpenedDto {

    private Long boxId;
    private Long openTime;

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Long openTime) {
        this.openTime = openTime;
    }
}
