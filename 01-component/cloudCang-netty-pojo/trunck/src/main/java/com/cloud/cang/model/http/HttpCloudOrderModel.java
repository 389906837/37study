package com.cloud.cang.model.http;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.model.GoodModel;

import java.util.List;

/**
 * @version 1.0
 * @Description: 短连接--关门盘货后生成的订单对象，先根据订单类型判断
 * @Author: zengzexiong
 * @Date: 2018年9月20日11:45:07
 */
public class HttpCloudOrderModel extends SuperDto {

    private Integer orderType = 30;                 // 订单类型 10 购物，20 补货，30游客
    private String orderNo;                         // 订单编号
    private Integer number = 0;                     // 商品总数量
    private Integer payType = 10;                   // 支付类型  10 自动扣款 20 手动支付
    private String actualPayMoney = "0";            // 实付金额
    private String discountedMoney = "0";           // 优惠金额
    private String amountPayMoney = "0";            // 订单总金额
    private List<GoodModel> goodsList;              // 具体订单商品

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
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
}
