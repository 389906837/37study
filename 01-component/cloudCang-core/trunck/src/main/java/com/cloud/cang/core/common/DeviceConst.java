package com.cloud.cang.core.common;

/**
 * 设备相关常量
 * @author zengzexiong
 * @version 1.0
 * 2018年4月12日18:56:16
 */
public class DeviceConst {



    /**
     * 设备状态：  10=未注册 20=正常   30=故障   40=报废 50=离线
     */
    public static final int DEVICE_UNREGISTERED = 10;       // 未注册
    public static final int DEVICE_NORMAL = 20;             // 正常
    public static final int DEVICE_MALFUNCTION = 30;        // 故障
    public static final int DEVICE_SCRAPPED = 40;           // 报废
    public static final int DEVICE_OFFLINE = 50;            // 离线

    /**
     * 运营状态：10=启用    20=停用
     */
    public static final int DEVICE_BUSINESS_ENABLE = 10;    // 启用
    public static final int DEVICE_BUSINESS_STOP = 20;      // 停用



    /**
     * 设备状态： 10 待审核 20  审核通过  30 审核拒绝 40 已注册
     */
    public static final int DEVICE_REGISTER_AUDIT_WAIT= 10;     // 待审核
    public static final int DEVICE_REGISTER_AUDIT_PASS= 20;     // 审核通过
    public static final int DEVICE_REGISTER_AUDIT_REFUSE = 30;  // 审核拒绝
    public static final int DEVICE_REGISTERED= 40;              // 已注册



}
