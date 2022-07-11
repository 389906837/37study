package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Description: 商品集合
 * @Author: zengzexiong
 * @Date: 2018年4月10日20:27:37
 */
public class Goods extends SuperDto {

    private String openDoorType;       // 开门类型
    private List<TagModel> goodsList;   // 视觉盘货的商品集合
    private BigDecimal totalWeight;     //总重量


    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

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
