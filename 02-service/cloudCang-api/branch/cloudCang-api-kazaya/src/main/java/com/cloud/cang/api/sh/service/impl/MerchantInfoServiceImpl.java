package com.cloud.cang.api.sh.service.impl;

import java.util.HashMap;
import java.util.List;

import com.cloud.cang.rfid.MerchantDto;
import com.cloud.cang.utils.MD5;
import com.cloud.cang.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.sh.dao.MerchantInfoDao;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.api.sh.service.MerchantInfoService;

@Service
public class MerchantInfoServiceImpl extends GenericServiceImpl<MerchantInfo, String> implements
		MerchantInfoService {

	@Autowired
	MerchantInfoDao merchantInfoDao;

	
	@Override
	public GenericDao<MerchantInfo, String> getDao() {
		return merchantInfoDao;
	}

	/**
	 * 获取商户信息
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public MerchantInfo selectByCode(String merchantCode) {
		return merchantInfoDao.selectByCode(merchantCode);
	}
	/**
	 * 根据用户名查询商户信息
	 * @param username 用户名或者手机号
	 * @param password
	 * @return
	 */
	@Override
	public List<MerchantDto> selectMerchantsByuserNameOrmobile(String username, String password) {
		return merchantInfoDao.selectMerchantsByuserNameOrmobile(username,MD5.encode(password));
	}


}