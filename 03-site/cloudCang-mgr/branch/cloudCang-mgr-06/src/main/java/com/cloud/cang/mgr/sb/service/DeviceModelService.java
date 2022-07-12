package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.mgr.sb.domain.DeviceModelDomain;
import com.cloud.cang.mgr.sb.vo.DeviceModelVo;
import com.cloud.cang.model.sb.DeviceModel;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface DeviceModelService extends GenericService<DeviceModel, String> {


    /**
     * 根据设备ID查询设备详细信息
     * @param deviceId
     * @return
     */
    DeviceModel selectByDeviceId(String deviceId);


    /**
     * 分页查询
     * @param page
     * @param deviceModelVo
     * @return
     */
    Page<DeviceModelDomain> selectPageByDomainWhere(Page<DeviceModelDomain> page, DeviceModelVo deviceModelVo);


    /**
     * 自定义修改model
     * 可以为空
     * @param deviceModel
     * @return
     */
    int updateBySelectiveVo(DeviceModel deviceModel);

    /**
     * 根据摄像头方法查询摄像头其他信息
     *
     * @param method
     * @return
     */
    String getCaremaInfoByMethod(String method);

}