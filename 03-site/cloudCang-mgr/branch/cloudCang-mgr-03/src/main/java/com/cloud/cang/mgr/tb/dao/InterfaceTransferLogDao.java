package com.cloud.cang.mgr.tb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.tb.domain.InterfaceTransferLogDomain;
import com.cloud.cang.mgr.tb.vo.InterfaceTransferLogVo;
import com.cloud.cang.model.tb.InterfaceTransferLog;
import com.github.pagehelper.Page;

/**
 * 第三方接口调用记录表(TB_INTERFACE_TRANSFER_LOG)
 **/
public interface InterfaceTransferLogDao extends GenericDao<InterfaceTransferLog, String> {

    /**
     * 第三方接口调用查询
     *
     * @param interfaceTransferLogVo
     * @return
     */
    Page<InterfaceTransferLogDomain> selectByDomainWhere(InterfaceTransferLogVo interfaceTransferLogVo);


    /** codegen **/
}