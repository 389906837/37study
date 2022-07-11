package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 订单中的商品对象
 * @Author: zengzexiong
 * @Date: 2018年4月13日14:16:24
 */
public class GoodModel extends SuperDto {

    private String goodName;            // 商品名称
    private Integer goodsNumber;        // 商品数量
    private Integer discountType;       // 优惠类型
    private String goodImgUrl;          // 商品图片url
    private String goodPrice;           // 商品价格
    private Double preferen;            //

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public String getGoodImgUrl() {
        return goodImgUrl;
    }

    public void setGoodImgUrl(String goodImgUrl) {
        this.goodImgUrl = goodImgUrl;
    }

    public Double getPreferen() {
        return preferen;
    }

    public void setPreferen(Double preferen) {
        this.preferen = preferen;
    }
}
