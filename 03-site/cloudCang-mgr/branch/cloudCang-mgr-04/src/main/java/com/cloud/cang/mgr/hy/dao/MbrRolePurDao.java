package com.cloud.cang.mgr.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.MbrRolePur;

/** 会员角色权限表(HY_MBR_ROLE_PUR) **/
public interface MbrRolePurDao extends GenericDao<MbrRolePur, String> {

    void deleteByRoleId(String roleId);
}