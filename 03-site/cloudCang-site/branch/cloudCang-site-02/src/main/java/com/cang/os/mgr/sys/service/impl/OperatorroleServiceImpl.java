package com.cang.os.mgr.sys.service.impl;

import com.cang.os.bean.sys.Operatorrole;
import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.mgr.sys.dao.OperatorroleDao;
import com.cang.os.mgr.sys.service.OperatorroleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class OperatorroleServiceImpl extends BaseServiceImpl<Operatorrole> implements
        OperatorroleService {

	@Autowired
    OperatorroleDao operatorroleDao;

	
	@Override
	public BaseMongoDao<Operatorrole> getDao() {
		return operatorroleDao;
	}


	@Override
	public void saveRolePurview(String operatorId, String[] sselectrole,
			String[] snotselectrole) {
		//删除用户角色
		Query query = new Query(Criteria.where("soperatorId").is(operatorId));
		operatorroleDao.remove(query);
		//保存角色
		if (sselectrole != null && sselectrole.length > 0) {
            for (String roleId : sselectrole) {
                Operatorrole operatorRole = new Operatorrole();
                operatorRole.setSoperatorId(operatorId);
                operatorRole.setSroleId(roleId);
                operatorRole.setId(null);
                operatorroleDao.insert(operatorRole);
            }
        }
		
	}

	
	

}