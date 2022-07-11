/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月12日
 * Description:TecConstants.java 
 */
package com.cloud.cang.tec.common;

/**
 * @author zhouhong
 * @version 1.0
 */
public class TecConstants {

    /**
     * 商户平台实时数据统计定时任务
     */
    public static final String PLATFORM_TJ_DATA_TASK = "platform_tj_data_task";

    /**
     * 商户平台统计数据更新定时任务
     */
    public static final String PLATFORM_TJ_UPDATE_TASK = "platform_tj_update_task";

    /**
     * 商户平台每日统计数据更新定时任务
     */
    public static final String PLATFORM_DAY_UPDATE_TASK = "platform_day_update_task";

    /**
     * 商户短信预警定时任务
     */
    public static final String MESSAGE_WARN_TASK = "message_warn_task";
    /**
     * 商户合约日到期预警定时任务
     */
    public static final String EXPIRE_DATE_WARN_TASK = "expire_date_warn_task";
    /**
     * 商户合约到期状态变更定时任务
     */
    public static final String MERCHANT_EXPIRE_TASK = "merchant_expire_task";
    /**
     * 活动过期状态变更定时任务
     */
    public static final String ACTIVITY_STATUS_CHANGE_TASK_ = "activity_status_change_task_";
    /**
     * 场景活动过期提醒定时任务
     */
    public static final String SCENEACTIVITY_EXPIRE_WARN_TASK = "sceneactivity_expire_warn_task";
    /**
     * 订单扫描定时任务
     */
    public static final String SCANNING_ORDER_TASK = "scanning_order_task";
    /**
     * 商品保质期过期预警定时任务
     */
    public static final String COMMODITY_EXPIRED_WARN_TASK = "commodity_expired_warn_task";
    /**
     * 商品过期状态变更定时任务
     */
    public static final String COMMODITY_EXPIRED_TASK = "commodity_expired_task";
    /**
     * 今日过期商品提示定时任务
     */
    public static final String TODAY_COMMODITY_EXPIRED_PROMPT = "today_commodity_expired_prompt";
    /**
     * 商品库存预警定时任务
     */
    public static final String COMMODITY_STOCK_WARN_TASK = "commodity_stock_warn_task";

    /**
     * 广告过期定时任务
     */
    public static final String ADVERTIS_EXPIRED_WARN_TASK = "advertis_expired_warn_task";
    /**
     * 资讯过期定时任务
     */
    public static final String ANNOUNCEMENT_EXPIRED_WARN_TASK = "announcement_expired_warn_task";

    /**
     * 商品实时库存同步定时任务
     */
    public static final String REALTIME_INVENTORY_SYN_TASK = "realtime_inventory_syn_task";

    /**
     * 异常商品预警定时任务
     */
    public static final String ERROR_COMMODITY_WARN_TASK = "error_commodity_warn_task";
    /**
     * 异常商品预警定时任务
     */
    public static final String COMMODITY_SALES_REP_CONTRAST_TASK = "commodity_sales_rep_contrast_task";
    /**
     * 设备在线扫描定时任务
     */
    public static final String DEVICE_ONLINE_SCAN_TASK = "device_online_scan_task";
    /**
     * 设备盘货定时任务
     */
    public static final String DEVICE_INVENTORY_TASK = "device_inventory_task";

    /**
     * 付款失败订单代支付
     */
    public static final String FAILED_ORDER_PAY_TASK = "failed_order_pay_task";
    /**
     * 付款处理中订单补处理
     */
    public static final String IN_PAYMENT_ORDER_TREATMENT_TASK = "in_payment_order_treatment_task";

    /**
     * 优惠券过期预警
     */
    public static final String COUPON_EXPIRED_WARN_TASK = "coupon_expired_warn_task";
    /**
     * 优惠券过期状态变更
     */
    public static final String COUPON_EXPIRED_TASK = "coupon_expired_task";

    /**
     * 商品总销售数量平均销售价平均成本价同步
     */
    public static final String SYNC_COMMODITY_DATA_TASK = "sync_commodity_data_task";

    /**
     * gpu服务器模型更新
     */
    public static final String GPU_SERVER_UPGRADE = "gpu_server_upgrade";

    /**
     * 后台升级视觉识别模型，视觉服务，升级APK定时任务
     */
    public static final String MGR_SEND_UPGRADE_MSG = "mgr_send_upgrade_msg";

    /**
     * 接口业务回调
     */
    public static final String INTERFACE_ACCEPT_CALLBACK = "interface_accept_callback";

    /**
     * 会员代扣协议解签定时查询
     */
    public static final String MEMBER_UNSIGN_ALIPAY_QUERY = "member_unsign_alipay_query";

}
