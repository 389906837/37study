package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDetailsDomain;
import com.cloud.cang.mgr.sb.vo.DeviceUpgradeDetailsVo;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface DeviceUpgradeDetailsService extends GenericService<DeviceUpgradeDetails, String> {


    /**
     * 分页查询升级记录明细
     *
     * @param page
     * @param deviceUpgradeDetailsVo
     * @return
     */
    Page<DeviceUpgradeDetailsDomain> selectPageByDomainWhere(Page<DeviceUpgradeDetailsDomain> page, DeviceUpgradeDetailsVo deviceUpgradeDetailsVo);


    /**
     * 升级明细详情
     *
     * @param sid
     * @return
     */
    DeviceUpgradeDetailsDomain selectDomainById(String sid);

}