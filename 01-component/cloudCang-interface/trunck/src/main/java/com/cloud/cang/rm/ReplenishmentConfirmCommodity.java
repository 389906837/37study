package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description:补货单待补货员确认订单商品详情
 * @Author: zhouhong
 * @Date: 2018/4/13 12:02
 */
public class ReplenishmentConfirmCommodity extends SuperDto {
    private String scommodityFullName;//商品名（全称）
    private Integer type;//类型 10上架 20下架 30 不变
    private Integer num;//上下架数量
    private Integer currentNum;//现有总数量


    @Override
    public String toString() {
        return "ReplenishmentConfirmCommodity{" +
                "scommodityFullName='" + scommodityFullName + '\'' +
                ", type=" + type +
                ", num='" + num + '\'' +
                ", currentNum='" + currentNum + '\'' +
                '}';
    }

    public String getScommodityFullName() {
        return scommodityFullName;
    }

    public void setScommodityFullName(String scommodityFullName) {
        this.scommodityFullName = scommodityFullName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }
}
