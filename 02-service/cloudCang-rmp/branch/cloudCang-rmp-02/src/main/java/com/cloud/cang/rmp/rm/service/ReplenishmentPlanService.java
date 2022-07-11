package com.cloud.cang.rmp.rm.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rm.DynamicReplenishmentDto;
import com.cloud.cang.rm.DynamicReplenishmentResult;
import com.cloud.cang.rm.ReplenishmentPlanDto;
import com.cloud.cang.rm.ReplenishmentPlanResult;

public interface ReplenishmentPlanService extends GenericService<ReplenishmentPlan, String> {

    /**
     * 动态生成补货单
     * @param dynamicReplenishmentDto 动态生成补货单参数
     * @return
     * @throws Exception
     */
    ResponseVo<DynamicReplenishmentResult> dynamicReplenishmentGenerate(DynamicReplenishmentDto dynamicReplenishmentDto) throws Exception;

    /**
     * 计划补货单保存
     * @param replenishmentPlanDto 计划补货单参数
     * @return
     */
    ResponseVo<ReplenishmentPlanResult> replenishmentPlanSave(ReplenishmentPlanDto replenishmentPlanDto) throws Exception;

    /**
     * 查询补货单数据 未完成状态
     * @param sdeviceId 设备ID
     * @return
     */
    ReplenishmentPlan selectUndoneByDeviceId(String sdeviceId);
}