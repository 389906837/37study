package com.cloud.cang.mgr.ws.web;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.utils.AmFaceUtils;
import com.cloud.cang.mgr.ws.service.FaceDetectService;
import com.cloud.cang.mgr.ws.service.FaceRegisterService;
import com.cloud.cang.mgr.ws.vo.FaceRegisterVo;
import com.cloud.cang.mgr.ws.vo.RegisterResponse;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 百度脸识别
 * YUSEN
 */
@Controller
@RequestMapping("/face")
public class BaiDuFaceRecognitionController {

    private static final Logger logger = LoggerFactory.getLogger(BaiDuFaceRecognitionController.class);
    @Autowired
    private FaceRegisterService faceRegisterService;
    @Autowired
    private FaceDetectService faceDetectService;

    /**
     * 客户人脸注册
     *
     * @param faceRegisterVo
     * @param
     */
    @RequestMapping("/register")
    @ResponseBody
    public RegisterResponse register(@RequestBody FaceRegisterVo faceRegisterVo) {
        logger.info("客户人脸注册:{}", JSON.toJSONString(faceRegisterVo));
        RegisterResponse registerResponse = new RegisterResponse();
        try {
            List<FaceRegisterVo.FaceVo> faceVos = faceRegisterVo.getFaceList();
            if (StringUtil.isBlank(faceRegisterVo.getBatchNo())) {
                logger.info("#######上传批次号为空########");
                registerResponse.setCode("-107");
                registerResponse.setMsg("batch is empty");
                return registerResponse;
            }
            if (CollectionUtils.isNotEmpty(faceVos)) {
            /*    if (faceVos.size() <= 10) {*/
                return faceRegisterService.registerFace(faceRegisterVo);
              /*  } else {
                    logger.info("人脸信息图片数量最多10张");
                    registerResponse.setCode("-102");
                    registerResponse.setMsg("FAIL");
                }*/
            } else {
                logger.info("人脸信息为空");
                registerResponse.setCode("-100");
                registerResponse.setMsg("param[] is null");
            }
        } catch (Exception e) {
            logger.error("雨森人脸注册异常：{}", e);
            registerResponse.setCode("-101");
            registerResponse.setMsg("Unknown error");
        }
        return registerResponse;
    }

    /**
     * 删除人脸注册信息
     *
     * @param userId
     * @param faceToken
     * @return
     */
    @RequestMapping("/deleteFace")
    @ResponseBody
    public String deleteFace(String userId, String faceToken) {
        AmFaceUtils.delete(userId, faceToken, AmFaceUtils.YS_GROUP_ID);
        return "success";
    }


    /**
     * 人脸检测接口
     *
     * @param
     */
    @RequestMapping("/detect")
    @ResponseBody
    public ResponseVo<String> faceDetect(@RequestBody Map<String, String> map) {
        logger.info("调用百度人脸检测接口开始");
        try {
            String faceJson = map.get("faceJson");
            if (StringUtil.isBlank(faceJson)) {
                logger.error("人脸检测接口JSON不存在");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("人脸检测参数为空");
            }
            return faceDetectService.faceDetect(faceJson);
        } catch (Exception e) {
            logger.error("调用人脸检测接口异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("调用人脸检测接口异常");
        }
    }
}
