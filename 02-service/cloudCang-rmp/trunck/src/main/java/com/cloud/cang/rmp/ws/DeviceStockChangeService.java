package com.cloud.cang.rmp.ws;


import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.rm.ReplenishmentDto;
import com.cloud.cang.rm.ReplenishmentResult;
import com.cloud.cang.rm.StockOperDto;
import com.cloud.cang.rmp.sm.service.DeviceStockService;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备库存服务
 * @author zhouhong
 * 2018年03月14日
 */
@RestController
@RequestMapping("/stockChangeService")
@RegisterRestResource
public class DeviceStockChangeService {

    @Autowired
    private DeviceStockService deviceStockService;
	private static final Logger logger = LoggerFactory.getLogger(DeviceStockChangeService.class);

	/**
	 * 生成补货单服务
	 * @param replenishmentDto 补货单参数
	 * @return
	 */
	@RequestMapping(value = "/generateReplenishment", method = RequestMethod.POST)
	public ResponseVo<ReplenishmentResult> generateReplenishment(@RequestBody ReplenishmentDto replenishmentDto) {
		logger.info("生成补货单服务服务开始...参数：{}", replenishmentDto);
		ResponseVo<ReplenishmentResult> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(replenishmentDto.getSmerchantId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户信息不能为空");
			}
			if (StringUtil.isBlank(replenishmentDto.getSdeviceId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备信息不能为空");
			}
			if (null == replenishmentDto.getItype()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补货类型不能为空");
			}
			if (null == replenishmentDto.getIsourceClientType()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补货来源客户端不能为空");
			}
			if ((null == replenishmentDto.getSubVoList() || replenishmentDto.getSubVoList().size() <= 0) &&
					(null == replenishmentDto.getAddVoList() || replenishmentDto.getAddVoList().size() <=0)) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补货变更商品不能为空");
			}
			return deviceStockService.generateReplenishmentRecord(replenishmentDto);
		} catch (Exception e) {
			logger.error("生成补货单服务服务异常：{}", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("系统异常，补货单生成服务异常");
		return responseVo;
	}

	/**
	 * 库存操作服务
	 * @param stockOperDto 库存操作服务参数
	 * @return
	 */
	@RequestMapping(value = "/stockOper", method = RequestMethod.POST)
	public ResponseVo<String> stockOper(@RequestBody StockOperDto stockOperDto) {
		logger.info("库存操作服务开始...参数：{}", stockOperDto);
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			//校验基础参数
			if (StringUtil.isBlank(stockOperDto.getSmerchantId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户信息不能为空");
			}
			if (StringUtil.isBlank(stockOperDto.getSdeviceId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备信息不能为空");
			}
			if (null == stockOperDto.getItype()) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("库存操作类型不能为空");
			}

			if ((null == stockOperDto.getSubVoList() || stockOperDto.getSubVoList().size() <= 0) &&
					(null == stockOperDto.getAddVoList() || stockOperDto.getAddVoList().size() <=0)) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("库存操作商品不能为空");
			}
			return deviceStockService.stockOper(stockOperDto);
		} catch (Exception e) {
			logger.error("库存操作服务异常：{}", e);
		}
		responseVo.setSuccess(false);
		responseVo.setErrorCode(-1000);
		responseVo.setMsg("系统异常，库存变更异常");
		return responseVo;
	}

}
