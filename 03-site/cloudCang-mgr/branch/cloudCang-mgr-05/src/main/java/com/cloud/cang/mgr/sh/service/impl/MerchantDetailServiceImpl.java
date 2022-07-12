package com.cloud.cang.mgr.sh.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sh.dao.MerchantDetailDao;
import com.cloud.cang.model.sh.MerchantDetail;
import com.cloud.cang.mgr.sh.service.MerchantDetailService;

@Service
public class MerchantDetailServiceImpl extends GenericServiceImpl<MerchantDetail, String> implements
		MerchantDetailService {

	@Autowired
	MerchantDetailDao merchantDetailDao;

	
	@Override
	public GenericDao<MerchantDetail, String> getDao() {
		return merchantDetailDao;
	}
	/**
	 * 新增商户详情表
	 * @param
	 * @return
	 */
	@Override
	public   void  insertAll(MerchantDetail merchantDetail){
		 merchantDetailDao.insertAll(merchantDetail);
	}
	

}