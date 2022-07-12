package com.cloud.cang.mgr.sl.service;

import com.cloud.cang.mgr.sl.vo.VistLogVo;
import com.cloud.cang.model.sl.VistLog;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface VistLogService extends GenericService<VistLog, String> {

    /**
     * 查询访问日志
     * @param page
     * @param vistLogVo
     * @return
     */
    Page<VistLog> queryVistLog(Page<VistLog> page, VistLogVo vistLogVo);
}