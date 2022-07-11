package com.cloud.cang.api.ws;

import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.IPUtils;
import com.cloud.cang.api.fr.service.FaceRegisterInfoService;
import com.cloud.cang.api.hy.service.MemberInfoService;
import com.cloud.cang.api.netty.service.AiFaceMsgService;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.api.sl.service.LoginLogService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.sb.AuthorizeAiFaceDto;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName AiFaceService
 * @Description 人脸识别设备控制层
 * @Author zengzexiong
 * @Date 2018年7月25日15:04:42
 */
@RestController
@RequestMapping("/aiFace")
@RegisterRestResource
public class AiFaceService {

    private static final Logger logger = LoggerFactory.getLogger(AiFaceService.class);

    @Autowired
    DeviceRegisterService registerService;

    @Autowired
    FaceRegisterInfoService faceRegisterInfoService;

    @Autowired
    MemberInfoService memberInfoService;

    @Autowired
    LoginLogService loginLogService;

    @Autowired
    AiFaceMsgService aiFaceMsgService;

    @Autowired
    DeviceInfoService deviceInfoService;

    /**
     * 人脸识别客户端
     * 通过注册码将注册表设备状态改为已注册，
     * 返回设备ID_通信密钥key_设备编号
     *
     * @param reqIp 设备
     * @return 注册成功返回“设备ID_安全密钥_设备编号”
     */
    @RequestMapping("/aiDeviceRegister")
    @ResponseBody
    public ResponseVo<String> aiDeviceRegister(String reqIp) {
        logger.debug("人脸识别注册开始，注册码:{}", reqIp);
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()));
        try {
            //校验基础参数
            if (StringUtils.isBlank(reqIp)) {
                responseVo.setMsg("注册码不能为空");
                logger.debug("人脸识别设备注册码参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            //注册设备，返回“设备ID_安全密钥_设备编号”；否则返回已经false
            responseVo = registerService.faceRegister(reqIp);
        } catch (Exception e) {
            logger.error("人脸识别注册码获取设备信息服务调用失败:{}", e);
        }
        logger.debug("人脸识别设备注册服务完成，设备ID_通信密钥_设备编号：{}", responseVo.getData());
        return responseVo;
    }

    /**
     * 人脸信息注册
     *
     * @param deviceId     设备ID
     * @param key          通信密钥
     * @param userId       用户ID
     * @param payType      支付方式 wechat=10,alipay=20
     * @param base64String 用户图片base64字符串
     * @return 注册成功返回“设备ID_安全密钥”
     */
    @RequestMapping("/faceRegister")
    @ResponseBody
    public ResponseVo<String> faceRegister(String deviceId, String key, String userId, Integer payType, String base64String, HttpServletRequest request) {
        logger.debug("用户注册人脸信息，设备ID：{}，用户ID：{}");
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()));
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备ID不能为空");
                logger.debug("人脸识别设备注册参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("通信密钥");
                logger.debug("人脸识别设备注册参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(userId)) {
                responseVo.setMsg("用户ID");
                logger.debug("人脸识别设备注册参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(base64String)) {
                responseVo.setMsg("用户图片base64字符串不能为空");
                logger.debug("人脸识别设备注册参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }

            // 人脸信息注册
            return faceRegisterInfoService.faceRegister(deviceId, key, userId, payType, base64String, request);

        } catch (ServiceException e) {
            logger.error("人脸识别注册服务出现ServiceException异常:{}", e.getMessage());
            responseVo.setMsg("人脸注册出现错误");
        } catch (Exception e) {
            logger.error("人脸识别注册服务调用失败:{}", e);
        }
        logger.debug("人脸识别设备注册服务完成，设备ID_通信密钥：{}", responseVo.getData());
        return responseVo;
    }


    /**
     * 获取注册授权二维码
     *
     * @param deviceId 设备ID
     * @param key      通信密钥
     * @return 注册码地址url
     */
    @RequestMapping("/faceRegisterQrCode")
    @ResponseBody
    public ResponseVo<String> faceRegisterQrCode(String deviceId, String key) {
        logger.debug("用户注册人脸信息，设备ID：{}",deviceId);
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()));
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备ID不能为空");
                logger.debug("人脸识别设备注册参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("通信密钥");
                logger.debug("人脸识别设备注册参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }

            // 人脸信息注册
            return faceRegisterInfoService.generateRegisterQrCode(deviceId, key);

        } catch (Exception e) {
            logger.error("人脸识别注册服务调用失败:{}", e);
        }
        logger.debug("人脸识别设备注册服务完成，设备ID_通信密钥：{}", responseVo.getData());
        return responseVo;
    }

    /**
     * 已经绑定人脸信息用户
     * 刷脸登录
     *
     * @param deviceId     设备ID
     * @param key          通信密钥
     * @param base64String 图片字符串
     * @param userIds      用户ID字符串（手机号后4位匹配用户）
     * @return 登录成功，返回true
     */
    @RequestMapping("/faceLogin")
    @ResponseBody
    public ResponseVo<String> faceLogin(String deviceId, String key, String base64String, String userIds, HttpServletRequest request) {
        logger.debug("刷脸登录开始，设备ID：{}，手机尾号匹配用户ID集合：{}", deviceId, userIds);
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()));
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备ID不能为空");
                logger.debug("刷脸登录参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("通信密钥不能为空");
                logger.debug("刷脸登录参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(userIds)) {
                responseVo.setMsg("匹配的用户ID不能为空");
                logger.debug("刷脸登录参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(base64String)) {
                responseVo.setMsg("用户图片base64字符串不能为空");
                logger.debug("刷脸登录参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }

            String ip = IPUtils.getIpFromRequest(request);
            // 刷脸登录
            ResponseVo responseVo1 = loginLogService.faceLogin(deviceId, key, base64String, userIds, ip);
            if (BooleanUtils.isTrue(responseVo1.isSuccess())) {
                logger.debug("刷脸登录成功，用户ID：{}", responseVo1.getData());
            } else {
                logger.debug("刷脸登录失败，失败原因：{}", responseVo1.getMsg());
            }
            return responseVo1;
        } catch (ServiceException e) {
            logger.error("人脸识别注册服务调用失败:{}", e.getMessage());
        } catch (Exception e) {
            logger.error("人脸识别注册服务调用失败:{}", e.getMessage());
        }
        logger.debug("刷脸登录出现错误，错误原因：{}", responseVo.getMsg());
        return responseVo;
    }


    /**
     * 手机尾号查找用户是否存在
     *
     * @param deviceId 设备ID
     * @param key      通信密钥
     * @param tailNum  手机后四位尾号
     * @return 查询到匹配用户时，返回userId拼接字符串，否则返回false，及错误信息
     */
    @RequestMapping("/searchUser")
    @ResponseBody
    public ResponseVo<String> searchUser(String deviceId, String key, String tailNum) {
        logger.debug("手机尾号查找用户是否存在，设备ID：{}，手机尾号：{}", deviceId, tailNum);
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()));
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备ID不能为空");
                logger.debug("手机尾号查找用户参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("通信密钥不能为空");
                logger.debug("手机尾号查找用户参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(tailNum)) {
                responseVo.setMsg("手机尾号不能为空");
                logger.debug("手机尾号查找用户参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }

            // 手机尾号查找用户
            ResponseVo responseVo1 = memberInfoService.searchUser(deviceId, key, tailNum);
            logger.debug("手机尾号查找用户完成，用户ID集合：{}", responseVo1.getData());
            return responseVo1;
        } catch (ServiceException e) {
            logger.error("手机尾号查找用户失败:{}", e);
        } catch (Exception e) {
            logger.error("手机尾号查找用户失败:{}", e);
        }
        logger.debug("手机尾号查找用户失败,错误原因：{}", responseVo.getMsg());
        return responseVo;
    }


    /**
     * AI设备获取售货机开门二维码
     *
     * @param deviceId 设备ID
     * @param key      通信密钥
     * @return 查询到匹配用户时，返回userId拼接字符串，否则返回false，及错误信息
     */
    @RequestMapping("/getOpenDoorQrCode")
    @ResponseBody
    public ResponseVo<String> getOpenDoorQrCode(String deviceId, String key) {
        logger.debug("AI设备获取售货机开门二维码开始，设备ID：{}，通信密钥：{}", deviceId, key);
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()));
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备ID不能为空");
                logger.debug("AI设备获取售货机开门二维码参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("通信密钥不能为空");
                logger.debug("AI设备获取售货机开门二维码参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }

            // AI设备获取售货机开门二维码
            ResponseVo responseVo1 = deviceInfoService.getOpenDoorQrCode(deviceId, key);
            logger.debug("AI设备获取售货机开门二维码完成，用户ID集合：{}", responseVo1.getData());
            return responseVo1;
        } catch (ServiceException e) {
            logger.error("AI设备获取售货机开门二维码失败:{}", e);
        } catch (Exception e) {
            logger.error("AI设备获取售货机开门二维码失败:{}", e);
        }
        logger.debug("AI设备获取售货机开门二维码失败,错误原因：{}", responseVo.getMsg());
        return responseVo;
    }


    /**
     * 扫码授权
     *
     * @return 成功/失败
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/scanCodeAuthorizeAiFace", method = RequestMethod.POST)
    public ResponseVo<String> scanCodeAuthorizeAiFace(@RequestBody AuthorizeAiFaceDto authorizeAiFaceDto) {
        if (StringUtils.isBlank(authorizeAiFaceDto.getAiId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("人脸设备ID参数不能为空！");
        }
        if (StringUtils.isBlank(authorizeAiFaceDto.getToken())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("token参数不能为空！");
        }
        Integer authorizeType = authorizeAiFaceDto.getAuthorizeType();
        if (null == authorizeType) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("授权类型参数不能为空！");
        }
        try {
            return aiFaceMsgService.scanCodeAuthorizeAiFace(authorizeAiFaceDto);
        } catch (Exception e) {
            logger.error("获取已经注册到服务器的TCP信息出现异常：{}", e.getMessage());
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，获取已经注册到服务器的TCP信息失败");
        }
    }

}
