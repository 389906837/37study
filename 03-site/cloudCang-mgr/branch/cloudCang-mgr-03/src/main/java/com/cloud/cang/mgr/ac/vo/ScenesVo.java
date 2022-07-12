package com.cloud.cang.mgr.ac.vo;

import com.cloud.cang.model.ac.ActivityConf;

import java.util.Arrays;

/**
 * @version 1.0
 * @Description: 场景活动保存Vo
 * @Author: zhouhong
 * @Date: 2018/2/10 16:06
 */
public class ScenesVo extends ActivityConf {

    private Integer irangeCommodity;//活动商品类型
    private Integer irangeDevice;//活动设备类型

    private String sdescription;  /* 描述 */

    private String[] deviceIds;//设备Id
    private String[] commodityIds;//商品Id

    public Integer getIrangeCommodity() {
        return irangeCommodity;
    }

    public void setIrangeCommodity(Integer irangeCommodity) {
        this.irangeCommodity = irangeCommodity;
    }

    public Integer getIrangeDevice() {
        return irangeDevice;
    }

    public void setIrangeDevice(Integer irangeDevice) {
        this.irangeDevice = irangeDevice;
    }

    public String[] getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String[] deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String[] getCommodityIds() {
        return commodityIds;
    }

    public void setCommodityIds(String[] commodityIds) {
        this.commodityIds = commodityIds;
    }

    @Override
    public String getSdescription() {
        return sdescription;
    }

    @Override
    public void setSdescription(String sdescription) {
        this.sdescription = sdescription;
    }

    @Override
    public String toString() {
        return "ScenesVo{" +
                "irangeCommodity=" + irangeCommodity +
                ", irangeDevice=" + irangeDevice +
                ", sdescription='" + sdescription + '\'' +
                ", deviceIds=" + Arrays.toString(deviceIds) +
                ", commodityIds=" + Arrays.toString(commodityIds) +
                '}';
    }
}
