package com.cloud.cang.wap.rm.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.wap.rm.dao.ReplenishmentPlanDao;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.wap.rm.service.ReplenishmentPlanService;

@Service
public class ReplenishmentPlanServiceImpl extends GenericServiceImpl<ReplenishmentPlan, String> implements
		ReplenishmentPlanService {

	@Autowired
	ReplenishmentPlanDao replenishmentPlanDao;

	
	@Override
	public GenericDao<ReplenishmentPlan, String> getDao() {
		return replenishmentPlanDao;
	}

    /***
     * 更新设备的计划补货单信息
     * @param deviceCode
     */
    @Override
    public void updateByLastTime(String deviceCode) {
        replenishmentPlanDao.updateByLastTime(deviceCode);
    }
}