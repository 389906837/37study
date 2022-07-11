package com.cloud.cang.jy;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Description: 订单优惠-内部服务之间的model
 * @Author: zengzexiong
 * @Date: 2018年4月10日17:25:38
 */
public class OrderDiscountInfoResult extends SuperDto {

    private String id;//用户ID

    private String scode;//用户编号

    private String smemberName;/* 用户名（手机号） */

    private String sdeviceCode;//设备编号

    private List<CommodityDiscountDto> commodityDisList;//商品集合

    private BigDecimal ftotalCostAmount;//订单商品总成本

    private BigDecimal ftotalAmount;//订单总额

    private BigDecimal fdiscountAmount;//订单优惠总额

    private BigDecimal ffirstDiscountAmount;//订单首单优惠金额

    private BigDecimal fpromotionDiscountAmount;//订单促销优惠金额

    private BigDecimal fcouponDeductionAmount;//订单优惠券抵扣金额

    private BigDecimal factualPayAmount;//实付金额

    private BigDecimal fpointDiscountAmount;//订单积分优惠金额

    private BigDecimal fotherDiscountAmount;//订单其他优惠金额

    private String sacId;	/* 活动ID 多个，隔开  */

    private String sacCode;	/* 活动编号 多个，隔开 */

    private String susedCouponCode;	/* 券使用编号 */

    private String susedCouponId;	/* 券使用ID */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getFtotalCostAmount() {
        return ftotalCostAmount;
    }

    public void setFtotalCostAmount(BigDecimal ftotalCostAmount) {
        this.ftotalCostAmount = ftotalCostAmount;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public List<CommodityDiscountDto> getCommodityDisList() {
        return commodityDisList;
    }

    public void setCommodityDisList(List<CommodityDiscountDto> commodityDisList) {
        this.commodityDisList = commodityDisList;
    }

    public BigDecimal getFtotalAmount() {
        return ftotalAmount;
    }

    public void setFtotalAmount(BigDecimal ftotalAmount) {
        this.ftotalAmount = ftotalAmount;
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

    public BigDecimal getFactualPayAmount() {
        return factualPayAmount;
    }

    public void setFactualPayAmount(BigDecimal factualPayAmount) {
        this.factualPayAmount = factualPayAmount;
    }

    public BigDecimal getFpointDiscountAmount() {
        return fpointDiscountAmount;
    }

    public void setFpointDiscountAmount(BigDecimal fpointDiscountAmount) {
        this.fpointDiscountAmount = fpointDiscountAmount;
    }

    public BigDecimal getFotherDiscountAmount() {
        return fotherDiscountAmount;
    }

    public void setFotherDiscountAmount(BigDecimal fotherDiscountAmount) {
        this.fotherDiscountAmount = fotherDiscountAmount;
    }

    public String getSacId() {
        return sacId;
    }

    public void setSacId(String sacId) {
        this.sacId = sacId;
    }

    public String getSacCode() {
        return sacCode;
    }

    public void setSacCode(String sacCode) {
        this.sacCode = sacCode;
    }

    public String getSusedCouponCode() {
        return susedCouponCode;
    }

    public void setSusedCouponCode(String susedCouponCode) {
        this.susedCouponCode = susedCouponCode;
    }

    public String getSusedCouponId() {
        return susedCouponId;
    }

    public void setSusedCouponId(String susedCouponId) {
        this.susedCouponId = susedCouponId;
    }
}
