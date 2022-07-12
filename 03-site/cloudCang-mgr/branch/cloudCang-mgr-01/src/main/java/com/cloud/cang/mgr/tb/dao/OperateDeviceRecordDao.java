package com.cloud.cang.mgr.tb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.tb.domain.OperateDeviceRecordDomain;
import com.cloud.cang.mgr.tb.vo.OperateDeviceRecordVo;
import com.cloud.cang.model.tb.OperateDeviceRecord;
import com.github.pagehelper.Page;

/**
 * 第三方订单/补货记录(TB_OPERATE_DEVICE_RECORD)
 **/
public interface OperateDeviceRecordDao extends GenericDao<OperateDeviceRecord, String> {


    /**
     * 分页查询
     *
     * @param operateDeviceRecordVo
     * @return
     */
    Page<OperateDeviceRecordDomain> selectByDomainWhere(OperateDeviceRecordVo operateDeviceRecordVo);


    /** codegen **/
}