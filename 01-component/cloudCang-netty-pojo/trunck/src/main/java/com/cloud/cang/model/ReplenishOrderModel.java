package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @Description: 补货实时订单结果
 * @Author: zengzexiong
 * @Date: 2018年4月10日20:27:37
 */
public class ReplenishOrderModel extends SuperDto {

    private List<GoodModel> subGoodsList;   // 下架商品集合
    private List<GoodModel> addGoodsList;   // 上架商品集合
    private Integer subNum;                 // 下架商品总数量
    private Integer addNum;                 // 上架商品总数量

    public List<GoodModel> getSubGoodsList() {
        return subGoodsList;
    }

    public void setSubGoodsList(List<GoodModel> subGoodsList) {
        this.subGoodsList = subGoodsList;
    }

    public List<GoodModel> getAddGoodsList() {
        return addGoodsList;
    }

    public void setAddGoodsList(List<GoodModel> addGoodsList) {
        this.addGoodsList = addGoodsList;
    }

    public Integer getSubNum() {
        return subNum;
    }

    public void setSubNum(Integer subNum) {
        this.subNum = subNum;
    }

    public Integer getAddNum() {
        return addNum;
    }

    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }
}
