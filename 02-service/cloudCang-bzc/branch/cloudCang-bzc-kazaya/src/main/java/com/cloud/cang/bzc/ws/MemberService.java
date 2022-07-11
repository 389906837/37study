package com.cloud.cang.bzc.ws;


import com.cloud.cang.bzc.hy.service.MemberInfoService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.sys.service.CityService;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.hy.MemberInfoDto;
import com.cloud.cang.model.hy.MemberInfo;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @version 1.0
 * @ClassName: MemberService
 * @Description: 会员服务
 * @Author: zengzexiong
 * @Date: 2017年12月27日
 */
@RestController
@RequestMapping("/memberService")
@RegisterRestResource
public class MemberService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

	// 弱类型引用
	private static Interner<String> interner = Interners.newWeakInterner();

	@Autowired
	private MemberInfoService memberInfoService;


	/*@Autowired
	private CityService cityService;*/


	/**
	 * @param memDto 会员对象
	 * @return
	 * @Author: zengzexiong
	 * @Date: 2017年12月27日
	 * @Description:会员注册服务
	 */
	@SuppressWarnings({"rawtypes", "unchecked", "static-access"})
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseVo<MemberInfo> register(@RequestBody MemberInfoDto memDto) {
		try {
			//校验基础参数
			ResponseVo<String> validateResult = validateRegisterParam(memDto);
			if (!validateResult.isSuccess()) {
				LOGGER.info("会员注册参数校验失败：{}", validateResult.getMsg());
				return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
			}
			//判读用户是否存在
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("smemberName", memDto.getSmobile());
			param.put("smerchantCode", memDto.getSmerchantCode());
			param.put("delFlag", 0);
			List<MemberInfo> memList = memberInfoService.selectByMapWhere(param);
			if (memList != null && memList.size() > 0) {
				return new ResponseVo(false, ErrorCodeEnum.ERROR_FAC_MOBULE_EXISTING.getCode(), ErrorCodeEnum.ERROR_FAC_MOBULE_EXISTING.getNameByCode(ErrorCodeEnum.ERROR_FAC_MOBULE_EXISTING.getCode()));
			}
			//开通账户
			synchronized (interner.intern(memDto.getSmobile())) {
				return memberInfoService.accountRegister(memDto);

			}

		} catch (Exception e) {
			LOGGER.error("注册服务调用失败", e);
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，创建用户失败");
		}
	}

	/**
	 * 校验注册信息参数
	 *
	 * @param memDto 会员注册信息
	 * @return 校验通过返回success，否则返回false
	 */
	@SuppressWarnings("unchecked")
	private ResponseVo<String> validateRegisterParam(MemberInfoDto memDto) {
		LOGGER.info("会员注册校验参数开始.....参数：{}", memDto);
		if (StringUtils.isBlank(memDto.getThirdUserId())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("third user id is empty");
		}
		if (StringUtils.isBlank(memDto.getSmobile())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员手机号不能为空");
		}

		if (StringUtils.isBlank(memDto.getSloginPassword())) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员登录密码不能为空");
		}

		if (memDto.getImemberType() == null) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员账户类型不能为空");
		}
		if (memDto.getIsourceClientType() == null) {
			return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员注册来源客户端类型不能为空");
		}
		LOGGER.debug("会员注册校验参数成功.....");
		return ResponseVo.getSuccessResponse();
	}

}
