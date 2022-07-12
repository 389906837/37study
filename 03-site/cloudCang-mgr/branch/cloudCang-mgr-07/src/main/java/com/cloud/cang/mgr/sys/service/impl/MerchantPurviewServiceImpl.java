package com.cloud.cang.mgr.sys.service.impl;

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

import com.cloud.cang.mgr.sys.dao.MerchantPurviewDao;
import com.cloud.cang.model.sys.MerchantPurview;
import com.cloud.cang.mgr.sys.service.MerchantPurviewService;

@Service
public class MerchantPurviewServiceImpl extends GenericServiceImpl<MerchantPurview, String> implements
		MerchantPurviewService {

	@Autowired
	MerchantPurviewDao merchantPurviewDao;

	
	@Override
	public GenericDao<MerchantPurview, String> getDao() {
		return merchantPurviewDao;
	}

	/**
	 * 删除商户菜单权限
	 * @param merchantId 商户ID
	 */
	@Override
	public void deleteByMerchantId(String merchantId) {
		merchantPurviewDao.deleteByMerchantId(merchantId);
	}
}