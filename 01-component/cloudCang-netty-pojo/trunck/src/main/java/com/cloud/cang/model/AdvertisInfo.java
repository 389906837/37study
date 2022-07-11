package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @Description: 更新广告VO
 * @Author: zhouhong
 * @Date: 2018/3/31 18:15
 */
public class AdvertisInfo extends SuperDto {

    private String sregionCode; //运营区域编号
    private List<AdvertisModel> advertisModels;//广告信息
    private String ftpImagePath;

    public String getFtpImagePath() {
        return ftpImagePath;
    }

    public void setFtpImagePath(String ftpImagePath) {
        this.ftpImagePath = ftpImagePath;
    }

    public String getSregionCode() {
        return sregionCode;
    }

    public void setSregionCode(String sregionCode) {
        this.sregionCode = sregionCode;
    }

    public List<AdvertisModel> getAdvertisModels() {
        return advertisModels;
    }

    public void setAdvertisModels(List<AdvertisModel> advertisModels) {
        this.advertisModels = advertisModels;
    }

    @Override
    public String toString() {
        return "AdvertisInfo{" +
                "sregionCode='" + sregionCode + '\'' +
                ", advertisModels=" + advertisModels +
                ", ftpImagePath='" + ftpImagePath + '\'' +
                '}';
    }
}
