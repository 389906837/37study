package com.cloud.cang.wap.index.web;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
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
import com.cloud.cang.wap.rm.vo.ReplenishmentCommodityVo;
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
 * ???????????????
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
	 *  ????????????
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(ModelMap map, HttpServletRequest request) throws Exception {

		DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
		if(null == deviceVo) {
			deviceVo = new DeviceVo();
		}
		logger.info("????????????:{}", deviceVo);
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
				iCached.remove(deviceVo.getOpenDoorKey());//?????????????????????KEY
			}
		}
		if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
			return WapUtils.errorDealWith("10000","");
		}
		//?????????????????????????????????
		MerchantClientConf clientConf = indexService.getMerchantClientConfByCode(deviceVo.getMerchantCode());

		if (StringUtil.isNotBlank(clientConf.getSloginLogo())) {
			map.put("slogo", clientConf.getSloginLogo());
		}
		//??????????????????
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
		//???????????????
		Operator operator = operatorService.selectByMemberName(authVo.getUserName(),authVo.getSmerchantCode());
		if(null == operator) {
			map.put("resVo", new SiteResponseVo(false, -1001, "??????????????????????????????", -1));
			return new ModelAndView("error/error");
		}
		map.put("srealName", operator.getSrealName());//????????????

		if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
			Map<String, Object> openParam = indexService.assemblyOpenParam(deviceVo, 20);
			map.put("openParam", JSON.toJSONString(openParam));
		}
		return new ModelAndView("replenishment/index");
	}

	/**
	 * ????????????
	 * @throws Exception
	 */
	@RequestMapping("/openSuccess")
	public ModelAndView openSuccess(ModelMap map) throws Exception {
		logger.debug("?????????????????????...");
		DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
		logger.info("????????????????????????????????????:{}", deviceVo);
		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
		Map<String, Object> params = new HashMap<>();
		//???????????????????????????
		params = indexService.getMerchantClientConfByMap(params, deviceInfo.getSmerchantCode());
		map.put("open", params);
		//????????????
		MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceInfo.getSmerchantCode());
		if (null != merchantInfo) {
			map.put("merchantCode", merchantInfo.getScode());
			MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode());
			map.put("merchantName", clientConf.getSshortName());
		}
		//??????????????????
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
		//????????????
		map.put("deviceCode", deviceInfo.getScode());//????????????
		map.put("deviceName", deviceInfo.getSname());//????????????
		map.put("deviceAddress", CoreUtils.generateDeviceAddress(deviceInfo));//????????????
		//???????????????
		Operator operator = operatorService.selectByMemberName(authVo.getUserName(),authVo.getSmerchantCode());
		if (null != operator) {
			map.put("srealName", operator.getSrealName());//????????????
		}
		String openTime = DateUtils.dateToString(new Date(),"yyyy???MM???dd??? HH:mm:ss");
		iCached.put("openTime_"+authVo.getId(), openTime);
		//????????????
		map.put("openTime", openTime);
		return new ModelAndView("replenishment/openSuccess");
	}

	/**
	 * ????????????????????? ????????????
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/closeDoorSuccess")
	public String closeDoorSuccess(String recordCode, ModelMap modelMap) throws Exception {
		logger.info("?????????????????????");
		if (StringUtil.isBlank(recordCode)) {
			return "replenishment/empty";
		}
		modelMap.put("recordCode",recordCode);
		ReplenishmentRecord record = replenishmentRecordService.selectByCode(recordCode);
		if (null == record) {
			return "replenishment/empty";
		}
		modelMap.put("record", record);

		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(record.getSdeviceId());
		//????????????
		MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceInfo.getSmerchantCode());
		if (null != merchantInfo) {
			modelMap.put("merchantCode", merchantInfo.getScode());
			MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode());
			modelMap.put("merchantName", clientConf.getSshortName());
		}
		//??????????????????
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
		//????????????
		modelMap.put("deviceCode", deviceInfo.getScode());//????????????
		modelMap.put("deviceName", deviceInfo.getSname());//????????????
		modelMap.put("deviceAddress", CoreUtils.generateDeviceAddress(deviceInfo));//????????????
		//???????????????
		Operator operator = operatorService.selectByMemberName(authVo.getUserName(),authVo.getSmerchantCode());
		if (null != operator) {
			modelMap.put("srealName", operator.getSrealName());//????????????
		}
		String closeTime = DateUtils.dateToString(new Date(),"yyyy???MM???dd??? HH:mm:ss");
		//????????????
		modelMap.put("openTime", iCached.get("openTime_"+authVo.getId()));
		iCached.remove("openTime_"+authVo.getId());
		modelMap.put("closeTime", closeTime);
		//????????????
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sreplenishmentId", record.getId());
		params.put("itype", 10);
		params.put("deviceId", deviceInfo.getId());
		List<ReplenishmentCommodityVo> addList = replenishmentCommodityService.selectByMap(params);
		if (null != addList) {
			modelMap.put("addList", addList);
			modelMap.put("addSize", record.getIactualShelves());
		}
		//????????????
		params = new HashMap<String, Object>();
		params.put("sreplenishmentId", record.getId());
		params.put("itype", 20);
		params.put("deviceId", deviceInfo.getId());
		List<ReplenishmentCommodityVo> subList = replenishmentCommodityService.selectByMap(params);
		if (null != subList) {
			modelMap.put("subList", subList);
			modelMap.put("subSize", record.getIactualUnder());
		}

		return "replenishment/success";
	}

	/***
	 * ?????? ????????????
	 * @param deviceCode ????????????
	 * @return
	 */
	@RequestMapping("/markingDone")
	public @ResponseBody
	ResponseVo<String> markingDone(String deviceCode) {
		try {
			if (StringUtil.isBlank(deviceCode)) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????");
			}
			//??????????????????
			DeviceInfo deviceInfo = deviceInfoService.selectByCode(deviceCode);
			if (null == deviceInfo) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????");
			}
			//??????????????????
			AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
			if (!authVo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????????????????????????????");
			}
			replenishmentPlanService.updateByLastTime(deviceCode);
			ResponseVo<String> responseVo = ResponseVo.getSuccessResponse("????????????");
			responseVo.setMsg("????????????");
			return responseVo;
		} catch (Exception e) {
			logger.error("???????????????????????????{}", e);
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????????????????????????????");
	}
}
