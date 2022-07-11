package com.cloud.cang.wap.rm.service;

import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.rm.vo.HistoryReplenishmentVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface ReplenishmentRecordService extends GenericService<ReplenishmentRecord, String> {

    /**
     * 获取补货单
     *
     * @param recordCode 补货单编号
     * @return
     */
    ReplenishmentRecord selectByCode(String recordCode);

    /**
     * 获取历史补货单
     *
     * @param
     * @return
     */
    Page<HistoryReplenishmentVo>  selectHistoryReplenishment(Page<HistoryReplenishmentVo> page, Map<String, Object> map);


}