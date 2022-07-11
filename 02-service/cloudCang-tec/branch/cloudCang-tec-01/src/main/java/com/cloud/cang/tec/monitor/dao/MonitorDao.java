package com.cloud.cang.tec.monitor.dao;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

public interface MonitorDao {

	Map<String,Object> executeSql(@Param("executeSql") String executeSql);
	
	Integer queryCountBySql(@Param("executeSql") String executeSql);
	
	BigDecimal queryAmountBySql(@Param("executeSql") String executeSql);
}
