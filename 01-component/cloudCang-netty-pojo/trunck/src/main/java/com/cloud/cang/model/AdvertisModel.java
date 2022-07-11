package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.util.Date;

/**
 * @version 1.0
 * @Description: 广告model
 * @Author: zhouhong
 * @Date: 2018/3/31 18:15
 */
public class AdvertisModel extends SuperDto {
    private String id;//广告ID
    /* 广告类型
            10=图片
            20=视频
            30=音频 */
    private Integer iadvType;
    /* 广告标题 */
    private String stitle;
    /* 显示时间（单位秒） */
    private Integer ishowTime;
    /* 排序号 */
    private Integer isort;
    /* 背景图片地址音频可用 */
    private String sbgImgUrl;
    /* 广告资源地址 */
    private String scontentUrl;
    /* 来源标题 */
    private String ssourceTitle;
    /* 开始日期 */
    private Date tstartDate;
    /* 结束日期 */
    private Date tendDate;
    /* 超链接 */
    private String shref;
    /* 联系人(预留) */
    private String scontactName;
    /* 手机(预留) */
    private String smobile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    public Integer getIshowTime() {
        return ishowTime;
    }

    public void setIshowTime(Integer ishowTime) {
        this.ishowTime = ishowTime;
    }

    public Integer getIsort() {
        return isort;
    }

    public void setIsort(Integer isort) {
        this.isort = isort;
    }

    public String getSbgImgUrl() {
        return sbgImgUrl;
    }

    public void setSbgImgUrl(String sbgImgUrl) {
        this.sbgImgUrl = sbgImgUrl;
    }

    public String getScontentUrl() {
        return scontentUrl;
    }

    public void setScontentUrl(String scontentUrl) {
        this.scontentUrl = scontentUrl;
    }

    public String getSsourceTitle() {
        return ssourceTitle;
    }

    public void setSsourceTitle(String ssourceTitle) {
        this.ssourceTitle = ssourceTitle;
    }

    public Date getTstartDate() {
        return tstartDate;
    }

    public void setTstartDate(Date tstartDate) {
        this.tstartDate = tstartDate;
    }

    public Date getTendDate() {
        return tendDate;
    }

    public void setTendDate(Date tendDate) {
        this.tendDate = tendDate;
    }

    public String getShref() {
        return shref;
    }

    public void setShref(String shref) {
        this.shref = shref;
    }

    public String getScontactName() {
        return scontactName;
    }

    public void setScontactName(String scontactName) {
        this.scontactName = scontactName;
    }

    public String getSmobile() {
        return smobile;
    }

    public void setSmobile(String smobile) {
        this.smobile = smobile;
    }

    public Integer getIadvType() {
        return iadvType;
    }

    public void setIadvType(Integer iadvType) {
        this.iadvType = iadvType;
    }

    @Override
    public String toString() {
        return "AdvertisModel{" +
                "id='" + id + '\'' +
                ", iadvType=" + iadvType +
                ", stitle='" + stitle + '\'' +
                ", ishowTime=" + ishowTime +
                ", isort=" + isort +
                ", sbgImgUrl='" + sbgImgUrl + '\'' +
                ", scontentUrl='" + scontentUrl + '\'' +
                ", ssourceTitle='" + ssourceTitle + '\'' +
                ", tstartDate=" + tstartDate +
                ", tendDate=" + tendDate +
                ", shref='" + shref + '\'' +
                ", scontactName='" + scontactName + '\'' +
                ", smobile='" + smobile + '\'' +
                '}';
    }
}