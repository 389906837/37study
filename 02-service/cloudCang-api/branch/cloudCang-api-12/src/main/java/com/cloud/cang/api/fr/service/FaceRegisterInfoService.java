package com.cloud.cang.api.fr.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.fr.FaceRegisterInfo;
import com.cloud.cang.generic.GenericService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface FaceRegisterInfoService extends GenericService<FaceRegisterInfo, String> {

    /**
     * @param deviceId     设备ID
     * @param key          通信密钥
     * @param userId       用户ID
     * @param payType      支付方式
     * @param base64String 用户图片字符串
     * @param request      请求
     * @return
     */
    ResponseVo<String> faceRegister(String deviceId, String key, String userId, Integer payType, String base64String, HttpServletRequest request);

    /**
     * 生成注册人脸信息二维码
     *
     * @param deviceId 设备ID
     * @param key      通信密钥
     * @return
     */
    ResponseVo<String> generateRegisterQrCode(String deviceId, String key);

}