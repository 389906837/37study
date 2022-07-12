package com.cloud.cang.mgr.sl.service;

import com.cloud.cang.mgr.sl.domain.LoginDomain;
import com.cloud.cang.mgr.sl.vo.LoginLogVo;
import com.cloud.cang.model.sl.LoginLog;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface LoginLogService extends GenericService<LoginLog, String> {

    /**
     * 登录日志查询接口
     * @param page
     * @param loginLogVo
     * @return
     */
    Page<LoginDomain> queryLoginLog(Page<LoginDomain> page, LoginLogVo loginLogVo);
}