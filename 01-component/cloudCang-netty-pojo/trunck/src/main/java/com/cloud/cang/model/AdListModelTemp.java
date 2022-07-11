package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @Description: 广告对象集合
 * @Author: zengzexiong
 * @Date: 2018年4月17日14:00:30
 */
public class AdListModelTemp extends SuperDto {
    private List<AdModel> adModelList;//横屏广告
    private List<AdModel> vcadModelList;//竖屏广告

    public List<AdModel> getAdModelList() {
        return adModelList;
    }

    public void setAdModelList(List<AdModel> adModelList) {
        this.adModelList = adModelList;
    }

    public List<AdModel> getVcadModelList() {
        return vcadModelList;
    }

    public void setVcadModelList(List<AdModel> vcadModelList) {
        this.vcadModelList = vcadModelList;
    }

    @Override
    public String toString() {
        return "AdListModel{" +
                "adModelList=" + adModelList +
                ", vcadModelList=" + vcadModelList +
                '}';
    }
}
