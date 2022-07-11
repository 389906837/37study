package com.cloud.cang.api.om.vo;

import com.cloud.cang.api.common.utils.PriceUtil;
import com.cloud.cang.model.om.OrderCommodity;

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
    /*商品图片*/
    private String scommodityImg;

    public String getScommodityImg() {
        return scommodityImg;
    }

    public void setScommodityImg(String scommodityImg) {
        this.scommodityImg = scommodityImg;
    }


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
                "favgActualAmount=" + favgActualAmount +
                ", scommodityImg='" + scommodityImg + '\'' +
                '}';
    }
}
