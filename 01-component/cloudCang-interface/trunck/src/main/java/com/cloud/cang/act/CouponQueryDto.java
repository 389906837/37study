package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;

/**
 * 券查询参数
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class CouponQueryDto extends SuperDto {

    /**
     * 会员ID
     */
    protected String memberId;

    /**
     * 订单总金额
     */
    protected BigDecimal useOrderAmount;
    /**
     * 订单商品分类使用限制（空不限制）多个用，隔开
     */
    protected String suseLimitCategory;

    /**
     * 订单商品品牌使用限制（空不限制）多个用，隔开
     */
    protected String suseLimitBrand;

    /**
     * 订单商品限使用制（空不限制）多个用，隔开
     */
    protected String suseLimitCommodity;


    /**
     * 订单设备使用限制（空不限制）多个用，隔开
     */
    protected String suseLimitDevice;


    @Override
    public String toString() {
        return "CouponQueryDto{" +
                "memberId='" + memberId + '\'' +
                ", useOrderAmount=" + useOrderAmount +
                ", suseLimitCategory='" + suseLimitCategory + '\'' +
                ", suseLimitBrand='" + suseLimitBrand + '\'' +
                ", suseLimitCommodity='" + suseLimitCommodity + '\'' +
                ", suseLimitDevice='" + suseLimitDevice + '\'' +
                '}';
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public BigDecimal getUseOrderAmount() {
        return useOrderAmount;
    }

    public void setUseOrderAmount(BigDecimal useOrderAmount) {
        this.useOrderAmount = useOrderAmount;
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

    public String getSuseLimitDevice() {
        return suseLimitDevice;
    }

    public void setSuseLimitDevice(String suseLimitDevice) {
        this.suseLimitDevice = suseLimitDevice;
    }
}
