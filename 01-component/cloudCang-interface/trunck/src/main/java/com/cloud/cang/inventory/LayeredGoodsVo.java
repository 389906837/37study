package com.cloud.cang.inventory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by YLF on 2019/7/3.
 */
public class LayeredGoodsVo {

    private List<CommodityVo> commodityVos;   // 视觉盘货的商品集合
    private BigDecimal layeredWeight;     //分层重量
    private String cameraIp;            //摄像头Ip

    public List<CommodityVo> getCommodityVos() {
        return commodityVos;
    }

    public void setCommodityVos(List<CommodityVo> commodityVos) {
        this.commodityVos = commodityVos;
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

    @Override
    public String toString() {
        return "LayeredGoodsVo{" +
                "commodityVos=" + commodityVos +
                ", layeredWeight=" + layeredWeight +
                ", cameraIp='" + cameraIp + '\'' +
                '}';
    }
}
