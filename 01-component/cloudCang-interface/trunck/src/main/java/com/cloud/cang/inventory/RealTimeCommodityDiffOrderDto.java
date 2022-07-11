package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;
import java.util.List;

/**
 * 重力视觉柜子商品差值实时订单
 *
 * @author zengzexiong
 * @version 1.0
 */
public class RealTimeCommodityDiffOrderDto extends SuperDto {

    private String deviceId;//设备编号
    private Integer isourceClientType;//来源客户端 10=传统 20=RFID射频 30=视觉 40=重力视觉柜子 50=云端识别柜子
    private String memberId;//会员ID 购物
    private List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList; //盘点商品集合

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getIsourceClientType() {
        return isourceClientType;
    }

    public void setIsourceClientType(Integer isourceClientType) {
        this.isourceClientType = isourceClientType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<InventoryCommodityDiffVo> getInventoryCommodityDiffVoList() {
        return inventoryCommodityDiffVoList;
    }

    public void setInventoryCommodityDiffVoList(List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList) {
        this.inventoryCommodityDiffVoList = inventoryCommodityDiffVoList;
    }

    @Override
    public String toString() {
        return "RealTimeCommodityDiffOrderDto{" +
                "deviceId='" + deviceId + '\'' +
                ", isourceClientType=" + isourceClientType +
                ", memberId='" + memberId + '\'' +
                ", inventoryCommodityDiffVoList=" + inventoryCommodityDiffVoList +
                '}';
    }
}
