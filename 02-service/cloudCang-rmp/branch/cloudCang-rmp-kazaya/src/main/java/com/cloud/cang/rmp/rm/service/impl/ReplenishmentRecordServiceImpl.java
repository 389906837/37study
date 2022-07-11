package com.cloud.cang.rmp.rm.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.rmp.rm.dao.ReplenishmentRecordDao;
import com.cloud.cang.rmp.rm.service.ReplenishmentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReplenishmentRecordServiceImpl extends GenericServiceImpl<ReplenishmentRecord, String> implements
		ReplenishmentRecordService {

	@Autowired
	ReplenishmentRecordDao replenishmentRecordDao;

	@Override
	public GenericDao<ReplenishmentRecord, String> getDao() {
		return replenishmentRecordDao;
	}


}
