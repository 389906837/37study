package com.cloud.cang.act;

import java.util.List;

/**
 * 券服务定义
 *
 * @author zhouhong
 * @version 1.0
 */
public class ActivityServicesDefine {

    /**
     * 活动赠送服务
     *
     * @Param {@link com.cloud.cang.act.GiveActivityDto }
     * @return {@link com.cloud.cang.common.ResponseVo<GiveActivityResult>} T--> GiveActivityResult
     */
    public static final String ACTIVITY_GIVE_SERVICE = "com.cloud.cang.act.ws.GiveActivityService.give";

    /**
     * 查券服务
     *
     * @Param {@link com.cloud.cang.act.CouponQueryDto}
     * @return {@link com.cloud.cang.common.ResponseVo< List <CouponQueryResult>>} T--> List<CouponQueryResult>
     */
    public static final String QUERY_COUPON_SERVICE = "com.cloud.cang.act.ws.CouponService.queryCoupon";


    /**
     * 验券服务
     *
     * @Param {@link com.cloud.cang.act.CouponValidateDto}
     * @return {@link com.cloud.cang.common.ResponseVo<CouponValidateResult>} T--> CouponValidateResult
     */
    public static final String VALIDATE_COUPON_SERVICE = "com.cloud.cang.act.ws.CouponService.validateCoupon";

    /**
     * 用券服务
     *
     * @Param {@link com.cloud.cang.act.CouponUseDto}
     * @return {@link com.cloud.cang.common.ResponseVo<CouponValidateResult>} T--> CouponValidateResult
     */
    public static final String USE_COUPON_SERVICE = "com.cloud.cang.act.ws.CouponService.useCoupon";
    /**
     * 更新券服务
     *
     * @Param {@link com.cloud.cang.act.UpCouponDto}
     * @return {@link com.cloud.cang.common.ResponseVo}
     */
    public static final String UP_COUPON = "com.cloud.cang.act.ws.CouponService.upCoupon";

    /**
     * 优惠券批量下发
     *
     * @Param {@link com.cloud.cang.act.BatchCouponDto}
     * @return {@link com.cloud.cang.common.ResponseVo<String>} T--> String
     */
    public static final String BATCH_COUPON_SERVICE = "com.cloud.cang.act.ws.CouponService.batchCoupon";

    /**
     * 单张优惠券下发
     *
     * @Param {@link com.cloud.cang.act.SendSingleCouponDto}
     * @return {@link com.cloud.cang.common.ResponseVo<CouponQueryResult>} T--> CouponQueryResult
     */
    public static final String SEND_SINGLE_SERVICE = "com.cloud.cang.act.ws.CouponService.sendSingleCoupon";

    /**
     * 多张优惠券下发
     *
     * @Param {@link com.cloud.cang.act.SendSingleCouponDto}
     * @return {@link com.cloud.cang.common.ResponseVo<List<CouponQueryResult>>} T--> List<CouponQueryResult>
     */
    public static final String SEND_MUTIPLE_SERVICE = "com.cloud.cang.act.ws.CouponService.sendMutipleCoupon";

    /**
     * 券码兑换
     *
     * @Param {@link com.cloud.cang.act.ExchangeCouponDto}
     * @return {@link  com.cloud.cang.common.ResponseVo<CouponQueryResult>} T--> CouponQueryResult
     */
    public static final String EXCHANGE_COUPON_SERVICE = "com.cloud.cang.act.ws.CouponService.exchangeCoupon";

    /**
     * 活动赠送查询
     *
     * @Param {@link com.cloud.cang.act.GiveResultQueryDto}
     * @return {@link  com.cloud.cang.common.ResponseVo<GiveActivityResult>} T--> GiveActivityResult
     */
    public static final String QUERYACTIVEGIVERESULT_SERVICE = "com.cloud.cang.act.ws.GiveActivityService.queryActiveGiveResult";

    /**
     * 查看设备活动
     *
     * @Param {@link String deviceId}
     * @return {@link  com.cloud.cang.common.ResponseVo<List<String>>} T-->
     */
    public static final String QUERY_SDEVICE_ACTIVITY_SDESCRIPTION = "com.cloud.cang.act.ws.OrderActivityService.queryDeviceActivity";

    /**
     * 更新活动优惠记录
     *
     * @Param {@link OrderInvocationActivityDto}
     * @return {@link  com.cloud.cang.common.ResponseVo} T-->
     */
    public static final String UP_DISCOUNT_RECORD = "com.cloud.cang.act.ws.OrderActivityService.upDiscountRecord";


}
