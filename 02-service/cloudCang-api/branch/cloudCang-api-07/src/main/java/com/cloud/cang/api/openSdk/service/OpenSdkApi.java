package com.cloud.cang.api.openSdk.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.open.sdk.client.DefaultRequestClient;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.model.request.ImageDetail;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.request.ImgRecognitionRequest;
import com.cloud.cang.open.sdk.response.ImgRecognitionResponse;
import com.cloud.cang.utils.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @version 1.0
 * @ClassName: OpenSdkApi
 * @Description: 云端识别API工具类
 * @Author: zengzexiong
 * @Date: 2018年9月20日15:01:35
 */
public class OpenSdkApi {

    private static final Logger logger = LoggerFactory.getLogger(OpenSdkApi.class);


    static ICached iCached;
    private static ICached getiCached() {
        if (iCached == null) {
            iCached = SpringContext.getBean(ICached.class);
        }
        return iCached;
    }

    /**
     * 云端识别配置项
     */
    public static String APP_ID = GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "app_id");    //
    public static String APP_SERCET_KEY = GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "app_sercet_key");    //
    public static String HOST = GrpParaUtil.getValue(GrpParaUtil.CLOUD_RECOGNITION_OPEN_SDK, "host");    //
    public static String PLATFORM_PUBLIC_KEY = getAppManage().getSplatformPublicKey();    //
    public static String PRIVATE_KEY = getAppManage().getSappPrivateKey();    //


    public static AppManage getAppManage() {
        String catcheKey = RedisConst.OP_APP_MANAGE + APP_ID;
        String str = null;
        try {
            str = (String) getiCached().get(catcheKey);
        } catch (Exception e) {
            logger.error("从Redis中获取openSDK配置信息，转换对象出错");
        }
        return JSON.parseObject(str, AppManage.class);
    }



    /**
     * 图片云端视觉识别接口
     *
     * @param deviceId    设备ID
     * @param outBatchNo  商户业务批次号
     * @param desc        描述
     * @param imageDetail 接口请求包含的图片base64集合
     * @return
     */
    public static ImgRecognitionResponse recognition(String deviceId, String outBatchNo, String desc, List<ImageDetail> imageDetail) {
        // 获得初始化的OpenSdkClient
        DefaultRequestClient defaultRequestClient = new DefaultRequestClient(HOST, APP_ID, APP_SERCET_KEY, PRIVATE_KEY, PLATFORM_PUBLIC_KEY);
        // 创建API对应的request
        ImgRecognitionRequest imgRecognitionRequest = new ImgRecognitionRequest();
        ImgRecognition imgRecognition = new ImgRecognition();
        imgRecognition.setDeviceId(deviceId);
        imgRecognition.setOutBatchNo(outBatchNo);
        imgRecognition.setImageDetail(imageDetail);
        imgRecognition.setDesc(desc);
        imgRecognitionRequest.setBizContent(JSON.toJSONString(imgRecognition));
        try {
            // 调用SDK
            ImgRecognitionResponse imgRecognitionResponse = defaultRequestClient.execute(imgRecognitionRequest);
            return imgRecognitionResponse;
        } catch (CloudCangException e) {
            logger.error("调用openSdk出现异常:{}", e);
        }
        return null;
    }

}
