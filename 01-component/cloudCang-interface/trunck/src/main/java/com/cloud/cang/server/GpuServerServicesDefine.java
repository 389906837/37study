package com.cloud.cang.server;

/**
 * GPU服务器
 * Created by yan on 2018/11/7.
 */
public class GpuServerServicesDefine {
    /**
     * 模型升级
     *
     * @param {@link com.cloud.cang.server.JobDto}
     * @return
     */
    public static final String GPU_SERVER_MODEL_UPGRADE = "com.cloud.cang.tec.ws.GpuServerUpService.addModelUpJob";


    /**
     * 后台升级视觉识别模型，视觉服务，升级APK定时任务
     *
     * @param {@link com.cloud.cang.server.MgrJobDto}
     * @return
     */
    public static final String MGR_OPERATE_DEVICE_UPGRADE = "com.cloud.cang.tec.ws.GpuServerUpService.addMgrDeviceUpgradeJob";
}
