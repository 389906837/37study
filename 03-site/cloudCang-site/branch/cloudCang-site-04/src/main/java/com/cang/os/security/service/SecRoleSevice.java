package com.cang.os.security.service;

import java.util.List;

import com.cang.os.security.vo.RoleVO;

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
