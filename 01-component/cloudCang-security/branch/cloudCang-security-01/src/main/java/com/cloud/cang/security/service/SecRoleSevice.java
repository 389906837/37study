package com.cloud.cang.security.service;


import com.cloud.cang.security.vo.RoleVO;

import java.util.List;

public interface SecRoleSevice {

	/**
	 * 查询所有的角色信息
	 * @return
	 */
	List<RoleVO> queryAllRole();
	
	/**
	 * 查询用户的角色信息
	 * 
	 */
	List<RoleVO> queryRoleByUserId(String userId);
	
	
	
}
