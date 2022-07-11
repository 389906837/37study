package com.cloud.cang.act;

public class CouponUseDto extends CouponValidateDto {

    private static final long serialVersionUID = 1785527863438080588L;
    private String sdismantlingCode;//拆单编号 订单为拆单订单则传

    private String targetId;//使用订单id

    private String targetCode;//使用订单编号

    private String targetInstruction;//使用说明

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

    public String getSdismantlingCode() {
        return sdismantlingCode;
    }

    public void setSdismantlingCode(String sdismantlingCode) {
        this.sdismantlingCode = sdismantlingCode;
    }

    public void setTargetInstruction(String targetInstruction) {
        this.targetInstruction = targetInstruction;
    }

    @Override
    public String toString() {
        return "CouponUseDto{" +
                "sdismantlingCode='" + sdismantlingCode + '\'' +
                ", targetId='" + targetId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", targetCode='" + targetCode + '\'' +
                ", couponUserId='" + couponUserId + '\'' +
                ", targetInstruction='" + targetInstruction + '\'' +
                ", useOrderAmount=" + useOrderAmount +
                ", suseLimitCategory='" + suseLimitCategory + '\'' +
                ", suseLimitBrand='" + suseLimitBrand + '\'' +
                ", suseLimitCommodity='" + suseLimitCommodity + '\'' +
                ", suseLimitDevice='" + suseLimitDevice + '\'' +
                '}';
    }
}
