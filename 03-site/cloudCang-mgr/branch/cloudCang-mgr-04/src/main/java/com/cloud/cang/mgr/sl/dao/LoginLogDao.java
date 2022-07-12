package com.cloud.cang.mgr.sl.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sl.domain.LoginDomain;
import com.cloud.cang.mgr.sl.vo.LoginLogVo;
import com.cloud.cang.model.sl.LoginLog;
import com.github.pagehelper.Page;

/** 会员登录日志记录(SL_LOGIN_LOG) **/
public interface LoginLogDao extends GenericDao<LoginLog, String> {

    Page<LoginDomain> queryLoginLog(LoginLogVo loginLogVo);
}