package com.cloud.cang.wap.sys.service.impl;

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

import com.cloud.cang.wap.sys.dao.IpAreaDao;
import com.cloud.cang.model.sys.IpArea;
import com.cloud.cang.wap.sys.service.IpAreaService;

@Service
public class IpAreaServiceImpl extends GenericServiceImpl<IpArea, String> implements
		IpAreaService {

	@Autowired
	IpAreaDao ipAreaDao;

	
	@Override
	public GenericDao<IpArea, String> getDao() {
		return ipAreaDao;
	}
	/**
	 * 根据Ip查找
	 * @param ip
	 * @return
	 */
	@Override
	public IpArea selectIpAreaByIp(String ip) {
		return ipAreaDao.selectIpAreaByIp(ip);
	}
	

}