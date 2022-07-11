package com.cloud.cang.api.sp.vo;

import java.util.List;

/**
 * Created by Alex on 2018/5/19.
 */
public class OrderWapVo {
    private String amount;//订单总额
    private List<CommodityVo> commodityVoList; // 订单商品对象集合

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<CommodityVo> getCommodityVoList() {
        return commodityVoList;
    }

    public void setCommodityVoList(List<CommodityVo> commodityVoList) {
        this.commodityVoList = commodityVoList;
    }
}
