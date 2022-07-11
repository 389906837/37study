package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.model.rm.ReplenishmentPlanDetail;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Description: 计划补货返回参数
 * @Author: ChangTanchang
 * @Date: 2018/4/8 19:27
 */
public class ReplenishmentPlanResult extends SuperDto {

	private ReplenishmentPlan plan;//计划补货单记录
	private List<ReplenishmentPlanDetail> details;//计划补货单商品明细

	public ReplenishmentPlan getPlan() {
		return plan;
	}

	public void setPlan(ReplenishmentPlan plan) {
		this.plan = plan;
	}

	public List<ReplenishmentPlanDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ReplenishmentPlanDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "ReplenishmentPlanResult{" +
				"plan=" + plan +
				", details=" + details +
				'}';
	}
}
