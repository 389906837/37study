package com.cloud.cang.mgr.ac.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.ac.dao.CouponUserSendDao;
import com.cloud.cang.mgr.ac.domain.CouponUserSendDomain;
import com.cloud.cang.mgr.ac.service.CouponUserSendService;
import com.cloud.cang.mgr.ac.vo.CouponUserSendVo;
import com.cloud.cang.model.ac.CouponUserSend;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CouponUserSendServiceImpl extends GenericServiceImpl<CouponUserSend, String> implements
		CouponUserSendService {

	@Autowired
	CouponUserSendDao couponUserSendDao;

	
	@Override
	public GenericDao<CouponUserSend, String> getDao() {
		return couponUserSendDao;
	}


	@Override
	public CouponUserSendDomain selectByCouponBatchSendId(String sid) {
		return couponUserSendDao.selectByCouponBatchSendId(sid);
	}

	@Override
	public void deleteNotInIds(String batchId) {
		couponUserSendDao.deleteNotInIds(batchId);
	}

//	@Override
//	public List<CouponUserSendDomain> selectCouponUserSend(CouponUserSendDomain couponUserSendDomain) {
//		return couponUserSendDao.selectCouponUserSend(couponUserSendDomain);
//	}

}