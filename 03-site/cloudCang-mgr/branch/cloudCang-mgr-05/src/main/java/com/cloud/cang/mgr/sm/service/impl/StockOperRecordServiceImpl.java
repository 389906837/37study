package com.cloud.cang.mgr.sm.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sm.dao.StockOperRecordDao;
import com.cloud.cang.mgr.sm.domain.StockOperRecordDomain;
import com.cloud.cang.mgr.sm.service.StockOperRecordService;
import com.cloud.cang.mgr.sm.vo.StockOperLogVo;
import com.cloud.cang.model.sm.StockOperRecord;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockOperRecordServiceImpl extends GenericServiceImpl<StockOperRecord, String> implements
		StockOperRecordService {

	@Autowired
	StockOperRecordDao stockOperRecordDao;

	
	@Override
	public GenericDao<StockOperRecord, String> getDao() {
		return stockOperRecordDao;
	}


	@Override
	public Page<StockOperRecordDomain> stockoperLog(Page<StockOperRecordDomain> page, StockOperLogVo stockOperLogVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		String spName = stockOperLogVo.getSpName();
		if (StringUtils.isNotBlank(spName)) {
			char[] chars = spName.toCharArray();
			stockOperLogVo.setSpName(StringUtils.join(chars, '%'));
		}
		return stockOperRecordDao.queryStockOperLog(stockOperLogVo);
	}
}