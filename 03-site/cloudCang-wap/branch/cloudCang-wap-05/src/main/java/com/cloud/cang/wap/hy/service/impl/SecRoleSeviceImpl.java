package com.cloud.cang.wap.hy.service.impl;/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月18日
 * Description:SecRoleSeviceImpl.java
 */

import com.cloud.cang.model.hy.MbrRole;
import com.cloud.cang.security.service.SecRoleSevice;
import com.cloud.cang.security.vo.RoleVO;
import com.cloud.cang.wap.hy.dao.MbrRoleDao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 加载用户角色
 * @author zhouhong
 * @version 1.0
 */
@Service
public class SecRoleSeviceImpl implements SecRoleSevice {
	
	private static final Logger log = LoggerFactory.getLogger(SecRoleSeviceImpl.class);
	
	@Autowired
    MbrRoleDao mbrRoleDao;

	/* (non-Javadoc)
	 * @see com.cang.security.service.SecRoleSevice#queryAllRole()
	 */
	@Override
	public List<RoleVO> queryAllRole() {
	List<RoleVO> _roles = new ArrayList<RoleVO>();
	List<MbrRole> roles = mbrRoleDao.selectByEntityWhere(new MbrRole());
	if (roles != null && roles.size() > 0) {
		for (MbrRole mbrRole:roles) {
			RoleVO roleVO = new RoleVO();
			roleVO.setRoleId(mbrRole.getId());
			roleVO.setRoleCode(mbrRole.getSroleName());
			roleVO.setRoleName(mbrRole.getSroleName());
			if (log.isDebugEnabled()) {
				log.debug("加载所有角色：{}，{}，{}",new Object[] {roleVO.getRoleId(),roleVO.getRoleCode(),roleVO.getRoleName()});
			}
			_roles.add(roleVO);
			
		}
	 }
	 log.info("系统角色加载ok~~");
	 return _roles;
	}

	/* (non-Javadoc)
	 * @see com.cang.security.service.SecRoleSevice#queryRoleByUserId(java.lang.String)
	 */
	@Override
	public List<RoleVO> queryRoleByUserId(String userId) {

		List<RoleVO> _roles = new ArrayList<RoleVO>();
		List<MbrRole> roles = mbrRoleDao.selectRolesByUserId(userId);
		if (roles != null && roles.size() > 0) {
			for (MbrRole mbrRole:roles) {
				RoleVO roleVO = new RoleVO();
				roleVO.setRoleId(mbrRole.getId());
				roleVO.setRoleCode(mbrRole.getSroleName());
				roleVO.setRoleName(mbrRole.getSroleName());
				if (log.isDebugEnabled()) {
					log.debug("加载所有角色：{}，{}，{}",new Object[] {roleVO.getRoleId(),roleVO.getRoleCode(),roleVO.getRoleName()});
				}
				_roles.add(roleVO);
				
			}
		 }
		 log.info("系统会员角色加载ok~~");
		 return _roles;
	}

}
