package com.cloud.cang.tec.monitor.service.impl;

import com.cloud.cang.tec.monitor.dao.MonitorDao;
import com.cloud.cang.tec.monitor.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class MonitorServiceImpl implements MonitorService {

	@Autowired
    MonitorDao monitorDao;
	@Override
	public Map<String, Object> executeSql(String sql) {
		return monitorDao.executeSql(sql);
	}
	@Override
	public Integer queryCountBySql(String sql) {
		return this.monitorDao.queryCountBySql(sql);
	}
	@Override
	public BigDecimal queryAmountBySql(String sql) {
		return this.monitorDao.queryAmountBySql(sql);
	}

}
