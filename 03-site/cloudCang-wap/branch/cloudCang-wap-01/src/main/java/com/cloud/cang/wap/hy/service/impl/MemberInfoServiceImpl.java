package com.cloud.cang.wap.hy.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.GiveActivityDto;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.act.RecommendParam;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.hy.MemberInfoDto;
import com.cloud.cang.hy.MemberServicesDefine;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.FreeOperLog;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.ac.service.ActivityConfService;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.dao.MemberInfoDao;
import com.cloud.cang.wap.hy.service.FreeDataService;
import com.cloud.cang.wap.hy.service.FreeOperLogService;
import com.cloud.cang.wap.hy.service.MemberInfoService;

import com.cloud.cang.wap.hy.service.ThirdAuthoriseService;
import com.cloud.cang.wap.hy.vo.MemberInfoDomain;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.MemberInfo;

import com.cloud.cang.wap.hy.vo.RegisterVo;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.weixin.bean.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, String> implements MemberInfoService {

	private static final Logger logger = LoggerFactory.getLogger(MemberInfoServiceImpl.class);

	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private ICached iCached;
	@Autowired
	private ThirdAuthoriseService thirdAuthoriseService;
	@Autowired
	private FreeDataService freeDataService;
	@Autowired
	private DeviceInfoService deviceInfoService;
	@Autowired
	private ActivityConfService activityConfService;
	@Autowired
	private FreeOperLogService freeOperLogService;
	@Override
	public GenericDao<MemberInfo, String> getDao() {
		return memberInfoDao;
	}

	/**
	 * 获取用户信息
	 * @param mobile 手机号
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public MemberInfo selectByMobile(String mobile, String merchantCode) {
		return memberInfoDao.selectByMobile(mobile,merchantCode);
	}

	/**
	 * 获取用户信息
	 * @param recommedCode 会员的广荐代码
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public List<MemberInfo> selectMemberInfoByRecommedCode(String recommedCode, String merchantCode) {
		return memberInfoDao.selectMemberInfoByRecommedCode(recommedCode, merchantCode);
	}
	/**
	 * 根据会员ID 查找会员信息
	 * @param memberId 会员ID
	 * @return
	 */
	@Override
	public MemberInfoDomain selectMemberInfoDomainById(String memberId) {
		return memberInfoDao.selectMemberInfoDomainById(memberId);
	}
	/**
	 * 根据会员登录名称 查找会员信息
	 * @param loginName 会员登录名称
	 * @param merchantCode 商户编号
	 * @return
	 */
	@Override
	public MemberInfo selectMemberInfoByLoginName(String loginName, String merchantCode) {
		return memberInfoDao.selectMemberInfoByLoginName(loginName,merchantCode);
	}

	/**
	 * 注册平台会员
	 * @param regVo 注册Vo
	 * @param isPwd 密码是否在自动生成
	 * @return
	 */
	@Override
	public MemberInfo registerMember(RegisterVo regVo, HttpServletRequest request, boolean isPwd) throws ServiceException, Exception {
		// 注册会员信息
		MemberInfo registerMember = this.selectMemberInfoByLoginName(regVo.getMobileNumber(), regVo.getMerchantCode());
		AlipayUserInfoShareResponse userInfo = null;
		MemberInfo recommendMember = null;
		User wechatUser = null;
		List<ThirdAuthorise> tempList = null;

		if (regVo.getItype().intValue() == 10) {//微信
			wechatUser = (User) SessionUserUtils.getSession().getAttribute(WapConstants.WEIXIN_SESSION_USER);
			tempList = thirdAuthoriseService.selectThirdAuthoriseByOpenId(regVo.getMerchantCode(), wechatUser.getOpenid(),10);
			if (null != tempList && tempList.size() > 0) {
				throw new ServiceException("注册异常，已有账号绑定此微信");
			}
		} else if (regVo.getItype().intValue() == 20) {//支付宝
			userInfo = (AlipayUserInfoShareResponse) SessionUserUtils.getSession().getAttribute(WapConstants.ALIPAY_SESSION_USER);
			tempList = thirdAuthoriseService.selectThirdAuthoriseByOpenId(regVo.getMerchantCode(), userInfo.getUserId(),20);
			if (null != tempList && tempList.size() > 0) {
				throw new ServiceException("注册异常，已有账号绑定此支付宝");
			}
		}
		boolean isSend = false;
		if (null == registerMember) {
			isSend = true;
			// 执行保存
			HttpSession session = request.getSession();
			MemberInfoDto memDto = new MemberInfoDto();

			if (regVo.getItype().intValue() == 10) {//微信
				if (wechatUser == null) {
					throw new ServiceException("注册异常，请重新操作");
				}
				memDto.setNikeName(wechatUser.getNickname());
				//会员头像
				if (StringUtil.isNotBlank(wechatUser.getHeadimgurl())) {
					memDto.setHeadImg(wechatUser.getHeadimgurl());
				}
				//会员性别
				if (null != wechatUser.getSex()) {
					if (wechatUser.getSex().intValue() == 1) {
						memDto.setIsex(1);
					} else {
						memDto.setIsex(0);
					}
				}
			} else if (regVo.getItype().intValue() == 20) {//支付宝
				if (userInfo == null) {
					throw new ServiceException("注册异常，请重新操作");
				}
				memDto.setNikeName(userInfo.getNickName());
				//会员头像
				if (StringUtil.isNotBlank(userInfo.getAvatar())) {
					memDto.setHeadImg(userInfo.getAvatar());
				}
				//会员性别
				if (StringUtil.isNotBlank(userInfo.getGender())) {
					if (userInfo.getGender().toUpperCase().equals("M")) {
						memDto.setIsex(1);
					} else {
						memDto.setIsex(0);
					}
				}
			}

			//获取cookie值 设备编号
			DeviceInfo deviceInfo = null;
			try {
                String deviceStr = WapUtils.getDeviceCodeCookie(request);
                if (StringUtil.isNotBlank(deviceStr)) {
                    String deviceCode = deviceStr.split("#//#")[0];
                    if (StringUtil.isNotBlank(deviceCode)) {
                        deviceInfo = deviceInfoService.selectByCode(deviceCode);
                    }
                }
			} catch (Exception e) {
				deviceInfo = null;
				logger.error("设备获取异常", e);
			}
			if (null != deviceInfo) {
				if (regVo.getItype().intValue() == 10) {
					memDto.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.WECHAT.getCode());
				} else if (regVo.getItype().intValue() == 20) {
					memDto.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.ALIPAY.getCode());
				}
				memDto.setDeviceCode(deviceInfo.getScode());
				memDto.setDeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
			} else {
				if (regVo.getItype().intValue() == 10) {
					memDto.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.WECHAT_NO_PUBLIC.getCode());
				} else if (regVo.getItype().intValue() == 20) {
					memDto.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.ALIPAY_LIFE_NUMBER.getCode());
				}
			}
			if (StringUtil.isNotBlank(regVo.getRecommonCode())) {// 会员推荐
				recommendMember = this.selectMemberInfoByRecommonCode(regVo.getRecommonCode(),regVo.getMerchantCode());
				if (null != recommendMember) {
					memDto.setSrecommonMbrCode(regVo.getRecommonCode());
				}
			}
			memDto.setSmobile(regVo.getMobileNumber());
			if (isPwd) {//自动生成密码 明文
				memDto.setSloginPassword(DigestUtils.sha1Hex(regVo.getLoginPwd()));
			} else {
				memDto.setSloginPassword(regVo.getLoginPwd());
			}
			memDto.setImemberType(BizTypeDefinitionEnum.MemberType.M1_MEMBER.getCode());
			memDto.setSuserIp(RequestUtils.getIpAddr(request));
			// 来源渠道
			Object obj = session.getAttribute(CoreConstant.SOURCE_CHANNEL_SESSION_KEY);
			if (obj != null) {
				memDto.setSsourcetype(obj.toString());
			}
			//获取商户信息
			String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.MERCHANT_INFO_DETAILS + regVo.getMerchantCode());
			if (StringUtil.isBlank(json)) {//异常情况返回空
				return null;
			}
			MerchantInfo merchantInfo = JSONObject.parseObject(json, MerchantInfo.class);
			memDto.setSmerchantId(merchantInfo.getId());
			memDto.setSmerchantCode(merchantInfo.getScode());
			RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MemberServicesDefine.MEMBER_REGISTER);// 服务名称
			// 返回对象中含有泛型，则需要设置返回类型，否则无需设置
			invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<MemberInfo>>() { });
			invoke.setRequestObj(memDto); // post 参数
			ResponseVo<MemberInfo> resVO = (ResponseVo<MemberInfo>) invoke.invoke();
			if (!resVO.isSuccess()) {
				logger.error("注册失败{}", resVO.getMsg());
				throw new ServiceException("注册失败，系统异常");
			}
			registerMember = resVO.getData();
		}
		//1.绑定成功 添加第三方用户信息
		if (regVo.getItype().intValue() == 10) {//微信
			ThirdAuthorise thirdAuthorise = new ThirdAuthorise();
			thirdAuthorise.setSmemberId(registerMember.getId());
			thirdAuthorise.setSmemberCode(registerMember.getScode());
			thirdAuthorise.setSmemberName(registerMember.getSmemberName());
			thirdAuthorise.setIthirdType(10);
			thirdAuthorise.setTauthoriseTime(new Date());
			thirdAuthorise.setSopenId(wechatUser.getOpenid());
			thirdAuthorise.setSotherNickname(wechatUser.getNickname());
			thirdAuthorise.setSotherImg(wechatUser.getHeadimgurl());
			thirdAuthorise.setIstatus(10);
			thirdAuthorise.setSprovince(wechatUser.getProvince());
			thirdAuthorise.setScity(wechatUser.getCity());
			//thirdAuthorise.setSuserStatus(wechatUser.get);
			//thirdAuthorise.setSuserType(userInfo.getUserType());
			//thirdAuthorise.setSisCertified(userInfo.getIsCertified());
			//thirdAuthorise.setSisStudentCertified(userInfo.getIsStudentCertified());
			thirdAuthoriseService.insert(thirdAuthorise);
		} else if (regVo.getItype().intValue() == 20) {//支付宝
			ThirdAuthorise thirdAuthorise = new ThirdAuthorise();
			thirdAuthorise.setSmemberId(registerMember.getId());
			thirdAuthorise.setSmemberCode(registerMember.getScode());
			thirdAuthorise.setSmemberName(registerMember.getSmemberName());
			thirdAuthorise.setIthirdType(20);
			thirdAuthorise.setTauthoriseTime(new Date());
			thirdAuthorise.setSopenId(userInfo.getUserId());
			thirdAuthorise.setSotherNickname(userInfo.getNickName());
			thirdAuthorise.setSotherImg(userInfo.getAvatar());
			thirdAuthorise.setIstatus(10);
			thirdAuthorise.setSprovince(userInfo.getProvince());
			thirdAuthorise.setScity(userInfo.getCity());
			thirdAuthorise.setSuserStatus(userInfo.getUserStatus());
			thirdAuthorise.setSuserType(userInfo.getUserType());
			thirdAuthorise.setSisCertified(userInfo.getIsCertified());
			thirdAuthorise.setSisStudentCertified(userInfo.getIsStudentCertified());
			thirdAuthoriseService.insert(thirdAuthorise);
		}
		if (isSend) {
			// 注册成功发送短信
			sendUserMsg(registerMember.getSmerchantId(),registerMember.getSmerchantCode(),registerMember.getSmobile(), regVo.getLoginPwd(), isPwd);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("会员注册成功！会员编号：{},会员手机：{}", new Object[] { registerMember.getScode(), registerMember.getSmobile() });
		}

		try {
			// 调用活动,推荐注册
			if (registerMember != null) {
				this.recommendRegisterGive(registerMember, recommendMember);
			}
		} catch (Exception e) {
			logger.error("调用注册推荐活动异常{}", e);
		}
		return registerMember;
	}
	/**
	 * 处理支付宝签约成功方法
	 * @param merchantCode 商户编号
	 * @param map 返回参数 详见文档
	 * @param conf 商户配置信息
	 * @throws Exception
	 */
	@Override
	public boolean dealwithAlipaySign(String merchantCode, Map<String, String> map, MerchantConf conf) throws Exception {
		if (!conf.getSappId().equals(map.get("app_id"))) {
			logger.error("处理签约数据异常，商户数据异常");
			return false;
		}
		String externalAgreementNo = map.get("external_agreement_no");//系统商户签约唯一标识
		String agreementNo = map.get("agreement_no");//支付宝签约唯一标识
		FreeData freeData = freeDataService.selectByExternalAgreementNo(externalAgreementNo);
		if (freeData == null) {
			logger.error("支付宝签约数据异常");
			return false;
		}
		//更新数据
		FreeData updateData = new FreeData();
		updateData.setId(freeData.getId());
		if (StringUtil.isNotBlank(freeData.getSagreementNo()) && agreementNo.equals(freeData.getSagreementNo())) {
			logger.info("支付宝签约数据重复处理");
			return true;
		}

		String validTime = map.get("valid_time");//生效时间
		String invalidTime = map.get("invalid_time");//失效时间
		String status = map.get("status");//协议状态
		String signScene = map.get("sign_scene");//签约场景
		String alipayLogonId = map.get("alipay_logon_id");//支付宝脱敏账号
		String signTime = map.get("sign_time");//实际签约时间
		String externalLogonId = map.get("external_logon_id");//平台签约账户


		updateData.setSagreementNo(agreementNo);
		updateData.setTvalidTime(DateUtils.parseDateByFormat(validTime,"yyyy-MM-dd HH:mm:ss"));
		updateData.setTinvalidTime(DateUtils.parseDateByFormat(invalidTime,"yyyy-MM-dd HH:mm:ss"));
		updateData.setSsignScene(signScene);
		updateData.setSstatus(status);
		updateData.setSalipayLogonId(alipayLogonId);
		updateData.setTsignTime(DateUtils.parseDateByFormat(signTime,"yyyy-MM-dd HH:mm:ss"));
		updateData.setSexternalLogonId(externalLogonId);

		freeDataService.saveOrUpdate(updateData);

		//新增免密操作记录
		FreeOperLog freeOperLog = new FreeOperLog();

		freeOperLog.setSmemberId(freeData.getSmemberId());
		freeOperLog.setSmemberName(freeData.getSmemberName());
		freeOperLog.setSmemberNo(freeData.getSmemberNo());
		freeOperLog.setSmerchantCode(freeData.getSmerchantCode());
		freeOperLog.setSmerchantId(freeData.getSmerchantId());

		freeOperLog.setIaction(10);
		freeOperLog.setItype(20);
		freeOperLog.setSopenid(alipayLogonId);
		freeOperLog.setToperTime(DateUtils.parseDateByFormat(signTime,"yyyy-MM-dd HH:mm:ss"));
		freeOperLog.setScontractCode(agreementNo);
		freeOperLog.setScontractId(externalAgreementNo);
		freeOperLog.setSoperIp(map.get("sip"));
		freeOperLogService.insert(freeOperLog);

		if(updateData.getSstatus().equals("NORMAL")) {
			//修改用户状态
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setId(freeData.getSmemberId());
			memberInfo.setIaipayOpen(1);
			memberInfo.setUpdateTime(new Date());
			this.updateBySelective(memberInfo);
		}
		//删除缓存
		iCached.remove("is_remind_open_free_data_" + freeData.getSmemberId());
		//开通免密 调用活动
		openFreeGive(freeData);
		return true;
	}
	/**
	 * 处理支付宝免密解约
	 * @throws Exception
	 */
	@Override
	public boolean dealwithAlipayUnsign() throws Exception {
		//更新登录用户的session中的值
		AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();

		//修改用户状态
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(authVo.getId());
		memberInfo.setIaipayOpen(0);
		memberInfo.setUpdateTime(new Date());
		this.updateBySelective(memberInfo);

		authVo.setIaipayOpen(0);
		SessionUserUtils.setSessionAttributeForUserDtl(authVo);
		return true;
	}
	/**
	 * 判断会员是否存在 支付宝 或 微信
	 * @param mobileNumber 手机号
	 * @param bindType 绑定类型  10 微信 20 支付宝
	 * @param merchantCode
	 * @return
	 */
	@Override
	public MemberInfo selectMemberInfoByBindType(String mobileNumber, Integer bindType, String merchantCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobileNumber", mobileNumber);
		map.put("bindType", bindType);
		map.put("merchantCode", merchantCode);
		return memberInfoDao.selectMemberInfoByBindType(map);
	}

	@Override
	public void recommendRegisterGive(MemberInfo registerMember, MemberInfo recommendMember) {
		try {
			logger.debug("会员注册调用活动：{}", registerMember);
			/**
			 * 查询商户的会员注册活动编号
			 */
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("itype", 10);
			if(null != recommendMember) {
				map.put("iscenesType", BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode());
			} else {
				map.put("iscenesType", BizTypeDefinitionEnum.SourceBizStatus.REGISTER.getCode());
			}
			map.put("istatus", 1);
			map.put("smerchantId", registerMember.getSmerchantId());
			ActivityConf activityConf = activityConfService.selectByMap(map);
			if (null != activityConf) {
				GiveActivityDto giveActivityDto = new GiveActivityDto();
				giveActivityDto.setSmerchantId(registerMember.getSmerchantId());
				giveActivityDto.setSmerchantCode(registerMember.getSmerchantCode());
				giveActivityDto.setMemberId(registerMember.getId());
				giveActivityDto.setMemberCode(registerMember.getScode());
				giveActivityDto.setMemberRealName(registerMember.getSmemberName());


				giveActivityDto.setSsourceCode(registerMember.getScode());
				giveActivityDto.setSsourceId(registerMember.getId());
				//设备注册编号
				giveActivityDto.setSdeviceCode(registerMember.getSregDeviceCode());
				if (null != recommendMember) {
					giveActivityDto.setActiveConfCode(activityConf.getSconfCode());
					giveActivityDto.setSsourceType(BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode());
					giveActivityDto.setSsourceInstruction("会员推荐注册");

					RecommendParam recommendParam = new RecommendParam();
					recommendParam.setRecUserBirthDay(recommendMember.getDbirthdate());
					recommendParam.setRecUserCode(recommendMember.getScode());
					recommendParam.setRecUserId(recommendMember.getId());
					recommendParam.setRecUserRealName(recommendMember.getSrealName());
					recommendParam.setRegistTime(recommendMember.getTregisterTime());
					giveActivityDto.setRecommendParam(recommendParam);
				} else {
					giveActivityDto.setSsourceInstruction("会员注册");
					giveActivityDto.setActiveConfCode(activityConf.getSconfCode());
					giveActivityDto.setSsourceType(BizTypeDefinitionEnum.SourceBizStatus.REGISTER.getCode());
				}
				//创建Rest服务客户端
				RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
						.newInvoker(ActivityServicesDefine.ACTIVITY_GIVE_SERVICE);
				invoke.setRequestObj(giveActivityDto);
				//返回对象中含有泛型，则需要设置返回类型，否则无需设置
				invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {});
				ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
				logger.info("会员注册调用活动返回信息：{}", JSON.toJSONString(responseVo));
			}
		} catch (Exception e) {
			logger.error("会员注册调用活动异常：{}", e);
		}
	}
	/**
	 * 获取会员信息
	 * @param recommonCode 会员的推荐码
	 * @param merchantCode 商户编号
	 */
	@Override
	public MemberInfo selectMemberInfoByRecommonCode(String recommonCode, String merchantCode) {
		return memberInfoDao.selectMemberInfoByRecommonCode(recommonCode, merchantCode);
	}


	/**
	 * 绑定成功发送短信
	 * @param mobile 手机号
	 * @param password 密码
	 * @param isPwd 密码是否在自动生成
	 */
	private void sendUserMsg(final String merchantId, final String merchantCode, final String mobile, final String password, final boolean isPwd) {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				logger.debug("绑定手机号注册用户成功短信用户开始手机号：{}", mobile);
				try {
					MessageDto messageDto = new MessageDto();
					Map<String, Object> contentParam = null;
					messageDto.setSmerchantId(merchantId);
					messageDto.setSmerchantCode(merchantCode);
					if (isPwd) {
						// 模板类型
						messageDto.setTemplateType("bind_register_success");
						// 模板编号
						//messageDto.setTemplateCode(MessageCodeDefine.AUTH_REGISTER_SUCCESS_MSG);
						// 内容
						contentParam = new HashMap<String, Object>();
						contentParam.put("username", mobile);
						contentParam.put("pwd", password);
						messageDto.setContentParam(contentParam);
					} else {
						// 模板类型
						messageDto.setTemplateType("register_success");
						// 模板编号
						//messageDto.setTemplateCode(MessageCodeDefine.BIND_SUCCESS_MSG);
					}
					// 手机号（如果没有不需要设置值）
					messageDto.setMobile(mobile);
					RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);// 服务名称
					invoke.setRequestObj(messageDto); // post 参数
					invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() { });
					ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
					logger.debug("绑定手机号注册用户成功短信发送成功：{}。。。。。。", responseVo);
				} catch (Exception e) {
					logger.error("绑定手机号注册用户成功短信发送失败{}", e);
				}
			}
		});
	}

	/**
	 * 调用开通免密活动 支付宝
	 * @param freeData
	 */
	public void openFreeGive(FreeData freeData) {
		try {
			logger.debug("会员开通免密调用活动：{}", JSON.toJSONString(freeData));
			/**
			 * 查询商户的会员开通免密活动编号
			 */
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("itype", 10);
			Integer sourceType = BizTypeDefinitionEnum.SourceBizStatus.FIRST_OPEN_ALIPAY.getCode();
			map.put("iscenesType", sourceType);
			map.put("istatus", 1);
			map.put("smerchantId", freeData.getSmerchantId());
			ActivityConf activityConf = activityConfService.selectByMap(map);
			if (null != activityConf) {
				GiveActivityDto giveActivityDto = new GiveActivityDto();

				giveActivityDto.setSmerchantId(freeData.getSmerchantId());
				giveActivityDto.setSmerchantCode(freeData.getSmerchantCode());
				giveActivityDto.setMemberId(freeData.getSmemberId());
				giveActivityDto.setMemberCode(freeData.getSmemberNo());
				giveActivityDto.setMemberRealName(freeData.getSmemberName());

				giveActivityDto.setSsourceType(sourceType);
				giveActivityDto.setSsourceId(freeData.getSmemberId());
				giveActivityDto.setSsourceCode(freeData.getSmemberNo());
				giveActivityDto.setActiveConfCode(activityConf.getSconfCode());

				logger.info("会员开通免密调用活动参数：{}", giveActivityDto);
				//创建Rest服务客户端
				RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
						.newInvoker(ActivityServicesDefine.ACTIVITY_GIVE_SERVICE);
				invoke.setRequestObj(giveActivityDto);
				//返回对象中含有泛型，则需要设置返回类型，否则无需设置
				invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {});
				ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
				logger.info("会员开通免密调用活动返回信息：{}", JSON.toJSONString(responseVo));
			}
		} catch (Exception e) {
			logger.error("会员开通免密调用活动异常：{}", e);
		}
	}
}