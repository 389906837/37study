package com.cloud.cang.tec.hy.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.tec.hy.dao.MemberInfoDao;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.tec.hy.service.MemberInfoService;

@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, String> implements
		MemberInfoService {

	@Autowired
	MemberInfoDao memberInfoDao;

	
	@Override
	public GenericDao<MemberInfo, String> getDao() {
		return memberInfoDao;
	}

	/**
	 * 查询所有单次代扣且开通免密的人
	 * @return
	 */
	@Override
	public List<MemberInfo> selectByAlipaySign() {
		return memberInfoDao.selectByAlipaySign();
	}
}