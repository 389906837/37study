package com.cloud.cang.tec.ac.service;

import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.tec.ac.vo.ActivityConfVo;

import java.util.List;

public interface ActivityConfService extends GenericService<ActivityConf, String> {
    /**
     * 关闭商户过期且未关闭的活动
     *
     * @param merchantId 商户ID
     */
    int upActivityStatus(String merchantId);


    /**
     * 查询商户即将过期(数据字典配置时间)的场景活动
     *
     * @param merchantId 商户ID
     */
    ActivityConfVo selectExpireSceneActivity(String merchantId);

    /**
     * 查询商户过期且未关闭的活动
     *
     * @param merchantId 商户ID
     */
    List<ActivityConf> selectExpireActivity(String merchantId);
}