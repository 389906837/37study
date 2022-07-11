package com.cloud.cang.wap.hy.web;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.wap.common.CodeEnum;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.common.utils.gt.GeetestConfig;
import com.cloud.cang.wap.common.utils.gt.GeetestLib;
import com.cloud.cang.wap.hy.service.ThirdAuthoriseService;
import com.cloud.cang.wap.hy.vo.RegisterVo;
import com.cloud.cang.wap.index.service.IndexService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.weixin.api.SnsAPI;
import com.cloud.cang.weixin.bean.SnsToken;
import com.cloud.cang.weixin.bean.User;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhouhong
 * 会员登录  授权登录 控制器
 */
@Controller
@RequestMapping("/uc")
public class UcLoginController {

	private static final Logger logger = LoggerFactory.getLogger(UcLoginController.class);

	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private ThirdAuthoriseService thirdAuthoriseService;
	@Autowired
	private IndexService indexService;

	/**
	 * 处理登录
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		try {
			AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
			if (authorizationVO != null) {
				//跳转页面
				String fallbackUrl = (String) SessionUserUtils.getSession().getAttribute("authc.fallbackUrl");
				if (StringUtil.isNotBlank(fallbackUrl)) {
					SessionUserUtils.getSession().setAttribute("authc.fallbackUrl", "");
					return new ModelAndView("redirect:" + fallbackUrl);
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchantCode", authorizationVO.getSmerchantCode());
				map.put("isScan", "0");
				//1、处理访问来源
				if (WapUtils.isWXRequest(request)) {
					map.put("openSource", "wechat");
				} else if (WapUtils.isAlipayRequest(request)) {
					map.put("openSource", "alipay");
				}
				return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/index/main.html", map);
			}
			String url = "";
			if(WapUtils.isWXRequest(request)) {// 是微信浏览器
				url = indexService.getWxUrlForXW(request);
			} else if(WapUtils.isAlipayRequest(request)) {//是支付宝浏览器
				url = indexService.getAlipayUrlForXW(request);
			}
			if (StringUtil.isNotBlank(url)) {
				return new ModelAndView("redirect:" + url);
			}
			return new ModelAndView("uc/mobile_alipay_bind");
		} catch (Exception e) {
			logger.error("登录系统异常:{}", e);
			return WapUtils.errorDealWith("10008","");
		}
	}

	/**
	 * 支付宝绑定手机号页面
	 * @return
	 */
	@RequestMapping("/mobileAlipayBind")
	public String mobileAlipayBind() {
		return "uc/register";
	}

	/**
	 * 微信绑定手机号页面
	 * @return
	 */
	@RequestMapping("/mobileWxBind")
	public String mobileWxBind() {
		return "uc/register";
	}

	/**
	 * 不是微信支付宝打开 跳转错误页面
	 * @param currentUrl 访问url
	 * @param errorCode 错误码
	 * @return
	 */
	@RequestMapping("/error")
	public String error(Integer errorCode, String currentUrl, ModelMap map) {
		if (null != errorCode) {
			if (errorCode == -1) {
				map.put("errorMsg", currentUrl);
				map.put("errorCode", errorCode);
			} else {
				if (StringUtil.isNotBlank(currentUrl)) {
					map.put("currentUrl", currentUrl);
				}
				map.put("errorCode", errorCode);
				map.put("errorMsg", CodeEnum.getNameByCode(errorCode));
			}
		}
		return "uc/error";
	}

	/**
	 * 支付宝联合回调
	 * @param request
	 * @return
	 */
	@RequestMapping("/callback/alipay")
	public ModelAndView callbackAlipay(HttpServletRequest request) {
		String appId = request.getParameter("app_id");
		String code = request.getParameter("auth_code");
		String back_uri = request.getParameter("back_uri");
		String state = request.getParameter("state");
		Map<String, String> map = new HashMap<String, String>();
		map.put("back_uri", back_uri);
		map.put("code", code);
		map.put("state", state);
		map.put("appId", appId);
		return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/uc/alipayBind", map);
	}

	/**
	 * 支付宝联合回调
	 * @param request
	 * @return
	 */
	@RequestMapping("/alipayBind")
	public ModelAndView alipayBind(HttpServletRequest request,ModelMap map) throws Exception {
		String appId = request.getParameter("appId");
		String code = request.getParameter("code");
		String back_uri = request.getParameter("back_uri");
		//获取商户编号
		String merchantCode = WapUtils.getMerchantCookie(request);
		AlipayClient alipayClient = indexService.getAlipayClient(merchantCode);
		if (null == alipayClient) {
			map.put("resVo", new SiteResponseVo(false, -1, "居然出现这个错误，在重新登录试试呢！", -1));
			return new ModelAndView("error/error");
		}
		AlipaySystemOauthTokenRequest authTokenRequest = new AlipaySystemOauthTokenRequest();
		authTokenRequest.setCode(code);
		authTokenRequest.setGrantType("authorization_code");

		AlipaySystemOauthTokenResponse authTokenResponse = alipayClient.execute(authTokenRequest);
		AlipayUserInfoShareRequest userInfoShareRequest = new AlipayUserInfoShareRequest();
		AlipayUserInfoShareResponse userInfoShareResponse = alipayClient.execute(userInfoShareRequest,authTokenResponse.getAccessToken());
		if(userInfoShareResponse.isSuccess()){
			// 将微信用户信息缓存与session中
			SessionUserUtils.getSession().setAttribute(WapConstants.ALIPAY_SESSION_USER, userInfoShareResponse);
			// 将微信授权码缓存与session中
			SessionUserUtils.getSession().setAttribute(WapConstants.ALIPAY_SESSION_AUTH_CODE, code);
			SessionUserUtils.getSession().setAttribute("authc.fallbackUrl", back_uri);
		} else {
			map.put("resVo", new SiteResponseVo(false, -1, "居然出现这个错误，在重新登录试试呢！", -1));
			return new ModelAndView("error/error");
		}
		List<ThirdAuthorise> list = thirdAuthoriseService.selectThirdAuthoriseByOpenId(userInfoShareResponse.getUserId(),20);
		MemberInfo memberInfo = null;
		if (null != list && list.size() > 0) {
			ThirdAuthorise thirdAuthorise = list.get(0);
			memberInfo = memberInfoService.selectByPrimaryKey(thirdAuthorise.getSmemberId());
			// 登录授权
			Subject currentUser = SecurityUtils.getSubject();
			String remoteIp = SessionUserUtils.getIpAddr(request);
			CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(memberInfo.getSmobile(), memberInfo.getSloginPwd(), false, remoteIp, "", memberInfo.getSmerchantCode());
			token.setLoginForm("y");
			currentUser.login(token);
			SessionUserUtils.getSession().setAttribute("LOGIN_TYPE",10);
			SessionUserUtils.getSession().setAttribute("LOGIN_DEVICE_TYPE",20);
		} else {
			return toBindAlipayPage(userInfoShareResponse);
		}
		if (StringUtils.isNotBlank(back_uri)) {//登录跳转地址
			return new ModelAndView("redirect:" + back_uri);
		} else {//不存在跳转首页
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("merchantCode", memberInfo.getSmerchantCode());
			paramMap.put("isScan", "0");
			//1、处理访问来源
			if (WapUtils.isWXRequest(request)) {
				paramMap.put("openSource", "wechat");
			} else if (WapUtils.isAlipayRequest(request)) {
				paramMap.put("openSource", "alipay");
			}
			return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/index/main.html", paramMap);
		}
	}

	/**
	 * 跳转到支付宝绑定手机号页面
	 * @param userInfoShareResponse 支付宝授权信息
	 * @return
	 */
	private ModelAndView toBindAlipayPage(AlipayUserInfoShareResponse userInfoShareResponse) {
		SessionUserUtils.getSession().setAttribute(WapConstants.ALIPAY_SESSION_USER, userInfoShareResponse);
		return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/uc/mobileAlipayBind");
	}

	/**
	 * 微信联合登录回调
	 * @param request
	 * @return
	 */
	@RequestMapping("/callback/weixin")
	public ModelAndView callbackWX(HttpServletRequest request) {
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String back_uri = request.getParameter("back_uri");
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
		map.put("state", state);
		map.put("back_uri", back_uri);
		return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/uc/wxbind", map);
	}
	/**
	 * 微信联合登录回调
	 * @param request
	 * @return
	 */
	@RequestMapping("/wxbind")
	public ModelAndView wxbind(HttpServletRequest request, ModelMap map) throws Exception {
		String code = request.getParameter("code");
		String back_uri = request.getParameter("back_uri");
		//获取商户编号
		String merchantCode = WapUtils.getMerchantCookie(request);
		//获取商户微信配置数据
		MerchantConf conf = indexService.getWechatMerchantConf(merchantCode,1);
		if (null == conf) {//异常情况返回空
			map.put("resVo", new SiteResponseVo(false, -1, "居然出现这个错误，在重新登录试试呢！", -1));
			return new ModelAndView("error/error");
		}
		String appId = conf.getSappId();
		String secret = conf.getSappSecret();
		// 获取Token
		SnsToken snsToken = getSnsToken(appId, code, secret);
		// 获取用户信息
		User user = SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "zh_CN");
		
		if(null == user ){
			map.put("resVo", new SiteResponseVo(false, -1, "居然出现这个错误，在重新登录试试呢！", -1));
			return new ModelAndView("error/error");
		}

		// 将微信用户信息缓存与session中
		SessionUserUtils.getSession().setAttribute(WapConstants.WEIXIN_SESSION_USER, user);
		// 将微信授权码缓存与session中
		SessionUserUtils.getSession().setAttribute(WapConstants.WEIXIN_SESSION_AUTH_CODE, code);
		SessionUserUtils.getSession().setAttribute("authc.fallbackUrl", back_uri);

		List<ThirdAuthorise> list = thirdAuthoriseService.selectThirdAuthoriseByOpenId(user.getOpenid(),10);
		MemberInfo memberInfo = null;
		if (null != list && list.size() > 0) {
			ThirdAuthorise thirdAuthorise = list.get(0);
			memberInfo = memberInfoService.selectByPrimaryKey(thirdAuthorise.getSmemberId());
			// 登录授权
			Subject currentUser = SecurityUtils.getSubject();
			String remoteIp = SessionUserUtils.getIpAddr(request);
			CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(memberInfo.getSmobile(), memberInfo.getSloginPwd(), false, remoteIp, "", memberInfo.getSmerchantCode());
			token.setLoginForm("y");
			currentUser.login(token);
			SessionUserUtils.getSession().setAttribute("LOGIN_TYPE",10);
			SessionUserUtils.getSession().setAttribute("LOGIN_DEVICE_TYPE",10);
		} else {
			return toWxBindPage(user);
		}
		if (StringUtils.isNotBlank(back_uri)) {//登录跳转地址
			return new ModelAndView("redirect:" + back_uri);
		} else {//不存在跳转首页
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("merchantCode", memberInfo.getSmerchantCode());
			paramMap.put("isScan", "0");
			//1、处理访问来源
			if (WapUtils.isWXRequest(request)) {
				paramMap.put("openSource", "wechat");
			} else if (WapUtils.isAlipayRequest(request)) {
				paramMap.put("openSource", "alipay");
			}
			return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/index/main.html", paramMap);
		}
	}

	/**
	 * 微信跳转到绑定手机号页面
	 * @param user 微信返回的用户
	 * @return
	 */
	private ModelAndView toWxBindPage(User user) {
		SessionUserUtils.getSession().setAttribute(WapConstants.WEIXIN_SESSION_USER, user);
		return new ModelAndView("redirect:" + EvnUtils.getValue("wap.http.domain") + "/uc/mobileWxBind");
	}
	/**
	 * 授权获取微信信息
	 * @param appid 微信APPID
	 * @param code 授权码
	 * @param secret 秘钥
	 * @return
	 */
	private SnsToken getSnsToken(String appid, String code, String secret) {
		SnsToken snsToken = SnsAPI.oauth2AccessToken(appid, secret, code);
		return snsToken;
	}


	@RequestMapping("/testWebsoket")
	public String testWebsoket() {
		return "uc/testWebsoket";
	}



	/**
	 * 获取验证码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getGtCode")
	@ResponseBody
	public ResponseVo<String> getGtCode(HttpServletRequest request) {
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
		try {
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
					GeetestConfig.isnewfailback());
			String userid = "";
			if (WapUtils.isWXRequest(request)) {
				User user = (User) SessionUserUtils.getSession().getAttribute(WapConstants.WEIXIN_SESSION_USER);
				if (null != user) {
					userid = user.getOpenid();
				}
			} else if (WapUtils.isAlipayRequest(request)) {
				AlipayUserInfoShareResponse response = (AlipayUserInfoShareResponse) SessionUserUtils.getSession().getAttribute(WapConstants.ALIPAY_SESSION_USER);
				if (null != response) {
					userid = response.getUserId();
				}
			}
			if (StringUtil.isNotBlank(userid)) {
				//自定义参数,可选择添加
				HashMap<String, String> param = new HashMap<String, String>();
				param.put("user_id", userid); //网站用户id
				param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
				param.put("ip_address", RequestUtils.getIpAddr(request)); //传输用户请求验证时所携带的IP

				//进行验证预处理
				int gtServerStatus = gtSdk.preProcess(param);

				//将服务器状态设置到session中
				request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
				//将userid设置到session中
				request.getSession().setAttribute("userid", userid);
				responseVo.setData(gtSdk.getResponseStr());
				return responseVo;
			}
		} catch (Exception e) {
			logger.error("系统异常:{}",e.getMessage());
		}
		return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("验证码获取异常");
	}
}
