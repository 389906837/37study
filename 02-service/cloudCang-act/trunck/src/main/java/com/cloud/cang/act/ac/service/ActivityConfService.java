package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface ActivityConfService extends GenericService<ActivityConf, String> {


    /**
     * 根据活动编号查找活动
     *
     * @param code
     * @return
     */
    ActivityConf selectActivityConfByCode(String code);

    /**
     * 根据活动编号和商户查找有效活动
     *
     * @param code
     * @return
     */
    ActivityConf selectActivityConfByCodeAndMerId(String code, String merchantId);

    /**
     * 查看设备活动
     *
     * @param
     * @return
     */
    List<String> viewActivityInformation(String merchantId);

    /**
     * 查询活动配置并锁表
     *
     * @param
     * @return
     */
    ActivityConf selectByKeyLocked(String acId);
}