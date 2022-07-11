package com.cloud.cang.wap.rm.service;

import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.generic.GenericService;

public interface ReplenishmentRecordService extends GenericService<ReplenishmentRecord, String> {

    /**
     * 获取补货单
     * @param recordCode 补货单编号
     * @return
     */
    ReplenishmentRecord selectByCode(String recordCode);
}