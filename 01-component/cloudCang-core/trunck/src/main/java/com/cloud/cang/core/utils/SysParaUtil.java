package com.cloud.cang.core.utils;


import com.cloud.cang.core.sys.service.CacheDataDicService;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.utils.SpringContext;


/**
 * 系统参数常量类
 * @author zhouhong
 * @version 1.0
 */
public class SysParaUtil {
	
	private static CacheDataDicService service=null;
    private static final String SYS_GROUP_NO="SP000000";

    
    /**
	 * 在缓存中根据关键字和名字查找值
	 */
	public static String getValue(String name) {
		ParameterGroupDetail dtl= getService().selectCacheParamGroupDetailInfo(SYS_GROUP_NO, name);
		if(dtl==null)return "";
		return dtl.getSvalue();
	}

	/***
	 * 初始化Bean
	 */
	private static CacheDataDicService getService() {
		if (service == null) {
			service = SpringContext.getBean("dataDicServiceImpl");
		}
		return service;
	}
}