package com.cloud.cang.wap.hy.service.impl;/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月18日
 * Description:SecUserServiceImpl.java
 */

import com.alibaba.fastjson.JSON;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sl.LoginLog;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.hy.service.MemberInfoService;

import com.cloud.cang.wap.hy.vo.AreaInfo;
import com.cloud.cang.wap.hy.vo.AreaInfoMain;
import com.cloud.cang.wap.hy.vo.MemberInfoDomain;


import java.util.Date;
import java.util.List;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sys.IpArea;
import com.cloud.cang.security.core.CaptchaUsernamePasswordToken;
import com.cloud.cang.security.service.SecUserService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.sh.dao.MerchantInfoDao;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import com.cloud.cang.wap.sl.service.LoginLogService;
import com.cloud.cang.wap.sys.service.IpAreaService;
import com.cloud.cang.weixin.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhouhong
 * @version 1.0
 */
@Service
public class SecUserServiceImpl implements SecUserService {
	private static final Logger log = LoggerFactory.getLogger(SecUserServiceImpl.class);

	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private ICached iCached;

	@Autowired
	LoginLogService loginLogService;
	@Autowired
	IpAreaService ipAreaService;
	private static RestTemplate restTemplate = new RestTemplate();

	//private String areaSinaIp = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=${ip}";
	private String areaSinaIp = "http://ip.taobao.com/service/getIpInfo.php?ip=${ip}";

	@Override
	public AuthorizationVO getUserByUserName(CaptchaUsernamePasswordToken loginObj) {
		// 将登录信息放入缓存
		MemberInfo memberInfo = memberInfoService.selectMemberInfoByLoginName(loginObj.getUsername(),loginObj.getMerchantCode());
		if (memberInfo == null)
			return null;
		AuthorizationVO vo = new AuthorizationVO();
		vo.setId(memberInfo.getId());
		vo.setUserName(memberInfo.getSmemberName());
		vo.setPassword(memberInfo.getSloginPwd());

		vo.setRegisterTime(memberInfo.getTregisterTime());
		vo.setRegIp(memberInfo.getSmemberRegip());
		vo.setRegDeviceCode(memberInfo.getSregDeviceCode());
		vo.setRegPlatform(String.valueOf(memberInfo.getIregClientType()));
		vo.setIsOrder(memberInfo.getIisOrder());
		vo.setBirthDay(memberInfo.getDbirthdate());
		vo.setReplenishment((memberInfo.getIisReplenishment()==null || memberInfo.getIisReplenishment().intValue() == 0)? false:true);
		vo.setEmail(memberInfo.getSemail());
		vo.setMemberType(memberInfo.getImemberType());
		vo.setMemberLevel(memberInfo.getImemberLevel());
		vo.setImageIco(memberInfo.getSheadImage());
		vo.setSex(memberInfo.getIsex());
		vo.setIaipayOpen(memberInfo.getIaipayOpen());
		vo.setIwechatOpen(memberInfo.getIwechatOpen());
		vo.setMobile(memberInfo.getSmobile());

		String key = WapConstants.PREFIX_CACHE_SECURITY_CODE_ERROR_TIMES + DateUtils.getCurrentDTimeNumber() + loginObj.getUsername();
		Integer count = 0;
		try {
			count = (Integer) iCached.get(key);
		} catch (Exception e) {
			log.error("", e);
		}
		if (count == null) {
			count = 0;
		}

		if (count > 4) {
			vo.setFreeze(true);// 锁定账号
		}

		//商户信息
		vo.setSmerchantId(memberInfo.getSmerchantId());
		vo.setSmerchantCode(memberInfo.getSmerchantCode());
		MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(memberInfo.getSmerchantId());
		vo.setSmerchantName(merchantInfo.getSname());
		return vo;
	}

	@Override
	public AuthorizationVO getUserDetail(String userId) {
		// 将登录信息放入缓存
		MemberInfoDomain memberInfoDomain = memberInfoService.selectMemberInfoDomainById(userId);
		AuthorizationVO authorizationVO = new AuthorizationVO();
		authorizationVO.setCode(memberInfoDomain.getScode());
		authorizationVO.setId(memberInfoDomain.getId());
		authorizationVO.setFreeze(memberInfoDomain.getIstatus() == 2 ? true : false);
		authorizationVO.setLastLoginTime(authorizationVO.getLastLoginTime());
		authorizationVO.setMemberType(memberInfoDomain.getImemberType());
		authorizationVO.setMobile(memberInfoDomain.getSmobile());
		authorizationVO.setRealName(memberInfoDomain.getSrealName());
		authorizationVO.setUserName(memberInfoDomain.getSmemberName());


		authorizationVO.setRegisterTime(memberInfoDomain.getTregisterTime());
		authorizationVO.setRegIp(memberInfoDomain.getSmemberRegip());
		authorizationVO.setRegDeviceCode(memberInfoDomain.getSregDeviceCode());
		authorizationVO.setRegPlatform(String.valueOf(memberInfoDomain.getIregClientType()));
		authorizationVO.setIsOrder(memberInfoDomain.getIisOrder());
		authorizationVO.setBirthDay(memberInfoDomain.getDbirthdate());
		authorizationVO.setReplenishment((memberInfoDomain.getIisReplenishment()==null || memberInfoDomain.getIisReplenishment().intValue() == 0)? false:true);
		authorizationVO.setEmail(memberInfoDomain.getSemail());
		authorizationVO.setMemberType(memberInfoDomain.getImemberType());
		authorizationVO.setMemberLevel(memberInfoDomain.getImemberLevel());
		authorizationVO.setImageIco(memberInfoDomain.getSheadImage());
		authorizationVO.setSex(memberInfoDomain.getIsex());
		authorizationVO.setIaipayOpen(memberInfoDomain.getIaipayOpen());
		authorizationVO.setIwechatOpen(memberInfoDomain.getIwechatOpen());

		//商户信息
		authorizationVO.setSmerchantId(memberInfoDomain.getSmerchantId());
		authorizationVO.setSmerchantCode(memberInfoDomain.getSmerchantCode());
		MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(memberInfoDomain.getSmerchantId());
		authorizationVO.setSmerchantName(merchantInfo.getSname());

		return authorizationVO;
	}

	@Override
	public void loginSuccess(final String userId, String host) {
		final AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
		final String sip = host;
		// 加入登录日志
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					iCached.remove("uid_principal_" + userId);
				} catch (Exception ex) {
					log.error("wap站登录踢除SSO用户异常：", ex);
				}
				try {
					AreaInfo areaInfo = new AreaInfo();
					AreaInfoMain areaInfoMain = null;
					if (ipAreaService != null) {
						IpArea ipArea = ipAreaService.selectIpAreaByIp(sip);
						if (ipArea == null) {
							String ip = "58.213.69.106";
							if (!sip.startsWith("10.0.101")) {
								ip = sip;
							}
							String requestAddress = areaSinaIp.replace("${ip}", ip);
							log.debug("area ip requdst:{}", requestAddress);
							ResponseEntity<String> areaInfoRes = restTemplate.getForEntity(requestAddress, String.class);
							String areaInfoStr  = areaInfoRes.getBody();
							areaInfoMain = JSON.parseObject(areaInfoStr, AreaInfoMain.class);
							if (areaInfoMain != null) {
								areaInfo = areaInfoMain.getData();
								IpArea insertIpArea = new IpArea();
								insertIpArea.setCountry(areaInfo.getCountry());
								insertIpArea.setIstatus(1);
								insertIpArea.setSprovince(areaInfo.getRegion());
								insertIpArea.setSip(sip);
								insertIpArea.setScity(areaInfo.getCity());
								insertIpArea.setTaddtime(new Date());
								insertIpArea.setTupdatetime(new Date());
								ipAreaService.insert(insertIpArea);
							}
						} else {
							areaInfo.setCity(ipArea.getScity());
							areaInfo.setCountry(ipArea.getCountry());
							areaInfo.setRegion(ipArea.getSprovince());
						}
					}
					LoginLog loginLog = new LoginLog();
					loginLog.setSmemberId(authorizationVO.getId());
					loginLog.setSmemberName(authorizationVO.getUserName());
					loginLog.setSuserCode(authorizationVO.getCode());
					loginLog.setSuserRealname(authorizationVO.getRealName());

					loginLog.setItype(10);
					loginLog.setIdeviceType(20);
					loginLog.setCountry(areaInfo.getCountry());
					loginLog.setSprovince(areaInfo.getRegion());
					loginLog.setScity(areaInfo.getCity());
					loginLog.setSip(sip);

					//loginLog.setSversion("1");
					loginLog.setTloginTime(new Date());
					loginLogService.insert(loginLog);

				} catch (RestClientException e) {
					log.error("", e);
				}
			}
		});
	}
	@Override
	public void loginError(String userName, String host) {
	}
	@Override
	public void logout(String userId) {

	}
}
