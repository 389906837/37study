package com.cang.os.mgr.sys.service;

import com.cang.os.common.service.BaseService;
import com.cang.os.bean.sys.Operatorrole;

public interface OperatorroleService extends BaseService<Operatorrole> {
 
    
	/**
	 * 保存用户角色信息
	 * @param operatorId
	 * @param sselectrole
	 * @param snotselectrole
	 */
	public void saveRolePurview( String operatorId,String[] sselectrole,String [] snotselectrole);
}