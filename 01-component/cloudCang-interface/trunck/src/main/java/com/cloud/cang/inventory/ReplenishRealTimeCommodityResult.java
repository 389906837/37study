package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

/**
 * 盘点操作参数
 * @author zengzexiong
 * @version 1.0
 */
public class ReplenishRealTimeCommodityResult extends SuperDto {

    private String scommodityId;        // 商品ID
    private String scommodityCode;      // 商品编号
    private String scommodityName;      // 商品名称
    /* 商品全名称 = 品牌+商品名+口味+规格+单位*/
    private String scommodityFullName;
    private String scommodityUrl;       // 图片地址
    private Integer number;             // 相差 数量

    public String getScommodityUrl() {
        return scommodityUrl;
    }

    public void setScommodityUrl(String scommodityUrl) {
        this.scommodityUrl = scommodityUrl;
    }

    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }

    public String getScommodityName() {
        return scommodityName;
    }

    public void setScommodityName(String scommodityName) {
        this.scommodityName = scommodityName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getScommodityFullName() {
        return scommodityFullName;
    }

    public void setScommodityFullName(String scommodityFullName) {
        this.scommodityFullName = scommodityFullName;
    }

    @Override
    public String toString() {
        return "ReplenishRealTimeCommodityResult{" +
                "scommodityId='" + scommodityId + '\'' +
                ", scommodityCode='" + scommodityCode + '\'' +
                ", scommodityName='" + scommodityName + '\'' +
                ", scommodityFullName='" + scommodityFullName + '\'' +
                ", scommodityUrl='" + scommodityUrl + '\'' +
                ", number=" + number +
                '}';
    }
}
