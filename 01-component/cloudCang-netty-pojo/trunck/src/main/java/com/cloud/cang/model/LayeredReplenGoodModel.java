package com.cloud.cang.model;

import java.util.List;

/**
 * 补货称重返回信息
 */
public class LayeredReplenGoodModel {
    private List<LayeredReplenGoods> layeredReplenGoods;//补货称重返回商品信息
    private String weightDiff;                      //重量差
    private boolean iisReplenCorrect;                   //补货结果是否正确

    public boolean isIisReplenCorrect() {
        return iisReplenCorrect;
    }

    public void setIisReplenCorrect(boolean iisReplenCorrect) {
        this.iisReplenCorrect = iisReplenCorrect;
    }

    public String getWeightDiff() {
        return weightDiff;
    }

    public void setWeightDiff(String weightDiff) {
        this.weightDiff = weightDiff;
    }

    public List<LayeredReplenGoods> getLayeredReplenGoods() {
        return layeredReplenGoods;
    }

    public void setLayeredReplenGoods(List<LayeredReplenGoods> layeredReplenGoods) {
        this.layeredReplenGoods = layeredReplenGoods;
    }

    @Override
    public String toString() {
        return "LayeredReplenGoodModel{" +
                "layeredReplenGoods=" + layeredReplenGoods +
                ", weightDiff=" + weightDiff +
                ", iisReplenCorrect=" + iisReplenCorrect +
                '}';
    }
}
