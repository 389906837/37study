package com.cloud.cang.open.sdk.response;

import com.cloud.cang.open.sdk.mapping.BaseField;
import com.cloud.cang.open.sdk.mapping.BaseListField;
import com.cloud.cang.open.sdk.model.response.ImgResultDetail;

import java.util.List;

/***
 * 视觉识别订单查询响应
 */
public class ImgRecognitionQueryResponse extends BaseResponse  {

    @BaseField("batchNo")
    private String batchNo;
    @BaseField("outBatchNo")
    private String outBatchNo;
    @BaseListField("imgResultDetail")
    private List<ImgResultDetail> imgResultDetail;
    @BaseField("deviceId")
    private String deviceId;

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
        return "ImgRecognitionQueryResponse{" +
                "batchNo='" + batchNo + '\'' +
                ", outBatchNo='" + outBatchNo + '\'' +
                ", imgResultDetail=" + imgResultDetail +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}