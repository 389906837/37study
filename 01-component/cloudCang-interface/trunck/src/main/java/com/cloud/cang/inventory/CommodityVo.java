package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.util.Set;

/**
 * @version 1.0
 * @Description: 视觉商品盘点入参信息
 * @Author: zhouhong
 * @Date: 2018/4/11 16:22
 */
public class CommodityVo extends SuperDto {

    private String svrCode;//视觉商品编号
    private Integer commodityNum;//商品数量
    private String commodityCode;//商品编号
    private String lables;//RFID标签(新增或减少的rfid，多个rfid以,分隔)


    public String getSvrCode() {
        return svrCode;
    }

    public void setSvrCode(String svrCode) {
        this.svrCode = svrCode;
    }

    public Integer getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getLables() {
        return lables;
    }

    public void setLables(String lables) {
        this.lables = lables;
    }

    @Override
    public String toString() {
        return "CommodityVo{" +
                "svrCode='" + svrCode + '\'' +
                ", commodityNum=" + commodityNum +
                ", commodityCode='" + commodityCode + '\'' +
                ", lables=" + lables +
                '}';
    }
}
