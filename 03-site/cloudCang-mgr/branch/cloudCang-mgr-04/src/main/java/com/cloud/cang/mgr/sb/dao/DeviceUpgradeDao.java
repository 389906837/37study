package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDomain;
import com.cloud.cang.mgr.sb.vo.DeviceUpgradeVo;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.github.pagehelper.Page;

/**
 * 设备升级记录(SB_DEVICE_UPGRADE)
 **/
public interface DeviceUpgradeDao extends GenericDao<DeviceUpgrade, String> {


    Page<DeviceUpgradeDomain> selectByDomainWhere(DeviceUpgradeVo deviceUpgradeVo);


    /** codegen **/
}