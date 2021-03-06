package com.cloud.cang.wap.index.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.hy.MemberServicesDefine;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.CodeEnum;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.CookieUtils;
import com.cloud.cang.wap.common.utils.IdGenerator;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.common.utils.gt.GeetestConfig;
import com.cloud.cang.wap.common.utils.gt.GeetestLib;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.index.service.IndexService;

import java.security.MessageDigest;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloud.cang.wap.om.service.OrderRecordService;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sb.vo.DeviceVo;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import com.cloud.cang.weixin.api.TicketAPI;
import com.cloud.cang.weixin.api.TokenAPI;
import com.cloud.cang.weixin.bean.Ticket;
import com.cloud.cang.weixin.bean.Token;
import com.cloud.cang.weixin.bean.User;
import com.cloud.cang.weixin.util.JsUtil;
import com.cloud.cang.weixin.util.JsonUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private IndexService indexService;
	@Autowired
	private ICached iCached;
	@Autowired
	private DeviceInfoService deviceInfoService;
	@Autowired
	private OrderRecordService orderRecordService;
	@Autowired
	private MemberInfoService memberInfoService;
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	/**
	 * ????????????
	 * @param deviceVo ??????????????????
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView main(DeviceVo deviceVo, ModelMap map, HttpServletRequest request) throws Exception {
		logger.info("????????????:{}", deviceVo);
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//??????????????????
		if (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode())) {
			//????????????????????? ????????????
			deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
			if(null == deviceVo) {
				deviceVo = new DeviceVo();
			}
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
		if (!authVo.getSmerchantCode().equals(deviceVo.getMerchantCode())) {
			return WapUtils.errorDealWith("10014","");
		}
		if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
			Map<String, Object> connectParam = new HashMap<String, Object>();
			connectParam.put("deviceId", deviceVo.getDeviceId());
			connectParam.put("deviceCode", deviceVo.getDeviceCode());
			connectParam.put("userId", authVo.getId());
			connectParam.put("userCode", authVo.getCode());
			connectParam.put("userName", authVo.getUserName());
			if (StringUtil.isBlank(deviceVo.getOpenSource())) {
				if (WapUtils.isWXRequest(request)) {
					deviceVo.setOpenSource("wechat");
				} else if (WapUtils.isAlipayRequest(request)) {
					deviceVo.setOpenSource("alipay");
				}
			}
			if (deviceVo.getOpenSource().equals("wechat")) {
				connectParam.put("isourceClientType", 10);
			} else if (deviceVo.getOpenSource().equals("alipay")) {
				connectParam.put("isourceClientType", 20);
			}
			map.put("connectParam", JSON.toJSONString(connectParam));
		}
		//?????????????????????????????????
		MerchantClientConf clientConf = indexService.getMerchantClientConfByCode(deviceVo.getMerchantCode());
		//????????????
		if (clientConf != null && StringUtil.isNotBlank(clientConf.getStitle())) {
			map.put("indexTitle", clientConf.getStitle());
		} else {
			map.put("indexTitle", GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_title"));
		}
		SessionUserUtils.getSession().setAttribute("session_device", deviceVo);//???????????????session???
        Integer replenishmentScan = (Integer) SessionUserUtils.getSession().getAttribute("is_replenishment_scan");
        String indexPath = "/index/page";
        if (null != replenishmentScan && replenishmentScan.intValue() == 1) {
            indexPath = "/replenishment/index";
            SessionUserUtils.getSession().setAttribute("is_replenishment_scan", 0);
        }
        map.put("indexPath", indexPath);
		if (WapUtils.isWXRequest(request)) {
			map.put("wechat", 1);

			StringBuffer url = request.getRequestURL();
			if (request.getQueryString() != null) {
				url.append('?');
				url.append(request.getQueryString());
			}
			String jsUrl = url.toString();
			String jsonStr = getwxConfig(jsUrl, false);
			map.put("jsonStr", jsonStr);

		} else if (WapUtils.isAlipayRequest(request)) {
			map.put("alipay", 1);
		}

		return new ModelAndView("index/index_main");
	}

	private String getwxConfig(String url, boolean debug) throws Exception {
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
		MerchantConf conf = indexService.getWechatMerchantConf(authVo.getSmerchantCode(),1);
		Long timestamp = new Date().getTime();
		Long getWxAccessTime = iCached.get("getWxAccessTime") == null ? 0 : (Long) iCached.get("getWxAccessTime");
		String jsapiTicket = (String) iCached.get("jsapiTicket");
		// ????????????7200s
		if ((timestamp - getWxAccessTime) / 1000 > 7200 || timestamp - getWxAccessTime<=0) {
			Token token = TokenAPI.token(conf.getSappId(), conf.getSappSecret());
			Ticket tiket =  TicketAPI.ticketGetticket(token.getAccess_token());
			jsapiTicket = tiket.getTicket();
			iCached.put("getWxAccessTime", timestamp);
			iCached.put("jsapiTicket", jsapiTicket);
		}
		String jsonStr = JsUtil.generateConfigJson(jsapiTicket, debug, conf.getSappId(), url,null);
		return jsonStr;
	}

	/**
	 *  ????????????
	 * @return
	 */
	@RequestMapping("/page")
	public ModelAndView page(ModelMap map, HttpServletRequest request) throws Exception {

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

		if (StringUtil.isBlank(deviceVo.getOpenSource())) {
			if (WapUtils.isWXRequest(request)) {
				deviceVo.setOpenSource("wechat");
			} else if (WapUtils.isAlipayRequest(request)) {
				deviceVo.setOpenSource("alipay");
			}
		}
		if (WapUtils.isWXRequest(request)) {
			map.put("signPage","/personal/wechatSign.html");
		} else if (WapUtils.isAlipayRequest(request)) {
			map.put("signPage","/personal/alipaySign.html");
		}
		Map<String, Object> mapData = indexService.getMainData(deviceVo);
		map.put("index", mapData);

		return new ModelAndView("index/index");
	}


	/**
	 * ????????????
	 * @throws Exception
	 */
	@RequestMapping("/openSuccess")
	public ModelAndView openSuccess(ModelMap map) throws Exception {
		logger.debug("??????????????????...");
		DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
		logger.info("?????????????????????????????????:{}", deviceVo);
		DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
		Map<String, Object> params = new HashMap<>();
		//???????????????????????????
		params = indexService.getMerchantClientConfByMap(params, deviceInfo.getSmerchantCode());
		//??????????????????
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
		params.put("userId", authVo.getId());
		map.put("open", params);
		//??????????????????
		RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.QUERY_SDEVICE_ACTIVITY_SDESCRIPTION);// ????????????
		// ??????????????????????????????????????????????????????????????????????????????
		invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<List<String>>>() { });
		invoke.setRequestObj(deviceInfo.getId()); // post ??????
		ResponseVo<List<String>> resVO = (ResponseVo<List<String>>) invoke.invoke();
		if (resVO.isSuccess()) {
			map.put("preferentialInfos", resVO.getData());
		}
		return new ModelAndView("index/openSuccess");
	}
	
	/**
	 * ???????????????????????????
	 * @param operType 0 ?????????????????? 1 ????????????????????????
	 * @param memberType ???????????? 0 ???????????? 1 ????????????
	 * @throws Exception
	 */
	@RequestMapping("/isAutoOpen")
	@ResponseBody
	public ResponseVo<String> isAutoOpen(Integer operType,Integer memberType, HttpServletRequest request) throws Exception {
		DeviceVo deviceVo = (DeviceVo) SessionUserUtils.getSession().getAttribute("session_device");
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();
		logger.info("????????????:{}", deviceVo);
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		//0 ??????????????????
		responseVo.setData("0");
		if(WapUtils.isWXRequest(request)) {
			responseVo.setMsg("1");
		} else if(WapUtils.isAlipayRequest(request)) {
			responseVo.setMsg("2");
		}
		if (null != operType && operType.intValue() == 0) {//????????????????????????
			String deviceId = (String) iCached.get(deviceVo.getDeviceId()+"_"+authVo.getId()+"_open_door");
			if (StringUtil.isNotBlank(deviceId)) {
				ResponseVo<String> resVo = verifyOpen(authVo.getId(),memberType,operType);
				if (!resVo.isSuccess()) {
					iCached.remove(deviceId+"_"+authVo.getId()+"_open_door");//?????????????????????????????????KEY
					return resVo;
				}
				if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {//???????????????KEY ???????????? ?????????????????? ???????????????
					String deviceIdBySession = (String) iCached.get(deviceVo.getOpenDoorKey());
					if (StringUtil.isNotBlank(deviceIdBySession) && deviceIdBySession.equals(deviceVo.getDeviceId())) {
						SessionUserUtils.getSession().setAttribute("is_replenishment_scan", 0);
						responseVo.setData("1");
						return responseVo;
					}
				}
			}
		} else {
			ResponseVo<String> resVo = verifyOpen(authVo.getId(),memberType,operType);
			if (!resVo.isSuccess()) {
				return resVo;
			}
			//????????????????????????
			if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {//???????????????KEY ???????????? ?????????????????? ???????????????
				String deviceIdBySession = (String) iCached.get(deviceVo.getOpenDoorKey());
				if (StringUtil.isNotBlank(deviceIdBySession) && deviceIdBySession.equals(deviceVo.getDeviceId())) {
					//1 ???????????????
					SessionUserUtils.getSession().setAttribute("is_replenishment_scan", 0);
					responseVo.setData("1");
					return responseVo;
				}
			}

			//???????????????????????? ????????????????????????
			iCached.put(deviceVo.getDeviceId()+"_"+authVo.getId()+"_open_door", deviceVo.getDeviceId());
		}
		return responseVo;
	}

	/***
	 * ???????????? ???????????????
	 * @param memberId ??????Id
	 * @param memberType ???????????? 0 ???????????? 1 ?????????
	 * @param operType ????????????  0 ?????????????????? 1 ??????????????????
	 * @return
	 */
	private ResponseVo<String> verifyOpen(String memberId, Integer memberType, Integer operType) {
		MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
		if (memberInfo == null || memberInfo.getIstatus().intValue() != 1) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????");
		}
		if (null != memberType && memberType.intValue() == 1) {
			if (null != operType && operType.intValue() == 1) {
				SessionUserUtils.getSession().setAttribute("is_replenishment_scan", 1);
			} else {
				SessionUserUtils.getSession().setAttribute("is_replenishment_scan", 0);
			}
		} else {
			//?????????????????????  ???????????????????????????
			List<OrderRecord> orderRecords = orderRecordService.selectExceptionOrder(memberId);
			if (null != orderRecords && orderRecords.size() > 0) {
				//???????????????
				return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????????????????");
			}
		}
		return ResponseVo.getSuccessResponse();
	}

	/**
	 * ???????????? ?????? ??????????????????
	 * ????????????
	 * @param deviceVo
	 * @return
	 */
	@RequestMapping("/visit")
	public ModelAndView visit(DeviceVo deviceVo, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("????????????????????????...");

		String rootPath = EvnUtils.getValue("wap.http.domain");
		String currentUrl = rootPath + request.getServletPath();
		try {
			logger.info("???????????????????????????{}", currentUrl);
			//????????????
			//???????????????????????? ???????????????Id ?????????????????????
			if (null == deviceVo || (StringUtil.isBlank(deviceVo.getDeviceId()) && StringUtil.isBlank(deviceVo.getMerchantCode()))) {//??????????????????
				return WapUtils.errorDealWith("10001", currentUrl);
			}
			logger.info("???????????????????????????{}", deviceVo);
			//1?????????????????????
			if (WapUtils.isWXRequest(request)) {
				deviceVo.setOpenSource("wechat");
			} else if (WapUtils.isAlipayRequest(request)) {
				deviceVo.setOpenSource("alipay");
			}
			//2?????????????????????
			if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
				DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceVo.getDeviceId());
				if (null == deviceInfo) {//???????????????
					return WapUtils.errorDealWith("10002", currentUrl);
				} else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.NO_REGISTER.getCode()
						|| deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.SCRAPPED.getCode()) {
					//???????????????
					return WapUtils.errorDealWith("10003", currentUrl);
				} else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.OFFLINE.getCode()) {
					//????????????
					return WapUtils.errorDealWith("10013", currentUrl);
				} else if (deviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.MALFUNCTION.getCode()) {
					//????????????
					return WapUtils.errorDealWith("10004", currentUrl);
				} else if (deviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.OperateStatus.DISABLED.getCode()) {
					//???????????????
					return WapUtils.errorDealWith("10007", currentUrl);
				}
				deviceVo.setMerchantCode(deviceInfo.getSmerchantCode());
				deviceVo.setIsScan(1);
				deviceVo.setDeviceCode(deviceInfo.getScode());
				//3??????????????????????????????
				//????????????  ???????????????
				int validity = 30;//30???
				String validityStr = BizParaUtil.get(WapConstants.OPEN_DOOR_VALIDITY);
				try {
					validity = Integer.parseInt(validityStr);
				} catch (Exception e) {
					validity = 30;
				}
				String openDoorKey = IdGenerator.randomUUID(32);
				iCached.put(openDoorKey, deviceVo.getDeviceId(), validity);
				deviceVo.setOpenDoorKey(openDoorKey);
				//???????????????cookie
				setMerchantCookie(response, deviceVo.getMerchantCode());
				//??????????????????cookie
				setDeviceCookie(response, deviceInfo.getScode()+"#//#"+deviceInfo.getId());
				request.getSession().setAttribute("openDoorKey", openDoorKey);//???????????????key ???????????????

			} else if (StringUtil.isNotBlank(deviceVo.getMerchantCode())) {
				MerchantInfo merchantInfo = null;
				String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.MERCHANT_INFO_DETAILS + deviceVo.getMerchantCode());
				if (StringUtil.isNotBlank(json)) {
					merchantInfo = JSONObject.parseObject(json, MerchantInfo.class);
				}
				//MerchantInfo merchantInfo = merchantInfoService.selectByCode(deviceVo.getMerchantCode());
				if (null == merchantInfo) {
					return WapUtils.errorDealWith("10005", currentUrl);
				} else if (merchantInfo.getIstatus().intValue()
						!= BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode()) {
					return WapUtils.errorDealWith("10006", currentUrl);
				}
				deviceVo.setIsScan(0);
				//???????????????cookie
				setMerchantCookie(response, deviceVo.getMerchantCode());
				CookieUtils.addCookie(response,WapConstants.DEVICE_COOKIE_NAME, "", 0);//????????????????????????
			} else {
				return WapUtils.errorDealWith("10008", currentUrl);
			}

			Map<String, String> map = new HashMap<String, String>();
			if (StringUtil.isNotBlank(deviceVo.getDeviceId())) {
				map.put("deviceId", deviceVo.getDeviceId());
			}
			if (StringUtil.isNotBlank(deviceVo.getDeviceCode())) {
				map.put("deviceCode", deviceVo.getDeviceCode());
			}
			if (StringUtil.isNotBlank(deviceVo.getMerchantCode())) {
				map.put("merchantCode", deviceVo.getMerchantCode());
			}
			if (null != deviceVo.getIsScan()) {
				map.put("isScan", String.valueOf(deviceVo.getIsScan()));
			}
			if (StringUtil.isNotBlank(deviceVo.getOpenDoorKey())) {
				map.put("openDoorKey", deviceVo.getOpenDoorKey());
			}
			if (StringUtil.isNotBlank(deviceVo.getOpenSource())) {
				map.put("openSource", deviceVo.getOpenSource());
			}
			return new ModelAndView("redirect:" + rootPath + "/index/main", map);
		} catch (Exception e) {
			logger.error("??????????????????:{}", e);
			return WapUtils.errorDealWith("10008", currentUrl);
		}
	}

	/**
	 * ?????????????????????cookie
	 * @param deviceStr ????????????#//#??????ID
	 * @param response
	 */
	private void setDeviceCookie(HttpServletResponse response, String deviceStr) {
		//??????cookie ?????????
		int hours = 1;//??????
		String validityStr = BizParaUtil.get(WapConstants.DEVICE_COOKIE_VALIDITY);
		try {
			hours = Integer.parseInt(validityStr);
		} catch (Exception e) {
			hours = 1;
		}
		int expiry = hours*60*60;
		CookieUtils.addCookie(response,WapConstants.DEVICE_COOKIE_NAME, deviceStr, expiry);
	}

	/**
	 * ???????????????cookie
	 * @param merchantCode ????????????
	 * @param response
	 */
	private void setMerchantCookie(HttpServletResponse response,String merchantCode) {
		//??????cookie ?????????
		int hours = 24;//??????
		String validityStr = BizParaUtil.get(WapConstants.MERCHANT_COOKIE_VALIDITY);
		try {
			hours = Integer.parseInt(validityStr);
		} catch (Exception e) {
			hours = 24;
		}
		int expiry = hours*60*60;
		CookieUtils.addCookie(response,WapConstants.MERCHANT_COOKIE_NAME, merchantCode, expiry);
	}


}
