package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 苏宁棚格图商品信息
 * @Author: zengzexiong
 * @Date: 2018年9月4日14:28:46
 */
public class SuNingShedGoodModel extends SuperDto {
    private String thirdSkuCode;    // 第三方视觉商品编号
    private String visualId;        // 视觉库中视觉商品ID
    private String price;           // 第三方视觉商品价格
    private String name;            // 第三方视觉商品名称
    private String unitWeight;      // 第三方视觉商品重量

    private String id;              // 第三方商户设备SKU库ID（不用填）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThirdSkuCode() {
        return thirdSkuCode;
    }

    public void setThirdSkuCode(String thirdSkuCode) {
        this.thirdSkuCode = thirdSkuCode;
    }

    public String getVisualId() {
        return visualId;
    }

    public void setVisualId(String visualId) {
        this.visualId = visualId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(String unitWeight) {
        this.unitWeight = unitWeight;
    }

    @Override
    public String toString() {
        return "SuNingShedGoodModel{" +
                "thirdSkuCode='" + thirdSkuCode + '\'' +
                ", visualId='" + visualId + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", unitWeight='" + unitWeight + '\'' +
                '}';
    }
}
