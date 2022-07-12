package com.cloud.cang.mgr.sl.service;

import com.cloud.cang.mgr.sl.domain.SystemAlarmDomain;
import com.cloud.cang.mgr.sl.vo.SystemAlarmVo;
import com.cloud.cang.model.sl.SystemAlarm;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface SystemAlarmService extends GenericService<SystemAlarm, String> {

    /**
     * 查询系统报警日志
     * @param page
     * @param systemAlarmVo
     * @return
     */
    Page<SystemAlarmDomain> selectSystemAlarm(Page<SystemAlarmDomain> page, SystemAlarmVo systemAlarmVo);
}