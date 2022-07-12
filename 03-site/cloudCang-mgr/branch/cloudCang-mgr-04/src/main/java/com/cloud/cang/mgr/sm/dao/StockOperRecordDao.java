package com.cloud.cang.mgr.sm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sm.domain.StockOperRecordDomain;
import com.cloud.cang.mgr.sm.vo.StockOperLogVo;
import com.cloud.cang.model.sm.StockOperRecord;
import com.github.pagehelper.Page;

/** 单设备库存操作纪律(SM_STOCK_OPER_RECORD) **/
public interface StockOperRecordDao extends GenericDao<StockOperRecord, String> {

    Page<StockOperRecordDomain> queryStockOperLog(StockOperLogVo stockOperLogVo);
}