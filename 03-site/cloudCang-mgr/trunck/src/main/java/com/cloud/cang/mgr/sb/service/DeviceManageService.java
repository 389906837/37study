package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.mgr.sb.domain.DeviceManageDomain;
import com.cloud.cang.mgr.sb.domain.DeviceModelDomain;
import com.cloud.cang.mgr.sb.vo.DeviceManageVo;
import com.cloud.cang.model.sb.DeviceManage;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface DeviceManageService extends GenericService<DeviceManage, String> {


    /**
     * 根据设备ID查询设备管理信息
     * @param deviceId
     * @return
     */
    DeviceManage selectByDeviceId(String deviceId);

    /**
     * 分页查询
     * @param page
     * @param deviceManageVo
     * @return
     */
    Page<DeviceModelDomain> selectPageByDomainWhere(Page<DeviceModelDomain> page, DeviceManageVo deviceManageVo);

    /**
     * 设备管理（负责人）ID查询设备编号名称，商户编号名称
     * @param sid
     * @return
     */
    DeviceManageDomain selectViewBySid(String sid);

    /**
     * 自定义修改
     * @param deviceManage
     * @return
     */
    int updateBySelectiveVO1(DeviceManage deviceManage);
}