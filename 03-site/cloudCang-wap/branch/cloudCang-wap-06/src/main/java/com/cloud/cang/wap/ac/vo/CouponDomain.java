package com.cloud.cang.wap.ac.vo;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yan on 2018/5/22.
 */
public class CouponDomain implements Serializable {
    private String id;
    private Integer icouponType;//券类型
    private String sbriefDesc;//券简述
    private Date dcouponEffectiveDate;//券生效日期
    private Date dcouponExpiryDate;//券失效日期
    private BigDecimal fcouponValue;//券面值
    private Integer istatus;//券状态
    private String scouponInstruction;//券说明
    private String suseLimitCategory;//商品分类使用限制
    private String suseLimitBrand;//商品品牌分类使用限制
    private String suseLimitCommodity;//商品使用限制
    private Integer isAllUse;//是否适用于所有商品 0:否 1:是
    private String suseLimitDevice;//使用设备限制
    private Integer iisAllUseDevice;//是否适用于所有设备

    public Integer getIisAllUseDevice() {
        if (StringUtils.isBlank(suseLimitDevice)) {
            return 1;
        }
        return 0;
    }

    public void setIisAllUseDevice(Integer iisAllUseDevice) {
        this.iisAllUseDevice = iisAllUseDevice;
    }

    public String getSuseLimitDevice() {
        return suseLimitDevice;
    }

    public void setSuseLimitDevice(String suseLimitDevice) {
        this.suseLimitDevice = suseLimitDevice;
    }

    public Integer getIsAllUse() {
        if (StringUtils.isBlank(suseLimitCategory) && StringUtils.isBlank(suseLimitBrand) && StringUtils.isBlank(suseLimitCommodity)) {
            return 1;
        }
        return 0;
    }

    public void setIsAllUse(Integer isAllUse) {
        this.isAllUse = isAllUse;
    }

    public String getSuseLimitCategory() {
        return suseLimitCategory;
    }

    public void setSuseLimitCategory(String suseLimitCategory) {
        this.suseLimitCategory = suseLimitCategory;
    }

    public String getSuseLimitBrand() {
        return suseLimitBrand;
    }

    public void setSuseLimitBrand(String suseLimitBrand) {
        this.suseLimitBrand = suseLimitBrand;
    }

    public String getSuseLimitCommodity() {
        return suseLimitCommodity;
    }

    public void setSuseLimitCommodity(String suseLimitCommodity) {
        this.suseLimitCommodity = suseLimitCommodity;
    }

    public String getScouponInstruction() {
        return scouponInstruction;
    }

    public void setScouponInstruction(String scouponInstruction) {
        this.scouponInstruction = scouponInstruction;
    }

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    public Integer getIcouponType() {
        return icouponType;
    }

    public void setIcouponType(Integer icouponType) {
        this.icouponType = icouponType;
    }

    public String getSbriefDesc() {
        return sbriefDesc;
    }

    public void setSbriefDesc(String sbriefDesc) {
        this.sbriefDesc = sbriefDesc;
    }

    public Date getDcouponEffectiveDate() {
        return dcouponEffectiveDate;
    }

    public void setDcouponEffectiveDate(Date dcouponEffectiveDate) {
        this.dcouponEffectiveDate = dcouponEffectiveDate;
    }

    public Date getDcouponExpiryDate() {
        return dcouponExpiryDate;
    }

    public void setDcouponExpiryDate(Date dcouponExpiryDate) {
        this.dcouponExpiryDate = dcouponExpiryDate;
    }

    public BigDecimal getFcouponValue() {
        return fcouponValue;
    }

    public void setFcouponValue(BigDecimal fcouponValue) {
        this.fcouponValue = fcouponValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
