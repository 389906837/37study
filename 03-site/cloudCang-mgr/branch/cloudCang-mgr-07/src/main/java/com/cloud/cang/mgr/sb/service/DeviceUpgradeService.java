package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDomain;
import com.cloud.cang.mgr.sb.vo.DeviceUpgradeVo;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.github.pagehelper.Page;

public interface DeviceUpgradeService extends GenericService<DeviceUpgrade, String> {


    /**
     * 设备升级记录分页查询
     *
     * @param page
     * @param deviceUpgradeVo
     * @return
     */
    Page<DeviceUpgradeDomain> selectPageByDomainWhere(Page<DeviceUpgradeDomain> page, DeviceUpgradeVo deviceUpgradeVo);



}