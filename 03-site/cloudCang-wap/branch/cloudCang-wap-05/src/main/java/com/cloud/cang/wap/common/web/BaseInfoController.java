package com.cloud.cang.wap.common.web;


import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.MessageCodeDefine;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.common.CodeEnum;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.WapUtils;
import com.cloud.cang.wap.common.utils.gt.GeetestConfig;
import com.cloud.cang.wap.common.utils.gt.GeetestLib;
import com.cloud.cang.wap.common.vo.RequestVo;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.sh.service.MerchantInfoService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName: BaseInfoController
 * @Description: 常用方法控制层
 * @Author: zhouhong
 * @Date: 2016年9月6日 上午11:57:27
 */
@Controller
@RequestMapping("/base")
public class BaseInfoController {

    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private ICached iCached;
    private static final Logger logger = LoggerFactory.getLogger(BaseInfoController.class);

    /**
     * @param requestVo 请求参数
     * @Author: zhouhong
     * @Date: 2016年9月6日 下午1:50:22
     * @Description:用户操作发送短信验证码 根据types 10 绑定手机号
     */
    @SuppressWarnings({"static-access", "rawtypes", "unchecked"})
    @RequestMapping(value = "/sendMessageByTypes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo<String> sendMessageByTypes(RequestVo requestVo, HttpServletRequest request) {
        logger.debug("发送验证码开始...");
        ResponseVo responseVo = ResponseVo.getSuccessResponse();
        CodeEnum codeEnum = null;
        try {
            logger.debug("发送验证码接口参数：{}", requestVo);
            MessageDto messageDto = null;
            //获取会员信息
            AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
            String memId = requestVo.getMemId();// 会员ID 修改用户信息用到
            String mobileNumber = requestVo.getMobileNumber();// 手机号
            if (StringUtil.isBlank(mobileNumber) && null != authorizationVO) {
                memId = authorizationVO.getId();
                mobileNumber = authorizationVO.getMobile();
            }
            String authCode = "";
            String merchantCode = WapUtils.getMerchantCookie(request);
            if (StringUtil.isBlank(merchantCode)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("数据异常，请尝试重新扫码");
            }
            MerchantInfo merchantInfo = merchantInfoService.selectByCode(merchantCode);
            if (null == merchantInfo) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("短信发送失败，数据异常");
            }
            if (requestVo.getTypes().intValue() == 10) {
                if (WapUtils.isWXRequest(request)) {
                    requestVo.setBindType(10);
                } else if (WapUtils.isAlipayRequest(request)) {
                    requestVo.setBindType(20);
                }
                // 判断注册的手机号码是否存在
                MemberInfo memberInfo = memberInfoService.selectMemberInfoByBindType(mobileNumber, requestVo.getBindType(), merchantCode);
                if (null != memberInfo) {
                    return CodeEnum.MOBILE_PHONE_EXIST.getResponseVo();
                }
                // 注册
                String captcha = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
                if (StringUtil.isNull(mobileNumber)) {
                    return CodeEnum.EMPTY_MOBILE_PHONE.getResponseVo();
                } else if (!ValidateUtils.isMobile(mobileNumber)) {
                    return CodeEnum.MOBILE_PHONE_FORMAT_ERROR.getResponseVo();
                } else if (StringUtil.isNull(requestVo.getTxCode())) {
                    return CodeEnum.TX_CODE_EMPTY.getResponseVo();
                } else if (captcha == null || !captcha.equalsIgnoreCase(requestVo.getTxCode())) {
                    return CodeEnum.TX_CODE_ERROR.getResponseVo();
                }
                messageDto = new MessageDto();
                // 模板编号
                messageDto.setTemplateCode(MessageCodeDefine.USER_BIND_MSG);
                // 内容
                Map<String, Object> contentParam = new HashMap<String, Object>();
                authCode = RandomStringUtils.random(6, false, true);
                contentParam.put("yzcode", authCode);
                contentParam.put("smobile", mobileNumber);
                if (WapUtils.isWXRequest(request)) {
                    contentParam.put("typeName", "微信");
                } else if (WapUtils.isAlipayRequest(request)) {
                    contentParam.put("typeName", "支付宝");
                }

                messageDto.setContentParam(contentParam);
                // 手机号（如果没有不需要设置值）
                messageDto.setMobile(mobileNumber);
            } else if (requestVo.getTypes().intValue() == 100) {
                if (WapUtils.isWXRequest(request)) {
                    requestVo.setBindType(10);
                } else if (WapUtils.isAlipayRequest(request)) {
                    requestVo.setBindType(20);
                }
                if (!ValidateUtils.isMobile(mobileNumber)) {
                    return CodeEnum.MOBILE_PHONE_FORMAT_ERROR.getResponseVo();
                }
                // 判断注册的手机号码是否存在
                MemberInfo memberInfo = memberInfoService.selectMemberInfoByBindType(mobileNumber, requestVo.getBindType(), merchantCode);
                if (null != memberInfo) {
                    return CodeEnum.MOBILE_PHONE_EXIST.getResponseVo();
                }
                GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                        GeetestConfig.isnewfailback());
                //从session中获取gt-server状态
                int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
                //从session中获取userid
                String userid = (String) request.getSession().getAttribute("userid");
                //自定义参数,可选择添加
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("user_id", userid); //网站用户id
                param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
                param.put("ip_address", RequestUtils.getIpAddr(request)); //传输用户请求验证时所携带的IP
                int gtResult = 0;

                if (gt_server_status_code == 1) {
                    //gt-server正常，向gt-server进行二次验证
                    gtResult = gtSdk.enhencedValidateRequest(requestVo.getChallenge(), requestVo.getValidate(), requestVo.getSeccode(), param);
                } else {
                    // gt-server非正常情况下，进行failback模式验证
                    gtResult = gtSdk.failbackValidateRequest(requestVo.getChallenge(), requestVo.getValidate(), requestVo.getSeccode());
                }
                if (gtResult != 1) {
                    return CodeEnum.TX_CODE_ERROR.getResponseVo();
                }
                messageDto = new MessageDto();
                // 模板编号
                //messageDto.setTemplateCode(MessageCodeDefine.USER_BIND_MSG);
                messageDto.setSmerchantId(merchantInfo.getId());
                messageDto.setSmerchantCode(merchantInfo.getScode());
                messageDto.setTemplateType("register");
                // 内容
                Map<String, Object> contentParam = new HashMap<String, Object>();
                authCode = RandomStringUtils.random(4, false, true);
                contentParam.put("yzcode", authCode);
                contentParam.put("smobile", mobileNumber);
                if (WapUtils.isWXRequest(request)) {
                    contentParam.put("typeName", "微信");
                } else if (WapUtils.isAlipayRequest(request)) {
                    contentParam.put("typeName", "支付宝");
                }
                messageDto.setContentParam(contentParam);
                // 手机号（如果没有不需要设置值）
                messageDto.setMobile(mobileNumber);
            } else if (requestVo.getTypes().intValue() == 110) {
                Integer time = (Integer) iCached.get("isSend_" + mobileNumber);
                if (null == time || 1 != time) {
                    return CodeEnum.SEND_MSG_ERROR.getResponseVo();
                }
                if (WapUtils.isWXRequest(request)) {
                    requestVo.setBindType(10);
                } else if (WapUtils.isAlipayRequest(request)) {
                    requestVo.setBindType(20);
                }
                if (!ValidateUtils.isMobile(mobileNumber)) {
                    return CodeEnum.MOBILE_PHONE_FORMAT_ERROR.getResponseVo();
                }
                // 判断注册的手机号码是否存在e
                MemberInfo memberInfo = memberInfoService.selectMemberInfoByBindType(mobileNumber, requestVo.getBindType(), merchantCode);
                if (null != memberInfo) {
                    return CodeEnum.MOBILE_PHONE_EXIST.getResponseVo();
                }
                //从session中获取userid
                String userid = (String) request.getSession().getAttribute("userid");
                //自定义参数,可选择添加
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("user_id", userid); //网站用户id
                param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
                param.put("ip_address", RequestUtils.getIpAddr(request)); //传输用户请求验证时所携带的IP

                messageDto = new MessageDto();
                // 模板编号
                //messageDto.setTemplateCode(MessageCodeDefine.USER_BIND_MSG);
                messageDto.setSmerchantId(merchantInfo.getId());
                messageDto.setSmerchantCode(merchantInfo.getScode());
                messageDto.setTemplateType("register");
                // 内容
                Map<String, Object> contentParam = new HashMap<String, Object>();
                authCode = RandomStringUtils.random(4, false, true);
                contentParam.put("yzcode", authCode);
                contentParam.put("smobile", mobileNumber);
                if (WapUtils.isWXRequest(request)) {
                    contentParam.put("typeName", "微信");
                } else if (WapUtils.isAlipayRequest(request)) {
                    contentParam.put("typeName", "支付宝");
                }
                messageDto.setContentParam(contentParam);
                // 手机号（如果没有不需要设置值）
                messageDto.setMobile(mobileNumber);
            } else {
                System.out.println(memId);
            }
            if (messageDto != null) {
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);// 服务名称
                invoke.setRequestObj(messageDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                ResponseVo<String> resVO = (ResponseVo) invoke.invoke();
                if (resVO.isSuccess()) {
                    responseVo = CodeEnum.SUCCESS.getResponseVo();
                    responseVo.setSuccess(true);
                    // 保存到redis中 验证码超时时间
                    if (requestVo.getTypes().intValue() == 10 || requestVo.getTypes().intValue() == 100 || requestVo.getTypes().intValue() == 110) {
                        iCached.put(WapConstants.RedisConst.MEMBER_BIND_AUTH_CODE + mobileNumber, authCode, StringUtil.toNumber(GrpParaUtil.getValue(GrpParaUtil.SYSTEM_PARA_CODE, "CODE_FAILURE_TIME"), 1800));
                        iCached.put("isSend_" + mobileNumber, 1, 60 * 30);
                    }
                    return responseVo;
                } else {
                    return CodeEnum.SEND_MSG_ERROR.getResponseVo();
                }
            }
        } catch (Exception e) {
            logger.error("短信发送异常:{}", e);
        }
        return CodeEnum.ERROR_NETWORK.getResponseVo();
    }

}
