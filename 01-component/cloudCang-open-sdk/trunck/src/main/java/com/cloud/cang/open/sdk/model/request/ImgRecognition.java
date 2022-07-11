package com.cloud.cang.open.sdk.model.request;


import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Description: 图片视觉识别请求参数类
 * @Author: zhouhong
 * @Date: 2018/9/4 15:09
 */
public class ImgRecognition implements Serializable {

    //======必填=======
    private String outBatchNo;//商户业务批次号
    private List<ImageDetail> imageDetail;//接口请求包含的图片信息
    private String modelCode;//识别模型编号
    //======选填=======
    private String deviceId;//设备ID
    private String desc;//描述
    private Integer isLimitImg=1;//是否限制图片数量 0=不限制 1=限制
    private Integer responseType=10;//返回结果类型 10=不带坐标 20=带坐标
    // 是否保存图片 0=不保存 1=保存
    // (当isSaveImg = 0时候，List<ImageDetail> imageDetail的ImageDetail的uploadUrl由第三方提供)
    private Integer isSaveImg=1;

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public Integer getIsLimitImg() {
        return isLimitImg;
    }

    public void setIsLimitImg(Integer isLimitImg) {
        this.isLimitImg = isLimitImg;
    }

    public Integer getResponseType() {
        return responseType;
    }

    public void setResponseType(Integer responseType) {
        this.responseType = responseType;
    }

    public Integer getIsSaveImg() {
        return isSaveImg;
    }

    public void setIsSaveImg(Integer isSaveImg) {
        this.isSaveImg = isSaveImg;
    }

    @Override
    public String toString() {
        return "ImgRecognition{" +
                "outBatchNo='" + outBatchNo + '\'' +
                ", imageDetail=" + imageDetail +
                ", modelCode='" + modelCode + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", desc='" + desc + '\'' +
                ", isLimitImg=" + isLimitImg +
                ", responseType=" + responseType +
                ", isSaveImg=" + isSaveImg +
                '}';
    }
}
