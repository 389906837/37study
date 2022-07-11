package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

/**
 * Created by Alex on 2018/4/2.
 */
public class VersionModel extends SuperDto {
    private String version;         // 版本号
    private String pubtime;         // 升级时间
    private String url;             // 升级地址
    private String detailId;        // 升级记录明细表ID

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    @Override
    public String toString() {
        return "VersionModel{" +
                "version='" + version + '\'' +
                ", pubtime='" + pubtime + '\'' +
                ", detailId='" + detailId + '\'' +
                '}';
    }
}
