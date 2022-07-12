package com.cloud.cang.mgr.ac.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.ac.dao.CouponCodeExDetailsDao;
import com.cloud.cang.mgr.ac.domain.CouponCodeExDetailsDomain;
import com.cloud.cang.mgr.ac.service.CouponCodeExDetailsService;
import com.cloud.cang.mgr.ac.vo.CouponCodeExDetailsVo;
import com.cloud.cang.model.ac.CouponCodeExDetails;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponCodeExDetailsServiceImpl extends GenericServiceImpl<CouponCodeExDetails, String> implements
		CouponCodeExDetailsService {

	@Autowired
	CouponCodeExDetailsDao couponCodeExDetailsDao;

	
	@Override
	public GenericDao<CouponCodeExDetails, String> getDao() {
		return couponCodeExDetailsDao;
	}


	@Override
	public Page<CouponCodeExDetailsDomain> queryDataCouponCouponCodeExDetails(Page<CouponCodeExDetailsDomain> page, CouponCodeExDetailsVo couponCodeExDetailsVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return couponCodeExDetailsDao.queryDataCouponCouponCodeExDetails(couponCodeExDetailsVo);
	}
}