package com.cloud.cang.mgr.rm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.rm.domain.ReplenishMentDomain;
import com.cloud.cang.mgr.rm.vo.ReplenishMentVo;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 商品补货记录信息表(RM_REPLENISHMENT_RECORD) **/
public interface ReplenishmentRecordDao extends GenericDao<ReplenishmentRecord, String> {

    Page<ReplenishMentDomain> selectReplenishMent(ReplenishMentVo replenishMentVo);

    /**
     * 根据查询条件获取导出补货信息
     * @param replenishMentVo
     * @return
     */
    List<Map<String,Object>> selectReplenishMentByExport(ReplenishMentVo replenishMentVo);
}