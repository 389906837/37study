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
	 * 动态生成补货单查询
	 * @param dynamicDto 查询参数
	 * @throws Exception
	 */
	@Override
	public ResponseVo<DynamicReplenishmentResult> dynamicReplenishmentGenerate(DynamicReplenishmentDto dynamicDto) throws Exception {

		ResponseVo<DynamicReplenishmentResult> responseVo = ResponseVo.getSuccessResponse();
		DynamicReplenishmentResult result = null;

		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(dynamicDto.getSdeviceId());
		if (null == deviceInfo || deviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode() ||
				deviceInfo.getIstatus() != BizTypeDefinitionEnum.DeviceStatus.NORMAL.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("设备状态异常");
		}

		List<CommodityResult> commodityResults = new ArrayList<CommodityResult>();
		CommodityResult commodityResult = null;
		//查询设备是否有未完成的计划补货单
		ReplenishmentPlan replenishmentPlan = this.selectUndoneByDeviceId(dynamicDto.getSdeviceId());
		if (null != replenishmentPlan) {//有未完成的补货单   返回未完成计划补货单订单
			result = new DynamicReplenishmentResult();
			BeanUtils.copyProperties(result, replenishmentPlan);//复制对象
			result.setUpdate(false);//是否能修改
			result.setSremark(replenishmentPlan.getSremark());
			result.setSdeviceName(deviceInfo.getSname());
			result.setSreplenishmentCode(replenishmentPlan.getScode());
			ReplenishmentPlanDetail params = new ReplenishmentPlanDetail();
			params.setSreplenishmentId(replenishmentPlan.getId());
			List<ReplenishmentPlanDetail> planDetailList = replenishmentPlanDetailService.selectByEntityWhere(params);
			if (null != planDetailList && planDetailList.size() > 0) {
				for (ReplenishmentPlanDetail detail : planDetailList) {
					commodityResult = new CommodityResult();
					BeanUtils.copyProperties(commodityResult, detail);//复制对象
					commodityResults.add(commodityResult);
				}
			}
			result.setCommodityResults(commodityResults);
			responseVo.setData(result);
			return responseVo;
		}

		StandardStock standardStock = standardStockService.selectBySdeviceId(dynamicDto.getSdeviceId());
		// 判断设备是不是标准库存(根据状态:10=启用,20=禁用),若是启用状态去查询有没有计划补货单,若是禁用给出提示
		if (null == standardStock || standardStock.getIstatus().intValue() == BizTypeDefinitionEnum.StockIstatus.DISABLE_ISTATUS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请先设置标准库存");
		}
		//查询设备标准库存配置明细表
		List<StandardStockVo> detailList = standardDetailService.selectStandardStockByDeviceId(deviceInfo.getId());
		if (null != detailList && detailList.size() > 0) {
			CommodityBatch commodityBatch = null;
			StringBuffer sb = null;
			int tempCount = 0;
			for (StandardStockVo tempVo : detailList) {//循环标准库存商品 跟库存对比计算补货数量

				//盘点标准库存是否大于当前库存
				if (null != tempVo.getIstandardStock() && tempVo.getIstandardStock().intValue() > tempVo.getIstock()) {
					//需要补库存
					commodityResult = new CommodityResult();
					commodityResult.setScommodityId(tempVo.getScommodityId());
					commodityResult.setScommodityCode(tempVo.getScommodityCode());
					commodityResult.setScommodityName(tempVo.getSname());
					commodityResult.setForderCount(tempVo.getIstandardStock().intValue()-tempVo.getIstock());
					commodityResult.setFcommodityPrice(tempVo.getFsalePrice());
					commodityResult.setFcommodityAmount(commodityResult.getFcommodityPrice().multiply(new BigDecimal(commodityResult.getForderCount())));

					//设备商品批次数据 获取批次备注
					sb = new StringBuffer();
					int tempNum = commodityResult.getForderCount();//初始化
					do {
						tempCount = 0;
						commodityBatch = commodityBatchService.selectByCommodityId(tempVo.getScommodityId());
						if (null != commodityBatch) {
							//批次商品数量-已上架的数量-本次计划上架数量
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
		//查询设备补货员信息
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
	 * 计划补货单保存
	 * @param planDto 计划补货单参数
	 * @throws Exception
	 */
	@Override
	public ResponseVo<ReplenishmentPlanResult> replenishmentPlanSave(ReplenishmentPlanDto planDto) throws Exception {

		ResponseVo<ReplenishmentPlanResult> responseVo = ResponseVo.getSuccessResponse();
		ReplenishmentPlanResult replenishmentResult = new ReplenishmentPlanResult();

		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(planDto.getSdeviceId());
		if (null == deviceInfo || deviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode() ||
				deviceInfo.getIstatus() != BizTypeDefinitionEnum.DeviceStatus.NORMAL.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("设备状态异常");
		}
		StandardStock standardStock = standardStockService.selectBySdeviceId(planDto.getSdeviceId());
		// 判断设备是不是标准库存(根据状态:10=启用,20=禁用),若是启用状态去查询有没有计划补货单,若是禁用给出提示
		if (null == standardStock || standardStock.getIstatus().intValue() == BizTypeDefinitionEnum.StockIstatus.DISABLE_ISTATUS.getCode()) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("标准库存状态异常，无法生存计划补货单");
		}

		ReplenishmentPlan replenishmentPlan = this.selectUndoneByDeviceId(planDto.getSdeviceId());
		if (null != replenishmentPlan) {//有未完成的补货单
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("重复添加计划补货单");
		}
		//新增计划补货单信息
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
		plan.setTgenerateTime(new Date());//生成日期

		plan.setSaddUser(planDto.getOperMan());
		plan.setTaddTime(new Date());
		plan.setSupdateUser(planDto.getOperMan());
		plan.setTupdateTime(new Date());

		this.insert(plan);//保存数据
		replenishmentResult.setPlan(plan);
		//新增计划补货单明细
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
			replenishmentPlanDetailService.insert(detail);//新增计划补货单明细数据
			details.add(detail);
		}
		replenishmentResult.setDetails(details);
		responseVo.setData(replenishmentResult);
		return responseVo;

	}
	/**
	 * 查询补货单数据 未完成状态
	 * @param sdeviceId 设备ID
	 * @return
	 */
	@Override
	public ReplenishmentPlan selectUndoneByDeviceId(String sdeviceId) {
		return replenishmentPlanDao.selectUndoneByDeviceId(sdeviceId);
	}

}