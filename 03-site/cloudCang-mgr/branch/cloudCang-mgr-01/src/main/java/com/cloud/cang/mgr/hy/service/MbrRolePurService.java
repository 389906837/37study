package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.model.hy.MbrRolePur;
import com.cloud.cang.generic.GenericService;

public interface MbrRolePurService extends GenericService<MbrRolePur, String> {

    void deleteByRoleId(String roleId);
}