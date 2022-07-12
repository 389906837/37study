package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.generic.GenericEntity;

import java.util.List;

public class Goods extends GenericEntity {
    private String openDoorType;       // 开门类型
    private List<TagModel> goodsList;   // 视觉盘货的商品集合

    public String getOpenDoorType() {
        return openDoorType;
    }

    public void setOpenDoorType(String openDoorType) {
        this.openDoorType = openDoorType;
    }

    public List<TagModel> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<TagModel> goodsList) {
        this.goodsList = goodsList;
    }
}
