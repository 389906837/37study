package com.cloud.cang.core.sys.service;


import com.cloud.cang.core.sys.domain.BusinessParameterDomain;
import com.cloud.cang.core.sys.vo.BusinessParameterVo;
import com.cloud.cang.model.sys.BusinessParameter;
import com.github.pagehelper.Page;

/**
 * 平台业务参数服务提供类
 * @version 1.0
 */
public interface BusinessParameterService {
   
    
    /**
     * 在缓存中根据key取得值
     * @param key
     * @return
     */
    String getCacheValue(String key);
    
    /**
     * 在缓存中根据key取得业务参数对象
     * @param key
     * @return
     */
    BusinessParameterDomain getCacheParamObj(String key);

	Page<BusinessParameter> queryPage(Page<BusinessParameter> page,
									  BusinessParameterVo businessParameter);

	void batchDeleteByIds(String[] checkboxId);

	void insert(BusinessParameter businessParameter);

	void updateByPrimaryKey(BusinessParameter businessParameter);

	void loadDataDict();
	BusinessParameter selectByKey(String key);
}