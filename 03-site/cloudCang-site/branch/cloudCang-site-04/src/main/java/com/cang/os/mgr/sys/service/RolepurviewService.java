package com.cang.os.mgr.sys.service;

import com.cang.os.common.service.BaseService;
import com.cang.os.bean.sys.Rolepurview;

public interface RolepurviewService extends BaseService<Rolepurview> {
	
    /**
     * 分派权限，
     * @param checkPurviewId 选中的权限ID 用","隔开
     * @param id 
     */
    public void saveRolePurview(String checkPurviewId,String roleId);
 
    
}