package com.cloud.cang.tec.monitor.service;

import java.math.BigDecimal;
import java.util.Map;

public interface MonitorService {

	Map<String,Object> executeSql(String sql);
	
	Integer queryCountBySql(String sql);
	
	BigDecimal queryAmountBySql(String sql);
}
