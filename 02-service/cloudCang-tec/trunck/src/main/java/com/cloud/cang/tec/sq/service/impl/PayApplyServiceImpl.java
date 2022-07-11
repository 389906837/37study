package com.cloud.cang.tec.sq.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.sq.dao.PayApplyDao;
import com.cloud.cang.model.sq.PayApply;
import com.cloud.cang.tec.sq.service.PayApplyService;

@Service
public class PayApplyServiceImpl extends GenericServiceImpl<PayApply, String> implements
		PayApplyService {

	@Autowired
	PayApplyDao payApplyDao;

	
	@Override
	public GenericDao<PayApply, String> getDao() {
		return payApplyDao;
	}

	/**
	 * 更新付款申请信息
	 *
	 * @param pmap
	 */
	@Override
	public void updateStatusById(Map<String, Object> pmap) {
		payApplyDao.updateStatusById(pmap);
	}
	

}