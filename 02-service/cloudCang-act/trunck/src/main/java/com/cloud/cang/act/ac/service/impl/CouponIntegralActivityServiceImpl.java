package com.cloud.cang.act.ac.service.impl;

import com.cloud.cang.act.*;
import com.cloud.cang.act.ac.dao.ActivityConfDao;
import com.cloud.cang.act.ac.ext.OrderActivityServiceProxy;
import com.cloud.cang.act.ac.service.*;
import com.cloud.cang.act.common.ActivityConstants;
import com.cloud.cang.act.hy.service.MemberInfoService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.jf.IntegralAccountServicesDefine;
import com.cloud.cang.jm.ChangeIntegralDto;
import com.cloud.cang.jy.CommodityDiscountDto;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.ac.*;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.utils.DateUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 活动引擎
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service
public class CouponIntegralActivityServiceImpl implements
        CouponIntegralActivityService {

    private static final Logger logger = LoggerFactory
            .getLogger(CouponIntegralActivityServiceImpl.class);


    @Autowired
    private CouponUserService couponUserService;
    @Autowired
    private CouponRuleService couponRuleService;
    @Autowired
    private IntegralRuleService integralRuleService;
    @Autowired
    private FirstRewardIntegralService firstRewardIntegralService;
    @Autowired
    private CouponBatchService couponBatchService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private CouponCodeExDetailsService couponCodeExDetailsService;
    @Autowired
    private OrderActivityServiceProxy orderActivityServiceProxy;
    @Autowired
    private DiscountDetailService discountDetailService;
    @Autowired
    private ActivityConfDao activityConfDao;
    @Autowired
    private UseRangeService useRangeService;
    @Autowired
    private DiscountRecordService discountRecordService;

    /**
     * 可用
     */
    public static final Integer DELFLAG_ENABLE = 0;

    /**
     * 已启用
     */
    public static final Integer AVAILABLE_YES = 1;


    /**
     * 使用券
     *
     * @param couponUseDto couponValidateResult
     * @return ResponseVo<CouponValidateResult>
     */
    @Override
    public ResponseVo<CouponValidateResult> useCoupon(CouponUseDto couponUseDto,
                                                      CouponValidateResult couponValidateResult) {

        Map<String, Object> updateMap = new HashMap<String, Object>();
        updateMap.put("factualExchangeAmount", couponValidateResult.getFdeductibleAmount()); // 实际兑换金额
        //如果拆单编号不为空,则使用订单编号为拆单编号,使用订单Id为空
        if (StringUtils.isNotBlank(couponUseDto.getSdismantlingCode())) {
            updateMap.put("suseTargetCode", couponUseDto.getSdismantlingCode());// 使用订单编号
        } else {
            updateMap.put("suseTargetId", couponUseDto.getTargetId());// 使用订单id
            updateMap.put("suseTargetCode", couponUseDto.getTargetCode());// 使用订单编号
        }
        updateMap.put("suseTargetInstruction", couponUseDto.getTargetInstruction());// 使用说明
        //券面值
        BigDecimal couponValue = couponValidateResult.getCouponValue();
        //订单金额
        BigDecimal orderValue = couponValidateResult.getOrderAmount();
        //非现金券
        updateMap.put("fcouponValue", couponValidateResult.getCouponValue());
        BigDecimal value = BigDecimal.ZERO;
        if (ActivityConstants.ICOUPON_TYPE_XIANJIN.equals(couponValidateResult.getCouponType())) {
            int a = couponValue.compareTo(orderValue);

            if (a > 0) {//现金券未使用完
                updateMap.put("istatus", ActivityConstants.COUPON_STATUS_NOTUSE);
                updateMap.put("fcouponValue", couponValue.subtract(orderValue));//券面值
            } else {//现金券面值清零
                updateMap.put("istatus", ActivityConstants.COUPON_STATUS_USE);
                updateMap.put("fcouponValue", value);//券面值
            }
        } else {
            updateMap.put("istatus", ActivityConstants.COUPON_STATUS_USE);
           /* updateMap.put("fcouponValue", value);*///券面值
        }

        updateMap.put("id", couponUseDto.getCouponUserId());
        int count = couponUserService.updateCouponUserByMap(updateMap);
        if (count != 1) {
            logger.debug("券状态不正确！");
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券状态不正确");
        }
        //非现金券用券成功


        return ResponseVo.getSuccessResponse(couponValidateResult);
    }

    /**
     * 更新券
     *
     * @param upCouponDto
     */
    @Override
    public ResponseVo upCoupon(UpCouponDto upCouponDto) {
        CouponUser couponUser = couponUserService.selectByPrimaryKey(upCouponDto.getCouponUserId());
        BigDecimal couponValue = couponUser.getFcouponValue();//券面值
        BigDecimal couponDeductionAmount = upCouponDto.getCouponDeductionAmount();//优惠券抵扣金额
        //如果优惠券是现金券
        if (ActivityConstants.ICOUPON_TYPE_XIANJIN.equals(couponUser.getIcouponType())) {
            int a = couponValue.compareTo(couponDeductionAmount);
            if (a > 0) {//现金券未使用完
                couponUser.setIstatus(ActivityConstants.COUPON_STATUS_NOTUSE);
            } else {//现金券面值清零
                couponUser.setIstatus(ActivityConstants.COUPON_STATUS_USE);
            }
        } else {
            couponUser.setIstatus(ActivityConstants.COUPON_STATUS_USE);
        }
        couponUser.setSuseTargetId(upCouponDto.getTargetId());// 使用订单id
        couponUser.setSuseTargetCode(upCouponDto.getTargetCode());// 使用订单编号(拆单订单为拆单编号)
        couponUser.setSuseTargetInstruction(upCouponDto.getTargetInstruction());// 使用说明
        couponUser.setTactualUseDatetime(DateUtils.getCurrentDateTime());//实际使用时间
        couponUser.setFactualExchangeAmount(couponDeductionAmount);//实际使用金额
        couponUserService.updateBySelective(couponUser);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 用券成功后执行
     *
     * @param couponUseDto
     */
    private int useCouponSuc(CouponUseDto couponUseDto) {
        // 调用转账成功后
        CouponUser model = new CouponUser();
        model.setId(couponUseDto.getCouponUserId());// 券ID
        model.setTactualUseDatetime(new Date());// 实际使用时间
        model.setIstatus(ActivityConstants.COUPON_STATUS_USE);
        model.setTupdateTime(new Date());
        return couponUserService.updateBySelective(model);
    }

    /**
     * 处理券活动(活动赠送服务)
     *
     * @param activityConf
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public ResponseVo<List<CouponUser>> handleCoupon(ActivityConf activityConf, GiveActivityDto giveActivityDto) {
        logger.debug("处理券活动开始...");
        // 1.是否满足发券条件
        ResponseVo preResult = preHandleCoupon(activityConf, giveActivityDto);
        if (!preResult.isSuccess()) {
            logger.debug("不满足发券条件。{}", preResult.getMsg());
            return preResult;
        }

        // 2.生成券持有记录
        CouponRule couponRule = (CouponRule) preResult.getData();
        Integer couponCount = couponRule.getIsendNumber();// 送券数量
        List<CouponUser> couponList = new ArrayList<CouponUser>();

        for (int i = 0; i < couponCount; i++) {
            // 生成持有券信息
            CouponUser couponUser = new CouponUser();
            couponUser.setScouponCode(CoreUtils
                    .newCode(EntityTables.AC_COUPON_USER));// 券编号
            couponUser.setSbriefDesc(couponRule.getSbriefDesc());//券简述
            couponUser.setIcouponType(couponRule.getIcouponType());// 券类型
            couponUser.setScouponInstruction(couponRule
                    .getSactivityInstruction());// 券说明
            couponUser.setDcouponEffectiveDate(couponRule
                    .getDcouponEffectiveDate());// 券生效日期
            couponUser.setDcouponExpiryDate(couponRule
                    .getDcouponExpiryDate());// 券失效日期
            couponUser.setIstatus(ActivityConstants.COUPON_STATUS_NOTUSE);// 状态：未使用
            couponUser.setScouponValidityValue(couponRule.getIcouponValidityValue());//券有效期数值
            couponUser.setSmemberId(giveActivityDto.getMemberId());// 用户ID
            couponUser.setSmemberCode(giveActivityDto.getMemberCode());// 用户编号
            couponUser.setSmemberName(giveActivityDto.getMemberRealName());// 会员名
            couponUser.setIsourceType(giveActivityDto.getSsourceType());// 来源单据类型
            couponUser.setSsourceAcCode(activityConf.getSconfCode());//来源活动编号
            couponUser.setSsourceAcName(activityConf.getSconfName());//来源活动名称
            couponUser.setSsourceId(giveActivityDto.getSsourceId());// 来源单据id
            couponUser.setSsourceCode(giveActivityDto.getSsourceCode());// 来源单据编号
            couponUser.setSmerchantId(giveActivityDto.getSmerchantId());//商户id
            couponUser.setSmerchantCode(giveActivityDto.getSmerchantCode());//商户CODE
            //来源说明
            if (StringUtils.isBlank(giveActivityDto.getSsourceInstruction())) {
                couponUser.setSsourceInstruction(activityConf.getSconfName());// 来源说明：改成活动配置名称
            } else {
                couponUser.setSsourceInstruction(giveActivityDto
                        .getSsourceInstruction());// 来源说明
            }
            //推荐获得的券要获得被推荐人ID
            if (BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode() == giveActivityDto.getSsourceType()
              /*      || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST.getCode() == giveActivityDto.getSsourceType()
                    || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST_AGAIN.getCode() == giveActivityDto.getSsourceType()
                    || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_OPEN_ACCOUNT.getCode() == giveActivityDto.getSsourceType()*/) {
                couponUser.setSinvestmentId(giveActivityDto.getRecommendParam().getRecUserId()); //推荐人ID
            }
            couponUser.setTgetDatetime(DateUtils.getCurrentDateTime());// 获券时间
            couponUser.setTaddTime(DateUtils.getCurrentDateTime());//添加日期
            couponUser.setTupdateTime(DateUtils.getCurrentDateTime());//修改日期
            if (BizTypeDefinitionEnum.CouponType.COMMODITY.getCode() != couponUser.getIcouponType()) {
               /* 10:比例
                20:固定额度*/
                Integer igiveMethod = couponRule.getIgiveMethod();
                if (null != igiveMethod && 10 == couponRule.getIgiveMethod()) {
                    BigDecimal factualPayAmount = giveActivityDto.getOrderParam().getFactualPayAmount();//订单实付金额
                    BigDecimal temp = couponRule.getFcouponValue().divide(new BigDecimal("100"));
                    BigDecimal fcouponValue = factualPayAmount.multiply(temp);
                    BigDecimal newNumber = fcouponValue.setScale(1, BigDecimal.ROUND_UP);
                    //添加券面值
                    //1.保留一位小数,向上取整
                    //2.小于最小券面值,取最小券面值为券面值
                    //3.大于最大券面值,取最大券面值为券面值
                    if (newNumber.compareTo(couponRule.getFminValue()) < 0) {
                        couponUser.setFcouponValue(couponRule.getFminValue());
                    } else if (newNumber.compareTo(couponRule.getFmaxValue()) > 0) {
                        couponUser.setFcouponValue(couponRule.getFmaxValue());
                    } else {
                        couponUser.setFcouponValue(newNumber);
                    }
                } else if (null != igiveMethod && 20 == couponRule.getIgiveMethod()) {
                    couponUser.setFcouponValue(couponRule.getFcouponValue());//添加券面值
                }
            }
            couponUser.setFuseLimitAmount(couponRule.getFuseLimitAmount());// 订单使用实付金额限制
            couponUser.setSuseLimitBrand(couponRule.getSuseLimitBrand());// 订单使用商品品牌限制
            couponUser.setSuseLimitCategory(couponRule.getSuseLimitCategory());// 订单使用商品分类限制
            couponUser.setSuseLimitDevice(couponRule.getSuseLimitDevice());// 订单使用设备限制
            couponUser.setSuseLimitCommodity(couponRule.getSuseLimitCommodity());// 订单使用商品限制
            if (BizTypeDefinitionEnum.CouponType.COMMODITY.getCode() == couponRule.getIcouponType()) {
                couponUser.setSuseLimitCommodity(couponRule.getScommodityCode());// 订单使用商品限制
            }
            insertCouponUser(couponUser);
            if (couponUser != null) {
                couponList.add(couponUser);
                //调用活动优惠记录服务
                OrderInvocationActivityDto orderInvocationActivityDto = new OrderInvocationActivityDto();
                orderInvocationActivityDto.setSmemberId(giveActivityDto.getMemberId());//用户ID
                orderInvocationActivityDto.setSmemberCode(giveActivityDto.getMemberCode());//用户Code
                orderInvocationActivityDto.setSmemberName(giveActivityDto.getMemberRealName());//用户名
                orderInvocationActivityDto.setSmerchantId(activityConf.getSmerchantId());//商户ID
                orderInvocationActivityDto.setSmerchantCode(activityConf.getSmerchantCode());//商户Code
                orderInvocationActivityDto.setSacId(activityConf.getId());//活动Id
                orderInvocationActivityDto.setIsourceType(giveActivityDto.getSsourceType());//来源单据类型
                orderInvocationActivityDto.setSsourceId(couponUser.getId());//来源单据Id
                orderInvocationActivityDto.setSsourceCode(couponUser.getScouponCode());//来源单据Code
                orderInvocationActivityDto.setScouponId(couponUser.getId());   //券使用ID
                orderInvocationActivityDto.setScouponCode(couponUser.getScouponCode());// 券使用编号
                orderInvocationActivityDto.setSsourceDeviceId(giveActivityDto.getSdeviceId());//来源设备Id
                orderInvocationActivityDto.setSsourceDeviceCode(giveActivityDto.getSdeviceCode());//来源设备Code
                orderInvocationActivityDto.setSsourceDeviceAddress(giveActivityDto.getSdeviceAddress());//来源设备地址
               /* 10:批量下发券 - 平台赠送
                20:活动赠送 - 券服务
                30:促销活动 - 下单*/
                orderInvocationActivityDto.setUpType(20);
                logger.info("#######################下订单活动赠送,开始调用优惠记录服务#######################:{}", orderInvocationActivityDto);
                ResponseVo responseVo = orderActivityServiceProxy.upDiscountRecord(orderInvocationActivityDto);
                if (responseVo != null && responseVo.isSuccess()) {
                    logger.info("活动赠送,调用优惠记录服务成功！");
                }
            }
        }
        // 3.生成首次记录
       /* insertFirstReward(giveActivityDto, ActivityConstants.TYPE_COUPON,
                couponRule.getId());*/
        logger.debug("处理券活动结束...");
        logger.debug("生成" + couponList.size() + "张券成功！");
        return ResponseVo.getSuccessResponse(couponList);
    }

    /**
     * 保存用户持有券
     *
     * @param couponUser
     * @return
     */
    private CouponUser insertCouponUser(
            CouponUser couponUser) {
        if (BizTypeDefinitionEnum.CouponType.COMMODITY.getCode() != couponUser.getIcouponType()) {
            if (null != couponUser.getFcouponValue() && couponUser.getFcouponValue().compareTo(BigDecimal.ZERO) != 0) {
                couponUserService.insert(couponUser);
                return couponUser;
            } else {
                logger.warn("【注意】生成的券面值为0！不生成券。会员编号：{}",
                        couponUser.getSmemberCode());
            }
        } else {
            couponUserService.insert(couponUser);
            return couponUser;
        }
        return null;
    }

    /**
     * 生成首次记录
     *
     * @param type   1=积分,2=劵
     * @param ruleId 活动规则ID
     */
    private void insertFirstReward(GiveActivityDto giveActivityDto,
                                   Integer type, String ruleId) {
        logger.debug("处理生成首次记录开始...1积分2券：" + type);
        // 生成首次记录
        FirstRewardIntegral firstRewardIntegralCondition = new FirstRewardIntegral();
        firstRewardIntegralCondition.setSmemberId(giveActivityDto.getMemberId());
        firstRewardIntegralCondition.setSruleId(ruleId);
        firstRewardIntegralCondition.setIruleType(type);
        List<FirstRewardIntegral> firstRewardIntegralList = firstRewardIntegralService.selectByEntityWhere(firstRewardIntegralCondition);
        if (firstRewardIntegralList == null || firstRewardIntegralList.size() == 0) {
            FirstRewardIntegral firstReward = new FirstRewardIntegral();
            firstReward.setSmemberId(giveActivityDto.getMemberId());// 用户ID
            firstReward.setSmemberCode(giveActivityDto.getMemberCode());// 用户编号
            firstReward.setSmemberName(giveActivityDto.getMemberRealName());// 用户姓名
            firstReward.setSruleId(ruleId);// 活动规则ID
            firstReward.setIruleType(type);// 类型
            firstReward.setIisUse(0);// 未使用
            firstReward.setSintegralRuleCode(giveActivityDto.getActiveConfCode());
            firstReward.setTaddTime(new Date());
            firstRewardIntegralService.insert(firstReward);
            logger.debug("生成首次记录成功！");
        }
        logger.debug("处理生成首次记录结束。");
    }

    /**
     * 券活动预处理
     *
     * @param activityConf
     * @return
     */
    @SuppressWarnings("unchecked")
    private ResponseVo<CouponRule> preHandleCoupon(ActivityConf activityConf,
                                                   GiveActivityDto giveActivityDto) {
        CouponRule couponRule = couponRuleService
                .selectCouponRuleByActivityId(activityConf.getId());
        if (couponRule == null) {
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                    .getResponseVo("没有有效的券活动！");
        }

        // a.规则活动开始结束日期
        Date todayDate = DateUtils.getCurrentDateTime();

        if (todayDate.getTime() < activityConf.getTactiveStartTime().getTime()
                || todayDate.getTime() > activityConf.getTactiveEndTime().getTime()) {
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                    .getResponseVo("券活动已经失效！");
        }

        // b.券是否在有效期
        if (null != couponRule.getDcouponExpiryDate() && couponRule.getDcouponExpiryDate().getTime() < todayDate.getTime()) {
            return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL.getResponseVo("活动赠送券已过期，不发券！");
        }

        //应用范围类型限制
       /* 10:场景活动
        20:促销活动*/
        UseRange useRange = useRangeService.selectByAcId(activityConf.getId());
        if (null == useRange) {
            return ErrorCodeEnum.ERROR_ACT_NOT_USERANGE.getResponseVo("活动范围明细不存在！");
        }
        if (10 == activityConf.getItype()) {
            //如果活动为会员注册,设备编号为空,并且活动范围不为全部则参与不了活动
            if (BizTypeDefinitionEnum.SourceBizStatus.REGISTER.getCode() == activityConf.getIscenesType()) {
                if (null == giveActivityDto.getSdeviceCode() && 10 != useRange.getIrangeType()) {
                    return ErrorCodeEnum.ERROR_ACT_NOT_USERANGE
                            .getResponseVo("场景活动,用户注册注册地址为空！");
                }
            } else {
                if (20 == useRange.getIrangeType() &&
                        !Arrays.asList(useRange.getSdeviceCode().split(",")).contains(giveActivityDto.getSdeviceCode())) {
                    return ErrorCodeEnum.ERROR_ACT_NOT_USERANGE
                            .getResponseVo("场景活动,活动设备范围不满足条件！");
                }
            }
        } else if (20 == activityConf.getItype()) {
            /*10:全部
            20:部分设备
            30:部分商品
            40:部分设备部分商品*/
            if (20 == activityConf.getIrangeType() && !Arrays.asList(useRange.getSdeviceCode().split(",")).contains(giveActivityDto.getSdeviceCode())) {
                return ErrorCodeEnum.ERROR_ACT_NOT_USERANGE
                        .getResponseVo("促销活动,活动设备范围不满足条件！");
            } else if (30 == activityConf.getIrangeType()) {
                ResponseVo responseVo = checkCommodity(giveActivityDto, useRange);
                if (!responseVo.isSuccess()) {
                    return responseVo;
                }
            } else if (40 == activityConf.getIrangeType()) {
                if (!Arrays.asList(useRange.getSdeviceCode().split(",")).contains(giveActivityDto.getSdeviceCode())) {
                    return ErrorCodeEnum.ERROR_ACT_NOT_USERANGE
                            .getResponseVo("促销活动,活动设备范围不满足条件！");
                }
                ResponseVo responseVo = checkCommodity(giveActivityDto, useRange);
                if (!responseVo.isSuccess()) {
                    return responseVo;
                }
            }
        }
        //限制参与次数条件
        if (activityConf.getIallCount() != 0 && activityConf.getIjoinNum().compareTo(activityConf.getIallCount()) >= 0) {
            return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                    .getResponseVo("活动参与人数已满！");
        }
        if (10 == activityConf.getItype() && (activityConf.getIscenesType() == BizTypeDefinitionEnum.SourceBizStatus.REGISTER.getCode() ||
                activityConf.getIscenesType() == BizTypeDefinitionEnum.SourceBizStatus.FIRST_OPEN_ALIPAY.getCode() ||
                activityConf.getIscenesType() == BizTypeDefinitionEnum.SourceBizStatus.FIRST_OPEN_WECHAT.getCode() ||
                activityConf.getIscenesType() == BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode())) {
            Map map = new HashMap();
            map.put("smemberId", giveActivityDto.getMemberId());
            map.put("sacId", activityConf.getId());
            Integer res = discountRecordService.selectCount(map);
            if (res >= 1) {
                logger.info("用户已参加过活动：{}", BizTypeDefinitionEnum.SourceBizStatus.FIRST_OPEN_ALIPAY.getDesc());
                return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                        .getResponseVo("用户已参加过该活动！");
            }
        }

       /* 10：活动期间用户
        20：活动期间设备*/

        Integer countType = activityConf.getIcountType();
        if (null != countType && 10 == countType) {
            if (0 != activityConf.getIuserDayCount()) {
                Map map = new HashMap();
                map.put("smemberId", giveActivityDto.getMemberId());
                map.put("sacId", activityConf.getId());
                map.put("queryTime", "DATEDIFF(TADD_TIME,NOW())=0");
                Integer res = discountRecordService.selectCount(map);
                if (res >= activityConf.getIuserDayCount()) {
                    return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                            .getResponseVo("活动期间用户单日参与人数已达上限！");
                }
            }
            if (0 != activityConf.getIuserAllCount()) {
                Map map = new HashMap();
                map.put("smemberId", giveActivityDto.getMemberId());
                map.put("sacId", activityConf.getId());
                Integer res = discountRecordService.selectCount(map);
                if (res >= activityConf.getIuserAllCount()) {
                    return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                            .getResponseVo("活动期间用户参与人数已达上限！");
                }
            }
        } else if (null != countType && 20 == countType) {
            if (0 != activityConf.getIuserDayCount()) {
                Map map = new HashMap();
                map.put("ssourceDeviceId", giveActivityDto.getSdeviceId());
                map.put("sacId", activityConf.getId());
                map.put("queryTime", "DATEDIFF(TADD_TIME,NOW())=0");
                Integer res = discountRecordService.selectCount(map);
                if (res >= activityConf.getIuserDayCount()) {
                    return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                            .getResponseVo("活动期间设备单日参与数已达上限！");
                }
            }
            if (0 != activityConf.getIuserAllCount()) {
                Map map = new HashMap();
                map.put("ssourceDeviceId", giveActivityDto.getSdeviceId());
                map.put("sacId", activityConf.getId());
                Integer res = discountRecordService.selectCount(map);
                if (res >= activityConf.getIuserAllCount()) {
                    return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                            .getResponseVo("活动期间设备参与人数已达上限！");
                }
            }
        }

        // b.券是否在有效期
        // 如果失效日期为空，则表示永不失效
       /* if (couponRule.getDcouponExpiryDate() != null
                && todayDate.getTime() > couponRule.getDcouponExpiryDate().getTime()) {
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                    .getResponseVo("券活动已经失效！");
        }*/
        // c.考虑赠送次数限制如果赠送次数不为空且为一次
       /* if (couponRule.getIsendNumber() != null
                && couponRule.getIsendNumber().equals(
                ActivityConstants.ONLY_ONCE)) {
            FirstRewardIntegral firstRewardIntegralCondition = new FirstRewardIntegral();
            firstRewardIntegralCondition.setSmemberId(giveActivityDto.getMemberId());
            firstRewardIntegralCondition.setSruleId(couponRule.getId());
            firstRewardIntegralCondition.setIruleType(ActivityConstants.TYPE_COUPON);
            List<FirstRewardIntegral> firstRewardIntegralList = firstRewardIntegralService.selectByEntityWhere(firstRewardIntegralCondition);
            if (firstRewardIntegralList != null
                    && firstRewardIntegralList.size() > 0) {
                return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                        .getResponseVo("券已经赠送过一次！");
            }
        }*/
        //d.赠送条件限制
        if ((BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode() == giveActivityDto.getSsourceType()) ||
                (BizTypeDefinitionEnum.SourceBizStatus.FIRST_ORDER.getCode() == giveActivityDto.getSsourceType())) {
            OrderParam orderParam = giveActivityDto.getOrderParam();
            List<CommodityDiscountDto> commodityDiscountDtoList = orderParam.getCommodityDiscountDtoList();

            BigDecimal fgiveLimitAmount = couponRule.getFgiveLimitAmount();//订单实付金额限制
            String sgiveLimitDevice = couponRule.getSgiveLimitDevice();//订单设备限制

            String sgiveLimitCategory = couponRule.getSgiveLimitCategory();//订单商品分类赠送限制
            String sgiveLimitBrand = couponRule.getSgiveLimitBrand();//订单商品品牌赠送限制
            String sgiveLimitCommodity = couponRule.getSgiveLimitCommodity();//订单商品赠送限制
            BigDecimal temp = BigDecimal.ZERO;
            //设备限制
            if (StringUtils.isNotBlank(sgiveLimitDevice) && !Arrays.asList(sgiveLimitDevice.split(",")).contains(giveActivityDto.getSdeviceCode())) {
                return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                        .getResponseVo("订单设备不满足赠送条件！");
            }
            boolean res = false;
            for (CommodityDiscountDto commodityDiscountDto : commodityDiscountDtoList) {
                if ((StringUtils.isBlank(sgiveLimitCategory) || Arrays.asList(sgiveLimitCategory.split(",")).contains(commodityDiscountDto.getSsmallCategoryId())) &&
                        (StringUtils.isBlank(sgiveLimitBrand) || Arrays.asList(sgiveLimitBrand.split(",")).contains(commodityDiscountDto.getSbrandId())) &&
                        (StringUtils.isBlank(sgiveLimitCommodity) || Arrays.asList(sgiveLimitCommodity.split(",")).contains(commodityDiscountDto.getScommodityCode()))) {
                    res = true;
                    temp = temp.add(commodityDiscountDto.getFactualAmount());//实付金额
                }
            }
            //res 为true 表示有商品满足条件
            if (res) {
                if (fgiveLimitAmount.compareTo(temp) > 0) {
                    return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                            .getResponseVo("订单金额不满足赠送条件！");
                }
            } else {
                return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                        .getResponseVo("订单赠送限制不满足赠送条件！");
            }
        } else if (BizTypeDefinitionEnum.SourceBizStatus.REGISTER.getCode() == giveActivityDto.getSsourceType())

        {
            String deviceCode = giveActivityDto.getSdeviceCode();
            String sgiveLimitDevice = couponRule.getSgiveLimitDevice();
            if (StringUtils.isNotBlank(sgiveLimitDevice) && !Arrays.asList(sgiveLimitDevice.split(",")).contains(giveActivityDto.getSdeviceCode())) {
                return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                        .getResponseVo("订单设备不满足赠送条件！");
            }
        }

        return ResponseVo.getSuccessResponse(couponRule);
    }

    /**
     * 查看商品在不在活动范围内
     *
     * @param giveActivityDto
     * @param useRange
     * @return boolean
     */
    private ResponseVo checkCommodity(GiveActivityDto giveActivityDto, UseRange useRange) {
        OrderParam orderParam = giveActivityDto.getOrderParam();
        if (null != orderParam) {
            List<CommodityDiscountDto> commodityDiscountDtoList = orderParam.getCommodityDiscountDtoList();
            boolean temp = false;
            for (CommodityDiscountDto commodityDiscountDto : commodityDiscountDtoList) {
                if (Arrays.asList(useRange.getScommodityCode().split(",")).contains(commodityDiscountDto.getScommodityCode())) {
                    temp = true;
                    break;
                }
            }
            if (!temp) {
                return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                        .getResponseVo("促销活动,活动商品范围不满足条件！！");
            }
        } else {
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                    .getResponseVo("促销活动,订单商品为空！！");
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 处理积分活动
     *
     * @param activityConf
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResponseVo<ChangeIntegralDto> handleIntegral(
            ActivityConf activityConf, GiveActivityDto giveActivityDto, String scouponId)
            throws Exception {
        logger.debug("处理积分活动开始...");
        // 1.根据活动id查询积分活动
        IntegralRule integralRule = integralRuleService
                .selectIntegralRuleByActivityId(activityConf.getId());

        if (integralRule == null) {
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                    .getResponseVo("没有有效的积分活动！");
        }

        // 2.判断活动是否有效
      /* Date todayDate = DateUtils.getToday();
        if (todayDate.getTime() < integralRule.getTruleBeginDate().getTime()
                || todayDate.getTime() > integralRule.getTruleEndDate()
                .getTime()) {
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                    .getResponseVo("当前日期不在积分规则活动日期内！");
        }*/

        // 3.限制第一次、按天次数的情况
      /*  if (integralRule.getIlimitTimes() != null
                && ActivityConstants.INTE_CAL_METHOD_FIRST.equals(integralRule
                .getIlimitTimes())) {
            FirstRewardIntegral firstRewardIntegralCondition = new FirstRewardIntegral();
            firstRewardIntegralCondition.setSmemberId(giveActivityDto
                    .getMemberId());
            firstRewardIntegralCondition.setSruleId(integralRule.getId());
            firstRewardIntegralCondition
                    .setIruleType(ActivityConstants.TYPE_INTEGRAL);
            List<FirstRewardIntegral> firstRewardIntegralList = firstRewardIntegralService
                    .selectByEntityWhere(firstRewardIntegralCondition);
            if (firstRewardIntegralList != null
                    && firstRewardIntegralList.size() > 0) {
                return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                        .getResponseVo("积分已经赠送过一次！");
            }
        } else*/ /*if (integralRule.getIlimitTimes() != null
                && ActivityConstants.INTE_CAL_METHOD_DAY.equals(integralRule
                .getIlimitTimes())) {*/
           /* FirstRewardIntegral paramFirstRewardIntegral = new FirstRewardIntegral();
            paramFirstRewardIntegral.setIruleType(ActivityConstants.TYPE_INTEGRAL);
            paramFirstRewardIntegral.setTaddTime(todayDate);
            paramFirstRewardIntegral.setSmemberId(giveActivityDto.getMemberId());
            paramFirstRewardIntegral.setSruleId(integralRule.getId());
            Integer count = firstRewardIntegralService
                    .countFirstRewardIntegralByWhere(paramFirstRewardIntegral);
            // 如果当日获得奖励的次数已经达到赠送上限
            if (count >= integralRule.getFaccuPointnum().intValue()) {
                return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                        .getResponseVo("积分已经达到赠送上限！");
            }*/

        //  }
        // 积分值，计算积分
        Integer integralVal = calculateIntegral(integralRule, giveActivityDto);
        if (integralVal == null || integralVal.equals(0)) {
            return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                    .getResponseVo("积分值不在赠送范围");
        }

        // 4.调用积分变更接口
        ChangeIntegralDto dto = new ChangeIntegralDto();
        dto.setSmemberId(giveActivityDto.getMemberId());
        dto.setSmemberName(giveActivityDto.getMemberRealName());
        dto.setSmemberNo(giveActivityDto.getMemberCode());
        dto.setIsbackOperate(0);
        dto.setIrequestSource(10);
        dto.setIsourceType(giveActivityDto.getSsourceType());
        dto.setSintegralRuleId(integralRule.getId());
        dto.setSintegralRuleNo(integralRule.getScode());
        dto.setSintegralRuleName(activityConf.getSconfName());// 积分规则名称
        dto.setFvalue(integralVal);// 积分值
        if (StringUtils.isBlank(giveActivityDto.getSsourceInstruction())) {
            dto.setSsourceInstruction(activityConf.getSdescription());// 来源说明
        } else {
            dto.setSsourceInstruction(giveActivityDto.getSsourceInstruction());// 来源说明
        }
        dto.setSinvestmentId(giveActivityDto.getSsourceId());
        dto.setSsourceNo(giveActivityDto.getSsourceCode());
        dto.setSremark(integralRule.getSremark());

        dto.setSlinkId(scouponId);
        //推荐获得的券要获得被推荐人ID
        if (BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode() == giveActivityDto.getSsourceType())
              /*  || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST.getCode() == giveActivityDto.getSsourceType()
                || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST_AGAIN.getCode() == giveActivityDto.getSsourceType()
                || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_OPEN_ACCOUNT.getCode() == giveActivityDto.getSsourceType()) */ {
            dto.setSinvestmentId(giveActivityDto.getRecommendParam().getRecUserId());
        }
        Object[] params = {dto.getFvalue(),
                giveActivityDto.getActiveConfCode(), integralRule.getId(),
                activityConf.getSconfName(), giveActivityDto.getMemberId(),
                integralRule.getScode()};
        logger.debug(
                "调用积分变更接口开始，积分值:{},积分活动编号:{},积分活动规则ID:{},积分活动名称:{},用户ID：{}，用户编号：{}",
                params);
        // 创建Rest服务客户端
        RestServiceInvoker invoke = RestServiceInvokeBuilder
                .newBuilder()
                .newInvoker(IntegralAccountServicesDefine.INTEGRAL_CHANGE_INTEGRAL_SERVICE);
        invoke.setRequestObj(dto);
        // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
        });
        ResponseVo responseVo = (ResponseVo<String>) invoke.invoke();

        if (!responseVo.isSuccess()) {
            logger.debug("调用积分变更接口 失败，errCode:{},msg：{}",
                    responseVo.getErrorCode(), responseVo.getMsg());
            return responseVo;
        }
        logger.debug("调用积分变更接口成功。");

        // 5.插入首次增加记录表
        insertIntegralRecord(giveActivityDto, integralRule);
        logger.debug("处理积分活动结束...");
        return ResponseVo.getSuccessResponse(dto);
    }

    /**
     * 积分增加记录流水
     *
     * @param giveActivityDto 赠送活动参数
     * @param integralRule    活动返积分规则记录表
     */
    private void insertIntegralRecord(GiveActivityDto giveActivityDto,
                                      IntegralRule integralRule) {
        FirstRewardIntegral firstReward = new FirstRewardIntegral();
        firstReward.setSmemberId(giveActivityDto.getMemberId());// 用户ID
        firstReward.setSmemberCode(giveActivityDto.getMemberCode());// 用户编号
        firstReward.setSmemberName(giveActivityDto.getMemberRealName());// 用户姓名
        firstReward.setSruleId(integralRule.getId());// 活动规则ID
        firstReward.setIruleType(ActivityConstants.TYPE_INTEGRAL);// 类型
        firstReward.setIisUse(0);// 未使用
        firstReward.setTaddTime(new Date());
        firstRewardIntegralService.insert(firstReward);
        logger.debug("生成积分记录成功。");
    }

    /**
     * 计算积分
     *
     * @param integralRule 活动返积分规则记录表
     * @return
     */
    private Integer calculateIntegral(IntegralRule integralRule,
                                      GiveActivityDto giveActivityDto) {
        BigDecimal integralVal = BigDecimal.ZERO;
        BigDecimal baseIntegral = integralRule.getFbaseIntegral();
        return integralVal.intValue();
    }

    /**
     * 发券
     *
     * @param sendSingleCouponDto
     * @return
     */
    @Override
    public ResponseVo<CouponQueryResult> sendSingleCoupon(SendSingleCouponDto sendSingleCouponDto) {
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(sendSingleCouponDto.getMemberId());
        CouponBatch couponBatch = couponBatchService.selectByPrimaryKey(sendSingleCouponDto.getCouponBatchId());
        CouponQueryResult couponQueryResult = generateCoupon(sendSingleCouponDto.getSourceCode(), couponBatch, memberInfo, sendSingleCouponDto.getSourceType(), sendSingleCouponDto.getSourceTypeDesc());
        return ResponseVo.getSuccessResponse(couponQueryResult);
    }

    /**
     * 生成优惠券
     *
     * @param exCouponCode   来源单据编号
     * @param couponBatch    优惠券批量下发表
     * @param memberInfo     会员基础信息表
     * @param couponFromCode 来源单据类型
     * @param couponFromDesc 来源说明
     * @return CouponQueryResult
     */
    private CouponQueryResult generateCoupon(String exCouponCode,
                                             CouponBatch couponBatch, MemberInfo memberInfo, int couponFromCode,
                                             String couponFromDesc) {
        // 生成持有券信息
        CouponUser couponUser = new CouponUser();
        // 券编号在SYS表
        couponUser.setSbriefDesc(couponBatch.getSbriefDesc());//券简述
        couponUser.setScouponCode(CoreUtils.newCode(EntityTables.AC_COUPON_USER));// 券编号
        couponUser.setIcouponType(couponBatch.getIcouponType());// 券类型
        couponUser.setScouponInstruction(couponBatch.getScouponInstruction());// 券说明
        couponUser.setDcouponEffectiveDate(couponBatch.getDcouponEffectiveDate());// 券生效日期
        couponUser.setDcouponExpiryDate(couponBatch.getDcouponExpiryDate());// 券失效日期
        couponUser.setScouponValidityValue(couponBatch.getScouponValidityValue());//券的有效期天数
        couponUser.setSmemberId(memberInfo.getId());    //会员ID
        couponUser.setSmemberCode(memberInfo.getScode());//会员编号
        couponUser.setSmemberName(memberInfo.getSmemberName());//会员用户名（手机号）
        couponUser.setIsourceType(couponFromCode);// 来源单据类型
        couponUser.setSsourceId(couponBatch.getId());// 来源单据id
        couponUser.setSsourceCode(exCouponCode);// 来源单据编号
        couponUser.setSsourceInstruction(couponFromDesc);// 来源说明
        couponUser.setTgetDatetime(DateUtils.getCurrentDateTime());// 获券时间
        couponUser.setTaddTime(DateUtils.getCurrentDateTime());//添加时间
        //添加人couponUser.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId);
        couponUser.setTupdateTime(DateUtils.getCurrentDateTime());//修改时间
        //修改人couponUser.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId);
        couponUser.setFcouponValue(couponBatch.getFcouponValue());   // 券面值
        couponUser.setFuseLimitAmount(couponBatch.getFuseLimitAmount());    // 订单实付金额使用限制
        couponUser.setSuseLimitCategory(couponBatch.getSuseLimitCategory());    //订单商品分类使用限制
        couponUser.setSuseLimitBrand(couponBatch.getSuseLimitBrand());  //订单商品品牌使用限制
        couponUser.setSuseLimitDevice(couponBatch.getSuseLimitDevice());    //订单设备使用限制
        couponUser.setSuseLimitCommodity(couponBatch.getSuseLimitCommodity());  //订单商品使用限制
        couponUser.setSsourceAcCode(couponBatch.getSactivityCode());//来源活动编号
        couponUser.setSsourceAcName(couponBatch.getSactivityName());//来源活动名称
        couponUser.setIstatus(ActivityConstants.COUPON_STATUS_NOTUSE);// 状态：未使用
        couponUser.setSremark(couponBatch.getSremark());//券备注
        couponUser.setSmerchantId(couponBatch.getSmerchantId());//商户id
        couponUser.setSmerchantCode(couponBatch.getSmerchantCode());//商户Code

        // 执行插入
        insertCouponUser(couponUser);
        //调用方法生成活动优惠记录表
        if (null != couponUser) {
            OrderInvocationActivityDto orderInvocationActivityDto = new OrderInvocationActivityDto();
            orderInvocationActivityDto.setSmemberId(memberInfo.getId());//会员ID
            orderInvocationActivityDto.setSmemberCode(memberInfo.getScode());//会员Code
            orderInvocationActivityDto.setSmemberName(memberInfo.getSmemberName());//会员名
            orderInvocationActivityDto.setIsourceType(BizTypeDefinitionEnum.SourceBizStatus.PLATFORM_SEND.getCode());//来源单据类型(平台赠送)
            orderInvocationActivityDto.setSsourceId(couponBatch.getId());//来源单据Id
            orderInvocationActivityDto.setSsourceCode(couponBatch.getSbatchCode());//来源单据编号
            orderInvocationActivityDto.setScouponId(couponUser.getId());//用户持有券Id
            orderInvocationActivityDto.setScouponCode(couponUser.getScouponCode());//用户持有券Code
            orderInvocationActivityDto.setSmerchantId(memberInfo.getSmerchantId());//商户Id
            orderInvocationActivityDto.setSmerchantCode(memberInfo.getSmerchantCode());//商户Code
            orderInvocationActivityDto.setUpType(10);//批量送券
            logger.info("#######################批量下发券,开始调用优惠记录服务#######################:{}", orderInvocationActivityDto);
            ResponseVo responseVo = orderActivityServiceProxy.upDiscountRecord(orderInvocationActivityDto);
            if (responseVo != null && responseVo.isSuccess()) {
                logger.info("批量下发券,调用优惠记录服务成功！");
            }

            CouponQueryResult couponQueryResult = new CouponQueryResult();
            couponQueryResult.setActivityInstruction(couponUser.getScouponInstruction());
            couponQueryResult.setCouponCode(couponUser.getScouponCode());
            couponQueryResult.setCouponEffectiveDate(couponUser.getDcouponEffectiveDate());
            couponQueryResult.setCouponExpiryDate(couponUser.getDcouponExpiryDate());
            couponQueryResult.setCouponId(couponUser.getId());
            couponQueryResult.setCouponType(couponUser.getIcouponType());
            couponQueryResult.setCouponTypeName(ActivityConstants.couponTypeMap.get(couponUser.getIcouponType()));
            couponQueryResult.setCouponValue(couponUser.getFcouponValue());
            couponQueryResult.setSourceInstruction(couponUser.getSsourceInstruction());
            return couponQueryResult;
        }
        return null;
    }

    /**
     * 多张优惠券下发
     *
     * @param sendSingleCouponDto
     * @return
     */
    @Override
    public ResponseVo<List<CouponQueryResult>> sendMutipleCoupon(
            SendSingleCouponDto sendSingleCouponDto) {
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(sendSingleCouponDto.getMemberId());
        if (null == memberInfo) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员不存在");
        }
        CouponBatch couponBatch = couponBatchService.selectByPrimaryKey(sendSingleCouponDto.getCouponBatchId());
        if (null == couponBatch) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("优惠券批量下发不存在");
        }
        Integer sendCount = sendSingleCouponDto.getSendCount();
        if (sendCount == null) {
            sendCount = 1;
        }
        List<CouponQueryResult> result = new ArrayList<CouponQueryResult>();
        for (int i = 0; i < sendCount; i++) {
            CouponQueryResult couponQueryResult = generateCoupon(sendSingleCouponDto.getSourceCode(), couponBatch, memberInfo, sendSingleCouponDto.getSourceType(), sendSingleCouponDto.getSourceTypeDesc());
            result.add(couponQueryResult);
        }
        return ResponseVo.getSuccessResponse(result);
    }

    /**
     * 优惠券批量下发
     *
     * @param couponBatch userSendList
     */
    @Override
    @Transactional
    public void batchCoupon(String saddUser, CouponBatch couponBatch, List<CouponUserSend> userSendList) {
        for (CouponUserSend userSend : userSendList) {
            MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(userSend.getSmemberId());
            if (null == memberInfo) {
                logger.info("批量下发会员不存在！");
                throw new ServiceException("批量下发会员不存在，生成用户持券记录失败！");
            }
            for (int i = 0; i < userSend.getInumber(); i++) {
                CouponQueryResult couponQueryResult = generateCoupon(couponBatch.getSbatchCode(),
                        couponBatch, memberInfo, BizTypeDefinitionEnum.SourceBizStatus.PLATFORM_SEND.getCode(), "平台赠送");
                if (couponQueryResult == null) {
                    logger.info("生成用户持券记录失败");
                    throw new ServiceException("生成用户持券记录失败");
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public ResponseVo<CouponQueryResult> exchangeCoupon(
            ExchangeCouponDto exchangeCouponDto) {
        CouponCodeExDetails couponCodeExDetails = couponCodeExDetailsService
                .selectCouponCodeExDetailsByCouponCode(exchangeCouponDto
                        .getExCouponCode());
        if (couponCodeExDetails == null) {
            // 没有查询到券码
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("输入的券码无效");
        }

        if (ActivityConstants.EXCHANGED
                .equals(couponCodeExDetails.getIstatus())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券码已经兑换");
        }

        CouponBatch couponBatch = couponBatchService
                .selectByPrimaryKey(couponCodeExDetails.getSbatchId());
        if (couponBatch == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("输入的券码无效");
        }

        Date todayDate = DateUtils.getToday();
        if (todayDate.getTime() < couponBatch.getTexStarttime().getTime()) {
            // 当前日期不在兑券有效日期内！
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY.getResponseVo("输入的券码无效");
        }

        if (todayDate.getTime() > couponBatch.getTexEndtime().getTime()) {
            // 当前日期不在兑券有效日期内！
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY.getResponseVo("券码已过期");
        }

        // 置为兑换中间状态
        CouponCodeExDetails model = new CouponCodeExDetails();
        model.setId(couponCodeExDetails.getId());
        model.setIstatus(ActivityConstants.EXCHANGING);// 1:兑换中
        int count = couponCodeExDetailsService.updateBySelective(model);
        if (count < 1) {
            logger.debug("券码状态不正确！");
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券码状态不正确");
        }

        MemberInfo memberInfo = memberInfoService
                .selectByPrimaryKey(exchangeCouponDto.getMemberId());
        if (memberInfo == null
                || !memberInfo.getScode().equals(
                exchangeCouponDto.getMemberCode())) {
            logger.debug("兑换用户信息异常，用户ID:{},用户编号：{}，兑换码：{}",
                    new Object[]{exchangeCouponDto.getMemberId(),
                            exchangeCouponDto.getMemberCode(),
                            exchangeCouponDto.getExCouponCode()});
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("兑换用户信息异常,兑换失败");
        }

        ResponseVo timesLimitResponse = couponCodeJoinTimesLimit(
                exchangeCouponDto, couponBatch, memberInfo);
        if (!timesLimitResponse.isSuccess()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo(timesLimitResponse.getMsg());
        }

        // 生成持有券信息
        //来源单据类型
        //来源单据说明
        CouponQueryResult couponQueryResult = generateCoupon(
                exchangeCouponDto.getExCouponCode(), couponBatch, memberInfo,
                BizTypeDefinitionEnum.SourceBizStatus.EXCHANGE_COUPON_CODE
                        .getCode(),
                BizTypeDefinitionEnum.SourceBizStatus.EXCHANGE_COUPON_CODE
                        .getDesc());

        CouponCodeExDetails couponExDetails = new CouponCodeExDetails();
        couponExDetails.setId(couponCodeExDetails.getId());
        if (couponBatch.getIcouponCodeUseType().intValue() == ActivityConstants.EXCHANGE_LIMIT_MONTH_ONCE) {
            couponExDetails.setIstatus(ActivityConstants.CAN_EXCHANGE_MANY_TIMES);// 已兑换
        } else {
            couponExDetails.setIstatus(ActivityConstants.EXCHANGED);// 已兑换
        }
        couponExDetails.setTexTime(new Date());
       /* couponExDetails.setSexUserId(memberInfo.getId());
        couponExDetails.setSexUserCode(memberInfo.getScode());
        couponExDetails.setSexUserMobile(memberInfo.getSmobile());
        couponExDetails.setSexUserName(memberInfo.getSname());*/
        Integer clientType = exchangeCouponDto.getClientType();
        if (clientType == BizTypeDefinitionEnum.ClientType.WECHAT.getCode()) {
            couponExDetails.setSexSourceCode(BizTypeDefinitionEnum.ClientType.WECHAT.getDesc());
        } else if (clientType == BizTypeDefinitionEnum.ClientType.ALIPAY.getCode()) {
            couponExDetails.setSexSourceCode(BizTypeDefinitionEnum.ClientType.ALIPAY.getDesc());
        } else if (clientType == BizTypeDefinitionEnum.ClientType.IOS.getCode()) {
            couponExDetails.setSexSourceCode(BizTypeDefinitionEnum.ClientType.IOS.getDesc());
        } else if (clientType == BizTypeDefinitionEnum.ClientType.ANDROID.getCode()) {
            couponExDetails.setSexSourceCode(BizTypeDefinitionEnum.ClientType.ANDROID.getDesc());
        } else {
            couponExDetails.setSexSourceCode("未知");
        }

        couponExDetails.setSexSourceId(clientType + "");
        couponCodeExDetailsService.updateBySelective(couponExDetails);
        return ResponseVo.getSuccessResponse(couponQueryResult);
    }

    /**
     * 券码兑换参与次数限制
     *
     * @param exchangeCouponDto
     * @param couponBatch
     * @param memberInfo
     * @return
     */
    private ResponseVo couponCodeJoinTimesLimit(
            ExchangeCouponDto exchangeCouponDto, CouponBatch couponBatch,
            MemberInfo memberInfo) {
        // 限制校验
        // 券码使用限制只有一次
        if (ActivityConstants.EXCHANGE_LIMIT_ONLY_ONCE.equals(couponBatch
                .getIcouponCodeUseType())) {
            Integer joinCount = couponUserService
                    .countCouponUserOnceJoinByCouponCode(exchangeCouponDto
                            .getExCouponCode());
            if (joinCount > 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("券码已经兑换");
            }
        } else if (ActivityConstants.EXCHANGE_LIMIT_MONTH_ONCE
                .equals(couponBatch.getIcouponCodeUseType())) {
            Integer joinCount = couponUserService
                    .countCouponUserMouthJoinByCouponCode(exchangeCouponDto
                            .getExCouponCode());
            if (joinCount > 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("当月券码已经兑换");
            }
        }
        // 用户参与次数限制
        Map<String, String> map = new HashMap<String, String>();
        map.put("memberId", memberInfo.getId());
        map.put("sourceId", couponBatch.getId());
        // 用户只能参与一次
        if (ActivityConstants.EXCHANGE_LIMIT_ONLY_ONCE.equals(couponBatch
                .getImemberJoinType())) {

            Integer joinCount = couponUserService
                    .countCouponUserOnceJoinByMmeberId(map);
            if (joinCount > 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("每人限兑一次！");
            }
        } else if (ActivityConstants.EXCHANGE_LIMIT_MONTH_ONCE
                .equals(couponBatch.getImemberJoinType())) {
            Integer joinCount = couponUserService
                    .countCouponUserMouthJoinByMemberId(map);
            if (joinCount > 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("每月限兑一次！");
            }
        }
        return ResponseVo.getSuccessResponse();
    }
}

