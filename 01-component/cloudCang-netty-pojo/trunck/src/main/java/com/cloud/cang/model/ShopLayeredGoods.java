package com.cloud.cang.model;

import java.util.List;

/**
 * @version 1.0
 * @Description: 购物分层商品
 * @Date: 13:27:37
 */
public class ShopLayeredGoods {
    private List<LayeredGoods> layeredGoodsList; //设备视觉分层集合
    private String openDoorType;       // 开门类型
    private List<LayeredWeight> layeredWeightList;//设备重力分层集合

    public List<LayeredWeight> getLayeredWeightList() {
        return layeredWeightList;
    }

    public void setLayeredWeightList(List<LayeredWeight> layeredWeightList) {
        this.layeredWeightList = layeredWeightList;
    }

    public List<LayeredGoods> getLayeredGoodsList() {
        return layeredGoodsList;
    }

    public String getOpenDoorType() {
        return openDoorType;
    }

    public void setOpenDoorType(String openDoorType) {
        this.openDoorType = openDoorType;
    }

    public void setLayeredGoodsList(List<LayeredGoods> layeredGoodsList) {
        this.layeredGoodsList = layeredGoodsList;
    }
}
