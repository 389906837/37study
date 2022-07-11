package com.cloud.cang.model.LocalGravityVision;

import com.cloud.cang.common.SuperDto;

import java.util.List;


/**
 * @version 1.0
 * @Description: 本地重力视觉柜子商品信息集合
 * @Author: zengzexiong
 * @Date: 2018年10月24日09:32:15
 */
public class LocalGVGoods extends SuperDto {

    private String openDoorType;                            // 开门类型 10=购物 20=补货
    private List<LocalGVGoodModel> localGVGoodModelList;    // 本地识别商品集合
    private List<LayerGravityModel> layerGravityModelList;  // 本地识别重力集合

    public String getOpenDoorType() {
        return openDoorType;
    }

    public void setOpenDoorType(String openDoorType) {
        this.openDoorType = openDoorType;
    }

    public List<LocalGVGoodModel> getLocalGVGoodModelList() {
        return localGVGoodModelList;
    }

    public void setLocalGVGoodModelList(List<LocalGVGoodModel> localGVGoodModelList) {
        this.localGVGoodModelList = localGVGoodModelList;
    }

    public List<LayerGravityModel> getLayerGravityModelList() {
        return layerGravityModelList;
    }

    public void setLayerGravityModelList(List<LayerGravityModel> layerGravityModelList) {
        this.layerGravityModelList = layerGravityModelList;
    }

    @Override
    public String toString() {
        return "LocalGVGoods{" +
                "localGVGoodModelList=" + localGVGoodModelList +
                ", layerGravityModelList=" + layerGravityModelList +
                '}';
    }
}
