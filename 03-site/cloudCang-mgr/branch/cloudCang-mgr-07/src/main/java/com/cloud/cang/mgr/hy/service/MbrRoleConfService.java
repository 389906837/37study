package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.model.hy.MbrRoleConf;
import com.cloud.cang.generic.GenericService;

public interface MbrRoleConfService extends GenericService<MbrRoleConf, String> {

    /**
     * 根据角色ID和用户Id删除
     *
     * @param smemberId,roleId
     * @return
     */
    void delByMbrIdRoleId(String smemberId,String roleId);
}