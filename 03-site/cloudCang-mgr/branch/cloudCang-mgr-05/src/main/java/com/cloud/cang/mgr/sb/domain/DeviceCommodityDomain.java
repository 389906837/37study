package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.model.sb.DeviceCommodity;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Alex on 2018/2/10.
 */
public class DeviceCommodityDomain extends DeviceCommodity {

    private String sname;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */
    private String commodityName;           /* 商品名称 */
    private Integer totalSalesNum = 0;             /* 该设备总销量 */

    private String commodityFullName;   // 商品拼接名
    private String sbrandName;  /* 品牌名称 */
    private String staste;  /* 口味 */
    private Integer ispecWeight;    /* 规格/重量 */
    private String sspecUnit;   /* 规格单位 */
    private String spackageUnit;    /* 最小销售包装单位 */

    public String getCommodityFullName() {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(this.getSbrandName())) {
            sb.append(this.getSbrandName());
        }
        if (StringUtils.isNotBlank(this.getCommodityName())) {
            sb.append(this.getCommodityName());
        }
        if (StringUtils.isNotBlank(this.getStaste())) {
            sb.append(this.getStaste());
        }
        if (null != this.getIspecWeight()) {
            sb.append(this.getIspecWeight());
        }
        if (null != this.getSspecUnit()) {
            sb.append(this.getSspecUnit());
        }
        if (StringUtils.isNotBlank(this.getSpackageUnit())) {
            sb.append("/"+this.getSpackageUnit());
        }
        return sb.toString();
    }

    public void setCommodityFullName(String commodityFullName) {
        this.commodityFullName = commodityFullName;
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

    public String getSpackageUnit() {
        return spackageUnit;
    }

    public void setSpackageUnit(String spackageUnit) {
        this.spackageUnit = spackageUnit;
    }

    public Integer getTotalSalesNum() {
        return totalSalesNum;
    }

    public void setTotalSalesNum(Integer totalSalesNum) {
        this.totalSalesNum = totalSalesNum;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    @Override
    public String toString() {
        return "DeviceCommodityDomain{" +
                "sname='" + sname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", totalSalesNum='" + totalSalesNum + '\'' +
                '}';
    }
}
