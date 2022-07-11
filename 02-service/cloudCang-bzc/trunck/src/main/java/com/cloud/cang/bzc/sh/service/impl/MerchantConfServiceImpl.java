package com.cloud.cang.bzc.sh.service.impl;

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

import com.cloud.cang.bzc.sh.dao.MerchantConfDao;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.bzc.sh.service.MerchantConfService;

@Service
public class MerchantConfServiceImpl extends GenericServiceImpl<MerchantConf, String> implements
		MerchantConfService {

	@Autowired
	MerchantConfDao merchantConfDao;

	
	@Override
	public GenericDao<MerchantConf, String> getDao() {
		return merchantConfDao;
	}


	/**
	 * 根据商户Id,配置信息类型查询
	 * @param
	 * @return
	 */
	@Override
	public MerchantConf selectByIdType(MerchantConf merchantConf) {
		return merchantConfDao.selectByIdType(merchantConf);
	}
}