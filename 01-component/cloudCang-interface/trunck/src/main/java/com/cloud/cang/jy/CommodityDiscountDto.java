package com.cloud.cang.jy;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 订单优惠计算中的商品信息  内部服务之间的model
 * @Author: zengzexiong
 * @Date: 2017年12月28日15:43:31
 */
public class CommodityDiscountDto extends SuperDto {

    /* ----------必填参数开始 ----------*/
    private String scommodityCode;  //商品编号

    private BigDecimal fcommodityPrice;     //该类商品销售单价

    private Integer forderCount;    //订单中该类商品数量

    private String ssmallCategoryId;    /* 商品小类ID */

    private String sbrandId;    /* 商品品牌ID */

    /* 商品全名称 = 品牌+商品名+口味+规格+单位*/
    private String scommodityFullName;

    private String scommodityId;    /* 商品ID */

    private String scommodityName;  /* 商品名称 */

    /* 商品图片 */
    private String scommodityImg;

    private BigDecimal fcostAmount;//商品成本价

    /* 商品成本进价税点 */
    private BigDecimal ftaxPoint;

    /* ----------必填参数结束 ----------*/

    private BigDecimal fcommodityAmount;    //该类商品的总金额

    private BigDecimal fdiscountAmount;     //该类商品优惠总额

    private BigDecimal fotherDiscountAmount;//订单其他优惠金额

    private BigDecimal ffirstDiscountAmount;    //该类商品首单优惠金额

    private BigDecimal fpromotionDiscountAmount;    //该类商品促销优惠金额

    private BigDecimal fcouponDeductionAmount;      //该类商品优惠券抵扣金额

    private BigDecimal fpointDiscountAmount;    //该类商品积分优惠金额

    private BigDecimal factualAmount;   //该类商品实付金额

    private BigDecimal ipoint;      //该类商品抵扣积分

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

    public BigDecimal getFotherDiscountAmount() {
        return fotherDiscountAmount;
    }

    public void setFotherDiscountAmount(BigDecimal fotherDiscountAmount) {
        this.fotherDiscountAmount = fotherDiscountAmount;
    }

    public BigDecimal getFcostAmount() {
        return fcostAmount;
    }

    public void setFcostAmount(BigDecimal fcostAmount) {
        this.fcostAmount = fcostAmount;
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

    public BigDecimal getFcommodityPrice() {
        return fcommodityPrice;
    }

    public void setFcommodityPrice(BigDecimal fcommodityPrice) {
        this.fcommodityPrice = fcommodityPrice;
    }


    public BigDecimal getFcommodityAmount() {
        return fcommodityAmount;
    }

    public void setFcommodityAmount(BigDecimal fcommodityAmount) {
        this.fcommodityAmount = fcommodityAmount;
    }

    public BigDecimal getFdiscountAmount() {
        return fdiscountAmount;
    }

    public void setFdiscountAmount(BigDecimal fdiscountAmount) {
        this.fdiscountAmount = fdiscountAmount;
    }

    public BigDecimal getFfirstDiscountAmount() {
        return ffirstDiscountAmount;
    }

    public void setFfirstDiscountAmount(BigDecimal ffirstDiscountAmount) {
        this.ffirstDiscountAmount = ffirstDiscountAmount;
    }

    public BigDecimal getFpromotionDiscountAmount() {
        return fpromotionDiscountAmount;
    }

    public void setFpromotionDiscountAmount(BigDecimal fpromotionDiscountAmount) {
        this.fpromotionDiscountAmount = fpromotionDiscountAmount;
    }

    public BigDecimal getFcouponDeductionAmount() {
        return fcouponDeductionAmount;
    }

    public void setFcouponDeductionAmount(BigDecimal fcouponDeductionAmount) {
        this.fcouponDeductionAmount = fcouponDeductionAmount;
    }

    public BigDecimal getFpointDiscountAmount() {
        return fpointDiscountAmount;
    }

    public void setFpointDiscountAmount(BigDecimal fpointDiscountAmount) {
        this.fpointDiscountAmount = fpointDiscountAmount;
    }

    public BigDecimal getFactualAmount() {
        return factualAmount;
    }

    public void setFactualAmount(BigDecimal factualAmount) {
        this.factualAmount = factualAmount;
    }

    public BigDecimal getIpoint() {
        return ipoint;
    }

    public void setIpoint(BigDecimal ipoint) {
        this.ipoint = ipoint;
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
        return "CommodityDiscountDto{" +
                "scommodityCode='" + scommodityCode + '\'' +
                ", fcommodityPrice=" + fcommodityPrice +
                ", forderCount=" + forderCount +
                ", ssmallCategoryId='" + ssmallCategoryId + '\'' +
                ", sbrandId='" + sbrandId + '\'' +
                ", scommodityFullName='" + scommodityFullName + '\'' +
                ", scommodityId='" + scommodityId + '\'' +
                ", scommodityName='" + scommodityName + '\'' +
                ", scommodityImg='" + scommodityImg + '\'' +
                ", fcostAmount=" + fcostAmount +
                ", ftaxPoint=" + ftaxPoint +
                ", fcommodityAmount=" + fcommodityAmount +
                ", fdiscountAmount=" + fdiscountAmount +
                ", fotherDiscountAmount=" + fotherDiscountAmount +
                ", ffirstDiscountAmount=" + ffirstDiscountAmount +
                ", fpromotionDiscountAmount=" + fpromotionDiscountAmount +
                ", fcouponDeductionAmount=" + fcouponDeductionAmount +
                ", fpointDiscountAmount=" + fpointDiscountAmount +
                ", factualAmount=" + factualAmount +
                ", ipoint=" + ipoint +
                '}';
    }
}
