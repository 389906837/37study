package com.cloud.cang.wap.sm.vo;

import java.util.List;

/**
 * Created by yan on 2019/2/19.
 */
public class DeviceStockVo {
    private List<DeviceStockList> deviceStockVos;
    private Integer deviceStockAmount;

    public List<DeviceStockList> getDeviceStockVos() {
        return deviceStockVos;
    }

    public void setDeviceStockVos(List<DeviceStockList> deviceStockVos) {
        this.deviceStockVos = deviceStockVos;
    }

    public Integer getDeviceStockAmount() {
        if (null != deviceStockVos && !deviceStockVos.isEmpty()) {
            Integer temp = 0;
            for (DeviceStockList deviceStockList : deviceStockVos) {//其内部实质上还是调用了迭代器遍历方式，这种循环方式还有其他限制，不建议使用。
                temp += deviceStockList.getIstock();
            }
            return temp;
        }
        return 0;
    }

    public void setDeviceStockAmount(Integer deviceStockAmount) {
        this.deviceStockAmount = deviceStockAmount;
    }
}
