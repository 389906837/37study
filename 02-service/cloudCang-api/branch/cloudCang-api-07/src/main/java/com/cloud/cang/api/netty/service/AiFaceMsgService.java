package com.cloud.cang.api.netty.service;

import com.cloud.cang.api.common.utils.FaceMsgToJsonUtils;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.faceCommon.FaceType;
import com.cloud.cang.faceModel.AuthorizeSuccessModel;
import com.cloud.cang.facePojo.FaceResponseVo;
import com.cloud.cang.sb.AuthorizeAiFaceDto;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;

/**
 * @version 1.0
 * @ClassName AiFaceMsgService
 * @Description 人脸识别后台消息处理类
 * @Author zengzexiong
 * @Date 2018年7月20日09:21:32
 */
@Service("AiFaceMsgService")
public class AiFaceMsgService {

    private static final Logger logger = LoggerFactory.getLogger(AiFaceMsgService.class);

    @Autowired
    private ICached iCached;

    private static ConcurrentMap<String, ChannelHandlerContext> ctxMap = SingletonClientMap.newInstance().getCtxMap();  //netty通道
    private static ConcurrentMap<String, ChannelHandlerContext> aiFaceMap = SingletonClientMap.newInstance().getAiFaceMap();  //人脸识别通道


    public void handlerMessage(FaceResponseVo faceResponseVo) {
        logger.info("开始处理普通消息，数据：{}", faceResponseVo);
        String methodType = faceResponseVo.getMethodType();
        boolean success = faceResponseVo.isSuccess();
        Integer code = faceResponseVo.getCode();
        String deviceId = faceResponseVo.getDeviceId();


        if (StringUtils.isBlank(methodType)) {  //方法名为空直接返回
            logger.error("设备ID为：" + deviceId + "发送消息的方法名为空！");
            return;
        }

        // 消息
        if (BooleanUtils.isTrue(success)) {
            if (FaceType.CANCLE_REGISTER.equals(methodType) && Integer.valueOf(200).equals(code)) {   // 注册
                logger.debug("用户在人脸识别设备:{}上取消注册人脸操作", deviceId);
                String token = faceResponseVo.getData();
                if (StringUtils.isNotBlank(token)) {
                    try {
                        iCached.put(NettyConst.FACE_REGISTER_TOKEN + token, "cancel", 30 * 60);
                    } catch (Exception e) {
                        logger.error("将授权二维码链接token值修改为cancel出现异常,token值：{}", token);
                    }
                }
            }

            return;
        } else {
//            if (TypeConstant.QR_CODE.equals(methodType)) {   // 设备请求发送二维码
//                logger.error("二维码重新发送:{}", code);
////                resendQrCode(baseResponseVo);
//            } else if (TypeConstant.UPGRADE_SYSTEM.equals(methodType)) {
//                logger.debug("设备:{}发送Apk升级成功消息到netty服务器", deviceId);
////                upgradeApkResult(baseResponseVo);
//            }
            return;
        }


    }

    /**
     * 扫码授权
     *
     * @param authorizeAiFaceDto
     * @return
     */
    public ResponseVo<String> scanCodeAuthorizeAiFace(AuthorizeAiFaceDto authorizeAiFaceDto) {
        String token = authorizeAiFaceDto.getToken();   // 二维码连接token
        String aiId = authorizeAiFaceDto.getAiId();     // 人脸识别设备ID
        String userId = authorizeAiFaceDto.getUserId(); // 用户ID（授权时必填）
        Integer payType = authorizeAiFaceDto.getPayType(); // 支付方式（授权时必填）
//        String authorizeAccount = authorizeAiFaceDto.getAuthorizeAccount(); // 账号（授权时必填）
        String tokenValue = "";    // token是否存在

        // 校验token值
        // token值范围(有效="valid"，取消="cancel"，失效="invalid"||""
        try {
            tokenValue = (String) iCached.get(NettyConst.FACE_REGISTER_TOKEN + token);
        } catch (Exception e) {
            logger.error("从Redis中获取人脸识别设备授权token失败");
        }
        if (StringUtils.isBlank(tokenValue) || tokenValue.equals("cancel")) {
            logger.error("token校验失败，人脸识别设备ID：{}，token：{},token值状态：{}", aiId, token, tokenValue);
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("token校验失败");
        }

        // 判断设备是否在线
        ChannelHandlerContext faceCtx = aiFaceMap.get(aiId);
        if (null == faceCtx) {
            logger.error("AI设备离线，人脸识别设备ID：{}，token值：{}", aiId, token);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("AI设备离线");
        }

        // 判断授权类型
        Integer authorizeType = authorizeAiFaceDto.getAuthorizeType();  // 授权类型
        String faceType = "";   // 长连接方法名
        Object data = "";
        if (1 == authorizeType) { // 扫码成功
            faceType = FaceType.SCAN_CODE_SUCCESS;
            data = "scan code success";
            logger.debug("扫码成功，人脸识别设备ID：{}，token值：{}", aiId, token);
        } else if (2 == authorizeType) { // 扫码失败
            faceType = FaceType.SCAN_CODE_FAIL;
            data = "scan code fail";
            logger.debug("扫码失败，人脸识别设备ID：{}，token值：{}", aiId, token);
        } else if (3 == authorizeType) { // 授权成功
            if (StringUtils.isNotBlank(userId) && null != payType) {
                faceType = FaceType.SCAN_CODE_AUTHORIZE_SUCCESS;
                AuthorizeSuccessModel authorizeSuccessModel = new AuthorizeSuccessModel();
//                authorizeSuccessModel.setUserId(userId);
                authorizeSuccessModel.setPayType(payType);
                data = authorizeSuccessModel;
                logger.debug("授权成功，人脸识别设备ID：{}，用户ID：{}，token值：{},授权信息：{}", aiId, userId, token, authorizeSuccessModel.toString());
            } else {
                logger.debug("扫码授权成功，用户ID与支付方式不能为空，人脸识别设备ID：{}，token值：{}", aiId, token);
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("扫码授权出现未知类型操作");
            }
        } else if (4 == authorizeType) { // 授权拒绝
            faceType = FaceType.SCAN_CODE_AUTHORIZE_FAIL;
            data = "scan code authorize fail";
            logger.debug("授权拒绝，人脸识别设备ID：{}，token值：{}", aiId, token);
        } else {
            logger.debug("扫码授权出现未知类型操作，人脸识别设备ID：{}，token值：{}", aiId, token);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("扫码授权出现未知类型操作");
        }

        // 向人脸识别设备发送消息
        if (StringUtils.isNotBlank(faceType)) {
            FaceMsgToJsonUtils.SendFaceCommonNoCodeMsgByCtx(aiId, faceCtx, true, data, userId, faceType);
            return ResponseVo.getSuccessResponse(data.toString());
        }

        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("扫码授权出现未知错误");
    }

}
