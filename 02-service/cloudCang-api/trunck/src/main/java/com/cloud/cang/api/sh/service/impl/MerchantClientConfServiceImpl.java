package com.cloud.cang.api.sh.service.impl;

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

import com.cloud.cang.api.sh.dao.MerchantClientConfDao;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.api.sh.service.MerchantClientConfService;

@Service
public class MerchantClientConfServiceImpl extends GenericServiceImpl<MerchantClientConf, String> implements
		MerchantClientConfService {

	@Autowired
	MerchantClientConfDao merchantClientConfDao;

	
	@Override
	public GenericDao<MerchantClientConf, String> getDao() {
		return merchantClientConfDao;
	}

	
	

}