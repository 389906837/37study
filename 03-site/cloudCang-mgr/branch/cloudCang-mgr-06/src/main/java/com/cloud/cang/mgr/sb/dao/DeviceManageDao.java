package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DeviceModelDomain;
import com.cloud.cang.mgr.sb.vo.DeviceManageVo;
import com.cloud.cang.model.sb.DeviceManage;
import com.github.pagehelper.Page;

/**
 * 设备管理信息表(SB_DEVICE_MANAGE)
 **/
public interface DeviceManageDao extends GenericDao<DeviceManage, String> {

    /**
     * 根据设备ID查询设备管理信息
     * @param deviceId
     * @return
     */
    DeviceManage selectByDeviceId(String deviceId);

    /**
     * 分页查询
     * @param deviceManageVo
     */
    Page<DeviceModelDomain> selectByDomainWhere(DeviceManageVo deviceManageVo);

    /**
     * 自定义修改方法
     * @param deviceManage
     * @return
     */
    int updateByIdSelectiveVo(DeviceManage deviceManage);

    /**
     * 自定义修改方法-- 备注可以为空
     * @param deviceManage
     * @return
     */
    int updateByIdSelectiveVo1(DeviceManage deviceManage);



    /** codegen **/
}