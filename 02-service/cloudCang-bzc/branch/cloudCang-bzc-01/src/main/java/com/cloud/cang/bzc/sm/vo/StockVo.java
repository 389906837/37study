package com.cloud.cang.bzc.sm.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 设备商品库存VO
 * @Author: zhouhong
 * @Date: 2018/4/19 21:28
 */
public class StockVo implements Serializable {
    private String svrCode;//视觉商品编号
    private Integer istock;//库存数量
    /* 商品编号 */
    private String scode;
    /*商品ID*/
    private String id;
    /* 商品名称 */
    private String sname;
    /* 成本价 */
    private BigDecimal fcostPrice;
    /* 销售价 */
    private BigDecimal fsalePrice;
    /* 商品状态
            10=正常
            20=下架 */
    private Integer istatus;
    /* 最小销售包装单位 */
    private String spackageUnit;
    /* 品牌ID */
    private String sbrandId;
    /* 品牌名称 */
    private String sbrandName;
    /* 口味 */
    private String staste;
    /* 规格/重量 */
    private Integer ispecWeight;
    /* 规格单位 */
    private String sspecUnit;

    /* 小类ID */
    private String ssmallCategoryId;
    /* 商品图片 */
    private String scommodityImg;
    /* 保质期类型
            10=天
            20=月 */
    private Integer ilifeType;
    /* 保质期 */
    private Integer ishelfLife;

    /* 商户ID */
    private String smerchantId;
    /* 商户编号 */
    private String smerchantCode;


    public String getSvrCode() {
        return svrCode;
    }

    public void setSvrCode(String svrCode) {
        this.svrCode = svrCode;
    }

    public Integer getIstock() {
        return istock;
    }

    public void setIstock(Integer istock) {
        this.istock = istock;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public BigDecimal getFcostPrice() {
        return fcostPrice;
    }

    public void setFcostPrice(BigDecimal fcostPrice) {
        this.fcostPrice = fcostPrice;
    }

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    public String getSpackageUnit() {
        return spackageUnit;
    }

    public void setSpackageUnit(String spackageUnit) {
        this.spackageUnit = spackageUnit;
    }

    public String getSbrandId() {
        return sbrandId;
    }

    public void setSbrandId(String sbrandId) {
        this.sbrandId = sbrandId;
    }

    public String getSsmallCategoryId() {
        return ssmallCategoryId;
    }

    public void setSsmallCategoryId(String ssmallCategoryId) {
        this.ssmallCategoryId = ssmallCategoryId;
    }

    public BigDecimal getFsalePrice() {
        return fsalePrice;
    }

    public void setFsalePrice(BigDecimal fsalePrice) {
        this.fsalePrice = fsalePrice;
    }

    public Integer getIlifeType() {
        return ilifeType;
    }

    public void setIlifeType(Integer ilifeType) {
        this.ilifeType = ilifeType;
    }

    public Integer getIshelfLife() {
        return ishelfLife;
    }

    public void setIshelfLife(Integer ishelfLife) {
        this.ishelfLife = ishelfLife;
    }

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getScommodityImg() {
        return scommodityImg;
    }

    public void setScommodityImg(String scommodityImg) {
        this.scommodityImg = scommodityImg;
    }

    public String getSbrandName() {
        return sbrandName;
    }

    public void setSbrandName(String sbrandName) {
        this.sbrandName = sbrandName;
    }

    public String getStaste() {
        return staste;
    }

    public void setStaste(String staste) {
        this.staste = staste;
    }

    public Integer getIspecWeight() {
        return ispecWeight;
    }

    public void setIspecWeight(Integer ispecWeight) {
        this.ispecWeight = ispecWeight;
    }

    public String getSspecUnit() {
        return sspecUnit;
    }

    public void setSspecUnit(String sspecUnit) {
        this.sspecUnit = sspecUnit;
    }

    @Override
    public String toString() {
        return "StockVo{" +
                "svrCode='" + svrCode + '\'' +
                ", istock=" + istock +
                ", scode='" + scode + '\'' +
                ", id='" + id + '\'' +
                ", sname='" + sname + '\'' +
                ", fcostPrice=" + fcostPrice +
                ", fsalePrice=" + fsalePrice +
                ", istatus=" + istatus +
                ", spackageUnit='" + spackageUnit + '\'' +
                ", sbrandId='" + sbrandId + '\'' +
                ", sbrandName='" + sbrandName + '\'' +
                ", staste='" + staste + '\'' +
                ", ispecWeight=" + ispecWeight +
                ", sspecUnit='" + sspecUnit + '\'' +
                ", ssmallCategoryId='" + ssmallCategoryId + '\'' +
                ", scommodityImg='" + scommodityImg + '\'' +
                ", ilifeType=" + ilifeType +
                ", ishelfLife=" + ishelfLife +
                ", smerchantId='" + smerchantId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                '}';
    }
}
