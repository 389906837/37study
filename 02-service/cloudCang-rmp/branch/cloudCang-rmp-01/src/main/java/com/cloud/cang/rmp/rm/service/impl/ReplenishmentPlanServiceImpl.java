package com.cloud.cang.rmp.rm.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.model.rm.ReplenishmentPlanDetail;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceManage;
import com.cloud.cang.model.sm.StandardStock;
import com.cloud.cang.model.sp.CommodityBatch;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.rm.*;
import com.cloud.cang.rmp.rm.dao.ReplenishmentPlanDao;
import com.cloud.cang.rmp.rm.service.ReplenishmentPlanDetailService;
import com.cloud.cang.rmp.rm.service.ReplenishmentPlanService;
import com.cloud.cang.rmp.sb.service.DeviceInfoService;
import com.cloud.cang.rmp.sb.service.DeviceManageService;
import com.cloud.cang.rmp.sm.service.StandardDetailService;
import com.cloud.cang.rmp.sm.service.StandardStockService;
import com.cloud.cang.rmp.sm.vo.StandardStockVo;
import com.cloud.cang.rmp.sp.service.CommodityBatchService;
import com.cloud.cang.rmp.sp.service.CommodityInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReplenishmentPlanServiceImpl extends GenericServiceImpl<ReplenishmentPlan, String> implements
		ReplenishmentPlanService {

	@Autowired
	ReplenishmentPlanDao replenishmentPlanDao;

	@Autowired
	private DeviceInfoService deviceInfoService;

	@Autowired
	private StandardStockService standardStockService;

	@Autowired
	private ReplenishmentPlanDetailService replenishmentPlanDetailService;

	@Autowired
	private StandardDetailService standardDetailService;

	@Autowired
	private DeviceManageService deviceManageService;

	@Autowired
	private CommodityInfoService commodityInfoService;

	@Autowired
	private CommodityBatchService commodityBatchService;
	@Override
	public GenericDao<ReplenishmentPlan, String> getDao() {
		return replenishmentPlanDao;
	}

	/**
	 * ???????????????????????????
	 * @param dynamicDto ????????????
	 * @throws Exception
	 */
	@Override
	public ResponseVo<DynamicReplenishmentResult> dynamicReplenishmentGenerate(DynamicReplenishmentDto dynamicDto) throws Exception {

		ResponseVo<DynamicReplenishmentResult> responseVo = ResponseVo.getSuccessResponse();
		DynamicReplenishmentResult result = null;

		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(dynamicDto.getSdeviceId());
		if (null == deviceInfo || deviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode() ||
				deviceInfo.getIstatus() != BizTypeDefinitionEnum.DeviceStatus.NORMAL.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????");
		}

		List<CommodityResult> commodityResults = new ArrayList<CommodityResult>();
		CommodityResult commodityResult = null;
		//????????????????????????????????????????????????
		ReplenishmentPlan replenishmentPlan = this.selectUndoneByDeviceId(dynamicDto.getSdeviceId());
		if (null != replenishmentPlan) {//????????????????????????   ????????????????????????????????????
			result = new DynamicReplenishmentResult();
			BeanUtils.copyProperties(result, replenishmentPlan);//????????????
			result.setUpdate(false);//???????????????
			result.setSremark(replenishmentPlan.getSremark());
			result.setSdeviceName(deviceInfo.getSname());
			result.setSreplenishmentCode(replenishmentPlan.getScode());
			ReplenishmentPlanDetail params = new ReplenishmentPlanDetail();
			params.setSreplenishmentId(replenishmentPlan.getId());
			List<ReplenishmentPlanDetail> planDetailList = replenishmentPlanDetailService.selectByEntityWhere(params);
			if (null != planDetailList && planDetailList.size() > 0) {
				for (ReplenishmentPlanDetail detail : planDetailList) {
					commodityResult = new CommodityResult();
					BeanUtils.copyProperties(commodityResult, detail);//????????????
					commodityResults.add(commodityResult);
				}
			}
			result.setCommodityResults(commodityResults);
			responseVo.setData(result);
			return responseVo;
		}

		StandardStock standardStock = standardStockService.selectBySdeviceId(dynamicDto.getSdeviceId());
		// ?????????????????????????????????(????????????:10=??????,20=??????),???????????????????????????????????????????????????,????????????????????????
		if (null == standardStock || standardStock.getIstatus().intValue() == BizTypeDefinitionEnum.StockIstatus.DISABLE_ISTATUS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
		}
		//???????????????????????????????????????
		List<StandardStockVo> detailList = standardDetailService.selectStandardStockByDeviceId(deviceInfo.getId());
		if (null != detailList && detailList.size() > 0) {
			CommodityBatch commodityBatch = null;
			StringBuffer sb = null;
			int tempCount = 0;
			for (StandardStockVo tempVo : detailList) {//???????????????????????? ?????????????????????????????????

				//??????????????????????????????????????????
				if (null != tempVo.getIstandardStock() && tempVo.getIstandardStock().intValue() > tempVo.getIstock()) {
					//???????????????
					commodityResult = new CommodityResult();
					commodityResult.setScommodityId(tempVo.getScommodityId());
					commodityResult.setScommodityCode(tempVo.getScommodityCode());
					commodityResult.setScommodityName(tempVo.getSname());
					commodityResult.setForderCount(tempVo.getIstandardStock().intValue()-tempVo.getIstock());
					commodityResult.setFcommodityPrice(tempVo.getFsalePrice());
					commodityResult.setFcommodityAmount(commodityResult.getFcommodityPrice().multiply(new BigDecimal(commodityResult.getForderCount())));

					//???????????????????????? ??????????????????
					sb = new StringBuffer();
					int tempNum = commodityResult.getForderCount();//?????????
					do {
						tempCount = 0;
						commodityBatch = commodityBatchService.selectByCommodityId(tempVo.getScommodityId());
						if (null != commodityBatch) {
							//??????????????????-??????????????????-????????????????????????
							if (null == commodityBatch.getIcommodityNum()) {
								tempCount = 0;
							} else {
								if (null == commodityBatch.getIshelfNum()) {
									commodityBatch.setIshelfNum(0);
								}
								tempCount = (commodityBatch.getIcommodityNum() - commodityBatch.getIshelfNum()) - tempNum;
								if (tempCount < 0) {
									tempNum = commodityResult.getForderCount() - (commodityBatch.getIcommodityNum() - commodityBatch.getIshelfNum());
								}
								sb.append(commodityBatch.getSbatchNo() + ",");
							}
						}
					} while (tempCount < 0);
					if (sb.toString().length() > 0) {
						commodityResult.setSremark(sb.toString().substring(0, sb.toString().length()-1));
					}
					commodityResults.add(commodityResult);
				}
			}
		}
		result = new DynamicReplenishmentResult();
		result.setSreplenishmentCode(CoreUtils.newCode("rm_replenishment_plan"));
		result.setSdeviceId(deviceInfo.getId());
		result.setSdeviceCode(deviceInfo.getScode());
		result.setSdeviceName(deviceInfo.getSname());
		result.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
		result.setTgenerateTime(new Date());
		//???????????????????????????
		DeviceManage deviceManage = deviceManageService.selectByDeviceId(deviceInfo.getId());
		if(null != deviceManage) {
			result.setSrenewalMobile(deviceManage.getSreplenishmentContact());
			result.setSrenewalName(deviceManage.getSreplenishment());
		}
		result.setUpdate(true);
		result.setCommodityResults(commodityResults);
		responseVo.setData(result);
		return responseVo;
	}

	/**
	 * ?????????????????????
	 * @param planDto ?????????????????????
	 * @throws Exception
	 */
	@Override
	public ResponseVo<ReplenishmentPlanResult> replenishmentPlanSave(ReplenishmentPlanDto planDto) throws Exception {

		ResponseVo<ReplenishmentPlanResult> responseVo = ResponseVo.getSuccessResponse();
		ReplenishmentPlanResult replenishmentResult = new ReplenishmentPlanResult();

		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(planDto.getSdeviceId());
		if (null == deviceInfo || deviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode() ||
				deviceInfo.getIstatus() != BizTypeDefinitionEnum.DeviceStatus.NORMAL.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????");
		}
		StandardStock standardStock = standardStockService.selectBySdeviceId(planDto.getSdeviceId());
		// ?????????????????????????????????(????????????:10=??????,20=??????),???????????????????????????????????????????????????,????????????????????????
		if (null == standardStock || standardStock.getIstatus().intValue() == BizTypeDefinitionEnum.StockIstatus.DISABLE_ISTATUS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????????????????????????????????????????");
		}

		ReplenishmentPlan replenishmentPlan = this.selectUndoneByDeviceId(planDto.getSdeviceId());
		if (null != replenishmentPlan) {//????????????????????????
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
		}
		//???????????????????????????
		ReplenishmentPlan plan = new ReplenishmentPlan();

		plan.setScode(CoreUtils.newCode("rm_replenishment_plan"));
		plan.setSmerchantId(deviceInfo.getSmerchantId());
		plan.setSmerchantCode(deviceInfo.getSmerchantCode());
		plan.setSdeviceId(deviceInfo.getId());
		plan.setSdeviceCode(deviceInfo.getScode());
		plan.setSdeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));

		plan.setSrenewalMobile(planDto.getSrenewalMobile());
		plan.setSrenewalName(planDto.getSrenewalName());

		plan.setIdelFlag(0);
		plan.setIstatus(10);
		plan.setSremark(planDto.getSremark());
		plan.setTgenerateTime(new Date());//????????????

		plan.setSaddUser(planDto.getOperMan());
		plan.setTaddTime(new Date());
		plan.setSupdateUser(planDto.getOperMan());
		plan.setTupdateTime(new Date());

		this.insert(plan);//????????????
		replenishmentResult.setPlan(plan);
		//???????????????????????????
		List<ReplenishmentPlanDetail> details = new ArrayList<ReplenishmentPlanDetail>();
		ReplenishmentPlanDetail detail = null;
		CommodityInfo commodityInfo = null;
		for (CommodityDto commodityDto : planDto.getCommodityDtos()) {
			commodityInfo = commodityInfoService.selectByPrimaryKey(commodityDto.getScommodityId());
			detail = new ReplenishmentPlanDetail();
			detail.setSreplenishmentId(plan.getId());
			detail.setSreplenishmentCode(plan.getScode());
			detail.setScommodityId(commodityInfo.getId());
			detail.setScommodityCode(commodityInfo.getScode());
			detail.setScommodityName(commodityInfo.getSname());
			detail.setSremark(commodityDto.getSremark());
			detail.setForderCount(commodityDto.getForderCount());
			detail.setFcommodityPrice(commodityInfo.getFsalePrice());
			detail.setFcommodityAmount(detail.getFcommodityPrice().multiply(new BigDecimal(detail.getForderCount())));
			detail.setTaddTime(new Date());
			detail.setTupdateTime(new Date());
			replenishmentPlanDetailService.insert(detail);//?????????????????????????????????
			details.add(detail);
		}
		replenishmentResult.setDetails(details);
		responseVo.setData(replenishmentResult);
		return responseVo;

	}
	/**
	 * ????????????????????? ???????????????
	 * @param sdeviceId ??????ID
	 * @return
	 */
	@Override
	public ReplenishmentPlan selectUndoneByDeviceId(String sdeviceId) {
		return replenishmentPlanDao.selectUndoneByDeviceId(sdeviceId);
	}

}