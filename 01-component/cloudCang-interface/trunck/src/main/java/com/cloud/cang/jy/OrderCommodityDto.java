package com.cloud.cang.jy;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @Description: 创建订单商品明细 Dto
 * @Author: yanlingfeng
 */
public class OrderCommodityDto extends SuperDto {
    /*---------------------------------必传--------------------------------------------*/

    private String scommodityId;    /* 商品ID */

    private Integer forderCount;    /* 订单数量 */

    private BigDecimal fcommodityPrice;  /* 商品单价 */

    private BigDecimal ftaxPoint; /* 商品进价税点 */

    private String scommodityCode; /* 商品编号 */

    private String scommodityName;  /* 商品名称 */

    private String ssmallCategoryId;    /* 商品小类ID */

    private String sbrandId;    /* 商品品牌ID */

    private BigDecimal fcostAmount;/* 单商品成本价 */

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

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }

    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    public String getScommodityName() {
        return scommodityName;
    }

    public void setScommodityName(String scommodityName) {
        this.scommodityName = scommodityName;
    }

    public String getSsmallCategoryId() {
        return ssmallCategoryId;
    }

    public void setSsmallCategoryId(String ssmallCategoryId) {
        this.ssmallCategoryId = ssmallCategoryId;
    }

    public String getSbrandId() {
        return sbrandId;
    }

    public void setSbrandId(String sbrandId) {
        this.sbrandId = sbrandId;
    }

    public BigDecimal getFcostAmount() {
        return fcostAmount;
    }

    public void setFcostAmount(BigDecimal fcostAmount) {
        this.fcostAmount = fcostAmount;
    }

    public BigDecimal getFtaxPoint() {
        return ftaxPoint;
    }

    public void setFtaxPoint(BigDecimal ftaxPoint) {
        this.ftaxPoint = ftaxPoint;
    }

    @Override
    public String toString() {
        return "OrderCommodityDto{" +
                "scommodityId='" + scommodityId + '\'' +
                ", forderCount=" + forderCount +
                ", fcommodityPrice=" + fcommodityPrice +
                ", ftaxPoint=" + ftaxPoint +
                ", scommodityCode='" + scommodityCode + '\'' +
                ", scommodityName='" + scommodityName + '\'' +
                ", ssmallCategoryId='" + ssmallCategoryId + '\'' +
                ", sbrandId='" + sbrandId + '\'' +
                ", fcostAmount=" + fcostAmount +
                '}';
    }

/*---------------------------------组装计算优惠值(传参不用填)--------------------------------------------*/


}