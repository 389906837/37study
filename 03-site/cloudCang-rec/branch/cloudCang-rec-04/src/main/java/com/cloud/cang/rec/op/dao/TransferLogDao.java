package com.cloud.cang.rec.op.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.TransferLog;
import com.cloud.cang.rec.op.domain.TransferLogDomain;
import com.cloud.cang.rec.op.vo.TransferLogVo;
import com.github.pagehelper.Page;

/**
 * 接口调用日志表(OP_TRANSFER_LOG)
 **/
public interface TransferLogDao extends GenericDao<TransferLog, String> {


    Page<TransferLogVo> queryTransferLog(TransferLogDomain transferLogDomain);
}