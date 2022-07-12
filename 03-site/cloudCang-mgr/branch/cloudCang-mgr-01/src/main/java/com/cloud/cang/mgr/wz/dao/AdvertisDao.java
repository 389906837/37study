package com.cloud.cang.mgr.wz.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.wz.domain.AdvertisDomain;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.model.wz.Advertis;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 广告表(MYSQL)(WZ_ADVERTIS) **/
public interface AdvertisDao extends GenericDao<Advertis, String> {

    Page<AdvertisDomain> selectAdvertis(AdvertisVo advertisVo);

    Page<AdvertisDomain> selectSregionId(Map<String, Object> map);

    List<Advertis> selectListByVo(AdvertisVo paramAdvertis);

    List<Advertis> selectByRrgionId(String rid);

    void updateDefault(Map<String,String> map);

}