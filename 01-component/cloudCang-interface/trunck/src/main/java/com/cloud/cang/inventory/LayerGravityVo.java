package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.util.List;


/**
 * @version 1.0
 * @Description: 柜子层数重力值
 * @Author: zengzexiong
 * @Date: 2018年11月1日17:57:30
 */
public class LayerGravityVo extends SuperDto {

    private String layerNo;                  // 层数
    private String layerGravityIncrement;    // 该层增加总重力值
    private String layerGravityDecrement;    // 该层减少总重力值
    private String layerGravityRemain;       // 该层当前总重力值
    private List<CargoRoadGravityVo> cargoRoadGravityModelList; // 重力值（保留暂时不用）

    public String getLayerNo() {
        return layerNo;
    }

    public void setLayerNo(String layerNo) {
        this.layerNo = layerNo;
    }

    public String getLayerGravityIncrement() {
        return layerGravityIncrement;
    }

    public void setLayerGravityIncrement(String layerGravityIncrement) {
        this.layerGravityIncrement = layerGravityIncrement;
    }

    public String getLayerGravityDecrement() {
        return layerGravityDecrement;
    }

    public void setLayerGravityDecrement(String layerGravityDecrement) {
        this.layerGravityDecrement = layerGravityDecrement;
    }

    public String getLayerGravityRemain() {
        return layerGravityRemain;
    }

    public void setLayerGravityRemain(String layerGravityRemain) {
        this.layerGravityRemain = layerGravityRemain;
    }

    public List<CargoRoadGravityVo> getCargoRoadGravityModelList() {
        return cargoRoadGravityModelList;
    }

    public void setCargoRoadGravityModelList(List<CargoRoadGravityVo> cargoRoadGravityModelList) {
        this.cargoRoadGravityModelList = cargoRoadGravityModelList;
    }

    @Override
    public String toString() {
        return "LayerGravityVo{" +
                "layerNo='" + layerNo + '\'' +
                ", layerGravityIncrement='" + layerGravityIncrement + '\'' +
                ", layerGravityDecrement='" + layerGravityDecrement + '\'' +
                ", layerGravityRemain='" + layerGravityRemain + '\'' +
                ", cargoRoadGravityModelList=" + cargoRoadGravityModelList +
                '}';
    }
}
