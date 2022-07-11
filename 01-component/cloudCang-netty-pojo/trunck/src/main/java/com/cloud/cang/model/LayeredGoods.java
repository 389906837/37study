package com.cloud.cang.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Description: 分层商品集合
 * @Date: 13:27:37
 */
public class LayeredGoods {

    private List<TagModel> goodsList;   // 视觉盘货的商品集合
    private BigDecimal layeredWeight;     //分层重量
    private String cameraIp;            //摄像头Ip

    public List<TagModel> getGoodsList() {
        return goodsList;
    }

    public BigDecimal getLayeredWeight() {
        return layeredWeight;
    }

    public void setLayeredWeight(BigDecimal layeredWeight) {
        this.layeredWeight = layeredWeight;
    }

    public String getCameraIp() {
        return cameraIp;
    }

    public void setCameraIp(String cameraIp) {
        this.cameraIp = cameraIp;
    }

    public void setGoodsList(List<TagModel> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public String toString() {
        return "LayeredGoods{" +
                "goodsList=" + goodsList +
                ", layeredWeight=" + layeredWeight +
                ", cameraIp='" + cameraIp + '\'' +
                '}';
    }
}
