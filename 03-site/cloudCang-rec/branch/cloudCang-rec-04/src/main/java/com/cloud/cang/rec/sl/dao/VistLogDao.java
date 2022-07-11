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
import com.cloud.cang.model.sl.VistLog;
import com.github.pagehelper.Page;

/** 系统访问日志(SL_VIST_LOG) **/
public interface VistLogDao extends GenericDao<VistLog, String> {


    Page<VistLog> queryVistLog(VistLogVo vistLogVo);
}