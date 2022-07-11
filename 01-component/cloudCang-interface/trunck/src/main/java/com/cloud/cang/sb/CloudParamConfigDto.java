package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 云端设备 配置参数调整
 * @author zhouhong
 * @version 1.0
 * @date 2018年3月19日16:45:37
 */
public class CloudParamConfigDto extends SuperDto {

    //推送类型 必填
    private Integer type;//10 设备 20 商户
    private String merchantCode;//  商户编号 必填
    private String merchantId;//  商户ID 必填
    private List<String> devices;//推送设备 推送类型为设备必填
    private Integer shoppingInventory;//购物实时盘货状态 0 关闭 1 开启
    private Integer replenishmentInventory;//补货实时盘货状态 0 关闭 1 开启
    private String cloudAppId;//云识别账户是否修改 存在修改不存在不修改


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public String getCloudAppId() {
        return cloudAppId;
    }

    public void setCloudAppId(String cloudAppId) {
        this.cloudAppId = cloudAppId;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Integer getShoppingInventory() {
        return shoppingInventory;
    }

    public void setShoppingInventory(Integer shoppingInventory) {
        this.shoppingInventory = shoppingInventory;
    }

    public Integer getReplenishmentInventory() {
        return replenishmentInventory;
    }

    public void setReplenishmentInventory(Integer replenishmentInventory) {
        this.replenishmentInventory = replenishmentInventory;
    }

    @Override
    public String toString() {
        return "CloudParamConfigDto{" +
                "type=" + type +
                ", merchantCode='" + merchantCode + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", devices=" + devices +
                ", shoppingInventory=" + shoppingInventory +
                ", replenishmentInventory=" + replenishmentInventory +
                ", cloudAppId='" + cloudAppId + '\'' +
                '}';
    }
}
