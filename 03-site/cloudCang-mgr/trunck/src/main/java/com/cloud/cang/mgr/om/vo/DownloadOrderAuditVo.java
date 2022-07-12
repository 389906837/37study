package com.cloud.cang.mgr.om.vo;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.om.OrderAudit;
import org.apache.shiro.SecurityUtils;

import java.math.BigDecimal;

/**
 * 导出异常订单Vo
 * Created by yan on 2018/6/28.
 */
public class DownloadOrderAuditVo extends OrderAudit {
    private String merchantName;//商户名
    private BigDecimal fcommodityPrice;//商品单价
    private Integer forderCount;//订单数量
    private BigDecimal fcommodityAmount;//商品总额
    private String smemberNameDesensitize;//用户名脱敏
    private String commodityName;//商品名

    private String sbrandName;//品牌名

    private String staste;//口味

    private String ispecWeight;// 规则/重量

    private String sspecUnit;//单位

    private String spackageUnit;//最小销售包装单位

    private String commodityFullName;//商品全称

    //品牌+商品名+口味+规格+单位
    public String getCommodityFullName() {
        return this.sbrandName + this.commodityName + this.staste + this.ispecWeight + this.sspecUnit + "/" + this.spackageUnit;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
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

    public String getIspecWeight() {
        return ispecWeight;
    }

    public void setIspecWeight(String ispecWeight) {
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

    public void setCommodityFullName(String commodityFullName) {
        this.commodityFullName = commodityFullName;
    }

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("ORDER_AUDIT_MEMBER_NAME_DESENSITIZE")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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
}
