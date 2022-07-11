package com.cloud.cang.core.sys.service;

import com.cloud.cang.core.sys.domain.DataDicDomain;
import com.cloud.cang.model.sys.ParameterGroupDetail;

/**
 * 数据字典缓存
 * @author zhouhong
 *
 */
public interface CacheDataDicService {

	/**
	 *  在缓存中根据参数组code 查询详细信息
	 * @param groupCode
	 * @return
	 */
    DataDicDomain selectCacheDataDictInfo(String groupCode);
	
	/**
	 *  在缓存中查询字典元素详情
	 * @param groupCode
	 * @param sname
	 * @return
	 */
    ParameterGroupDetail selectCacheParamGroupDetailInfo(String groupCode, String sname);
	
}
