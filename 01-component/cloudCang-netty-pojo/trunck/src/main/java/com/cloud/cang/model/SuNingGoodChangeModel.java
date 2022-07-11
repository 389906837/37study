package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 苏宁商品数目变化模型
 * @Author: zengzexiong
 * @Date: 2018年9月4日14:28:46
 */
public class SuNingGoodChangeModel extends SuperDto {

    private String skuCode;         // 第三方视觉商品编号
    private String visualId;        // 视觉商品库视觉商品ID
    private String increment;       // 增加数量
    private String decrement;       // 减少数量
    private String totalAmount;     // 剩余商品数量

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getVisualId() {
        return visualId;
    }

    public void setVisualId(String visualId) {
        this.visualId = visualId;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public String getDecrement() {
        return decrement;
    }

    public void setDecrement(String decrement) {
        this.decrement = decrement;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "SuNingGoodChangeModel{" +
                "skuCode='" + skuCode + '\'' +
                ", visualId='" + visualId + '\'' +
                ", increment='" + increment + '\'' +
                ", decrement='" + decrement + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }
}
