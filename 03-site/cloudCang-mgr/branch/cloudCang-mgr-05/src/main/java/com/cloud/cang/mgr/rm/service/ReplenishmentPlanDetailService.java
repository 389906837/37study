package com.cloud.cang.mgr.rm.service;

import com.cloud.cang.mgr.rm.domain.ReplenishMentPlanDetailDomain;
import com.cloud.cang.model.rm.ReplenishmentPlanDetail;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface ReplenishmentPlanDetailService extends GenericService<ReplenishmentPlanDetail, String> {

    List<ReplenishMentPlanDetailDomain> selectInfoId(String sid);
}