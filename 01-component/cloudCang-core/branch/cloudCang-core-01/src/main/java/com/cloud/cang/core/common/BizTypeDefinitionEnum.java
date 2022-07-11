package com.cloud.cang.core.common;


import java.util.HashMap;
import java.util.Map;

/**
 * 对枚举数据的定义
 * 会员状态 :MemberStatus
 * 客户端类型：ClientType
 * 支付方式：PayWay
 * 来源业务：SourceBizStatus
 * 会员级别：MemberLevel
 * 会员类型：MemberType
 * 会员投资评级：MemberInvestLevel
 * 活动分类：ActivityType
 * 优惠类型（大类）：ActivityDiscountWay
 * 优惠类型（小类）：ActivityDiscountType
 * 应用范围类型：ActivityRangeType
 * 次数限制：ActivityCountType
 * 标准库存状态: StandarDstockIStatus
 */
public class BizTypeDefinitionEnum {

    public enum PayType implements EnumInterface {
        /*PAY_BALANCE(10, "账户余额"),
        PAY_BANKCARD(20, "银行卡"),*/
        PAY_WECHAT(30, "微信支付"),
        PAY_ALIPAY(40, "支付宝"),
        PAY_VENDSTOP(80, "vendstop"),
        /*PAY_UNIONPAY(50, "银联支付"),
        PAY_CASH(60, "现金支付"),
        PAY_THRID(70, "第三方支付"),
        PAY_OTHER(70, "其他支付")*/;
        private int code;
        private String desc;

        PayType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (PayType type : PayType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    public enum ChargebackWay implements EnumInterface {
        WITHHOLDING(10, "商户代扣"),
        ACTIVE_PAYMENT(20, "主动支付"),
        ONE_WITHHOLDING(30, "单次代扣"),
        WECHAT_PAY_POINT_WITHHOLDING(40, "微信支付分代扣");
        private int code;
        private String desc;

        ChargebackWay(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (ChargebackWay chargebackWay : ChargebackWay.values()) {
                if (chargebackWay.getCode() == code)
                    return chargebackWay.getDesc();
            }
            return "";
        }
    }

    public enum PayWay implements EnumInterface {
        PUBLIC_NUMBER(10, "公众号/生活号支付"),
        H5(20, "H5支付"),
        SWEEP_CODE(30, "扫码支付"),
        APP(40, "APP支付"),
        SWIPE_CARD(50, "刷卡支付"),
        SMALL_PROGRAM(60, "小程序支付"),
        WITHHOLDING(70, "代扣"),
        WECHAT_PAY_POINT_WITHHOLDING(80, "微信支付分代扣");
        private int code;
        private String desc;

        PayWay(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (PayWay type : PayWay.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /***
     * 来源客户类型
     */
    public enum ClientType implements EnumInterface {
        WECHAT(10, "微信支付"),
        ALIPAY(20, "支付宝"),
        ANDROID(30, "android"),
        IOS(40, "ios"),
        VENDSTOP(70, "vendstop"),
        KAZAYA(80, "kazaya");
        private int code;
        private String desc;

        ClientType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }

    /**
     * 客户端类型
     * 10 ：微信支付
     * 20 ：支付宝
     * 30：微信公众号
     * 40：支付宝生活号
     * 50 ：APP android
     * 60 : APP ios
     */
    public enum RegClientType implements EnumInterface {
        WECHAT(10, "微信支付"),
        ALIPAY(20, "支付宝"),
        WECHAT_NO_PUBLIC(30, "微信公众号"),
        ALIPAY_LIFE_NUMBER(40, "支付宝生活号"),
        ANDROID(50, "android"),
        IOS(60, "ios"),
        VENDSTOP(70,"vendstop"),
        KAYAZA(80,"kayaza");
        private int code;
        private String desc;

        RegClientType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }


    /**
     * 会员状态 1：正常 2：注销停用 3：系统黑名单 4：冻结
     */
    public enum MemberStatus implements EnumInterface {
        /**
         * 1：正常 2：注销停用 3：系统黑名单 4：冻结
         */
        NORMAL(1, "正常"),
        STOP(2, "注销停用"),
        BLACKLIST(3, "系统黑名单"),
        FREEZE(4, "冻结");
        private int code;
        private String desc;

        MemberStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }


    /**
     * 会员等级
     */
    public enum MemberLevel implements EnumInterface {
        /**
         * 10：大众会员
         * 20：黄金会员
         * 30：铂金会员
         * 40：钻石会员
         */
        PUBLIC(10, "大众会员"),
        GOLD(20, "黄金会员"),
        PLATINUM(30, "铂金会员"),
        DIAMOND(40, "钻石会员");
        private int code;
        private String desc;

        MemberLevel(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }
    }

    /**
     * 来源业务类型
     */
    public enum SourceBizStatus implements EnumInterface {
        PLATFORM_SEND(10, "平台赠送"),
        BATCH_COUPON_CODE(11, "批量下发券"),
        EXCHANGE_COUPON_CODE(12, "券码兑换"),
        REGISTER(20, "会员注册"),
        FIRST_OPEN_ALIPAY(30, "首次开通支付宝免密"),
        FIRST_OPEN_WECHAT(40, "首次开通微信免密"),
        RECOMMEND_REGISTER(50, "推荐注册"),
        RECOMMEND_ORDER(51, "推荐首单"),
        FIRST_ORDER(60, "首次下单"),
        ORDER(70, "下单"),
        SALESPROMOTION(80, "促销活动");


        private int code;
        private String desc;


        SourceBizStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (SourceBizStatus type : SourceBizStatus.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }


    /**
     * 会员类型
     *
     * @version 1.0
     */
    public enum MemberType implements EnumInterface {
        /*  会员类型:10:买家
            20:卖家
			30:买家卖家 */
        M1_MEMBER(10, "购物会员"),
        M2_MEMBER(20, "补货会员");
        private int code;
        private String desc;

        MemberType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (MemberType type : MemberType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }


    /**
     * 券类型
     *
     * @author zhouhong
     * @version 1.0
     */
    public enum CouponType implements EnumInterface {
        /**
         * 券类型编号
         * 10:现金券
         * 20:满减券
         * 30:抵扣券
         * 40:商品券
         */
        MONEY_COUPON(10, "现金券"),
        REBATE_COUPON(20, "满减券"),
        FULL_CUT_COUPON(30, "抵扣券"),
        COMMODITY(40, "商品券");

        private int code;
        private String desc;

        CouponType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (CouponType type : CouponType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }


    /**
     * 券状态
     *
     * @author zhouhong
     * @version 1.0
     */
    public enum CouponStatus implements EnumInterface {
        /**
         * 1=未使用2=已使用3=冻结（中间状态）4=已失效5=删除6=锁定
         */
        UNUSED(1, "未使用"),
        USED(2, "已使用"),
        FREEZE(3, "冻结"),
        EXPIRED(4, "已失效"),
        DELETE(5, "删除"),
        LOCK(6, "锁定");

        private int code;
        private String desc;

        CouponStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }
    }

    /**
     * 活动分类
     *
     * @author zengzexiong
     * @version 1.0
     */
    public enum ActivityType implements EnumInterface {
        /**
         * 10:场景活动 20：促销活动
         */
        SCENES_ACTIVITY(10, "场景活动"),
        PROMOTIONS_ACTIVITY(20, "促销活动");

        private int code;
        private String desc;

        ActivityType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (ActivityType type : ActivityType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /**
     * 优惠方式(优惠大类)
     *
     * @author zengzexiong
     * @version 1.0
     */
    public enum ActivityDiscountWay implements EnumInterface {
        /**
         * 10:首单 20：打折 30：满减 40：返券 50：返现
         */
        USER_FIRSTORDER(10, "首单"),
        DISCOUT(20, "打折"),
        FULLREDUCE(30, "满减")/*,
        REBATES(40, "返券"),
        CASHBACK(50, "返现")*/;

        private int code;
        private String desc;

        ActivityDiscountWay(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (ActivityDiscountWay type : ActivityDiscountWay.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /**
     * 优惠类型（优惠小类）
     *
     * @author zengzexiong
     * @version 1.0
     */
    public enum ActivityDiscountType implements EnumInterface {
        /**
         * 10:首单
         * 20：打折满X元 30：打折满X件 40：打折满X元且满X件
         * 50：满减满X元 60：满减每满X元 70：满减满X件 80：满减满X元且满Y件
         * 90：返券 100：返现
         */
        USER_FIRSTORDER(10, "首单"),
        DISCOUNT_MONEY(20, "打折满X元"),
        DISCOUNT_COUNT(30, "打折满X件"),
        DISCOUNT_MONTY_COUNT(40, "打折满X元且满X件"),
        FULLREDUCE_MONEY(50, "满减满X元"),
        FULLREDUCE_EVERY_MONEY(60, "满减每满X元"),
        FULLREDUCE_COUNT(70, "满减满X件"),
        FULLREDUCE_MONEY_COUNT(80, "满减满X元且满Y件")/*,
        REBATES(90, "返券"),
        CASH_BACK(100, "返现")*/;

        private int code;
        private String desc;

        ActivityDiscountType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (ActivityDiscountType type : ActivityDiscountType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /**
     * 应用范围类型
     *
     * @author zengzexiong
     * @version 1.0
     */
    public enum ActivityRangeType implements EnumInterface {
        /**
         * 10:全部 20：部分设备 30：部分商品 40：部分设备的部分商品
         */
        ALL(10, "全部"),
        SOME_EQUIPMENT(20, "部分设备"),
        SOME_COMMODITY(30, "部分商品"),
        SOMEE_QUIPMENT_COMMODITY(40, "部分设备的部分商品");

        private int code;
        private String desc;

        ActivityRangeType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (ActivityRangeType type : ActivityRangeType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /**
     * 次数限制
     *
     * @author zengzexiong
     * @version 1.0
     */
    public enum ActivityCountType implements EnumInterface {
        /**
         * 10:活动期间用户 20：活动期间设备
         */
        DURING_ACTIVITY(10, "活动期间用户"),
        DURING_ACTIVITY_EQUIPMENT(20, "活动期间设备");

        private int code;
        private String desc;

        ActivityCountType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (ActivityCountType type : ActivityCountType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    public enum IIsOrder implements EnumInterface {
        /**
         * 用户是否使用首单，0未使用，1已使用
         */
        NO_ISORDER(0, "未使用首单"),
        YES_ISORDER(1, "已使用首单");
        private int code;
        private String desc;

        IIsOrder(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (IIsOrder type : IIsOrder.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /**
     * 商户类型
     * 10:个人
     * 20:企业
     */
    public enum MerchantType implements EnumInterface {

        PERSONAL(10, "个人"),
        ENTERPRISE(20, "企业");
        private int code;
        private String desc;

        MerchantType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (MerchantType type : MerchantType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }

        public static Map<Integer, String> getValue() {
            Map<Integer, String> map = new HashMap<>();
            for (MerchantType merchantType : MerchantType.values()) {
                map.put(merchantType.getCode(), merchantType.getDesc());
            }
            return map;
        }
    }

    /**
     * 商户公司类型
     * 10:国企
     * 20:民营
     */
    public enum IcompanyType implements EnumInterface {
        STATEOWNEDENTERPRISE(10, "国企"),
        PRIVATELYOPERATED(20, "民营");
        private int code;
        private String desc;

        IcompanyType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (IcompanyType type : IcompanyType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }

        public static Map<Integer, String> getValue() {
            Map<Integer, String> map = new HashMap<>();
            for (IcompanyType icompanyType : IcompanyType.values()) {
                map.put(icompanyType.getCode(), icompanyType.getDesc());
            }
            return map;
        }
    }

    /**
     * 商户公司类型
     * 状态：
     * 10=洽谈中
     * 20=已签约
     * 30=已解约
     * 40=合约到期
     */
    public enum IcompanyStatus implements EnumInterface {
        NEGOTIATION(10, "洽谈中"),
        ALREADYSIGNED(20, "已签约"),
        CANCELLED(30, "已解约"),
        CONTRACTEXPIRATION(40, "合约到期");
        private int code;
        private String desc;

        IcompanyStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (IcompanyStatus icompanyStatus : IcompanyStatus.values()) {
                if (icompanyStatus.getCode() == code)
                    return icompanyStatus.getDesc();
            }
            return "";
        }

        public static Map<Integer, String> getValue() {
            Map<Integer, String> map = new HashMap<>();
            for (IcompanyStatus icompanyStatus : IcompanyStatus.values()) {
                map.put(icompanyStatus.getCode(), icompanyStatus.getDesc());
            }
            return map;
        }
    }

    /**
     * 类型
     * 状态：
     * 1 web  2 wap 3 app 4 移动端
     */
    public enum RemindMeeType implements EnumInterface {
        WEB(1, "web"),
        WAP(2, "wap"),
        APP(3, "app"),
        MOBILETERMINAL(4, "移动端");
        private int code;
        private String desc;

        RemindMeeType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (RemindMeeType remindMeeType : RemindMeeType.values()) {
                if (remindMeeType.getCode() == code)
                    return remindMeeType.getDesc();
            }
            return "";
        }

        public static Map<Integer, String> getValue() {
            Map<Integer, String> map = new HashMap<>();
            for (RemindMeeType remindMeeType : RemindMeeType.values()) {
                map.put(remindMeeType.getCode(), remindMeeType.getDesc());
            }
            return map;
        }
    }

    /**
     * 域名状态
     */
    public enum DomainStatus implements EnumInterface {
        APPLY(10, "申请中"),
        AUDIT_SUCCESS(20, "审核通过"),
        AUDIT_FAILURE(30, "审核驳回"),
        DISABLED(40, "停用");
        private int code;
        private String desc;

        DomainStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (DomainStatus domainStatus : DomainStatus.values()) {
                if (domainStatus.getCode() == code)
                    return domainStatus.getDesc();
            }
            return "";
        }
    }

    /**
     * 设备状态
     */
    public enum DeviceStatus implements EnumInterface {
        NO_REGISTER(10, "未注册"),
        NORMAL(20, "正常"),
        MALFUNCTION(30, "故障"),
        SCRAPPED(40, "报废"),
        OFFLINE(50, "离线");
        private int code;
        private String desc;

        DeviceStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (DeviceStatus deviceStatus : DeviceStatus.values()) {
                if (deviceStatus.getCode() == code)
                    return deviceStatus.getDesc();
            }
            return "";
        }
    }

    /**
     * 设备状态
     */
    public enum DeviceType implements EnumInterface {
        TRADITION(10, "传统"),
        RFID(20, "RFID射频"),
        VISION(30, "视觉"),
        QD_ZL_VISION(40, "前端视觉+重力 "),
        CLOUD_VISION(50, "云端识别 "),
        CLOUD_ZL_VISION(60, "云端识别+重力 ");
        private int code;
        private String desc;

        DeviceType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (DeviceType deviceType : DeviceType.values()) {
                if (deviceType.getCode() == code)
                    return deviceType.getDesc();
            }
            return "";
        }
    }

    /**
     * 运营状态
     */
    public enum OperateStatus implements EnumInterface {
        ENABLED(10, "启用"),
        DISABLED(20, "停用");
        private int code;
        private String desc;

        OperateStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (OperateStatus operateStatus : OperateStatus.values()) {
                if (operateStatus.getCode() == code)
                    return operateStatus.getDesc();
            }
            return "";
        }
    }

    /***
     * 活动应用范围
     */
    public enum RangeType implements EnumInterface {

        ALL(10, "全部"),
        SECTION_DEVICE(20, "部分设备"),
        SECTION_COMMODITY(30, "部分商品"),
        SECTION_DEVICE_COMMODITY(40, "部分设备部分商品");
        private int code;
        private String desc;

        RangeType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (MerchantType type : MerchantType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }

        public static Map<Integer, String> getValue() {
            Map<Integer, String> map = new HashMap<>();
            for (RangeType rangeType : RangeType.values()) {
                map.put(rangeType.getCode(), rangeType.getDesc());
            }
            return map;
        }
    }

    /***
     * 商户管理类型
     */
    public enum ImerType implements EnumInterface {
        ALL(0, "全部"),
        CORRESPONDING_BD(1, "对应bd管理商户"),
        ONSELF(2, "自己商户"),
        DESIGNATED(3, "指定商户"),
        CORRESPONDING_BD_BELOW(4, "对应bd管理商户及以下");
        private int code;
        private String desc;

        ImerType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (ImerType type : ImerType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }

        public static Map<Integer, String> getValue() {
            Map<Integer, String> map = new HashMap<>();
            for (ImerType imerType : ImerType.values()) {
                map.put(imerType.getCode(), imerType.getDesc());
            }
            return map;
        }
    }


    /***
     * 订单状态
     */
    public enum OrderStatus implements EnumInterface {
        PENDING_PAYMENT(100, "待付款"),
        IN_PAYMENT(110, "付款处理中"),
        PAYMENT_SUCCESS(10, "付款成功"),
        PAYMENT_FAIL(20, "付款失败")/*,
        ABNORMAL(30, "订单异常"),
        ABNORMAL_SUCCESS(40, "异常处理成功"),
        ABNORMAL_IGNORE(50, "异常处理忽略")*/;
        private int code;
        private String desc;

        OrderStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (OrderStatus type : OrderStatus.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }


    /**
     * 标准库存
     */
    public enum StockIstatus implements EnumInterface {
        ENABLE_ISTATUS(10, "启用"),
        DISABLE_ISTATUS(20, "禁用");

        private int code;
        private String desc;

        StockIstatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (StockIstatus stockIstatus : StockIstatus.values()) {
                if (stockIstatus.getCode() == code)
                    return stockIstatus.getDesc();
            }
            return "";
        }
    }


    /***
     * 广告状态
     */
    public enum AdvertisStatus implements EnumInterface {
        INVALID(0, "已过期"),
        USING(1, "投放中"),
        WAIT(2, "待投放"),
        PAUSE(3, "暂停");

        private int code;
        private String desc;

        AdvertisStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (AdvertisStatus type : AdvertisStatus.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /***
     * 系统公告状态
     */
    public enum AnnouncementStatus implements EnumInterface {
        WAIT(10, "待发布"),
        USING(20, "已发布"),
        INVALID(30, "已过期");

        private int code;
        private String desc;

        AnnouncementStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (AnnouncementStatus type : AnnouncementStatus.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /***
     * 接口收费方式
     */
    public enum InterfaceTollWay implements EnumInterface {
        FREE_NO_OPEN(10, "免费无需开通"),
        FREE_NEED_OPEN(20, "免费需开通"),
        TOLL(30, "收费");

        private int code;
        private String desc;

        InterfaceTollWay(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (InterfaceTollWay type : InterfaceTollWay.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /***
     * 接口收费类型
     */
    public enum InterfaceTollType implements EnumInterface {

        NUM_TOLL_TYPE(10, "按次收费"),
        IMG_NUM_TOLL_TYPE(20, "按单位次数计费"),
        TIME_TOLL_TYPE(30, "按时间计费"),
        ALL_NUM_TOLL_TYPE(40, "通用时间按次收费"),
        ALL_TOLL_TYPE(50, "通用时间按单位次数计费");

        private int code;
        private String desc;

        InterfaceTollType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (InterfaceTollType type : InterfaceTollType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }


    /***
     * 接口类型
     */
    public enum InterfacType implements EnumInterface {

        SYNCHRONIZE(10, "同步"),
        SYNCHRONIZE_CALLBACK(20, "同步有回调"),
        ASYNCHRONOUS(30, "异步");
        private int code;
        private String desc;

        InterfacType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (InterfacType type : InterfacType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /***
     * 接口收费类型
     */
    public enum ValidityType implements EnumInterface {

        FIXED_DATE(10, "固定日期"),
        LONG_TERM_EFFECTIVE(20, "长期有效");

        private int code;
        private String desc;

        ValidityType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (ValidityType type : ValidityType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

    /**
     * 设备状态
     */
    public enum DeviceOperateStatus implements EnumInterface {
        ENABLE(10, "启用"),
        DISABLE(20, "停用");
        private int code;
        private String desc;

        DeviceOperateStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (DeviceOperateStatus type : DeviceOperateStatus.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }

}