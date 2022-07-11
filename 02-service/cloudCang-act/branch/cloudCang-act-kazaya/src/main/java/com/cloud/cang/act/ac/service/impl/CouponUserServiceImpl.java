package com.cloud.cang.act.ac.service.impl;

import com.cloud.cang.act.CouponQueryDto;
import com.cloud.cang.act.ac.dao.CouponUserDao;
import com.cloud.cang.act.ac.service.CouponUserService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.ac.CouponUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CouponUserServiceImpl extends GenericServiceImpl<CouponUser, String> implements
		CouponUserService {

	@Autowired
	CouponUserDao couponUserDao;


	@Override
	public GenericDao<CouponUser, String> getDao() {
		return couponUserDao;
	}


	@Override
	public List<CouponUser> selectCouponUserByMemberId( CouponQueryDto couponQueryDto) {
		return couponUserDao.selectCouponUserByCouponQueryDto(couponQueryDto);
	}

	@Override
	public int updateCouponUserByMap(Map map) {
		return couponUserDao.updateCouponUserByMap(map);
	}


	@Override
	public Integer countCouponUserMouthJoinByCouponCode(String couponCode) {
		return couponUserDao.countCouponUserMouthJoinByCouponCode(couponCode);
	}

	@Override
	public Integer countCouponUserOnceJoinByCouponCode(String couponCode) {
		return couponUserDao.countCouponUserOnceJoinByCouponCode(couponCode);
	}
	@Override
	public Integer countCouponUserMouthJoinByMemberId(Map<String, String> map) {
		return couponUserDao.countCouponUserMouthJoinByMemberId(map);
	}


	@Override
	public Integer countCouponUserOnceJoinByMmeberId(Map<String, String> map) {
		return couponUserDao.countCouponUserOnceJoinByMmeberId(map);
	}

}