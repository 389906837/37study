package com.cloud.cang.open.api.common.utils;

import com.cloud.cang.eye.sdk.CAvatarEye;
import com.cloud.cang.eye.sdk.LogLevel;
import com.cloud.cang.open.api.init.CAvatarEyeNotifier;
import com.google.gson.Gson;


/**
 * @version 1.0
 * @Description: 视觉识别工具类
 * @Author: zhouhong
 * @Date: 2018/9/4 14:38
 */
public class VisualUtils {
    static CAvatarEyeNotifier notifier = new CAvatarEyeNotifier();
    static CAvatarEye goodsRecognition = new CAvatarEye(LogLevel.ALL_LOG_LEVEL);
    static Gson gson = new Gson();

    public static CAvatarEye getGoodsRecognition() {
        if (null == goodsRecognition.getNotifier()) {
            goodsRecognition.setNotifier(notifier);
        }
        return goodsRecognition;
    }
    public static Gson getGson() {
        return gson;
    }
}
