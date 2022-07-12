package com.cloud.cang.mgr.wz.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.wz.domain.AdvertisDomain;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.model.wz.Advertis;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 广告表(MYSQL)(WZ_ADVERTIS)
 **/
public interface AdvertisDao extends GenericDao<Advertis, String> {

    Page<AdvertisDomain> selectAdvertis(AdvertisVo advertisVo);

    Page<AdvertisDomain> selectSregionId(Map<String, Object> map);

    List<Advertis> selectListByVo(AdvertisVo paramAdvertis);

    List<Advertis> selectByRrgionId(String rid);

    void updateDefault(Map<String, String> map);

    /**
     * 查询广告列表数据
     *
     * @param advertisVo
     * @return
     */
    Page<AdvertisDomain> selectPageByDomainWhere(AdvertisVo advertisVo);

    /***
     * 查询广告信息
     * @param advId 资讯ID
     * @return
     */
    AdvertisDomain selectDomainByAdvId(String advId);

    /**
     * 分页查询广告资源数据
     * @param advertisVo 查询条件
     * @return
     */
    Page<AdvertisDomain> selectPageByDeviceId(AdvertisVo advertisVo);
}