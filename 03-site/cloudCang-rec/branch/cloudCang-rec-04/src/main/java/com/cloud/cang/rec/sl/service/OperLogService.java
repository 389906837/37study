package com.cloud.cang.rec.sl.service;

import com.cloud.cang.rec.sl.vo.VistLogVo;
import com.cloud.cang.model.sl.OperLog;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface OperLogService extends GenericService<OperLog, String> {

    /**
     * 操作日志查询
     * @param page
     * @param vistLogVo
     * @return
     */
    Page<OperLog> queryOperLog(Page<OperLog> page, VistLogVo vistLogVo);
}