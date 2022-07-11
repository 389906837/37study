package com.cloud.cang.mq;

public enum QueueEnum {
    IMG_MODEL("img.model.","open-api发送给vis消息, 识别图片"),
    IMG_MODEL_CALLBACK("img.callback.model.","open-api发送给vis消息, 识别图片(第三方)"),
    IMG_MODEL_YOLOV4_CALLBACK("img.yolov4.callback.model.","open-api发送给vis消息, YOLOV4识别图片(第三方)"),
    IMG_MODEL_SYNCHRONIZE("img.synchronize.model.","open-api发送给vis消息, 识别图片同步方法(第三方)"),
    IMG_RESULT("img.result","vis把识别结果发送给open-api"),
    IMG_RESULT_CALLBACK("img.callback.result","vis把识别结果发送给open-api(第三方)"),
    IMG_RESULT_YOLOV4_CALLBACK("img.callback.yolov4.result","vis把YOLOV4识别结果发送给open-api(第三方)"),
    IMG_RESULT_SYNCHRONIZE("img.synchronize.result","vis把识别结果发送给open-api(第三方同步方法)"),
    IMG_OPENAPI_RESULT("img.openapi.result","open-api处理识别结果，发送给api，纯视觉"),
    IMG_OPENAPI_WEIGHT_RESULT("img.openapi.weight.result","open-api处理识别结果，发送给api，视觉+称重"),
    IMG_ERROR("img.error","识别错误，发送给api，纯视觉"),
    IMG_WEIGHT_ERROR("img.weight.error","识别错误，发送给api，视觉+称重"),
    IMG_MODEL_DARKNET("img.darknet.model.","第三方调用open-api发送给vis消息, 识别图片(V4模型)"),
    IMG_RESULT_DARKNET("img.darknet.result","vis把识别结果发送给open-api(V4模型)"),
    MODEL_UPDATE("model.update","更新模型");

    private String name;
    private String desc;

    QueueEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
