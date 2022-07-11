package com.cloud.cang.open.sdk.response;

import com.cloud.cang.open.sdk.mapping.BaseField;
import com.cloud.cang.open.sdk.mapping.BaseListField;
import com.cloud.cang.open.sdk.model.response.ImgResultDetail;

import java.util.List;

/**
 * @version 1.0
 * @Description: 图片视觉识别响应类
 * @Author: zhouhong
 * @Date: 2018年9月7日16:08:32
 */
public class ImgRecognitionResponse extends BaseResponse  {

    @BaseField("batchNo")
    private String batchNo;                         //  37号仓业务批次号
    @BaseField("outBatchNo")
    private String outBatchNo;                      //  商户业务批次号
    @BaseListField("imgResultDetail")
    private List<ImgResultDetail> imgResultDetail;  //  接口请求图片结果信息
    @BaseField("deviceId")
    private String deviceId;                        //  商户传入的设备ID

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getOutBatchNo() {
        return outBatchNo;
    }

    public void setOutBatchNo(String outBatchNo) {
        this.outBatchNo = outBatchNo;
    }

    public List<ImgResultDetail> getImgResultDetail() {
        return imgResultDetail;
    }

    public void setImgResultDetail(List<ImgResultDetail> imgResultDetail) {
        this.imgResultDetail = imgResultDetail;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "ImgRecognitionResponse{" +
                "batchNo='" + batchNo + '\'' +
                ", outBatchNo='" + outBatchNo + '\'' +
                ", imgResultDetail=" + imgResultDetail +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}