package com.cang.os.mgr.sys.service;

import com.cang.os.common.service.BaseService;
import com.cang.os.bean.sys.Role;

public interface RoleService extends BaseService<Role> {
	
	public void deleteRole(String id);
 
    
}