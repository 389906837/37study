package com.cloud.cang.wap.om.vo;

import com.cloud.cang.inventory.RealTimeCommodityResult;

import java.io.Serializable;
import java.util.List;


/**
 * @version 1.0
 * @Description: 购物车
 * @Author: zengzeixong
 * @Date: 2018年11月27日09:36:44
 */
public class ShoppingCartDoamin implements Serializable {

    private List<RealTimeCommodityResult> commodityInfoList;    // 商品信息（购物专用）
    private Integer totalNum;                                   // 商品数量（购物专用）
    private String discountedPrice;                             // 优惠金额（购物专用）
    private String actuallyPaid;                                // 实付金额（购物专用）

    private List<RealTimeCommodityResult> addCommodityInfoList; // 上架商品（补货专用）
    private List<RealTimeCommodityResult> subCommodityInfoList; // 下架商品（补货专用）
    private Integer shelfNum;                                   // 上架数量（补货专用）
    private Integer obtainedNum;                                   // 下架数量（补货专用）


    public List<RealTimeCommodityResult> getAddCommodityInfoList() {
        return addCommodityInfoList;
    }

    public void setAddCommodityInfoList(List<RealTimeCommodityResult> addCommodityInfoList) {
        this.addCommodityInfoList = addCommodityInfoList;
    }

    public List<RealTimeCommodityResult> getSubCommodityInfoList() {
        return subCommodityInfoList;
    }

    public void setSubCommodityInfoList(List<RealTimeCommodityResult> subCommodityInfoList) {
        this.subCommodityInfoList = subCommodityInfoList;
    }

    public List<RealTimeCommodityResult> getCommodityInfoList() {
        return commodityInfoList;
    }

    public void setCommodityInfoList(List<RealTimeCommodityResult> commodityInfoList) {
        this.commodityInfoList = commodityInfoList;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String getActuallyPaid() {
        return actuallyPaid;
    }

    public void setActuallyPaid(String actuallyPaid) {
        this.actuallyPaid = actuallyPaid;
    }


    public Integer getShelfNum() {
        return shelfNum;
    }

    public void setShelfNum(Integer shelfNum) {
        this.shelfNum = shelfNum;
    }

    public Integer getObtainedNum() {
        return obtainedNum;
    }

    public void setObtainedNum(Integer obtainedNum) {
        this.obtainedNum = obtainedNum;
    }

    @Override
    public String toString() {
        return "ShoppingCartDomain{" +
                "commodityInfoList=" + commodityInfoList +
                ", totalNum=" + totalNum +
                ", discountedPrice='" + discountedPrice + '\'' +
                ", actuallyPaid='" + actuallyPaid + '\'' +
                ", addCommodityInfoList=" + addCommodityInfoList +
                ", subCommodityInfoList=" + subCommodityInfoList +
                ", shelfNum=" + shelfNum +
                ", obtainedNum=" + obtainedNum +
                '}';
    }


}
