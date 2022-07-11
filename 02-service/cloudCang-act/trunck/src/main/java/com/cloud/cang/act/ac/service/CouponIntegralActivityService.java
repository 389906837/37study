package com.cloud.cang.act.ac.service;

import com.cloud.cang.act.*;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.jm.ChangeIntegralDto;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.model.ac.CouponUserSend;

import java.util.List;


/**
 * 活动引擎
 *
 * @author Hunter
 * @version 1.0
 */
public interface CouponIntegralActivityService {


    /**
     * 用券
     *
     * @param couponUseDto
     * @param couponValidateResult
     * @return
     */
    ResponseVo<CouponValidateResult> useCoupon(CouponUseDto couponUseDto,
                                               CouponValidateResult couponValidateResult);

    /**
     * 更新券
     *
     * @param upCouponDto
     * @return
     */
    ResponseVo upCoupon(UpCouponDto upCouponDto);


    /**
     * 处理发券
     *
     * @param activityConf
     * @param giveActivityDto
     * @return
     */
    ResponseVo<List<CouponUser>> handleCoupon(ActivityConf activityConf, GiveActivityDto giveActivityDto);

    /**
     * 处理积分活动
     *
     * @param activityConf
     * @param giveActivityDto
     * @return
     * @throws Exception
     */
    ResponseVo<ChangeIntegralDto> handleIntegral(ActivityConf activityConf, GiveActivityDto giveActivityDto, String scouponId) throws Exception;

    /**
     * 优惠券下发
     *
     * @param sendSingleCouponDto
     * @return
     */
    ResponseVo<CouponQueryResult> sendSingleCoupon(SendSingleCouponDto sendSingleCouponDto);

    /**
     * 多张优惠券下发
     *
     * @param sendSingleCouponDto
     * @return
     */
    ResponseVo<List<CouponQueryResult>> sendMutipleCoupon(SendSingleCouponDto sendSingleCouponDto);

    /**
     * 优惠券批量下发
     *
     * @param saddUser
     * @param couponBatch
     * @param userSendList
     */
    void batchCoupon(String saddUser,CouponBatch couponBatch, List<CouponUserSend> userSendList);

    /**
     * 券码兑换
     *
     * @param exchangeCouponDto
     * @return
     */
    ResponseVo<CouponQueryResult> exchangeCoupon(ExchangeCouponDto exchangeCouponDto);
}
