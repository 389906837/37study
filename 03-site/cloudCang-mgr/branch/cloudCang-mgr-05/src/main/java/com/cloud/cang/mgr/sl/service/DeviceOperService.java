package com.cloud.cang.mgr.sl.service;

import com.cloud.cang.mgr.sl.domain.OperatelogDomain;
import com.cloud.cang.mgr.sl.vo.DeviceLogVo;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface DeviceOperService extends GenericService<DeviceOper, String> {

    /**
     * 设备操作日志查询接口
     * @param page
     * @param deviceLogVo
     * @return
     */
    Page<OperatelogDomain> deviceOperLog(Page<OperatelogDomain> page, DeviceLogVo deviceLogVo);
}