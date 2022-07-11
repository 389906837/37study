package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * @version 1.0
 * @Description: 苏宁棚格图信息
 * @Author: zengzexiong
 * @Date: 2018年9月4日14:28:46
 */
public class SuNingShedModel extends SuperDto {

    List<SuNingShedGoodModel> suNingShedGoodModelList;

    public List<SuNingShedGoodModel> getSuNingShedGoodModelList() {
        return suNingShedGoodModelList;
    }

    public void setSuNingShedGoodModelList(List<SuNingShedGoodModel> suNingShedGoodModelList) {
        this.suNingShedGoodModelList = suNingShedGoodModelList;
    }
}
