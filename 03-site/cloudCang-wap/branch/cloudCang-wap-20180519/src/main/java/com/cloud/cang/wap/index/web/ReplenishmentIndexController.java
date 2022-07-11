package com.cloud.cang.wap.index.web;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.om.vo.RefundOrderVo;
import com.cloud.cang.wap.rm.dao.ReplenishmentCommodityDao;
import com.cloud.cang.wap.rm.service.ReplenishmentCommodityService;
import com.cloud.cang.wap.rm.service.ReplenishmentPlanService;
import com.cloud.cang.wap.rm.service.ReplenishmentRecordService;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import com.cloud.cang.wap.sys.service.OperatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 补货员首页
 */
@Controller
@RequestMapping("/replenishment")
public class ReplenishmentIndexController {

	@Autowired
	private IndexService indexService;
	@Autowired
	private DeviceInfoService deviceInfoService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private ReplenishmentRecordService replenishmentRecordService;
	@Autowired
	private ReplenishmentCommodityService replenishmentCommodityService;
	@Autowired
	private ReplenishmentPlanService replenishmentPlanService;
	@Autowired
	private ICached iCached;
	private static Logger logger = LoggerFactory.getLogger(ReplenishmentIndexController.class);

	/**
	 *  首页数据
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(ModelMap map, HttpServletRequest request) throws Exception {

		DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
		if(null == deviceVo) {
			deviceVo = new DeviceVo();
		}
		logger.info("设备信息:{}", deviceVo);
		if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
			if (StringUtil.isBlank(deviceVo.getMerchantCode())) {
				String merchantCode = WapUtils.getMerchantCookie(request);
				if (StringUtil.isNotBlank(merchantCode)) {
					deviceVo.setMerchantCode(merchantCode);
				}
			}
			if (StringUtil.isBlank(deviceVo.getDeviceId())) {
				String deviceStr = WapUtils.getDeviceCodeCookie(request);
				if (StringUtil.isNotBlank(deviceStr)) {
					deviceVo.setDeviceCode(deviceStr.split("#//#")[0]);
					deviceVo.setDeviceId(deviceStr.split("#//#")[1]);
				}
			}
			if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {
				iCached.remove(deviceVo.getOpenDoorKey());//删除上次开门的KEY
			}
		}
		if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
			return WapUtils.errorDealWith("10000","");
		}


		//获取用户数据
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
		//补货员信息
		Operator operator = operatorService.selectByMemberName(authVo.getUserName());
		if(null == operator) {
			map.put("resVo", new SiteResponseVo(false, -1001, "访问异常，请重新操作", -1));
			return new ModelAndView("error/error");
		}
		map.put("srealName", operator.getSrealName());//真实姓名

		if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
			Map<String, Object> openParam = indexService.assemblyOpenParam(deviceVo, 20);
			map.put("openParam", JSON.toJSONString(openParam));
		}
		return new ModelAndView("replenishment/index");
	}

	/**
	 * 开门成功
	 * @throws Exception
	 */
	@RequestMapping("/openSuccess")
	public ModelAndView openSuccess(ModelMap map) throws Exception {
		logger.debug("补货员开门成功...");
		DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
		logger.info("补货员开门成功，设备信息:{}", deviceVo);
		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
		Map<String, Object> params = new HashMap<>();
		//商户客户端配置信息
		params = indexService.getMerchantClientConfByMap(params, deviceInfo.getSmerchantCode());
		map.put("open", params);
		//商户信息
		MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceInfo.getSmerchantCode());
		if (null != merchantInfo) {
			map.put("merchantName", merchantInfo.getSname());
		}
		//获取用户数据
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
		//设备信息
		map.put("deviceCode", deviceInfo.getScode());//设备编号
		map.put("deviceAddress", CoreUtils.generateDeviceAddress(deviceInfo));//设备地址
		//补货员信息
		Operator operator = operatorService.selectByMemberName(authVo.getUserName());
		if (null != operator) {
			map.put("srealName", operator.getSrealName());//真实姓名
		}
		//开门时间
		map.put("openTime", DateUtils.dateToString(new Date(),"yyyy-MM-dd HH:mm"));
		return new ModelAndView("replenishment/openSuccess");
	}

	/**
	 * 补货员关门成功 补货成功
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/closeDoorSuccess")
	public String closeDoorSuccess(String recordCode, ModelMap modelMap) throws Exception {
		logger.info("补货员关门成功");
		if (StringUtil.isBlank(recordCode)) {
			return "replenishment/empty";
		}
		modelMap.put("recordCode",recordCode);
		ReplenishmentRecord record = replenishmentRecordService.selectByCode(recordCode);
		if (null == record) {
			return "replenishment/empty";
		}
		modelMap.put("record",record);
		//上架商品
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sreplenishmentId", record.getId());
		params.put("itype", 10);
		List<ReplenishmentCommodity> addList = replenishmentCommodityService.selectByMapWhere(params);
		modelMap.put("addList", addList);
		//下架商品
		params = new HashMap<String, Object>();
		params.put("sreplenishmentId", record.getId());
		params.put("itype", 20);
		List<ReplenishmentCommodity> subList = replenishmentCommodityService.selectByMapWhere(params);
		modelMap.put("subList", subList);

		return "replenishment/success";
	}

	/***
	 * 补货 标记完成
	 * @param deviceCode 设备编号
	 * @return
	 */
	@RequestMapping("/markingDone")
	public @ResponseBody
	ResponseVo<String> markingDone(String deviceCode) {
		try {
			if (StringUtil.isBlank(deviceCode)) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数异常");
			}
			//获取商户数据
			DeviceInfo deviceInfo = deviceInfoService.selectByCode(deviceCode);
			if (null == deviceInfo) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备不存在");
			}
			//获取用户数据
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
			if (!authVo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("非法操作，如有疑问请联系客服");
			}
			replenishmentPlanService.updateByLastTime(deviceCode);
			return ResponseVo.getSuccessResponse();
		} catch (Exception e) {
			logger.error("补货完成标记失败：{}", e);
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("补货完成标记失败，请重新操作");
	}
}
