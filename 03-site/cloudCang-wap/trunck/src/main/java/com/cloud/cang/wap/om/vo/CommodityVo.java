package com.cloud.cang.wap.om.vo;

import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.wap.common.utils.PriceUtil;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 订单商品
 * @Author: zhouhong
 * @Date: 2018/4/11 16:22
 */
public class CommodityVo extends OrderCommodity {

    /* 平均实付金额 */
    private BigDecimal favgActualAmount;

    public BigDecimal getFavgActualAmount() {
        if (null != this.getFactualAmount() && null != this.getForderCount()) {
            return PriceUtil.divideDown(this.getFactualAmount(), new BigDecimal(String.valueOf(this.getForderCount())));
        }
        return favgActualAmount;
    }

    public void setFavgActualAmount(BigDecimal favgActualAmount) {
        this.favgActualAmount = favgActualAmount;
    }

    @Override
    public String toString() {
        return "CommodityVo{" +
                ", favgActualAmount=" + favgActualAmount +
                '}';
    }
}
