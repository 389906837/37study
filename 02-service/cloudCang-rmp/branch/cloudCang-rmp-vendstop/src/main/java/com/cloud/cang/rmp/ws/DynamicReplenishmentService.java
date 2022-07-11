package com.cloud.cang.rmp.ws;


import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.rm.*;
import com.cloud.cang.rmp.rm.service.ReplenishmentPlanService;
import com.cloud.cang.rmp.rm.service.ReplenishmentRecordService;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态补货单服务
 * @author ChangTanchang
 * 2018年03月14日
 */
@RestController
@RequestMapping("/dynamicReplenishmentService")
@RegisterRestResource
public class DynamicReplenishmentService {

    @Autowired
    private ReplenishmentPlanService replenishmentPlanService;

	private static final Logger logger = LoggerFactory.getLogger(DynamicReplenishmentService.class);

	/**
	 * 动态补货单服务
	 * @param dynamicDto 补货单参数
	 * @return
	 */
	@RequestMapping(value = "/dynamicReplenishment", method = RequestMethod.POST)
	public ResponseVo<DynamicReplenishmentResult> dynamicReplenishment(@RequestBody DynamicReplenishmentDto dynamicDto) {
		logger.debug("动态补货单服务开始...");
        logger.info("动态补货单服务参数：{}", dynamicDto);
		ResponseVo<DynamicReplenishmentResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(dynamicDto.getSdeviceId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备信息不能为空");
			}
			return replenishmentPlanService.dynamicReplenishmentGenerate(dynamicDto);
		} catch (Exception e) {
			logger.error("动态补货单服务异常：{}", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("动态补货单服务异常");
		return responseVo;
	}


    /**
     * 计划补货单保存
     * @param planDto 计划补货单参数
     * @return
     */
    @RequestMapping(value = "/replenishmentPlan", method = RequestMethod.POST)
    public ResponseVo<ReplenishmentPlanResult> replenishmentPlan(@RequestBody ReplenishmentPlanDto planDto) {
        logger.debug("计划补货单保存服务开始...");
        logger.info("计划补货单保存服务参数：{}", planDto);
        ResponseVo<ReplenishmentPlanResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            if (StringUtil.isBlank(planDto.getSdeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备信息不能为空");
            }
            if (StringUtil.isBlank(planDto.getOperMan())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("操作人不能为空");
            }
            if (null == planDto.getCommodityDtos() || planDto.getCommodityDtos().size() <= 0){
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品信息不能为空");
            }
            return replenishmentPlanService.replenishmentPlanSave(planDto);
        } catch (Exception e) {
            logger.error("计划补货保存服务异常：{}", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("计划补货保存服务异常");
        return responseVo;
    }
}
