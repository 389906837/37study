package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 视觉商品盘点入参信息
 * @Author: zhouhong
 * @Date: 2018/4/11 16:22
 */
public class RealTimeCommodityResult extends SuperDto {

    /* 商品ID */
    private String scommodityId;

    /* 商品编号 */
    private String scommodityCode;

    /* 商品名称 */
    private String scommodityName;

    /* 商品全名称 = 品牌+商品名+口味+规格+单位*/
    private String scommodityFullName;

    /* 相差 数量 */
    private Integer number;

    /* 商品销售单价 */
    private BigDecimal fcommodityPrice;

    /* 商品成本单价 */
    private BigDecimal fcostPrice;

    /* 商品成本进价税点 */
    private BigDecimal ftaxPoint;


    /* 商品图片 */
    private String scommodityImg;

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

    public BigDecimal getFcommodityPrice() {
        return fcommodityPrice;
    }

    public void setFcommodityPrice(BigDecimal fcommodityPrice) {
        this.fcommodityPrice = fcommodityPrice;
    }

    public BigDecimal getFcostPrice() {
        return fcostPrice;
    }

    public void setFcostPrice(BigDecimal fcostPrice) {
        this.fcostPrice = fcostPrice;
    }

    public BigDecimal getFtaxPoint() {
        return ftaxPoint;
    }

    public void setFtaxPoint(BigDecimal ftaxPoint) {
        this.ftaxPoint = ftaxPoint;
    }

    public String getScommodityImg() {
        return scommodityImg;
    }

    public void setScommodityImg(String scommodityImg) {
        this.scommodityImg = scommodityImg;
    }

    public String getScommodityFullName() {
        return scommodityFullName;
    }

    public void setScommodityFullName(String scommodityFullName) {
        this.scommodityFullName = scommodityFullName;
    }

    @Override
    public String toString() {
        return "RealTimeCommodityResult{" +
                "scommodityId='" + scommodityId + '\'' +
                ", scommodityCode='" + scommodityCode + '\'' +
                ", scommodityName='" + scommodityName + '\'' +
                ", scommodityFullName='" + scommodityFullName + '\'' +
                ", number=" + number +
                ", fcommodityPrice=" + fcommodityPrice +
                ", fcostPrice=" + fcostPrice +
                ", ftaxPoint=" + ftaxPoint +
                ", scommodityImg='" + scommodityImg + '\'' +
                '}';
    }
}
