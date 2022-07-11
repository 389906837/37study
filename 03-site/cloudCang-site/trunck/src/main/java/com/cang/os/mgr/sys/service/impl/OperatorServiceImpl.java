/**
 * 
 */
package com.cang.os.mgr.sys.service.impl;

import com.cang.os.bean.sys.Operator;
import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.mgr.sys.dao.OperatorDao;
import com.cang.os.mgr.sys.service.OperatorService;
import com.cang.os.security.core.CaptchaUsernamePasswordToken;
import com.cang.os.security.service.SecUserService;
import com.cang.os.security.vo.AuthorizationVO;
import com.cang.os.mgr.sys.dao.OperatorroleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;






import org.springframework.stereotype.Service;

import com.cang.os.security.vo.UserDetail;

/**
 * @author hunter
 *
 */
@Service
public class OperatorServiceImpl extends BaseServiceImpl<Operator> implements OperatorService,SecUserService {

	@Autowired
    OperatorDao operatorDao;
	
	@Autowired
    OperatorroleDao operatorroleDao;
	
	@Override
	public BaseMongoDao<Operator> getDao() {
		return operatorDao;
	}

	@Override
	public AuthorizationVO getUserByUserName(
			CaptchaUsernamePasswordToken loginObj) {
		Query query = new Query(Criteria.where("suserName").is(loginObj.getUsername()));
		Operator po = operatorDao.findOne(query);
		if (po == null)
            return null;
        AuthorizationVO vo = new AuthorizationVO();
        if (po.getBisFreeze().intValue() ==0) {
            vo.setFreeze(true);
        }
        vo.setId(po.getId());
        vo.setUserName(po.getSuserName());
        vo.setPassword(po.getSpassword());
        if (po.getDlastLoginTime() != null){
            vo.setLastLoginTime(DateUtils.dateParseShortString(po.getDlastLoginTime()));
        }else {
            vo.setLastLoginTime(DateUtils.dateParseString(DateUtils.getCurrentDateTime()));
            
        }
        
        return vo;
	}

	@Override
	public AuthorizationVO getUserDetail(String userId) {
		UserDetail dtl = new UserDetail();
        Operator op = this.operatorDao.findById(userId);
        dtl.setId(op.getId());
        dtl.setCode(op.getSoperatorNo());
        dtl.setUserName(op.getSuserName());
        dtl.setRealName(op.getSrealName());
        dtl.setSex(op.getIsex());
        if (op.getDlastLoginTime() != null) {
            dtl.setLastLoginTime(DateUtils.dateToString(op.getDlastLoginTime(), "yyyy-MM-dd HH:mm"));
        }else {
            dtl.setLastLoginTime(DateUtils.dateToString(DateUtils.getCurrentDateTime(), "yyyy-MM-dd HH:mm"));
        }
        
        dtl.setMobile(op.getSmobile());
        dtl.setPhone(op.getSphone());
        dtl.setMai(op.getSmail());
        return dtl;
	}

	@Override
	public void loginSuccess(String userId, String host) {
		System.out.println("登录成功。。。。");
		
	}

	@Override
	public void loginError(String userName, String host) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOperator(String id) {
		 Query query = new Query(Criteria.where("id").is(id));
		 operatorDao.remove(query);
		 
		 Query operatorroleQquery = new Query(Criteria.where("soperatorId").is(id));
		 operatorroleDao.remove(operatorroleQquery);
	}



}
