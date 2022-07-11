package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

/**
 * 商品数量差值
 */
public class InventoryCommodityDiffVo extends SuperDto {

    private String svrCode;                 // 视觉商品编号
    private Integer increment;              // 商品增加数量
    private Integer decrement;              // 商品减少数量
    private Integer remainsNum;             // 商品剩余数量

    public String getSvrCode() {
        return svrCode;
    }

    public void setSvrCode(String svrCode) {
        this.svrCode = svrCode;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Integer getDecrement() {
        return decrement;
    }

    public void setDecrement(Integer decrement) {
        this.decrement = decrement;
    }

    public Integer getRemainsNum() {
        return remainsNum;
    }

    public void setRemainsNum(Integer remainsNum) {
        this.remainsNum = remainsNum;
    }

    @Override
    public String toString() {
        return "InventoryCommodityDiffVo{" +
                "svrCode='" + svrCode + '\'' +
                ", increment=" + increment +
                ", decrement=" + decrement +
                ", remainsNum=" + remainsNum +
                '}';
    }
}
