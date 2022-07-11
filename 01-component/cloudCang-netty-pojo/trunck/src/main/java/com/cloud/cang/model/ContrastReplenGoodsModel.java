package com.cloud.cang.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by YLF on 2019/8/3.
 */
public class ContrastReplenGoodsModel {
    private Map<String, Object> contrastMap;
    private BigDecimal openWeight;
    private BigDecimal realWeight;
    private String cameraIp;            //摄像头Ip
    private LayeredGoods openLayeredGoods;

    public Map<String, Object> getContrastMap() {
        return contrastMap;
    }

    public void setContrastMap(Map<String, Object> contrastMap) {
        this.contrastMap = contrastMap;
    }

    public BigDecimal getOpenWeight() {
        return openWeight;
    }

    public void setOpenWeight(BigDecimal openWeight) {
        this.openWeight = openWeight;
    }

    public BigDecimal getRealWeight() {
        return realWeight;
    }

    public void setRealWeight(BigDecimal realWeight) {
        this.realWeight = realWeight;
    }

    public String getCameraIp() {
        return cameraIp;
    }

    public void setCameraIp(String cameraIp) {
        this.cameraIp = cameraIp;
    }

    public LayeredGoods getOpenLayeredGoods() {
        return openLayeredGoods;
    }

    public void setOpenLayeredGoods(LayeredGoods openLayeredGoods) {
        this.openLayeredGoods = openLayeredGoods;
    }

    public boolean isIisShop() {
        return iisShop;
    }

    public void setIisShop(boolean iisShop) {
        this.iisShop = iisShop;
    }

    private boolean iisShop;//当前层是否购物 true:购物 false:误差范围内无购物
}
