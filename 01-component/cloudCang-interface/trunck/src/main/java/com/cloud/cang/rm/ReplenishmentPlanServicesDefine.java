package com.cloud.cang.rm;

import com.cloud.cang.common.ResponseVo;

/**
 * 计划补货服务定义
 * @version 1.0
 * @Author: ChangTanchang
 * @Date: 2018年03月14日15:05:36
 */
public class ReplenishmentPlanServicesDefine {

    /**
     * 计划补货单 保存服务
     * 请求参数：{@link ReplenishmentPlanDto}
     * 返回参数：{@link ResponseVo<ReplenishmentPlanResult>}
     */
    public static final String REPLENISHMENT_PLAN_SERVICE = "com.cloud.cang.rmp.ws.DynamicReplenishmentService.replenishmentPlan";

    /**
     * 计划补货单 动态生成补货单
     * 请求参数：{@link DynamicReplenishmentDto}
     * 返回参数：{@link ResponseVo<DynamicReplenishmentResult> }
     */
    public static final String DYNAMIC_REPLENISHMENT_SERVICE = "com.cloud.cang.rmp.ws.DynamicReplenishmentService.dynamicReplenishment";

}
