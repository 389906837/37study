package com.cloud.cang.act.hy.service.impl;


import com.cloud.cang.act.hy.dao.MemberInfoDao;
import com.cloud.cang.act.hy.service.MemberInfoService;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, String> implements
		MemberInfoService {

	@Autowired
	MemberInfoDao memberInfoDao;

	
	@Override
	public GenericDao<MemberInfo, String> getDao() {
		return memberInfoDao;
	}


	public Date selectRegisterDate(String id){
		return this.memberInfoDao.selectRegisterDate(id);
	}


}