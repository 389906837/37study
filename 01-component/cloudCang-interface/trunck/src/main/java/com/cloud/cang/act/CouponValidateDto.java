package com.cloud.cang.act;

/**
 * 验券参数
 *
 * @author zhouhong
 * @version 1.0
 */
public class CouponValidateDto extends CouponQueryDto {

    private static final long serialVersionUID = -6356021655617184855L;

    /**
     * 持有券Id
     */
    protected String couponUserId;// 必传

    /**
     * 订单金额
     */
    // protected Double orderAmount;
    public String getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(String couponUserId) {
        this.couponUserId = couponUserId;
    }

   /* public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }*/

    @Override
    public String toString() {
        return "CouponValidateDto{" +
                "couponUserId='" + couponUserId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", useOrderAmount=" + useOrderAmount +
                ", suseLimitCategory='" + suseLimitCategory + '\'' +
                ", suseLimitBrand='" + suseLimitBrand + '\'' +
                ", suseLimitCommodity='" + suseLimitCommodity + '\'' +
                ", suseLimitDevice='" + suseLimitDevice + '\'' +
                '}';
    }
}
