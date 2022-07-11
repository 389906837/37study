package com.cloud.cang.tec.wz.service;

import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.tec.wz.vo.AdvertisVo;
import com.cloud.cang.tec.wz.vo.RegionVo;

import java.util.List;

public interface AdvertisService extends GenericService<Advertis, String> {

    /**
     * 广告过期定时任务 根据商户
     *
     * @param merchantId 商户ID
     * @return Advertis 广告数据
     */
    List<RegionVo> selectNotExpiredAdvertis(String merchantId);
    /**
     * 更新缓存
     * @param region 运营位
     * @param smerchantCode 商户编号
     */
    void updateByCached(Region region, String smerchantCode) throws Exception;
    List<Advertis> selectListByVo(AdvertisVo paramAdvertis);
}