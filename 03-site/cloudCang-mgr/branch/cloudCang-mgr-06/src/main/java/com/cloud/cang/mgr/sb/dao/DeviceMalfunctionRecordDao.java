package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DeviceMalfunctionRecordDomain;
import com.cloud.cang.mgr.sb.vo.DeviceMalfunctionRecordVo;
import com.cloud.cang.model.sb.DeviceMalfunctionRecord;
import com.github.pagehelper.Page;

/** 设备故障信息记录(SB_DEVICE_MALFUNCTION_RECORD) **/
public interface DeviceMalfunctionRecordDao extends GenericDao<DeviceMalfunctionRecord, String> {
    /**
     * 查询设备故障信息
     * @param deviceMalfunctionRecordVo
     * @return
     */
    Page<DeviceMalfunctionRecordDomain> selectqueryData(DeviceMalfunctionRecordVo deviceMalfunctionRecordVo);
}