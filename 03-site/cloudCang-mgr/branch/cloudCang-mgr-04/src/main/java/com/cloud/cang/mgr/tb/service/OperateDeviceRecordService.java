package com.cloud.cang.mgr.tb.service;

import com.cloud.cang.mgr.tb.domain.OperateDeviceRecordDomain;
import com.cloud.cang.mgr.tb.vo.OperateDeviceRecordVo;
import com.cloud.cang.model.tb.OperateDeviceRecord;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface OperateDeviceRecordService extends GenericService<OperateDeviceRecord, String> {


    /**
     *  分页查询
     * @param page
     * @param operateDeviceRecordVo
     * @return
     */
    Page<OperateDeviceRecordDomain> selectPageByDomainWhere(Page<OperateDeviceRecordDomain> page, OperateDeviceRecordVo operateDeviceRecordVo);

}