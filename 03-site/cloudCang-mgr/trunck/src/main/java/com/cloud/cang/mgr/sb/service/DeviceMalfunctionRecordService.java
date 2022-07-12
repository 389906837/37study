package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.mgr.sb.domain.DeviceMalfunctionRecordDomain;
import com.cloud.cang.mgr.sb.vo.DeviceMalfunctionRecordVo;
import com.cloud.cang.model.sb.DeviceMalfunctionRecord;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface DeviceMalfunctionRecordService extends GenericService<DeviceMalfunctionRecord, String> {
    /**
     * 查询设备故障所有信息
     * @param page
     * @param deviceMalfunctionRecordVo
     * @return
     */
    Page<DeviceMalfunctionRecordDomain> selectqueryData(Page<DeviceMalfunctionRecordDomain> page, DeviceMalfunctionRecordVo deviceMalfunctionRecordVo);

    /**
     * 删除故障信息
     * @param checkboxId
     */
    void delete(String[] checkboxId);
}