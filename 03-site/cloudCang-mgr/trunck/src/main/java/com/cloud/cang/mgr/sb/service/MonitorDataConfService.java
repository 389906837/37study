package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.mgr.sb.domain.MonitorDataConfDomain;
import com.cloud.cang.mgr.sb.vo.MonitorDataConfVo;
import com.cloud.cang.model.sb.MonitorDataConf;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface MonitorDataConfService extends GenericService<MonitorDataConf, String> {

    /**
     * 根据设备ID查询设备设备监控信息
     * @param deviceId
     * @return
     */
    MonitorDataConf selectByDeviceId(String deviceId);

    /**
     * 分页查询
     * @param page
     * @param monitorDataConfVo
     * @return
     */
    Page<MonitorDataConfDomain> selectPageByDomainWhere(Page<MonitorDataConfDomain> page, MonitorDataConfVo monitorDataConfVo);

    /**
     * 根据设备监控配置ID查询配置表，设备表，商品表
     * @param sid
     * @return
     */
    MonitorDataConfDomain selectViewBySid(String sid);

}