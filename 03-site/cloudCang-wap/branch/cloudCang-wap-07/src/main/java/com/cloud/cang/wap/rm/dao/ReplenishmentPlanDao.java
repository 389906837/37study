package com.cloud.cang.wap.rm.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.rm.ReplenishmentPlan;

/** 计划商品补货记录信息表(RM_REPLENISHMENT_PLAN) **/
public interface ReplenishmentPlanDao extends GenericDao<ReplenishmentPlan, String> {

    /***
     * 更新设备的计划补货单信息
     * @param deviceCode
     */
    void updateByLastTime(String deviceCode);

}