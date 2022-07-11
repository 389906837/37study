package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 语音升级地址推送
 * @Author: zengzexiong
 * @Date: 2018年9月19日14:14:42
 */
public class VoiceModel extends SuperDto {

    // 没有的话，"noUrl"
    private String openDoorUrl;     //开门语音（必填）
    private String shoppingUrl;     //选择商品语音（必填）
    private String thanksUrl;       //谢谢购物语音（必填）
    private String localGravityUrl;       //重力视觉柜购物实时订单警报（必填）
    private String replenOpenDoorUrl;//新增补货员开门实时语音

    public String getReplenOpenDoorUrl() {
        return replenOpenDoorUrl;
    }

    public void setReplenOpenDoorUrl(String replenOpenDoorUrl) {
        this.replenOpenDoorUrl = replenOpenDoorUrl;
    }

    public String getLocalGravityUrl() {
        return localGravityUrl;
    }

    public void setLocalGravityUrl(String localGravityUrl) {
        this.localGravityUrl = localGravityUrl;
    }

    private String detailId;        // 升级记录明细表ID（记录升级操作）

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getOpenDoorUrl() {
        return openDoorUrl;
    }

    public void setOpenDoorUrl(String openDoorUrl) {
        this.openDoorUrl = openDoorUrl;
    }

    public String getShoppingUrl() {
        return shoppingUrl;
    }

    public void setShoppingUrl(String shoppingUrl) {
        this.shoppingUrl = shoppingUrl;
    }

    public String getThanksUrl() {
        return thanksUrl;
    }

    public void setThanksUrl(String thanksUrl) {
        this.thanksUrl = thanksUrl;
    }

    @Override
    public String toString() {
        return "VoiceModel{" +
                "openDoorUrl='" + openDoorUrl + '\'' +
                ", shoppingUrl='" + shoppingUrl + '\'' +
                ", thanksUrl='" + thanksUrl + '\'' +
                ", localGravityUrl='" + localGravityUrl + '\'' +
                ", detailId='" + detailId + '\'' +
                '}';
    }
}
