package com.cloud.cang.api.wz.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.AdModel;
import com.cloud.cang.model.wz.Advertis;

import java.util.List;
import java.util.Map;

/**
 * 广告表(WZ_ADVERTIS)
 **/
public interface AdvertisDao extends GenericDao<Advertis, String> {

    /**
     * 根据商户ID与运营位ID查询商户下的广告
     *
     * @param map
     * @return
     */
    List<AdModel> selectAdByMerchantId(Map<String, String> map);


    /** codegen **/
}