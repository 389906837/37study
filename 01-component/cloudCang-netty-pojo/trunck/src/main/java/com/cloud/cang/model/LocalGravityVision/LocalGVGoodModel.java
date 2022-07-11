package com.cloud.cang.model.LocalGravityVision;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 本地重力视觉柜子
 * @Author: zengzexiong
 * @Date: 2018年10月24日09:32:15
 */
public class LocalGVGoodModel extends SuperDto {
    private String layerNo;                  // 层数
    private String svrCode;         // 商品视觉识别编号
    private String increment;       // 增加数量
    private String decrement;       // 减少数量
    private String remainsNum;     // 剩余商品数量


    public String getLayerNo() {
        return layerNo;
    }

    public void setLayerNo(String layerNo) {
        this.layerNo = layerNo;
    }

    public String getSvrCode() {
        return svrCode;
    }

    public void setSvrCode(String svrCode) {
        this.svrCode = svrCode;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public String getDecrement() {
        return decrement;
    }

    public void setDecrement(String decrement) {
        this.decrement = decrement;
    }

    public String getRemainsNum() {
        return remainsNum;
    }

    public void setRemainsNum(String remainsNum) {
        this.remainsNum = remainsNum;
    }
}
