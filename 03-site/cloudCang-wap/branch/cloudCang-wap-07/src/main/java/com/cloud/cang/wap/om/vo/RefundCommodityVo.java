package com.cloud.cang.wap.om.vo;

import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.wap.common.utils.PriceUtil;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 退款审核订单商品
 * @Author: zhouhong
 * @Date: 2018/4/11 16:22
 */
public class RefundCommodityVo extends RefundCommodity {

    /* 平均实付金额 */
    private BigDecimal favgActualAmount;
    /*商品图片*/
    private String scommodityImg;

    public BigDecimal getFavgActualAmount() {
        if (null != this.getFrefundAmount() && null != this.getFrefundCount()) {
            return PriceUtil.divideDown(this.getFrefundAmount(), new BigDecimal(String.valueOf(this.getFrefundCount())));
        }
        return favgActualAmount;
    }

    public void setFavgActualAmount(BigDecimal favgActualAmount) {
        this.favgActualAmount = favgActualAmount;
    }

    @Override
    public String toString() {
        return "RefundCommodityVo{" +
                "favgActualAmount=" + favgActualAmount +
                ", scommodityImg='" + scommodityImg + '\'' +
                '}';
    }

    public String getScommodityImg() {
        return scommodityImg;
    }

    public void setScommodityImg(String scommodityImg) {
        this.scommodityImg = scommodityImg;
    }
}
