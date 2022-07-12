package com.cloud.cang.mgr.rm.service.impl;

import java.util.ArrayList;
import java.util.List;


import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.jy.GeneratingOrderResults;
import com.cloud.cang.jy.OrderDiscountDefine;
import com.cloud.cang.mgr.rm.domain.ReplenishMentPlanDomain;
import com.cloud.cang.mgr.rm.vo.ReplenishMentPlanVo;
import com.cloud.cang.mgr.sb.vo.ReplenishmentVo;
import com.cloud.cang.rm.CommodityDto;
import com.cloud.cang.rm.ReplenishmentPlanDto;
import com.cloud.cang.rm.ReplenishmentPlanResult;
import com.cloud.cang.rm.ReplenishmentPlanServicesDefine;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.mgr.rm.dao.ReplenishmentPlanDao;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.mgr.rm.service.ReplenishmentPlanService;

import javax.servlet.http.HttpServletRequest;

@Service
public class ReplenishmentPlanServiceImpl extends GenericServiceImpl<ReplenishmentPlan, String> implements
		ReplenishmentPlanService {

	@Autowired
	ReplenishmentPlanDao replenishmentPlanDao;

	
	@Override
	public GenericDao<ReplenishmentPlan, String> getDao() {
		return replenishmentPlanDao;
	}


	@Override
	public Page<ReplenishMentPlanDomain> selectReplenishMentPlan(Page<ReplenishMentPlanDomain> page, ReplenishMentPlanVo replenishMentPlanVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return replenishmentPlanDao.selectReplenishMentPlan(replenishMentPlanVo);
	}
	/**
	 * 动态生成补货单
	 * @param replenishmentVo 补货单参数
	 * @param request
	 * @return
	 */
	@Override
	public ResponseVo<ReplenishmentPlan> generateReplenishment(ReplenishmentVo replenishmentVo, HttpServletRequest request) throws Exception {
		ReplenishmentPlanDto planDto = new ReplenishmentPlanDto();
		List<CommodityDto> commodityDtos = new ArrayList<CommodityDto>();
		planDto.setOperMan(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
		planDto.setSdeviceId(replenishmentVo.getDeviceId());
		planDto.setSrenewalMobile(replenishmentVo.getSrenewalMobile());
		planDto.setSrenewalName(replenishmentVo.getSrenewalName());
		planDto.setSremark(replenishmentVo.getSremark());
		CommodityDto commodityDto = null;
		String tempStr = "";
		Integer tempCount = 0;
		for (String commodityId : replenishmentVo.getCommodityIds()) {
			commodityDto = new CommodityDto();
			if (StringUtil.isNotBlank(commodityId)) {
				commodityDto.setScommodityId(commodityId);
				tempStr = request.getParameter("count_"+commodityId);
				try {
					tempCount = Integer.parseInt(tempStr);
				} catch (Exception e) {
					continue;
				}
				commodityDto.setForderCount(tempCount);
				tempStr = request.getParameter("sremark_"+commodityId);
				if (StringUtil.isNotBlank(tempStr)) {
					commodityDto.setSremark(tempStr);
				}
				commodityDtos.add(commodityDto);
			}
		}
		if (commodityDtos.size() < 0) {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("补货单商品数据异常");
		}
		planDto.setCommodityDtos(commodityDtos);
		RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ReplenishmentPlanServicesDefine.REPLENISHMENT_PLAN_SERVICE);// 服务名称
		// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
		invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<ReplenishmentPlanResult>>() { });
		invoke.setRequestObj(planDto); // post 参数
		ResponseVo<ReplenishmentPlanResult> resVO = (ResponseVo<ReplenishmentPlanResult>) invoke.invoke();
		if (resVO.isSuccess()) {
			return ResponseVo.getSuccessResponse(resVO.getData().getPlan());
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(resVO.getMsg());
	}

	@Override
	public ReplenishMentPlanDomain selectByrmpId(String sid) {
		return replenishmentPlanDao.selectByrmpId(sid);
	}

}