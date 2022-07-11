package com.cloud.cang.wap.sp.service;

import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.ac.vo.CommodityVo;

import java.util.List;
import java.util.Map;

public interface CommodityInfoService extends GenericService<CommodityInfo, String> {

    List<CommodityVo> selectByCondition(Map<String, String> map);
}