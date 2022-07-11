package com.cloud.cang.jy;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @description: 异常订单生成 Dto
 * @author:严凌峰
 * @time:
 */
public class ExceptionOrderCommodityDto extends SuperDto {
     /*---------------------------------必传--------------------------------------------*/

    private String scommodityId;    /* 商品ID */

    private String scommodityName;  /* 商品名称 */

    private String scommodityCode; /* 商品编号 */

    private BigDecimal fcommodityPrice;  /* 商品单价 */

    private BigDecimal fcostPrice;/* 单商品成本价 */

    private BigDecimal ftaxPoint;   /* 商品成本进价税点 */

    private Integer forderCount;    /* 订单数量 */

    private BigDecimal fcommodityAmount;/*商品总额*/


    @Override
    public String toString() {
        return "ExceptionOrderCommodityDto{" +
                "scommodityId='" + scommodityId + '\'' +
                ", scommodityCode='" + scommodityCode + '\'' +
                ", scommodityName='" + scommodityName + '\'' +
                ", fcommodityPrice=" + fcommodityPrice +
                ", ftaxPoint=" + ftaxPoint +
                ", fcostPrice=" + fcostPrice +
                ", forderCount=" + forderCount +
                ", fcommodityAmount=" + fcommodityAmount +
                '}';
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

    public BigDecimal getFcommodityPrice() {
        return fcommodityPrice;
    }

    public void setFcommodityPrice(BigDecimal fcommodityPrice) {
        this.fcommodityPrice = fcommodityPrice;
    }

    public Integer getForderCount() {
        return forderCount;
    }

    public void setForderCount(Integer forderCount) {
        this.forderCount = forderCount;
    }

    public BigDecimal getFcommodityAmount() {
        return fcommodityAmount;
    }

    public void setFcommodityAmount(BigDecimal fcommodityAmount) {
        this.fcommodityAmount = fcommodityAmount;
    }


    public BigDecimal getFtaxPoint() {
        return ftaxPoint;
    }

    public void setFtaxPoint(BigDecimal ftaxPoint) {
        this.ftaxPoint = ftaxPoint;
    }

    public BigDecimal getFcostPrice() {
        return fcostPrice;
    }

    public void setFcostPrice(BigDecimal fcostPrice) {
        this.fcostPrice = fcostPrice;
    }
}
