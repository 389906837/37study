package com.cloud.cang.openapi;


public final class APIConstant {


    public static final String ZLQF_RECOGNITION_RESULT = "zlqf_recognition_result_";
    // 中林清风图片识别接口保存路径
    public static final String UPLOAD_IMAGE_PATH = "/dataSharing/businessData/train_model_data/images/";

    public class RedisKey{
        public static final String IMG_RECOGNITIONVO = "img_recognitionvo_";
        public static final String IMG_RECOGNITIONVO_CALLBACK = "img_recognitionvo_callback_";
        public static final String IMG_RECOGNITIONVO_YOLOV4_CALLBACK = "img_recognitionvo_yolov4_callback_";
        public static final String IMG_RECOGNITIONVO_SYNCHRONIZE = "img_recognitionvo_synchronize_";
        public static final String IMG_RECOGNITIONVO_THRID_SYNCHRONIZE_RESULT = "thrid_synchronize_result_";
    }
}
