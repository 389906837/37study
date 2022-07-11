package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @Description:补货单待补货员确认返回结果
 * @Author: zhouhong
 * @Date: 2018/4/13 12:02
 */
public class ReplenishmentConfirmResult extends SuperDto {
    private Integer upShelfTotal;//上架总数
    private Integer lowShelfTotal;//下架总数
    private Integer currentTotal;//当前总数
    private String deviceId;//设备Id
    private String deviceCode;//设备code
    private String userId;//用户Id
    private List<ReplenishmentConfirmCommodity> replenishmentConfirmCommodityList;


    @Override
    public String toString() {
        return "ReplenishmentConfirmResult{" +
                "upShelfTotal=" + upShelfTotal +
                ", lowShelfTotal=" + lowShelfTotal +
                ", currentTotal=" + currentTotal +
                ", deviceId='" + deviceId + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", userId='" + userId + '\'' +
                ", replenishmentConfirmCommodityList=" + replenishmentConfirmCommodityList +
                '}';
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUpShelfTotal() {
        return upShelfTotal;
    }

    public void setUpShelfTotal(Integer upShelfTotal) {
        this.upShelfTotal = upShelfTotal;
    }

    public Integer getLowShelfTotal() {
        return lowShelfTotal;
    }

    public void setLowShelfTotal(Integer lowShelfTotal) {
        this.lowShelfTotal = lowShelfTotal;
    }

    public Integer getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(Integer currentTotal) {
        this.currentTotal = currentTotal;
    }

    public List<ReplenishmentConfirmCommodity> getReplenishmentConfirmCommodityList() {
        return replenishmentConfirmCommodityList;
    }

    public void setReplenishmentConfirmCommodityList(List<ReplenishmentConfirmCommodity> replenishmentConfirmCommodityList) {
        this.replenishmentConfirmCommodityList = replenishmentConfirmCommodityList;
    }
}
