package com.cloud.cang.api.sp.service.impl;

import com.cloud.cang.api.sp.dao.CommodityRfidDao;
import com.cloud.cang.api.sp.service.CommodityRfidService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sp.CommodityRfid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityRfidServiceImpl extends GenericServiceImpl<CommodityRfid, String> implements
		CommodityRfidService {

	@Autowired
	CommodityRfidDao commodityRfidDao;

	
	@Override
	public GenericDao<CommodityRfid, String> getDao() {
		return commodityRfidDao;
	}


	@Override
	public List<CommodityRfid> commdityRfidList(String smerchantId, List<String> rfids) {
		return commodityRfidDao.commdityRfidList(smerchantId,rfids);
	}

	@Override
	public void batchDeleteByRfids(List<String> rfids) {
		if(rfids == null || rfids.size() < 1){
			return;
		}
		commodityRfidDao.batchDeleteByRfids(rfids);
	}
}