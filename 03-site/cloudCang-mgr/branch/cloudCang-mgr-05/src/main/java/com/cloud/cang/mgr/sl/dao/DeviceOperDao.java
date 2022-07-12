package com.cloud.cang.mgr.sl.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sl.domain.OperatelogDomain;
import com.cloud.cang.mgr.sl.vo.DeviceLogVo;
import com.cloud.cang.model.sl.DeviceOper;
import com.github.pagehelper.Page;

/** 设备操作日志(SL_DEVICE_OPER) **/
public interface DeviceOperDao extends GenericDao<DeviceOper, String> {

    Page<OperatelogDomain> queryDeviceLog(DeviceLogVo deviceLogVo);
}