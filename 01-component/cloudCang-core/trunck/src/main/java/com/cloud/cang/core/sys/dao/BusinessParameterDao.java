package com.cloud.cang.core.sys.dao;

import com.cloud.cang.core.sys.domain.BusinessParameterDomain;
import com.cloud.cang.core.sys.vo.BusinessParameterVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.BusinessParameter;
import com.github.pagehelper.Page;


import java.util.List;



/** 业务参数表(SYS_BUSINESS_PARAMETER) **/
public interface BusinessParameterDao extends GenericDao<BusinessParameter, String> {


	/** codegen **/
    
    /**
     * 查询所有的参数集
     * @return
     */
    List<BusinessParameterDomain> selectAll();

	Page<BusinessParameter> selectByVoWhere(
            BusinessParameterVo businessParameter);
}