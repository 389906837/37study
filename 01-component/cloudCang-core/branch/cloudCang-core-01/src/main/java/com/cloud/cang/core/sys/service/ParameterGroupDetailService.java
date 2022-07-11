package com.cloud.cang.core.sys.service;


import com.cloud.cang.core.sys.domain.ParameterGroupDetailDomain;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.model.sys.Parametergroup;
import com.github.pagehelper.Page;

import java.util.List;

public interface ParameterGroupDetailService extends GenericService<ParameterGroupDetail, String> {


	Page<ParameterGroupDetail> selectByQueryItems(String groupNo,
												  ParameterGroupDetailDomain parameterGroupDetailDomain,Page<ParameterGroupDetail> page);

	ParameterGroupDetail selectByExist(ParameterGroupDetail parameterGroupDetail);

	/**
	 * 查询数据字典商品分类编号
	 * @return
	 */
	List<ParameterGroupDetail> queryAllGroupNo();
 
    
}