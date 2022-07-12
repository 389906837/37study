package com.cloud.cang.mgr.sl.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.sl.vo.CheckLogVo;
import com.cloud.cang.model.sl.CheckLog;
import com.github.pagehelper.Page;

public interface CheckLogService extends GenericService<CheckLog, String> {

    /**
     * 对账日志接口
     * @param page
     * @param checkLogVo
     * @return
     */
    Page<CheckLog> checkLog(Page<CheckLog> page, CheckLogVo checkLogVo);
}