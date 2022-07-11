package com.cloud.cang.bzc.ac.service.impl;

import com.cloud.cang.bzc.ac.dao.DiscountRecordDao;
import com.cloud.cang.bzc.ac.service.DiscountRecordService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.ac.DiscountRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DiscountRecordServiceImpl extends GenericServiceImpl<DiscountRecord, String> implements
		DiscountRecordService {

	@Autowired
	DiscountRecordDao discountRecordDao;

	
	@Override
	public GenericDao<DiscountRecord, String> getDao() {
		return discountRecordDao;
	}



	@Override
	public Map<String, Integer> queryUserAllCount(String userId) {
		return discountRecordDao.selectUserAllCountByUserId();
	}

	@Override
	public Map<String, Integer> queryUserDayCount(String userId) {
		return discountRecordDao.selectUserDayCountByUserId();
	}

	@Override
	public Map<String, Integer> queryDeviceAllCount(String sdeviceCode) {
		return discountRecordDao.selectDeviceAllCountByDeviceCode();
	}

	@Override
	public Map<String, Integer> queryDeviceDayCount(String sdeviceCode) {
		return discountRecordDao.selectDeviceDayCountByDeviceCode();
	}


}