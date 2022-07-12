package com.cloud.cang.mgr.ws.vo;

import java.util.List;

/**
 * Created by YLF on 2020/7/1.
 */
public class RegisterResponse {
    private String code;
    private String msg;
    private String version;
    private String updateUrl;
    private List<FaceListResp> faceListResp;


    @Override
    public String toString() {
        return "RegisterResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", version='" + version + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", faceListResp=" + faceListResp +
                '}';
    }



    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public List<FaceListResp> getFaceListResp() {
        return faceListResp;
    }

    public void setFaceListResp(List<FaceListResp> faceListResp) {
        this.faceListResp = faceListResp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
