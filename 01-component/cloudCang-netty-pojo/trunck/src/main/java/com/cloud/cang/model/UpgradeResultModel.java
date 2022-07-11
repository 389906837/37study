package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 升级结果model
 * @Author: zengzexiong
 * @Date: 2018年6月21日09:08:55
 */
public class UpgradeResultModel extends SuperDto {
    private String upgradeId;       // 升级明细表ID
    private String success;         // 升级结果 0否 1是
    private String version;         // 升级包版本
    private String reason;          // 失败原因
    private String startTime;       // 开始升级时间
    private String endTime;         // 升级结束时间

    public String getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(String upgradeId) {
        this.upgradeId = upgradeId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "UpgradeResultModel{" +
                "upgradeId='" + upgradeId + '\'' +
                ", success='" + success + '\'' +
                ", version='" + version + '\'' +
                ", reason='" + reason + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
