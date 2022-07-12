package com.cloud.cang.mgr.ws.vo;

/**
 * Created by YLF on 2020/7/1.
 */
public class FaceListResp {
    private String userCode;//用户编号
    private String faceId;//人脸ID
    private String firstRegister;//是否首次注册

    @Override
    public String toString() {
        return "FaceListResp{" +
                "userCode='" + userCode + '\'' +
                ", faceId='" + faceId + '\'' +
                ", firstRegister='" + firstRegister + '\'' +
                '}';
    }

    public String getFirstRegister() {
        return firstRegister;
    }

    public void setFirstRegister(String firstRegister) {
        this.firstRegister = firstRegister;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
}
