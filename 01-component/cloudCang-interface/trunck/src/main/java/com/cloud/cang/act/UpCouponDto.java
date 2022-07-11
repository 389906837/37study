package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

/**
 * 下订单使用券修改优惠券状态
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class UpCouponDto extends SuperDto {
    private String targetId;//使用订单id

    private String targetCode;//使用订单编号(拆单订单传拆单编号)

    private String targetInstruction;//使用说明

    private String couponUserId;// 持有券Id

    //会员ID private String memberId;

    private BigDecimal  couponDeductionAmount;//优惠券抵扣金额


    @Override
    public String toString() {
        return "UpCouponDto{" +
                "targetId='" + targetId + '\'' +
                ", targetCode='" + targetCode + '\'' +
                ", targetInstruction='" + targetInstruction + '\'' +
                ", couponUserId='" + couponUserId + '\'' +
                //", memberId='" + memberId + '\'' +
                ", couponDeductionAmount=" + couponDeductionAmount +
                '}';
    }

    public BigDecimal getCouponDeductionAmount() {
        return couponDeductionAmount;
    }

    public void setCouponDeductionAmount(BigDecimal couponDeductionAmount) {
        this.couponDeductionAmount = couponDeductionAmount;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public String getTargetInstruction() {
        return targetInstruction;
    }

    public void setTargetInstruction(String targetInstruction) {
        this.targetInstruction = targetInstruction;
    }

    public String getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(String couponUserId) {
        this.couponUserId = couponUserId;
    }

}
