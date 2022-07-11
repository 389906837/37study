package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.util.List;


/**
 * 开门前盘货对象
 *
 * @author zengzexiong
 * @version 1.0
 */
public class BeforeOpenDoorInventoryDto extends SuperDto {
    private static final long serialVersionUID = -2913572903105378881L;

    private String deviceId;        //设备编号
    private List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList;    //盘点商品集合

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<InventoryCommodityDiffVo> getInventoryCommodityDiffVoList() {
        return inventoryCommodityDiffVoList;
    }

    public void setInventoryCommodityDiffVoList(List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList) {
        this.inventoryCommodityDiffVoList = inventoryCommodityDiffVoList;
    }

    @Override
    public String toString() {
        return "BeforeOpenDoorInventoryDto{" +
                ", deviceId='" + deviceId + '\'' +
                ", inventoryCommodityDiffVoList=" + inventoryCommodityDiffVoList +
                '}';
    }
}
