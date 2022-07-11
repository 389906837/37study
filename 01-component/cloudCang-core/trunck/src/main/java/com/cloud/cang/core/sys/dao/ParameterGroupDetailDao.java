package com.cloud.cang.core.sys.dao;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 参数集合明细(SYS_PARAMETER_GROUP_DETAIL) **/
public interface ParameterGroupDetailDao extends GenericDao<ParameterGroupDetail, String> {

	void batchDeleteByGroupIds(String[] idArray);

	Page<ParameterGroupDetail> selectByQueryItems(Map<String, Object> map);


	ParameterGroupDetail selectByExist(ParameterGroupDetail parameterGroupDetail);

	/**
	 * 根据ID查询详情
	 * @param
	 * @return
	 */
	List<ParameterGroupDetail> queryAllGroupNo();

}