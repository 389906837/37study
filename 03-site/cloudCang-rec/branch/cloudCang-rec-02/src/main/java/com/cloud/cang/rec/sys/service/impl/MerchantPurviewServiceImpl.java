package com.cloud.cang.rec.sys.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.rec.sys.dao.MerchantPurviewDao;
import com.cloud.cang.rec.sys.service.MerchantPurviewService;
import com.cloud.cang.model.sys.MerchantPurview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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