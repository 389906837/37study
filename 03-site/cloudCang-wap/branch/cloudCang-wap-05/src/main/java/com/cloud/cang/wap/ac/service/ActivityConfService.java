package com.cloud.cang.wap.ac.service;

import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface ActivityConfService extends GenericService<ActivityConf, String> {

    /**
     * 查询活动信息
     * @param map 查询参数
     * @return
     */
    ActivityConf selectByMap(Map<String, Object> map);
}