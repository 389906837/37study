package com.cang.os.mgr.sys.service.impl;

import com.cang.os.bean.sys.Rolepurview;
import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.mgr.sys.service.RolepurviewService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;




import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import com.cang.os.mgr.sys.dao.RolepurviewDao;

@Service
public class RolepurviewServiceImpl extends BaseServiceImpl<Rolepurview> implements
        RolepurviewService {

	@Autowired
	RolepurviewDao rolepurviewDao;

	
	@Override
	public BaseMongoDao<Rolepurview> getDao() {
		return rolepurviewDao;
	}


	@Override
	public void saveRolePurview(String checkPurviewId, String roleId) {
		 Query query = new Query(Criteria.where("sroleId").is(roleId));
        //删除已经存在的权限
        rolepurviewDao.remove(query);
       if (StringUtils.isNotBlank(checkPurviewId))
       {
           String [] purIds = checkPurviewId.split(",");
           if (purIds != null && purIds.length > 0)
           {
               for (String purId:purIds)
               {
                   Rolepurview rolepurview  = new Rolepurview();
                   rolepurview.setSpurviewId(purId);
                   rolepurview.setSroleId(roleId);
                   rolepurviewDao.insert(rolepurview);
               }
               
           }
       }
    
	}

}