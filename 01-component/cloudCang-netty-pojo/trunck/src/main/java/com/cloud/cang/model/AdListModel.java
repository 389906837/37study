package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @Description: 广告对象集合
 * @Author: zengzexiong
 * @Date: 2018年4月17日14:00:30
 */
public class AdListModel extends SuperDto {
    private List<AdModel> adModelList;

    public List<AdModel> getAdModelList() {
        return adModelList;
    }

    public void setAdModelList(List<AdModel> adModelList) {
        this.adModelList = adModelList;
    }
}
