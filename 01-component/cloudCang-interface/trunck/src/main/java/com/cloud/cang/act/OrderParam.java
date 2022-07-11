package com.cloud.cang.act;

import com.cloud.cang.jy.CommodityDiscountDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单相关参数
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class OrderParam implements Serializable {

    private static final long serialVersionUID = 4741294698458260029L;

//--------------------------------------------------------------首单、下单必传------------------------------------------------------------
    /**
     * 订单明细
     */
    private List<CommodityDiscountDto> commodityDiscountDtoList;

    //--------------------------------------------------------------首单、下单按比例送券必传------------------------------------------------------------

    /**
     * 订单实付金额
     */
    private BigDecimal factualPayAmount;


    public BigDecimal getFactualPayAmount() {
        return factualPayAmount;
    }

    public void setFactualPayAmount(BigDecimal factualPayAmount) {
        this.factualPayAmount = factualPayAmount;
    }

    public List<CommodityDiscountDto> getCommodityDiscountDtoList() {
        return commodityDiscountDtoList;
    }

    public void setCommodityDiscountDtoList(List<CommodityDiscountDto> commodityDiscountDtoList) {
        this.commodityDiscountDtoList = commodityDiscountDtoList;
    }

    @Override
    public String toString() {
        return "OrderParam{" +
                "commodityDiscountDtoList=" + commodityDiscountDtoList +
                ", factualPayAmount=" + factualPayAmount +
                '}';
    }
}
