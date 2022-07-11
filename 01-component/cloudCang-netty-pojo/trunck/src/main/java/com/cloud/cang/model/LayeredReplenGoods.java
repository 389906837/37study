package com.cloud.cang.model;

import java.math.BigDecimal;

/**
 * 补货称重返回商品信息
 */
public class LayeredReplenGoods {
    private String goodName;                     // 商品名称
    private Integer goodsOriginalNumber;         // 商品原始数量
    private Integer goodsCurrentNumber;          // 商品当前数量
    private String goodsNumberDiff;             // 商品数量差
    private String goodImgUrl;                   // 商品图片url

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Integer getGoodsOriginalNumber() {
        return goodsOriginalNumber;
    }

    public void setGoodsOriginalNumber(Integer goodsOriginalNumber) {
        this.goodsOriginalNumber = goodsOriginalNumber;
    }

    public Integer getGoodsCurrentNumber() {
        return goodsCurrentNumber;
    }

    public void setGoodsCurrentNumber(Integer goodsCurrentNumber) {
        this.goodsCurrentNumber = goodsCurrentNumber;
    }

    public String getGoodsNumberDiff() {
        return goodsNumberDiff;
    }

    public void setGoodsNumberDiff(String goodsNumberDiff) {
        this.goodsNumberDiff = goodsNumberDiff;
    }

    public String getGoodImgUrl() {
        return goodImgUrl;
    }

    public void setGoodImgUrl(String goodImgUrl) {
        this.goodImgUrl = goodImgUrl;
    }

    @Override
    public String toString() {
        return "LayeredReplenGoods{" +
                "goodName='" + goodName + '\'' +
                ", goodsOriginalNumber=" + goodsOriginalNumber +
                ", goodsCurrentNumber=" + goodsCurrentNumber +
                ", goodsNumberDiff=" + goodsNumberDiff +
                ", goodImgUrl='" + goodImgUrl + '\'' +
                '}';
    }
}
