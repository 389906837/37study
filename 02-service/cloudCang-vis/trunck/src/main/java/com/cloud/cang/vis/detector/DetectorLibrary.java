package com.cloud.cang.vis.detector;

import com.sun.jna.Native;

public class DetectorLibrary {

    static {
        Native.register("yolov3");
    }

    /**
     * 初始化视觉服务
     * @param loadParams
     * @return 成功 失败
     */
    public static native int loadRecogitionServer(String loadParams);

    /***
     * 重启视觉服务
     * @param reloadParams
     * @return 成功 失败
     */
    public static native int reloadRecogitionServer(String reloadParams);

    /***
     * 识别图片
     * @param recogitionParams 识别参数 json字符串
     * @return 识别编码和数量
     */
    public static native String recogitionImageByFileName(String recogitionParams);

    /**
     * 识别图片
     * @param recogitionParams 识别参数 json字符串
     * @return 识别编码、位置坐标、置信度和数量
     */
    public static native String recogitionImageByFileNamePos(String recogitionParams);
}
