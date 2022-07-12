package com.cloud.cang.mgr.hy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.hy.dao.MbrRoleConfDao;
import com.cloud.cang.model.hy.MbrRoleConf;
import com.cloud.cang.mgr.hy.service.MbrRoleConfService;

@Service
public class MbrRoleConfServiceImpl extends GenericServiceImpl<MbrRoleConf, String> implements
		MbrRoleConfService {

	@Autowired
	MbrRoleConfDao mbrRoleConfDao;

	
	@Override
	public GenericDao<MbrRoleConf, String> getDao() {
		return mbrRoleConfDao;
	}


	/**
	 * 根据角色ID和用户Id删除
	 *
	 * @param smemberId,sroleId
	 * @return
	 */
	public  void delByMbrIdRoleId(String smemberId,String sroleId){
		Map<String,String> map =new HashMap<>();
		map.put("smemberId",smemberId);
		map.put("sroleId",sroleId);
		mbrRoleConfDao.delByMbrIdRoleId(map);
	}

}