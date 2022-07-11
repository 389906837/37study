package com.cloud.cang.core.sys.service.impl;

import com.cloud.cang.core.sys.dao.ParameterGroupDetailDao;
import com.cloud.cang.core.sys.domain.ParameterGroupDetailDomain;
import com.cloud.cang.core.sys.service.ParameterGroupDetailService;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ParameterGroupDetailServiceImpl extends GenericServiceImpl<ParameterGroupDetail, String> implements
        ParameterGroupDetailService {

	@Autowired
	ParameterGroupDetailDao parameterGroupDetailDao;

	
	@Override
	public GenericDao<ParameterGroupDetail, String> getDao() {
		return parameterGroupDetailDao;
	}


	@Override
	public Page<ParameterGroupDetail> selectByQueryItems(String sgroupNo,
			ParameterGroupDetailDomain parameterGroupDetailDomain,  Page<ParameterGroupDetail> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
	     Map<String,Object> map=new HashMap<String,Object>();
	        map.put("sgroupNo", sgroupNo);
	        map.put("sname", parameterGroupDetailDomain.getSname());
	        map.put("svalue", parameterGroupDetailDomain.getSvalue());
	        map.put("sremark", parameterGroupDetailDomain.getSremark());
	        map.put("orderStr",parameterGroupDetailDomain.getOrderStr());
	        return parameterGroupDetailDao.selectByQueryItems(map);
	}



	@Override
	public ParameterGroupDetail selectByExist(
			ParameterGroupDetail parameterGroupDetail) {
		return parameterGroupDetailDao.selectByExist(parameterGroupDetail);
	}

	@Override
	public List<ParameterGroupDetail> queryAllGroupNo() {
		return parameterGroupDetailDao.queryAllGroupNo();
	}


}