package com.cloud.cang.core.sys.dao;


import com.cloud.cang.core.sys.domain.SysParamDomain;

import com.cloud.cang.core.sys.vo.ParametergroupVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.Parametergroup;
import com.github.pagehelper.Page;

import java.util.List;

/** 参数集合主表(SYS_PARAMETERGROUP) **/
public interface ParametergroupDao extends GenericDao<Parametergroup, String> {

	Page<SysParamDomain> selectParaGroupFetchDetails();

	Parametergroup selectByGroupNo(String groupNo);

	Page<Parametergroup> selectByVoWhere(ParametergroupVo parametergroup);

	/**
	 * 查询数据字典
	 * @return
	 */
	List<Parametergroup> queryAllData();
}