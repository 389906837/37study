package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;


/**
 * 商品库存差
 */
public class CommodityDiffVo extends SuperDto {

    /* 商品ID */
    private String scommodityId;

    /* 商品编号 */
    private String scommodityCode;

    /* 商品名称 */
    private String scommodityName;

    /* 商品全名称 = 品牌+商品名+口味+规格+单位*/
    private String scommodityFullName;

    /* 商品销售单价 */
    private BigDecimal fcommodityPrice;

    /* 商品成本单价 */
    private BigDecimal fcostPrice;

    /* 商品成本进价税点 */
    private BigDecimal ftaxPoint;

    /* 商品小类ID */
    private String ssmallCategoryId;

    /* 商品图片 */
    private String scommodityImg;

    /* 商品品牌ID */
    private String sbrandId;

    /* 最小销售包装单位 */
    private String spackageUnit;

    /* 商品状态 10=正常 20=失效*/
    private Integer istatus;

    /* 视觉识别编号 */
    private String svrCode;

    /* 商品保质期 */
    private String sshelfLife;

    /* 相差 数量 */
    private Integer number;

    /* 当前库存 */
    private Integer currStock;

    /* 相差类型 10 增加 20 减少 */
    private Integer itype;

    /* rfid识别码集合 多个用，隔开 */
    private String rfidList;

    private BigDecimal iweigth;//重量

    private BigDecimal icommodityFloat;//商品浮动值


    public BigDecimal getIcommodityFloat() {
        return icommodityFloat;
    }

    public void setIcommodityFloat(BigDecimal icommodityFloat) {
        this.icommodityFloat = icommodityFloat;
    }

    public BigDecimal getIweigth() {
        return iweigth;
    }

    public void setIweigth(BigDecimal iweigth) {
        this.iweigth = iweigth;
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

    public String getSvrCode() {
        return svrCode;
    }

    public void setSvrCode(String svrCode) {
        this.svrCode = svrCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getItype() {
        return itype;
    }

    public void setItype(Integer itype) {
        this.itype = itype;
    }

    public String getRfidList() {
        return rfidList;
    }

    public void setRfidList(String rfidList) {
        this.rfidList = rfidList;
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

    public String getSshelfLife() {
        return sshelfLife;
    }

    public void setSshelfLife(String sshelfLife) {
        this.sshelfLife = sshelfLife;
    }

    public String getSpackageUnit() {
        return spackageUnit;
    }

    public void setSpackageUnit(String spackageUnit) {
        this.spackageUnit = spackageUnit;
    }

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
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

    public Integer getCurrStock() {
        return currStock;
    }

    public void setCurrStock(Integer currStock) {
        this.currStock = currStock;
    }


    @Override
    public String toString() {
        return "CommodityDiffVo{" +
                "scommodityId='" + scommodityId + '\'' +
                ", scommodityCode='" + scommodityCode + '\'' +
                ", scommodityName='" + scommodityName + '\'' +
                ", scommodityFullName='" + scommodityFullName + '\'' +
                ", fcommodityPrice=" + fcommodityPrice +
                ", fcostPrice=" + fcostPrice +
                ", ftaxPoint=" + ftaxPoint +
                ", ssmallCategoryId='" + ssmallCategoryId + '\'' +
                ", scommodityImg='" + scommodityImg + '\'' +
                ", sbrandId='" + sbrandId + '\'' +
                ", spackageUnit='" + spackageUnit + '\'' +
                ", istatus=" + istatus +
                ", svrCode='" + svrCode + '\'' +
                ", sshelfLife='" + sshelfLife + '\'' +
                ", number=" + number +
                ", currStock=" + currStock +
                ", itype=" + itype +
                ", rfidList='" + rfidList + '\'' +
                ", iweigth=" + iweigth +
                ", icommodityFloat=" + icommodityFloat +
                '}';
    }
}
