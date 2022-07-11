package com.cloud.cang.core.utils;


import com.cloud.cang.core.sys.domain.BusinessParameterDomain;
import com.cloud.cang.core.sys.service.BusinessParameterService;
import com.cloud.cang.utils.SpringContext;

/**
 * 缓存业务参数提供常量操作类
 * @author zhouhong
 * @version 1.0
 */
public class BizParaUtil {

	private static BusinessParameterService service=null;



	/**
	 * 在缓存中根据key返回业务参数表中配置值
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return getService().getCacheValue(key);
	}

	/***
	 * 初始化Bean
	 */
	private static BusinessParameterService getService() {
		if (service == null) {
			service = SpringContext.getBean("businessParameterService");
		}
		return service;
	}
	
	/**
     * 在缓存中根据key返回业务参数表中配置对象
     * 
     * @param key
     * @return
     */
    public static BusinessParameterDomain getObj(String key) {
        return getService().getCacheParamObj(key);
    }
}