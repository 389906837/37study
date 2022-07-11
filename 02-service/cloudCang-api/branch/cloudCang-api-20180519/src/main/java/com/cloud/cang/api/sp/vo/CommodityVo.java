package com.cloud.cang.api.sp.vo;

/**
 * Created by Alex on 2018/5/21.
 */
public class CommodityVo {
    private String url;         // 商品图片地址
    private String name;        // 商品名称
    private String price;       // 商品价格
    private String num;         // 商品数量

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
