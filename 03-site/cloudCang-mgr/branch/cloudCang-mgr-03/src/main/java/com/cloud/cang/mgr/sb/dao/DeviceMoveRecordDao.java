package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DeviceMoveRecordDomain;
import com.cloud.cang.mgr.sb.vo.DeviceMoveRecordVo;
import com.cloud.cang.model.sb.DeviceMoveRecord;
import com.github.pagehelper.Page;

/** 设备搬迁记录(SB_DEVICE_MOVE_RECORD) **/
public interface DeviceMoveRecordDao extends GenericDao<DeviceMoveRecord, String> {
    /**
     * 查询所有
     * @param deviceMoveRecordVo
     * @return
     */
    Page<DeviceMoveRecordDomain> selectqueryData(DeviceMoveRecordVo deviceMoveRecordVo);
}