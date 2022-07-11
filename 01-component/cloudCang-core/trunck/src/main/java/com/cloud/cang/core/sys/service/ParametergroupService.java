package com.cloud.cang.core.sys.service;


import com.cloud.cang.core.sys.vo.ParametergroupVo;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.model.sys.Parametergroup;
import com.github.pagehelper.Page;

import java.util.List;

/**
 *  数据字典
 * @author zhouhong
 *
 */
public interface ParametergroupService {
	/**
	 *  查询字典元素详情
	 * @param groupCode
	 * @param sname
	 * @return
	 */
    ParameterGroupDetail selectParamGroupDetailInfo(String groupCode, String sname);
	
	/**
	 *  根据ID批量删除字典项和字典详情
	 * @param idArray
	 */
    void deleteParameterGroup(String[] idArray);
	
	/**
	 * 根据字典编号获取字典
	 * @param groupNo
	 * @return
	 */
    Parametergroup selectByGroupNo(String groupNo);

	/**
	 * 加载数据字字典
	 */
    void loadDataDict();

	void updateByPrimaryKey(Parametergroup parametergroup);


	void insert(Parametergroup parametergroup);

	Page<Parametergroup> selectByVoWhere(Page<Parametergroup> page,
										 ParametergroupVo parametergroup);

	List<Parametergroup> queryAllData();
}