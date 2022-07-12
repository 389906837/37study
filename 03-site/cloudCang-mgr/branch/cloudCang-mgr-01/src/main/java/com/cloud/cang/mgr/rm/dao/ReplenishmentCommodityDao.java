package com.cloud.cang.mgr.rm.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.rm.domain.ReplenishCommodityDomain;
import com.cloud.cang.mgr.rm.domain.ReplenishMentDomain;
import com.cloud.cang.model.rm.ReplenishmentCommodity;

import java.util.List;
import java.util.Map;

/** 补货商品明细表(RM_REPLENISHMENT_COMMODITY) **/
public interface ReplenishmentCommodityDao extends GenericDao<ReplenishmentCommodity, String> {


    List<ReplenishCommodityDomain> selectInfoId(String sid);
}