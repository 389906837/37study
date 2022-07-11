package com.cloud.cang.open.sdk.model.request;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Description: 图片视觉识别请求参数类
 * @Author: zhouhong
 * @Date: 2018/9/4 15:09
 */
public class ImgRecognitionTmpDto implements Serializable {

    //======必填=======
    private String outBatchNo;//商户业务批次号
    private List<ImageDetail> imageDetail;//接口请求包含的图片信息

    private String uniquelyIdentifies;//异步关门唯一标识
    private String deviceId;//设备ID
    private String deviceType;//设备类型 (50=纯视觉 60=视觉加称重)
    private String key;//设备通信密钥
    private String method;//操作方法名(openDoor=开门 openDoorInventory=实时盘货 closeDoor=关门  closeDoor_inventory=关门盘货)
    private String userId;//用户ID
    private String userType;//用户类型(10=购物 20=补货员)
    //重力数据
    private BigDecimal totalWeight;     //总重量
    private List<LayeredWeight> layeredWeightList;//分成总重量

    //======选填=======
    private String desc;//描述


    public String getOutBatchNo() {
        return outBatchNo;
    }

    public void setOutBatchNo(String outBatchNo) {
        this.outBatchNo = outBatchNo;
    }

    public List<ImageDetail> getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(List<ImageDetail> imageDetail) {
        this.imageDetail = imageDetail;
    }

    public String getUniquelyIdentifies() {
        return uniquelyIdentifies;
    }

    public void setUniquelyIdentifies(String uniquelyIdentifies) {
        this.uniquelyIdentifies = uniquelyIdentifies;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public List<LayeredWeight> getLayeredWeightList() {
        return layeredWeightList;
    }

    public void setLayeredWeightList(List<LayeredWeight> layeredWeightList) {
        this.layeredWeightList = layeredWeightList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "ImgRecognitionTmpDto{" +
                "outBatchNo='" + outBatchNo + '\'' +
                ", imageDetail=" + imageDetail +
                ", uniquelyIdentifies='" + uniquelyIdentifies + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", key='" + key + '\'' +
                ", method='" + method + '\'' +
                ", userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", totalWeight=" + totalWeight +
                ", layeredWeightList=" + layeredWeightList +
                ", desc='" + desc + '\'' +
                '}';
    }
}
