package com.cloud.cang.wap.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.MbrRole;

import java.util.List;



/** 会员角色表(HY_MBR_ROLE) **/
public interface MbrRoleDao extends GenericDao<MbrRole, String> {


	/**
	 * 查找用户角色
	 * @param userId
	 * @return
	 */
	List<MbrRole> selectRolesByUserId(String userId);
}