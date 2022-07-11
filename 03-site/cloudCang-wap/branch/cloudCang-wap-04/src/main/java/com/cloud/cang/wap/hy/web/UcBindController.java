package com.cloud.cang.wap.hy.web;


import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.act.GiveResultQueryDto;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;

import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.CodeEnum;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.hy.vo.RegisterVo;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import javax.servlet.http.HttpServletRequest;


/**
 * @author zhouhong
 * 会员登录  授权登录 控制器
 */
@Controller
@RequestMapping("/uc")
public class UcBindController {

	private static final Logger logger = LoggerFactory.getLogger(UcBindController.class);
	@Autowired
	private ICached iCached;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private IndexService indexService;
	/**
	 * 会员注册 支付宝端
	 * @param regVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/alipayRegister", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo<String> alipayRegister(RegisterVo regVo, HttpServletRequest request) {
		ResponseVo<String> responseVo = new ResponseVo<String>();
		CodeEnum codeEnum = CodeEnum.ERROR_10000;
		responseVo.setErrorCode(codeEnum.getCode());
		responseVo.setSuccess(false);
		String errorMsg = "";
		try{
			String merchantCode = WapUtils.getMerchantCookie(request);//获取
			if (StringUtil.isBlank(merchantCode)) {
				throw new ServiceException("注册异常，访问超时");
			}
			regVo.setMerchantCode(merchantCode);
			//验证注册基础参数
			validationParam(regVo, request);
			//登录密码加密
			regVo.setLoginPwd(DigestUtils.sha1Hex("37cang"));
			regVo.setItype(20);//支付宝
			//注册平台用户
			MemberInfo memberInfo = memberInfoService.registerMember(regVo, request, false);
			if(memberInfo == null || StringUtil.isNull(memberInfo.getId())) {
				throw new ServiceException("绑定异常，请重新操作");
			}
			//1.登录 跳转页面
			Subject currentUser = SecurityUtils.getSubject();
			String remoteIp = SessionUserUtils.getIpAddr(request);
			CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(regVo.getMobileNumber(), regVo.getLoginPwd(), false, remoteIp, "",regVo.getMerchantCode());
			token.setLoginForm("y");
			currentUser.login(token);
			codeEnum = CodeEnum.SUCCESS;
			responseVo.setSuccess(true);
			responseVo.setErrorCode(codeEnum.getCode());
			//跳转注册成功
			String fallbackUrl = EvnUtils.getValue("wap.http.domain")+"/uc/registerSuccess";
			responseVo.setData(fallbackUrl);
			return responseVo;
		}catch(ServiceException e) {
			errorMsg = e.getMessage();
			logger.error("系统异常:{}",e.getMessage());
		} catch (Exception e) {
			errorMsg = "支付宝绑定异常";
			logger.error("系统异常:{}",e.getMessage());
		}
		responseVo.setMsg(errorMsg);
		return responseVo;
	}


	/**
	 * 会员注册 微信、支付宝
	 * @param regVo 注册参数
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo<String> register(RegisterVo regVo, HttpServletRequest request) {
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		String errorMsg = "";
		try{
			if (WapUtils.isWXRequest(request)) {
				regVo.setItype(10);
			} else if (WapUtils.isAlipayRequest(request)) {
				regVo.setItype(20);
			}
			String merchantCode = WapUtils.getMerchantCookie(request);//获取
			if (StringUtil.isBlank(merchantCode)) {
				throw new ServiceException("注册异常，访问超时");
			}
			regVo.setMerchantCode(merchantCode);
			//验证注册基础参数
			validationParam(regVo, request);
			//登录密码加密
			regVo.setLoginPwd(DigestUtils.sha1Hex("37cang"));
			//注册平台用户
			MemberInfo memberInfo = memberInfoService.registerMember(regVo, request, false);
			if(memberInfo == null || StringUtil.isNull(memberInfo.getId())) {
				throw new ServiceException("注册异常，请重新操作");
			}
			//1.登录 跳转页面
			Subject currentUser = SecurityUtils.getSubject();
			String remoteIp = SessionUserUtils.getIpAddr(request);
			CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(memberInfo.getSmemberName(), memberInfo.getSloginPwd(), false, remoteIp, "", regVo.getMerchantCode());
			token.setLoginForm("y");
			currentUser.login(token);
			//跳转注册成功
			String fallbackUrl = EvnUtils.getValue("wap.http.domain")+"/uc/registerSuccess";
			responseVo.setData(fallbackUrl);
			return responseVo;
		}catch(ServiceException e) {
			errorMsg = e.getMessage();
			logger.error("系统异常:{}",e.getMessage());
		} catch (Exception e) {
			errorMsg = "会员注册异常异常";
			logger.error("系统异常:{}",e.getMessage());
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(errorMsg);
	}


	/**
	 *  注册成功页面
	 * @return
	 */
	@RequestMapping("/registerSuccess")
	public ModelAndView registerSuccess(ModelMap map, HttpServletRequest request) throws Exception {
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据

		//1、查询注册、绑定活动 赠送信息  展示前台
		//调用查券服务
		GiveResultQueryDto queryDto = new GiveResultQueryDto();
		queryDto.setSourceType(BizTypeDefinitionEnum.SourceBizStatus.REGISTER.getCode());
		queryDto.setMemberId(authVo.getId());
		queryDto.setSourceCode(authVo.getCode());
		// 创建Rest服务客户端
		RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.QUERYACTIVEGIVERESULT_SERVICE);
		invoke.setRequestObj(queryDto);
		// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
		invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() { });
		ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
		GiveActivityResult giveActivityResult = responseVo.getData();
		if (giveActivityResult != null) {
			if (giveActivityResult.getCouponGiveResultList() != null) {
				map.put("coupons", giveActivityResult.getCouponGiveResultList());
			}
			if (giveActivityResult.getIntegralGiveResult() != null) {
				map.put("integral", giveActivityResult.getIntegralGiveResult().getIntegralValue());
			}
		}

		if (WapUtils.isWXRequest(request)) {
			map.put("signPage","/personal/wechatSign.html");
		} else if (WapUtils.isAlipayRequest(request)) {
			map.put("signPage","/personal/alipaySign.html");
		}
		//2、判断用户商户是否必须开通代扣
		//获取商户客户端配置信息
		MerchantClientConf clientConf = indexService.getMerchantClientConfByCode(authVo.getSmerchantCode());
		if (null == clientConf.getIsupportPayWay()) {
			map.put("isupportPayWay", Integer.parseInt(GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG,"default_support_pay_way").getSvalue()));//支持支付方式 默认仅代扣
		} else {
			map.put("isupportPayWay", clientConf.getIsupportPayWay());
		}

		return new ModelAndView("uc/reg_success_new");
	}


	/**
	 * 验证参数
	 * @param regVo 注册参数
	 * @param request
	 */
	private void validationParam(RegisterVo regVo, HttpServletRequest request) {
		// 验证码有误
		/*if (StringUtil.isBlank(regVo.getCaptcha().trim())) {
			throw new ServiceException("请输入图形验证码");
		}
		if (validTxCode(regVo.getCaptcha().trim(), request) == 0) {
			throw new ServiceException("图形验证码不正确");
		}*/
		// 手机号不正确
		if (StringUtil.isBlank(regVo.getMobileNumber())) {
			throw new ServiceException("请输入手机号码");
		}
		if (!ValidateUtils.isMobile(regVo.getMobileNumber())) {
			throw new ServiceException("手机号码格式不正确");
		}
		// 手机号已经存在

		if (memberInfoService.selectMemberInfoByBindType(regVo.getMobileNumber(), regVo.getItype(), regVo.getMerchantCode()) != null) {
			throw new ServiceException("手机号已存在");
		}
		// 短信验证码不正确
		int msgErrorCode = checkMessageCode(regVo.getMsgCode(),
				regVo.getMobileNumber());
		if (msgErrorCode != 1) {
			if (msgErrorCode == -1) {
				throw new ServiceException("短信验证码已超时");
			} else if (msgErrorCode == -2) {
				throw new ServiceException("短信验证码错误");
			} else if (msgErrorCode == -3) {
				throw new ServiceException("短信验证码不能为空");
			} else if (msgErrorCode == -4) {
				throw new ServiceException("短信验证码校验异常");
			}
		}
		// 密码
		/*if (StringUtils.isBlank(regVo.getLoginPwd())) {
			throw new ServiceException("请输密码");
		} else if (!ValidateUtils.isPassword(regVo.getLoginPwd())) {
			throw new ServiceException("密码为6~20位，字母、数字和常用字符两种及以上的组合");
		}*/
		// 推荐码没有值取缓存中的值
		if (StringUtils.isBlank(regVo.getRecommonCode())) {
			Object obj = SessionUserUtils.getSession().getAttribute(
					SessionUserUtils.SESSION_KEY_TJ);
			if (obj != null) {
				regVo.setRecommonCode(obj.toString());
			}
		}
		// 推荐
		if (StringUtils.isNotBlank(regVo.getRecommonCode())
				&& regVo.getRecommonCode().length() != 6
				&& regVo.getRecommonCode().length() != 11) {
			SessionUserUtils.getSession().removeAttribute(
					SessionUserUtils.SESSION_KEY_TJ);
			throw new ServiceException("推荐码格式错误，请重新输入");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("会员注册基础校验成功！会员手机：{}", regVo.getMobileNumber());
		}
	}
	/**
	 * 图片验证码校验
	 * @param txCode 图片验证码
	 * @param request
	 * @return
	 */
	@RequestMapping("/validTxCode")
	@ResponseBody
	public Integer validTxCode(String txCode, HttpServletRequest request) {
		String captcha = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (txCode.equalsIgnoreCase(captcha)) {
			return 1;
		}
		return 0;
	}
	/**
	 * 校验短信验证码
	 * @param messageCode 短信验证码
	 * @param mobile 手机号码
	 * @return
	 */
	@RequestMapping("/checkMessageCode")
	@ResponseBody
	public Integer checkMessageCode(String messageCode, String mobile) {
		try {
			if(org.apache.commons.lang.StringUtils.isNotBlank(messageCode)) {
				String authCodeByCached = (String) iCached.get(WapConstants.RedisConst.MEMBER_BIND_AUTH_CODE + mobile);
				if (StringUtil.isNull(authCodeByCached)) {
					return -1;//短信验证码已超时
				} else if (!authCodeByCached.equalsIgnoreCase(messageCode)) {
					return -2;//短信验证码错误
				}
			} else {
				return -3;//短信验证码不能为空
			}
		} catch (Exception e) {
			logger.error("用户注册短信验证码校验错误", e);
			return -4;//短信验证码校验异常
		}
		return 1;
	}
}
