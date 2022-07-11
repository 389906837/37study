package com.cloud.cang.act;

import java.io.Serializable;
import java.util.List;

/**
 * 赠送活动返回结果
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class GiveActivityResult implements Serializable {

    private static final long serialVersionUID = 7822623828964421473L;

    private List<CouponGiveResult> couponGiveResultList;//券列表

    private IntegralGiveResult integralGiveResult;//积分
    private Integer couponNum;//优惠券总数

    public Integer getCouponNum() {
        couponNum = 0;
        if (null != couponGiveResultList && !couponGiveResultList.isEmpty()) {
            for (CouponGiveResult couponGiveResult : couponGiveResultList) {
                couponNum += couponGiveResult.getCount();
            }
        }
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    public List<CouponGiveResult> getCouponGiveResultList() {
        return couponGiveResultList;
    }

    public void setCouponGiveResultList(List<CouponGiveResult> couponGiveResultList) {
        this.couponGiveResultList = couponGiveResultList;
    }

    public IntegralGiveResult getIntegralGiveResult() {
        return integralGiveResult;
    }

    public void setIntegralGiveResult(IntegralGiveResult integralGiveResult) {
        this.integralGiveResult = integralGiveResult;
    }

    @Override
    public String toString() {
        return "ActiveGiveResult [couponGiveResultList=" + couponGiveResultList + ", integralGiveResult="
                + integralGiveResult + "]";
    }

}
