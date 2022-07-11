package com.cloud.cang.model.cloud;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 图片base64模型
 * @Author: zengzexiong
 * @Date: 2018年11月27日13:11:35
 */
public class ImageDetail implements Serializable {
    //=========必填========
    private String cellid;
    //=== imgUrl imgBase64 二选一
    private String imgUrl;
    private String imgBase64;
    // ========与imgBase64同时存在 如不填 默认jpg
    private String imgFormat;


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
}
