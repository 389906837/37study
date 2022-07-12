package com.cloud.cang.mgr.rm.service;


import com.cloud.cang.mgr.rm.domain.ReplenishMentDomain;
import com.cloud.cang.mgr.rm.vo.ReplenishMentVo;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ReplenishmentRecordService extends GenericService<ReplenishmentRecord, String> {

    Page<ReplenishMentDomain> selectReplenishMent(Page<ReplenishMentDomain> page, ReplenishMentVo replenishMentVo);

    /**
     * 根据查询条件获取导出补货信息
     *
     * @param replenishMentVo
     * @return
     */
    List<Map<String, Object>> selectReplenishMentByExport(ReplenishMentVo replenishMentVo);
}