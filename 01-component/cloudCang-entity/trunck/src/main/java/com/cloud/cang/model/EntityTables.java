package com.cloud.cang.model;

/**
 * 所有表
 *
 * @author zhouhong
 */
public class EntityTables {
    /**
     * 后台用户表
     **/
    public static final String SYS_OPERATOR = "sys_operator";
    /**
     * 用户角色表
     **/
    public static final String SYS_OPERATORROLE = "sys_operatorrole";
    /**
     * 后台权限码表
     **/
    public static final String SYS_PURVIEW = "sys_purview";
    /**
     * 后台角色表
     **/
    public static final String SYS_ROLE = "sys_role";
    /**
     * 后台角色权限分配
     **/
    public static final String SYS_ROLEPURVIEW = "sys_rolepurview";
    /**
     * 后台权限商户表
     **/
    public static final String SYS_MERCHANT_PURVIEW = "sys_merchant_purview";
    /**
     * 后台菜单管理表
     **/
    public static final String SYS_MENU = "sys_menu";


    /**
     * 数据字典主表
     **/
    public static final String SYS_PARAMETERGROUP = "sys_parametergroup";
    /**
     * 数据字典明细
     **/
    public static final String SYS_PARAMETER_GROUP_DETAIL = "sys_parameter_group_detail";
    /**
     * 编号生成配置表
     **/
    public static final String SYS_CODE_GENERATOR = "sys_code_generator";
    /**
     * IP地址管理
     **/
    public static final String SYS_IP_AREA = "sys_ip_area";
    /**
     * 运营参数表
     **/
    public static final String SYS_BUSINESS_PARAMETER = "sys_business_parameter";
    /**
     * 温馨提醒配置
     **/
    public static final String SYS_REMIND_MESSAGE = "sys_remind_message";
    /**
     * 国家表
     **/
    public static final String SYS_COUNTRY = "sys_country";
    /**
     * 省份表
     **/
    public static final String SYS_PROVINCE = "sys_province";
    /**
     * 城市表
     **/
    public static final String SYS_CITY = "sys_city";
    /**
     * 县镇区域表
     **/
    public static final String SYS_TOWN = "sys_town";
    /**
     * 节日管理
     **/
    public static final String CS_CURRENTTRADEDATE = "cs_currenttradedate";
    /**
     * 定时任务配置表
     **/
    public static final String SYS_SCHEDULE_CONF = "sys_schedule_conf";


    /**
     * 系统访问、操作日志
     **/
    public static final String SL_VIST_OPER_LOG = "sl_vist_oper_log";
    /**
     * 对账日志表
     **/
    public static final String SL_CHECK_LOG = "sl_check_log";
    /**
     * 设备操作日志
     **/
    public static final String SL_DEVICE_OPER = "sl_device_oper";


    /**
     * 会员角色表
     **/
    public static final String HY_MBR_ROLE = "hy_mbr_role";
    /**
     * 权限码管理
     **/
    public static final String HY_MBR_PURVIEW = "hy_mbr_purview";
    /**
     * 会员角色权限表
     **/
    public static final String HY_MBR_ROLE_PUR = "hy_mbr_role_pur";
    /**
     * 会员角色关系表
     **/
    public static final String HY_MBR_ROLE_CONF = "hy_mbr_role_conf";
    /**
     * 会员资金账户表
     **/
    public static final String HY_FUND_ACCOUNT = "hy_fund_account";
    /**
     * 会员积分账户
     **/
    public static final String HY_INTEGRAL_ACCOUNT = "hy_integral_account";
    /**
     * 会员基础信息表
     **/
    public static final String HY_MEMBER_INFO = "hy_member_info";
    /**
     * 第三方授权信息表
     **/
    public static final String HY_THIRD_AUTHORISE = "hy_third_authorise";


    /**
     * 消息任务表
     **/
    public static final String XX_MSG_TASK = "xx_msg_task";
    /**
     * 消息供应商信息表
     **/
    public static final String XX_SUPPLIER_INFO = "xx_supplier_info";
    /**
     * 模板主表表
     **/
    public static final String XX_MSG_TEMPLATE_MAIN = "xx_msg_template_main";
    /**
     * 消息/协议  模板从表
     **/
    public static final String XX_MSG_TEMPLATE = "xx_msg_template";
    /**
     * 营销短信表
     **/
    public static final String XX_SALES_MSG_MAIN = "xx_sales_msg_main";


    /**
     * 活动应用范围明细表
     **/
    public static final String AC_USE_RANGE = "ac_use_range";
    /**
     * 活动配置表
     **/
    public static final String AC_ACTIVITY_CONF = "ac_activity_conf";
    /**
     * 活动优惠信息明细表
     **/
    public static final String AC_DISCOUNT_DETAIL = "ac_discount_detail";
    /**
     * 用户持有劵信息表
     **/
    public static final String AC_COUPON_USER = "ac_coupon_user";
    /**
     * 活动优惠记录表
     **/
    public static final String AC_DISCOUNT_RECORD = "ac_discount_record";
    /**
     * 活动返券规则记录表
     **/
    public static final String AC_COUPON_RULE = "ac_coupon_rule";
    /**
     * 活动返积分规则记录表
     **/
    public static final String AC_INTEGRAL_RULE = "ac_integral_rule";
    /**
     * 好友推荐记录表
     **/
    public static final String AC_RECOMMEND_RECORD = "ac_recommend_record";
    /**
     * 券码兑换明细表
     **/
    public static final String AC_COUPON_CODE_EX_DETAILS = "ac_coupon_code_ex_details";
    /**
     * 优惠券批量下发表
     **/
    public static final String AC_COUPON_BATCH = "ac_coupon_batch";
    /**
     * 优惠券批量下发（用户信息）
     **/
    public static final String AC_COUPON_USER_SEND = "ac_coupon_user_send";


    /**
     * 设备分组管理
     **/
    public static final String SB_GROUP_MANAGE = "sb_group_manage";
    /**
     * 设备基础信息表
     **/
    public static final String SB_DEVICE_INFO = "sb_device_info";
    /**
     * 小屏幕设备基础信息
     **/
    public static final String SB_AI_INFO = "sb_ai_info";
    /**
     * 设备型号详细信息表
     **/
    public static final String SB_DEVICE_MODEL = "sb_device_model";
    /**
     * 设备分组关系
     **/
    public static final String SB_GROUP_RELATIONSHIP = "sb_group_relationship";
    /**
     * 设备商品信息表
     **/
    public static final String SB_DEVICE_COMMODITY = "sb_device_commodity";
    /**
     * 设备管理信息表
     **/
    public static final String SB_DEVICE_MANAGE = "sb_device_manage";
    /**
     * 设备监控数据配置信息表
     **/
    public static final String SB_MONITOR_DATA_CONF = "sb_monitor_data_conf";
   /**
     * 定时操作设备任务执行表
     **/
    public static final String SB_TIMING_TASK = "sb_timing_task";


    /**
     * 商户基础详细信息表
     **/
    public static final String SH_MERCHANT_DETAIL = "sh_merchant_detail";
    /**
     * 商户基础信息表
     **/
    public static final String SH_MERCHANT_INFO = "sh_merchant_info";
    /**
     * 商户资质附件信息表
     **/
    public static final String SH_MERCHANT_ATTACH = "sh_merchant_attach";
    /**
     * 商户配置信息
     **/
    public static final String SH_MERCHANT_CONF = "sh_merchant_conf";
    /**
     * 商户域名配置信息
     **/
    public static final String SH_DOMAIN_CONF = "sh_domain_conf";


    /**
     * 商品订单记录信息表
     **/
    public static final String OM_ORDER_RECORD = "om_order_record";
    /**
     * 订单商品明细表
     **/
    public static final String OM_ORDER_COMMODITY = "jy_order_commodity";
    /**
     * 退款图片说明表
     **/
    public static final String OM_REFUND_IMG_DESC = "om_refund_img_desc";
    /**
     * 商品订单退款审核记录信息表
     **/
    public static final String OM_REFUND_AUDIT = "om_refund_audit";
    /**
     * 退款订单审核商品明细表
     **/
    public static final String JY_REFUND_COMMODITY = "jy_refund_commodity";
    /**
     * 申请退款操作日志表
     **/
    public static final String JY_REFUND_OPERLOG = "jy_refund_operlog";
    /**
     * 审核订单表
     **/
    public static final String OM_ORDER_AUDIT = "om_order_audit";
    /**
     * 审核订单商品明细表
     **/
    public static final String OM_ORDER_AUDIT_COMMODITY = "om_order_audit_commodity";


    /**
     * 单设备库存记录表
     **/
    public static final String SM_DEVICE_STOCK = "sm_device_stock";
    /**
     * 设备实时库存明细
     **/
    public static final String SM_STOCK_DETAIL = "sm_stock_detail";
    /**
     * 设备标准库存配置信息表
     **/
    public static final String SM_STANDARD_STOCK = "sm_standard_stock";
    /**
     * 设备标准库存配置明细表
     **/
    public static final String SM_STANDARD_DETAIL = "sm_standard_detail";


    /**
     * 付款申请
     **/
    public static final String SQ_PAY_APPLY = "sq_pay_apply";
    /**
     * 退款申请
     **/
    public static final String SQ_REFUND_APPLY = "sq_refund_apply";
    /**
     * 微信支付分创建订单申请
     **/
    public static final String SQ_CREAT_APPLY = "sq_creat_apply";


    /**
     * 平台数据汇总表
     **/
    public static final String TJ_SUMMARIZATION_DATA_PLF = "tj_summarization_data_plf";


    /**
     * 商品分类表
     **/
    public static final String SP_CATEGORY = "sp_category";
    /**
     * 商品信息表
     **/
    public static final String SP_COMMODITY_INFO = "sp_commodity_info";
    /**
     * 商品品牌管理信息表
     **/
    public static final String SP_BRAND_INFO = "sp_brand_info";
    /**
     * 商品批次管理信息表
     **/
    public static final String SP_COMMODITY_BATCH = "sp_commodity_batch";
    /**
     * 商品标签管理信息表
     **/
    public static final String SP_LABEL_INFO = "sp_label_info";
    /**
     * 商品小类编号
     **/
    public static final String SP_CATEGORY_SMALL = "sp_category_small";

    /**
     * 视觉识别商品标准SKU
     **/
    public static final String VR_COMMODITY_SKU = "vr_commodity_sku";
    /**
     * 商品申报
     **/
    public static final String VR_COMMODITY_DECLARE = "vr_commodity_declare";
    /**
     * 商户设备SKU库
     **/
    public static final String VR_MERCHANT_DEVICE_SKU = "vr_merchant_device_sku";
    /**
     * 视觉识别商品特征
     **/
    public static final String VR_COMMODITY_FEATURE = "vr_commodity_feature";
    /**
     * 商户SKU库
     **/
    public static final String VR_MERCHANT_SKU = "vr_merchant_sku";


    /**
     * 任务中心日志表
     **/
    public static final String TEC_SCHEDULE_LOG = "tec_schedule_log";


    /**
     * 商品补货记录信息表
     **/
    public static final String OM_REPLENISHMENT_RECORD = "om_replenishment_record";
    /**
     * 补货商品明细表
     **/
    public static final String OM_REPLENISHMENT_COMMODITY = "om_replenishment_commodity";
    /**
     * 计划商品补货记录信息表
     **/
    public static final String OM_REPLENISHMENT_PLAN = "om_replenishment_plan";
    /**
     * 计划补货商品明细表
     **/
    public static final String OM_REPLENISHMENT_PLAN_DETAIL = "om_replenishment_plan_detail";


    /**
     * 运营区域表
     **/
    public static final String WZ_REGION = "wz_region";
    /**
     * 广告表
     **/
    public static final String WZ_ADVERTIS = "wz_advertis";
    /**
     * 网站关键字
     **/
    public static final String WZ_KEY_WORDS = "wz_key_words";

    /**
     * 标注批次管理信息表
     **/
    public static final String LM_LABEL_BATCH = "lm_label_batch";
    /**
     * 标注批次管理信息表商品表
     **/
    public static final String LM_LABEL_BATCH_COMMODITY = "lm_label_batch_commodity";
    /**
     * 打包记录表
     **/
    public static final String LM_PACKING_RECORD = "lm_packing_record";
    /**
     * 打包记录明细表
     **/
    public static final String LM_PACKING_RECORD_DETAIL = "lm_packing_record_detail";

    /**
     * 标注批次管理信息表商品表
     **/
    public static final String FR_FACE_REGISTER_INFO = "fr_face_register_info";


    /**
     * 开放平台API服务器管理
     **/
    public static final String CR_OPEN_SERVER_LIST = "cr_open_server_list";
    /**
     * GPU识别服务器列表
     **/
    public static final String CR_SERVER_LIST = "cr_server_list";
    /**
     * 识别服务模型管理
     **/
    public static final String CR_SERVER_MODEL = "cr_server_model";
    /**
     * 开放平台识别服务器管理
     **/
    public static final String CR_RECOGNITION_SERVER = "cr_recognition_server";
    /**
     * 平台应用管理信息表
     **/
    public static final String OP_APP_MANAGE = "op_app_manage";
    /**
     * 平台用户表
     **/
    public static final String OP_USER_INFO = "op_user_info";
   /**
     * 平台接口信息管理表
     **/
    public static final String OP_INTERFACE_INFO = "op_interface_info";
    /**
     * 平台应用管理信息应用
     **/
    public static final String OP_APP_MANAGE_SAPP_ID = "op_app_manage_sapp_id";
    /**
     * 接口购买记录表
     **/
    public static final String OP_BUY_RECORD = "op_buy_record";
    /**
     * 用户接口权限表
     **/
    public static final String OP_USER_INTERFACE_AUTH = "op_user_interface_auth";
    /**
     * 接口账户记录表
     **/
    public static final String OP_INTERFACE_ACCOUNT = "op_interface_account";

    /**
     * 第三方订单/补货记录
     **/
    public static final String TB_OPERATE_DEVICE_RECORD = "tb_operate_device_record";

    /**
     * 第三方订单/补货记录
     **/
    public static final String TB_INTERFACE_TRANSFER_LOG = "tb_interface_transfer_log";

    /**
     * 人脸注册信息表
     **/
    public static final String FR_FACE_REG_INFO = "fr_face_reg_info";

    /**
     * 设备管理信息模板表
     **/
    public static final String TP_DEVICE_MANAGE_TEMPLATE = "tp_device_manage_template";

    /**
     * 设备基础信息模板表
     **/
    public static final String TP_DEVICE_INFO_TEMPLATE = "tp_device_info_template";

    /**
     * 设备详细信息模板表
     **/
    public static final String TP_DEVICE_MODEL_TEMPLATE = "tp_device_model_template";

    /**
     * 图片信息管理表
     **/
    public static final String BPM_PIC_INFO_MAN = "bpm_pic_info_man";

    /**
     * 识别信息管理表
     **/
    public static final String CR_REPORT_MAN = "cr_report_man";

    /**
     * 宇森人脸注册版本号
     **/
    public static final String YS_VERSION_INFO = "ys_version_info";

}

