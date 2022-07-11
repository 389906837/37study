package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Description: 订单model -- 盘货后生成的订单对象
 * @Author: zengzexiong
 * @Date: 2018年4月13日14:16:24
 */
public class OrderModel extends SuperDto {

    private String orderNo;                 // 订单编号
    private Integer number;                 // 商品总数量
    private Integer payType;                 // 支付类型  10 自动扣款 20 手动支付
    private String actualPayMoney;          // 实付金额
    private String discountedMoney;         // 优惠金额
    private String amountPayMoney;          // 订单总金额
    private List<GoodModel> goodsList;      // 具体订单商品
    private BigDecimal weightTotal;         //总重量


    public BigDecimal getWeightTotal() {
        return weightTotal;
    }

    public void setWeightTotal(BigDecimal weightTotal) {
        this.weightTotal = weightTotal;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getActualPayMoney() {
        return actualPayMoney;
    }

    public void setActualPayMoney(String actualPayMoney) {
        this.actualPayMoney = actualPayMoney;
    }

    public String getDiscountedMoney() {
        return discountedMoney;
    }

    public void setDiscountedMoney(String discountedMoney) {
        this.discountedMoney = discountedMoney;
    }

    public String getAmountPayMoney() {
        return amountPayMoney;
    }

    public void setAmountPayMoney(String amountPayMoney) {
        this.amountPayMoney = amountPayMoney;
    }

    public List<GoodModel> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodModel> goodsList) {
        this.goodsList = goodsList;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderNo='" + orderNo + '\'' +
                ", number=" + number +
                ", payType=" + payType +
                ", actualPayMoney='" + actualPayMoney + '\'' +
                ", discountedMoney='" + discountedMoney + '\'' +
                ", amountPayMoney='" + amountPayMoney + '\'' +
                ", goodsList=" + goodsList +
                ", weightTotal=" + weightTotal +
                '}';
    }
}
