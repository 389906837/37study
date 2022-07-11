package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 设备音量调节值
 * @Author: zengzexiong
 * @Date: 2018年9月19日15:55:15
 */
public class VolumeModel extends SuperDto {
    private Integer volume;         // 音量值大小
    private String detailId;        // 升级记录明细表ID（记录升级操作）

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "VolumeModel{" +
                "volume=" + volume +
                ", detailId='" + detailId + '\'' +
                '}';
    }
}
