package com.cloud.cang.open.api.sb.service;

import com.cloud.cang.model.sb.DeviceUpgrade;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface DeviceUpgradeService extends GenericService<DeviceUpgrade, String> {

    /**
     * 获取更新记录 并同步锁
     * @param updateRecodeCode
     * @return
     */
    DeviceUpgrade selectSyncByPrimaryKey(String updateRecodeCode);
}