package com.cloud.cang.rec.sl.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.rec.sl.vo.VistLogVo;
import com.cloud.cang.model.sl.OperLog;
import com.github.pagehelper.Page;

/** 系统操作日志(SL_OPER_LOG) **/
public interface OperLogDao extends GenericDao<OperLog, String> {


    Page<OperLog> queryOperLog(VistLogVo vistLogVo);
}