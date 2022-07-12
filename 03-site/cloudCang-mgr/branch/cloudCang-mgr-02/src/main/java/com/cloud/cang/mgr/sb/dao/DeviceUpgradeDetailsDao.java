package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDetailsDomain;
import com.cloud.cang.mgr.sb.vo.DeviceUpgradeDetailsVo;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.github.pagehelper.Page;

/**
 * 设备升级明细表(SB_DEVICE_UPGRADE_DETAILS)
 **/
public interface DeviceUpgradeDetailsDao extends GenericDao<DeviceUpgradeDetails, String> {

    /**
     * 分页查询设备升级记录明细
     *
     * @param deviceUpgradeDetailsVo
     * @return
     */
    Page<DeviceUpgradeDetailsDomain> selectByDomainWhere(DeviceUpgradeDetailsVo deviceUpgradeDetailsVo);

    /**
     * 根据ID查询domain信息
     *
     * @param sid
     * @return
     */
    DeviceUpgradeDetailsDomain selectDomainById(String sid);


    /** codegen **/
}