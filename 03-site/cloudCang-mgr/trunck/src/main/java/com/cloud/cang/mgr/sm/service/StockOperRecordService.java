package com.cloud.cang.mgr.sm.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.sm.domain.StockOperRecordDomain;
import com.cloud.cang.mgr.sm.vo.StockOperLogVo;
import com.cloud.cang.model.sm.StockOperRecord;
import com.github.pagehelper.Page;

public interface StockOperRecordService extends GenericService<StockOperRecord, String> {

    Page<StockOperRecordDomain> stockoperLog(Page<StockOperRecordDomain> page, StockOperLogVo stockOperLogVo);
}