package com.cloud.cang.mgr.rm.service;



import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.rm.domain.ReplenishMentPlanDomain;
import com.cloud.cang.mgr.rm.vo.ReplenishMentPlanVo;
import com.cloud.cang.mgr.sb.vo.ReplenishmentVo;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;

public interface ReplenishmentPlanService extends GenericService<ReplenishmentPlan, String> {

    /**
     * 计划商品补货查询接口
     * @param page
     * @param replenishMentPlanVo
     * @return
     */
    Page<ReplenishMentPlanDomain> selectReplenishMentPlan(Page<ReplenishMentPlanDomain> page, ReplenishMentPlanVo replenishMentPlanVo);

    /**
     * 动态生成补货单
     * @param replenishmentVo 补货单参数
     * @param request
     * @return
     */
    ResponseVo<ReplenishmentPlan> generateReplenishment(ReplenishmentVo replenishmentVo, HttpServletRequest request) throws Exception;

    /**
     * 查询设备信息
     *
     * @param sid
     * @return
     */
    ReplenishMentPlanDomain selectByrmpId(String sid);
}