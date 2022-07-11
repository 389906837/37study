package com.cloud.cang.open.api.sb.service;

import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface DeviceUpgradeDetailsService extends GenericService<DeviceUpgradeDetails, String> {


    /**
     * 更新数据
     * @param paramMap 更新参数
     * @return
     */
    Integer updateByMap(Map<String, Object> paramMap);
}