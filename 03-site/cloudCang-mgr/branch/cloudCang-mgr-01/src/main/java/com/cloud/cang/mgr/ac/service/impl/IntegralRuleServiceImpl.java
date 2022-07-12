package com.cloud.cang.mgr.ac.service.impl;

import java.util.Date;

import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;


import com.cloud.cang.mgr.ac.dao.IntegralRuleDao;
import com.cloud.cang.model.ac.IntegralRule;
import com.cloud.cang.mgr.ac.service.IntegralRuleService;

@Service
public class IntegralRuleServiceImpl extends GenericServiceImpl<IntegralRule, String> implements
		IntegralRuleService {

	@Autowired
	IntegralRuleDao integralRuleDao;

	
	@Override
	public GenericDao<IntegralRule, String> getDao() {
		return integralRuleDao;
	}


	@Override
	public IntegralRule saveOrUpdate(IntegralRule integralRule) {
		if (StringUtil.isBlank(integralRule.getId())) {// 执行新增
			integralRule.setIdelFlag(0);
			integralRule.setScode(CoreUtils.newCode("ac_integral_rule"));
			integralRule.setTaddTime(new Date());
			integralRule.setTupdateTime(new Date());
			integralRule.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			integralRule.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			this.insert(integralRule);
		} else {// 执行修改
			integralRule.setTupdateTime(new Date());
			integralRule.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			this.updateBySelective(integralRule);
		}
		return integralRule;
	}
}