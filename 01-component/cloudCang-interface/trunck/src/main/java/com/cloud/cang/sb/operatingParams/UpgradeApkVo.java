package com.cloud.cang.sb.operatingParams;

import com.cloud.cang.common.SuperDto;

/**
 * @version 1.0
 * @ClassName UpgradeApkVo
 * @Description mgr后台操作设备--升级APK
 * @Author zengzexiong
 * @Date 2019年1月23日
 */
public class UpgradeApkVo extends SuperDto {
    private String version;         // 版本号
    private String pubtime;         // 升级时间
    private String url;             // 升级地址
    private String smainId;        // 升级主表ID
    private String timeType;        // 时间类型 10=立即，20=定时

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmainId() {
        return smainId;
    }

    public void setSmainId(String smainId) {
        this.smainId = smainId;
    }

    @Override
    public String toString() {
        return "UpgradeApkVo{" +
                "version='" + version + '\'' +
                ", pubtime='" + pubtime + '\'' +
                ", url='" + url + '\'' +
                ", smainId='" + smainId + '\'' +
                ", timeType='" + timeType + '\'' +
                '}';
    }
}
