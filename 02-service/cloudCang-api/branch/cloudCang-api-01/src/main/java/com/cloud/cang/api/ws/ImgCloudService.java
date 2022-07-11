package com.cloud.cang.api.ws;

import com.cloud.cang.api.netty.service.CloudRecognitionService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.http.HttpCloudOrderModel;
import com.cloud.cang.open.sdk.model.request.ImageDetail;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @version 1.0
 * @ClassName ImgCloudRecService
 * @Description 云识别接口
 * @Author zengzexiong
 * @Date 2018年1月25日11:26:16
 */
@RestController
@RequestMapping("/cloudRecognition")
@RegisterRestResource
public class ImgCloudService {

    private static final Logger logger = LoggerFactory.getLogger(ImgCloudService.class);

    @Autowired
    private CloudRecognitionService cloudRecognitionService;    // 接口服务处理类

    /**
     * 云识别接口关门结算
     *
     * @param deviceId     设备ID
     * @param key          通信密钥
     * @param userId       用户ID
     * @param openDoorType 开门类型
     * @param imgDetail    图片集合
     * @param request      请求
     * @return
     */
    @RequestMapping("/closeDoor")
    @ResponseBody
    public ResponseVo<HttpCloudOrderModel> aiDeviceRegister(String deviceId, String key, String userId, Integer openDoorType, List<ImageDetail> imgDetail, HttpServletRequest request) {
        logger.debug("云识别接口设备关门结算开始，设备ID:{},通信密钥:{}，用户ID:{}，开门类型:{}", deviceId, key, userId, openDoorType);
        ResponseVo<HttpCloudOrderModel> responseVo = ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo();
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备不能为空");
                logger.debug("云识别接口设备关门结算参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("设备通信密钥不能为空");
                logger.debug("云识别接口设备关门结算参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(userId)) {
                responseVo.setMsg("用户ID不能为空");
                logger.debug("云识别接口设备关门结算参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (null == openDoorType) {
                responseVo.setMsg("开门类型不能为空");
                logger.debug("云识别接口设备关门结算参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (CollectionUtils.isEmpty(imgDetail)) {
                responseVo.setMsg("图片集合不能为空");
                logger.debug("云识别接口设备关门结算参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }

            // 调用图片云识别服务
            responseVo = cloudRecognitionService.closeDoorSettle(deviceId, key, userId, openDoorType, imgDetail, request);

        } catch (Exception e) {
            logger.error("云识别系统设备关门结算接口调用失败:{}", e.getMessage());
        }
        logger.debug("云识别接口设备关门结算开始，设备ID：{}", deviceId);
        return responseVo;
    }


}
