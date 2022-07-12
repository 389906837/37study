package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.mgr.sb.domain.DeviceMoveRecordDomain;
import com.cloud.cang.mgr.sb.vo.DeviceMoveRecordVo;
import com.cloud.cang.model.sb.DeviceMoveRecord;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface DeviceMoveRecordService extends GenericService<DeviceMoveRecord, String> {

    /**
     * 查询所有
     * @param page
     * @param deviceMoveRecordVo
     * @return
     */
    Page<DeviceMoveRecordDomain> selectqueryData(Page<DeviceMoveRecordDomain> page, DeviceMoveRecordVo deviceMoveRecordVo);

    /**
     * 删除设备搬迁信息
     * @param checkboxId
     */
    void delete(String[] checkboxId);
}