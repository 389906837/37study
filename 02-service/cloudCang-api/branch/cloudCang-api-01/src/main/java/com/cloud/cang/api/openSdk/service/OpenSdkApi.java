package com.cloud.cang.api.openSdk.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.open.sdk.client.DefaultRequestClient;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.model.request.ImageDetail;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.request.ImgRecognitionRequest;
import com.cloud.cang.open.sdk.response.ImgRecognitionResponse;
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

    /**
     * 图片云端视觉识别接口
     *
     * @param conf        商户配置
     * @param deviceId    设备ID
     * @param outBatchNo  商户业务批次号
     * @param desc        描述
     * @param imageDetail 接口请求包含的图片base64集合
     * @return
     */
    public static ImgRecognitionResponse recognition(MerchantConf conf, String deviceId, String outBatchNo, String desc, List<ImageDetail> imageDetail) {
        // 获得初始化的OpenSdkClient
        DefaultRequestClient defaultRequestClient = openSdkClient("url", conf);
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
            logger.error("调用openSdk出现异常:{}", e.getErrMsg());
        }
        return null;
    }


    /**
     * 创建openSdk客户端
     *
     * @param url          请求服务器地址
     * @param merchantConf 商户配置
     * @return
     */
    private static DefaultRequestClient openSdkClient(String url, MerchantConf merchantConf) {
        // 获取商户配置信息
        DefaultRequestClient defaultRequestClient = new DefaultRequestClient(url, "appId", "appSecretKey", "privateKey", "platformPublicKey");
        return defaultRequestClient;
    }

}
