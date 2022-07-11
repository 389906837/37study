package com.cang.os.mgr.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cang.os.bean.sys.Role;
import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.mgr.sys.dao.RolepurviewDao;
import com.cang.os.mgr.sys.service.RoleService;
import com.cang.os.security.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.cang.os.bean.sys.Operatorrole;
import com.cang.os.mgr.sys.dao.RoleDao;
import com.cang.os.mgr.sys.service.OperatorroleService;
import com.cang.os.security.service.SecRoleSevice;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements
        RoleService,SecRoleSevice {

	@Autowired
	RoleDao roleDao;
	
	@Autowired
	OperatorroleService operatorroleService;
	
	@Autowired
    RolepurviewDao rolepurviewDao;

	
	@Override
	public BaseMongoDao<Role> getDao() {
		return roleDao;
	}


	@Override
	public List<RoleVO> queryAllRole() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<RoleVO> queryRoleByUserId(String userId) {
		List<RoleVO> voArr=new ArrayList<RoleVO>();
		Query query = new Query(Criteria.where("soperatorId").is(userId));
		List<Operatorrole> userRoles = operatorroleService.find(query);
		List<String> roleids = new ArrayList<String>();
		for (Operatorrole operatorrole:userRoles){
			roleids.add(operatorrole.getId());
		}
		Query roleQuery = new Query(Criteria.where("id").in(roleids));
		List<Role> roles = roleDao.find(roleQuery);
		if(roles!=null){
			for(Role r:roles){
				RoleVO vo=new RoleVO();
				vo.setRoleId(r.getId());
				vo.setRoleCode(r.getSroleName());
				vo.setRoleName(r.getSroleName());
				voArr.add(vo);
			}
		}
		return voArr;
	}


	@Override
	public void deleteRole(String id) {
		 Query query = new Query(Criteria.where("id").is(id));
		 roleDao.remove(query);
		 Query operatorroleQuery = new Query(Criteria.where("sroleId").is(id));
		 rolepurviewDao.remove(operatorroleQuery);
		 
	}

	
	

}