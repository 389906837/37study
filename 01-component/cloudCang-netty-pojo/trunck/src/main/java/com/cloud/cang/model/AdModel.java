package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @Description: 广告对象
 * @Author: zengzexiong
 * @Date: 2018年4月17日10:07:41
 */
public class AdModel extends SuperDto {
    private String adId;                // 内容ID
    private String scontentUrl;         // 内容地址
    private String shref;               // 超链接地址
    private Integer iadvType;           // 广告类型
    private Integer ilinkType;          // 链接类型

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getScontentUrl() {
        return scontentUrl;
    }

    public void setScontentUrl(String scontentUrl) {
        this.scontentUrl = scontentUrl;
    }

    public String getShref() {
        return shref;
    }

    public void setShref(String shref) {
        this.shref = shref;
    }

    public Integer getIadvType() {
        return iadvType;
    }

    public void setIadvType(Integer iadvType) {
        this.iadvType = iadvType;
    }

    public Integer getIlinkType() {
        return ilinkType;
    }

    public void setIlinkType(Integer ilinkType) {
        this.ilinkType = ilinkType;
    }

    @Override
    public String toString() {
        return "AdModel{" +
                "adId='" + adId + '\'' +
                ", scontentUrl='" + scontentUrl + '\'' +
                ", shref='" + shref + '\'' +
                ", iadvType=" + iadvType +
                ", ilinkType=" + ilinkType +
                '}';
    }
}
