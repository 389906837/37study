package com.cloud.cang.mgr.sl.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sl.vo.CheckLogVo;
import com.cloud.cang.model.sl.CheckLog;
import com.github.pagehelper.Page;

/** 对账日志表(SL_CHECK_LOG) **/
public interface CheckLogDao extends GenericDao<CheckLog, String> {

    Page<CheckLog> queryCheckLog(CheckLogVo checkLogVo);

}