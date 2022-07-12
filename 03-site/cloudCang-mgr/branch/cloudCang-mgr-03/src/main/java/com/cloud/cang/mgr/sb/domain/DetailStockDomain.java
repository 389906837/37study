package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.model.sm.StandardDetail;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Alex on 2018/3/19.
 */
public class DetailStockDomain extends StandardDetail {

    private String commodityName;

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

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
}
