package com.cloud.cang.open.sdk.model.response;

import com.cloud.cang.open.sdk.mapping.BaseField;
import com.cloud.cang.open.sdk.mapping.BaseListField;
import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Description: 图片视觉识别 结果model
 * @Author: zhouhong
 * @Date: 2018/9/4 15:29
 */
public class ImgResultDetail implements Serializable {

    @BaseField("cellid")
    private String cellid;
    @BaseField("status")
    private String status;
    @BaseListField("goodDetail")
    private List<GoodDetail> goodDetail;

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GoodDetail> getGoodDetail() {
        return goodDetail;
    }

    public void setGoodDetail(List<GoodDetail> goodDetail) {
        this.goodDetail = goodDetail;
    }

    @Override
    public String toString() {
        return "ImgResultDetail{" +
                "cellid='" + cellid + '\'' +
                ", status='" + status + '\'' +
                ", goodDetail=" + goodDetail +
                '}';
    }
}
