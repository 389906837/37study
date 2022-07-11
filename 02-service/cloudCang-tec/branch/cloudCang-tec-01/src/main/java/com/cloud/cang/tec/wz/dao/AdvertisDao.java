package com.cloud.cang.tec.wz.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.tec.wz.vo.AdvertisVo;
import com.cloud.cang.tec.wz.vo.RegionVo;

/**
 * 广告表(WZ_ADVERTIS)
 **/
public interface AdvertisDao extends GenericDao<Advertis, String> {

    /**
     * 广告过期定时任务 根据商户
     *
     * @param merchantId 商户ID
     * @return Advertis 广告数据
     */
    List<RegionVo> selectNotExpiredAdvertis(String merchantId);

    List<Advertis> selectListByVo(AdvertisVo paramAdvertis);

}