package com.cloud.cang.open.sdk.model.request;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 请求图片信息model
 * @Author: zhouhong
 * @Date: 2018/9/4 15:24
 */
public class ImageDetail implements Serializable {

    //=========必填========
    private String cellid;
    //=== imgUrl imgBase64 二选一
    private String imgUrl;
    private String imgBase64;
    // ========与imgBase64同时存在 如不填 默认jpg
    private String imgFormat;

    private String uploadUrl;//文件上传地址


    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getImgFormat() {
        return imgFormat;
    }

    public void setImgFormat(String imgFormat) {
        this.imgFormat = imgFormat;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    @Override
    public String toString() {
        return "ImageDetail{" +
                "cellid='" + cellid + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", imgBase64='" + imgBase64 + '\'' +
                ", imgFormat='" + imgFormat + '\'' +
                ", uploadUrl='" + uploadUrl + '\'' +
                '}';
    }
}
