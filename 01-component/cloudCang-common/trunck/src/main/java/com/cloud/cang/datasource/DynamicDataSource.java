package com.cloud.cang.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/** 
 * 动态数据源
 *  
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	final static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	@Override
	protected Object determineCurrentLookupKey(){
		return DataSourceContextHolder.getDataSourceType();
	}
}
