package com.cloud.cang.tec.rm.service;

import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.generic.GenericService;

public interface ReplenishmentRecordService extends GenericService<ReplenishmentRecord, String> {
    /**
     * 查询昨日补货数
     *
     * @param merchantId 商户ID
     * @return int
     */
    int selectYesterdayReplenishmentNum(String merchantId);
}