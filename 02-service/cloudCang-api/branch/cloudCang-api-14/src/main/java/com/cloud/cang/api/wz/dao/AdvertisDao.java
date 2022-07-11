package com.cloud.cang.api.wz.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.AdModel;
import com.cloud.cang.model.AdvertisModel;
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
    /**
     * 查询设备广告信息
     * @param paramMap scode 设备编号 sregionCode 广告运营位
     * @return
     */
    List<AdvertisModel> selectByDeviceCodeAndRegionCode(Map<String, Object> paramMap);
}