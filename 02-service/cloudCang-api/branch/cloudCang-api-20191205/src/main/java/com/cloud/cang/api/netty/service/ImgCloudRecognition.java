package com.cloud.cang.api.netty.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.open.sdk.model.response.ImgResultDetail;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by YLF on 2019/12/3.
 */
@Service("ImgCloudRecognition")
public class ImgCloudRecognition {

    private static final Logger logger = LoggerFactory.getLogger(ImgCloudRecognition.class);

    @Autowired
    private CloudRecognitionService cloudRecognitionService;    // 接口服务处理类

    @Autowired
    private DeviceRegisterService registerService;
    @Autowired
    private ICached iCached;

    /**
     * 云识别接口关门结算
     *
     * @param deviceId            设备ID
     * @param key                 通信密钥
     * @param userId              用户ID
     * @param openDoorType        开门类型
     * @param imgResultDetail     商品集合
     * @return
     */
    @RequestMapping("/cloudCloseDoor")
    @ResponseBody
    public ResponseVo<String> cloudCloseDoor(String deviceId, String key, String userId, Integer openDoorType, String imgResultDetail) {
        logger.debug("云识别接口设备关门结算开始，设备ID:{},通信密钥:{}，用户ID:{}，开门类型:{}", deviceId, key, userId, openDoorType);
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo();
        try {
            String tempDeviceId = (String) iCached.get("cloud_open_door_" + userId);
            if (StringUtil.isBlank(tempDeviceId)) {
                logger.debug("##########云端正常关门重复处理##############");
                return responseVo;
            }
            iCached.remove("open_door_key" + deviceId);
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
            List<ImgResultDetail> imgResultDetailList = null;
            if (StringUtils.isNotEmpty(imgResultDetail)) {
                imgResultDetailList = JSON.parseObject(imgResultDetail, new TypeReference<List<ImgResultDetail>>() {});
            }
            if (CollectionUtils.isEmpty(imgResultDetailList)) {
                responseVo.setMsg("图片集合不能为空");
                logger.debug("云识别接口设备开门记录信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }


            // 调用图片云识别服务
            responseVo = cloudRecognitionService.closeDoorSettle(deviceId, key, userId, openDoorType, imgResultDetailList);

        } catch (Exception e) {
            logger.error("云识别系统设备关门结算接口调用失败:{}", e);
        }
        logger.debug("云识别接口设备关门结算开始，设备ID：{}", deviceId);
        return responseVo;
    }



    /**
     * 开关门异常处理
     * @param deviceId           设备ID
     * @param key                通信密钥
     * @param userId     用户ID
     * @param openDoorType       开门类型
     * @param methodType        方法名称 10 开门 20 关门
     * @param errorCode     错误代码
     * @return
     */
    @RequestMapping("/cloudException")
    @ResponseBody
    public ResponseVo<String> cloudException(String deviceId, String key, String userId, Integer openDoorType, Integer methodType, String errorCode) {
        logger.debug("开关门异常处理，设备ID:{},通信密钥:{}，用户ID:{}，开门类型:{}", deviceId, key, userId, openDoorType);
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo();
        try {
            String tempDeviceId = (String) iCached.get("cloud_open_door_" + userId);
            if (StringUtil.isBlank(tempDeviceId)) {
                logger.debug("##########云端异常关门重复处理##############");
                return responseVo;
            }
            iCached.remove("open_door_key" + deviceId);
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备不能为空");
                logger.debug("云识别接口开关门异常处理参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("设备通信密钥不能为空");
                logger.debug("云识别接口开关门异常处理参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(userId)) {
                responseVo.setMsg("用户ID不能为空");
                logger.debug("云识别接口开关门异常处理参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (null == openDoorType) {
                responseVo.setMsg("开门类型不能为空");
                logger.debug("云识别接口开关门异常处理参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (null == methodType) {
                responseVo.setMsg("方法名称不能为空");
                logger.debug("云识别接口开关门异常处理参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            //开关门异常处理
            responseVo = cloudRecognitionService.cloudException(deviceId, key, userId, openDoorType, methodType, errorCode);

        } catch (Exception e) {
            logger.error("云识别系统开关门异常处理接口调用失败:{}", e);
        }
        return responseVo;
    }



    /**
     * 云识别接口开门记录信息功能
     *
     * @param deviceId        设备ID
     * @param key             通信密钥
     * @param imgResultDetail 商品集合
     * @return
     */
    @RequestMapping("/cloudOpenDoor")
    @ResponseBody
    public ResponseVo<String> cloudOpenDoor(String deviceId, String key, String imgResultDetail) {
        logger.debug("云识别接口设备开门记录信息开始，设备ID:{},通信密钥:{}", deviceId, key);
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo();
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备不能为空");
                logger.debug("云识别接口设备开门记录信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("设备通信密钥不能为空");
                logger.debug("云识别接口设备开门记录信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (BooleanUtils.isFalse(verifyByDeviceIdAndKey(deviceId, key))) {
                responseVo.setMsg("设备通信密钥校验未通过");
                logger.debug("云识别接口设备开门记录信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            List<ImgResultDetail> imgResultDetailList = null;
            if (StringUtils.isNotEmpty(imgResultDetail)) {
                imgResultDetailList = JSON.parseObject(imgResultDetail, new TypeReference<List<ImgResultDetail>>() {
                });
            }
            if (CollectionUtils.isEmpty(imgResultDetailList)) {
                responseVo.setMsg("图片集合不能为空");
                logger.debug("云识别接口设备开门记录信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            // 调用图片云识别服务
            responseVo = cloudRecognitionService.openDoorSettle(deviceId, imgResultDetailList);

        } catch (Exception e) {
            logger.error("云识别系统设备开门记录信息接口调用失败:{}", e);
        }
        logger.debug("云识别接口设备开门记录信息结束，设备ID：{}", deviceId);
        return responseVo;
    }


    /**
     * 云识别接口开门实时盘货
     *
     * @param deviceId        设备ID
     * @param key             通信密钥
     * @param userId          用户ID
     * @param openDoorType    开门类型
     * @param imgResultDetail 图片集合
     * @return
     */
    @RequestMapping("/cloudRealtime")
    @ResponseBody
    public ResponseVo<String> cloudRealtime(String deviceId, String key, String userId, Integer openDoorType, String imgResultDetail) {
        logger.debug("云识别接口设备开门实时记录信息开始，设备ID:{},通信密钥:{}", deviceId, key);
        ResponseVo<String> responseVo = ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo();
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                responseVo.setMsg("设备不能为空");
                logger.debug("云识别接口设备开门实时记录信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (StringUtils.isBlank(key)) {
                responseVo.setMsg("设备通信密钥不能为空");
                logger.debug("云识别接口设备开门实时记录信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            if (BooleanUtils.isFalse(verifyByDeviceIdAndKey(deviceId, key))) {
                responseVo.setMsg("设备通信密钥校验未通过");
                logger.debug("云识别接口设备开门记录信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            List<ImgResultDetail> imgResultDetailList = null;
            if (StringUtils.isNotEmpty(imgResultDetail)) {
                imgResultDetailList = JSON.parseObject(imgResultDetail, new TypeReference<List<ImgResultDetail>>() {
                });
            }
            if (CollectionUtils.isEmpty(imgResultDetailList)) {
                responseVo.setMsg("图片集合对象不能为空");
                logger.debug("云识别接口设备实时订单计算信息参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }

            // 调用图片云识别服务
            responseVo = cloudRecognitionService.realtimeOrder(deviceId, key, userId, openDoorType, imgResultDetailList);

        } catch (Exception e) {
            logger.error("云识别系统设备开门实时计算接口调用失败:{}", e);
        }
        logger.debug("云识别接口设备开门实时计算结束，设备ID：{}", deviceId);
        return responseVo;
    }

    /**
     * 根据设备ID与key校验该设备信息是否正确
     *
     * @param deviceId 设备ID
     * @param key 安全密钥
     * @return true校验通过，否则不通过
     */
    private boolean verifyByDeviceIdAndKey(String deviceId, String key) {
        DeviceRegister deviceRegister = new DeviceRegister();
        deviceRegister.setSdeviceId(deviceId);
        deviceRegister.setSsecurityKey(key);
        deviceRegister.setIdelFlag(0);
        List<DeviceRegister> deviceRegisterList = registerService.selectByEntityWhere(deviceRegister);

        if (CollectionUtils.isNotEmpty(deviceRegisterList)) {
            return true;
        } else {
            return false;
        }
    }
}
