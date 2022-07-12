package com.cloud.cang.mgr.rm.service;

import com.cloud.cang.mgr.rm.domain.ReplenishCommodityDomain;
import com.cloud.cang.mgr.rm.domain.ReplenishMentDomain;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.generic.GenericService;

import java.util.List;
import java.util.Map;

public interface ReplenishmentCommodityService extends GenericService<ReplenishmentCommodity, String> {

    /**
     * 根据ID查询补货商品详细表
     * @param sid
     * @return
     */
    List<ReplenishCommodityDomain> selectInfoId(String sid);
}