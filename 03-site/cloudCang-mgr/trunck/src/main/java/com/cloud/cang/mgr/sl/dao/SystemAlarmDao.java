package com.cloud.cang.mgr.sl.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sl.domain.SystemAlarmDomain;
import com.cloud.cang.mgr.sl.vo.SystemAlarmVo;
import com.cloud.cang.model.sl.SystemAlarm;
import com.github.pagehelper.Page;

/** 系统警报日志(SL_SYSTEM_ALARM) **/
public interface SystemAlarmDao extends GenericDao<SystemAlarm, String> {

    /**
     * 查询系统报警日志
     * @param systemAlarmVo
     * @return
     */
    Page<SystemAlarmDomain> selectSystemAlarm(SystemAlarmVo systemAlarmVo);
}