package com.cloud.cang.rec.sys.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.Rolepurview;

/**
 * 后台角色权限分配(SYS_ROLEPURVIEW)
 **/
public interface RolepurviewDao extends GenericDao<Rolepurview, String> {
    /**
     * 根据角色ID删除记录
     *
     * @param roleId
     */
    public void deleteByRoleId(String roleId);


}