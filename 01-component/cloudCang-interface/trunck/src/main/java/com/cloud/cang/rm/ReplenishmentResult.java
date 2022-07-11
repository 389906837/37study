package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.model.rm.ReplenishmentRecord;

import java.util.List;

/**
 * @version 1.0
 * @Description:补货单成功返回结果
 * @Author: zhouhong
 * @Date: 2018/4/13 12:02
 */
public class ReplenishmentResult extends SuperDto {

    private ReplenishmentRecord replenishmentRecord;//补货单
    private List<ReplenishmentCommodity> commodities;//补货单商品列表

    public ReplenishmentRecord getReplenishmentRecord() {
        return replenishmentRecord;
    }

    public void setReplenishmentRecord(ReplenishmentRecord replenishmentRecord) {
        this.replenishmentRecord = replenishmentRecord;
    }

    public List<ReplenishmentCommodity> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<ReplenishmentCommodity> commodities) {
        this.commodities = commodities;
    }

    @Override
    public String toString() {
        return "ReplenishmentResult{" +
                "replenishmentRecord=" + replenishmentRecord +
                ", commodities=" + commodities +
                '}';
    }
}
