package com.cloud.cang.recongition;

import com.cloud.cang.common.ResponseVo;

/**
 * @version 1.0
 * @Description: 视觉操作服务常量定义
 * @Author: zhouhong
 * @Date: 2016年3月5日 上午11:34:08
 */
public class VisionOperServicesDefine {
    /***
     * 视觉模型更新
     * 请求参数：{@link ModelUpdateDto}
     * 返回参数：{@link ResponseVo <String>}
     */
    public static final String MODEL_UPDATE = "com.cloud.cang.open.api.ws.RecongitionOperService.modelUpdate";

    /***
     * 视觉服务更新
     * 请求参数：{@link ServerUpdateDto}
     * 返回参数：{@link ResponseVo <String>}
     */
    public static final String SERVER_UPDATE = "com.cloud.cang.open.api.ws.RecongitionOperService.serverUpdate";

    /***
     * GPU视觉服务初始化
     * 请求参数：{@link String}
     * 返回参数：{@link ResponseVo <String>}
     */
    public static final String GPU_SERVER_INIT = "com.cloud.cang.open.api.ws.RecongitionOperService.gpuServerInit";

}
