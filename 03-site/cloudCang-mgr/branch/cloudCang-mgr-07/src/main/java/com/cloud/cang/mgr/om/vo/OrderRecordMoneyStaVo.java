package com.cloud.cang.mgr.om.vo;

import java.math.BigDecimal;

/**
 * 订单列表页脚总统计
 * Created by yan on 2018/7/10.
 */
public class OrderRecordMoneyStaVo {
    private BigDecimal ftotalAmountSta;
    private BigDecimal fdiscountAmountSta;
    private BigDecimal ffirstDiscountAmountSta;
    private BigDecimal fpromotionDiscountAmountSta;
    private BigDecimal fcouponDeductionAmountSta;
    private BigDecimal factualPayAmountSta;
    private BigDecimal factualRefundAmountSta;

    public BigDecimal getFtotalAmountSta() {
        return ftotalAmountSta;
    }

    public void setFtotalAmountSta(BigDecimal ftotalAmountSta) {
        this.ftotalAmountSta = ftotalAmountSta;
    }

    public BigDecimal getFdiscountAmountSta() {
        return fdiscountAmountSta;
    }

    public void setFdiscountAmountSta(BigDecimal fdiscountAmountSta) {
        this.fdiscountAmountSta = fdiscountAmountSta;
    }

    public BigDecimal getFfirstDiscountAmountSta() {
        return ffirstDiscountAmountSta;
    }

    public void setFfirstDiscountAmountSta(BigDecimal ffirstDiscountAmountSta) {
        this.ffirstDiscountAmountSta = ffirstDiscountAmountSta;
    }

    public BigDecimal getFpromotionDiscountAmountSta() {
        return fpromotionDiscountAmountSta;
    }

    public void setFpromotionDiscountAmountSta(BigDecimal fpromotionDiscountAmountSta) {
        this.fpromotionDiscountAmountSta = fpromotionDiscountAmountSta;
    }

    public BigDecimal getFcouponDeductionAmountSta() {
        return fcouponDeductionAmountSta;
    }

    public void setFcouponDeductionAmountSta(BigDecimal fcouponDeductionAmountSta) {
        this.fcouponDeductionAmountSta = fcouponDeductionAmountSta;
    }

    public BigDecimal getFactualPayAmountSta() {
        return factualPayAmountSta;
    }

    public void setFactualPayAmountSta(BigDecimal factualPayAmountSta) {
        this.factualPayAmountSta = factualPayAmountSta;
    }

    public BigDecimal getFactualRefundAmountSta() {
        return factualRefundAmountSta;
    }

    public void setFactualRefundAmountSta(BigDecimal factualRefundAmountSta) {
        this.factualRefundAmountSta = factualRefundAmountSta;
    }
}
