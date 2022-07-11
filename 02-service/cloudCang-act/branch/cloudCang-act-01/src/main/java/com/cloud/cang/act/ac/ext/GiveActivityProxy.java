package com.cloud.cang.act.ac.ext;

import com.cloud.cang.act.GiveActivityDto;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.act.RecommendParam;
import com.cloud.cang.act.ac.service.ActivityConfService;
import com.cloud.cang.act.ac.service.CouponIntegralActivityService;
import com.cloud.cang.act.ac.service.CouponUserService;
import com.cloud.cang.act.ac.service.RecommendRecordService;
import com.cloud.cang.act.common.ActivityConstants;
import com.cloud.cang.act.hy.service.MemberInfoService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.jm.ChangeIntegralDto;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.model.ac.RecommendRecord;
import com.cloud.cang.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;


/**
 * 活动代理类
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service
public class GiveActivityProxy {

    private static final Logger logger = LoggerFactory.getLogger(GiveActivityProxy.class);

    @Autowired
    private RecommendRecordService recommendRecordService;

    @Autowired
    private ActivityConfService activityConfService;

    @Autowired
    private CouponIntegralActivityService couponActivityService;

    @Autowired
    private MemberInfoService memberInfoService;


    /**
     * 活动赠送服务
     */
    public ResponseVo<GiveActivityResult> activeGive(
            GiveActivityDto giveActivityDto) {

        // 1.类型为推荐注册，则生成好友推荐记录，独立事物。给推荐人奖励
        ResponseVo recommendResult = recommendHandle(giveActivityDto);
        if (!recommendResult.isSuccess()) {
            logger.debug("推荐处理：{}", recommendResult.getMsg());
            return recommendResult;
        }
        // 2. 通过活动编号查询活动配置表
        ActivityConf activityConf = activityConfService
                .selectActivityConfByCode(giveActivityDto.getActiveConfCode());
        if (null == activityConf) {
            logger.debug("当前没有有效的活动配置！活动编号：{}",
                    giveActivityDto.getActiveConfCode());
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY.getResponseVo();
        }

        // 3.处理券活动，独立事物
        ResponseVo<List<CouponUser>> handleCouponResult = null;
        try {
            handleCouponResult = couponActivityService.handleCoupon(
                    activityConf, giveActivityDto);
        } catch (Exception e) {
            logger.error("处理券活动失败！", e);
            handleCouponResult = ErrorCodeEnum.ERROR_COMMON_SYSTEM
                    .getResponseVo("处理券活动失败！");
        }

        // 4.处理积分活动
 /*       ResponseVo<ChangeIntegralDto> handleIntegralResult = null;
        if (giveActivityDto.isEnableIntegration()) {
            try {
                String slinkId = "";
                if (handleCouponResult != null && handleCouponResult.getData() != null && handleCouponResult.getData().size() > 0) {
                    slinkId = handleCouponResult.getData().get(0).getId();//第一张券的id
                }
                handleIntegralResult = couponActivityService.handleIntegral(
                        activityConf, giveActivityDto, slinkId);
            } catch (Exception e) {
                logger.error("处理积分活动失败！", e);
                handleIntegralResult = ErrorCodeEnum.ERROR_COMMON_SYSTEM
                        .getResponseVo("处理积分活动失败！");
            }

        } else {
            logger.warn("！！！积分活动已经禁用。。。。");
            handleIntegralResult = ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("积分活动已经禁用！");
        }
*/
        // 5.更新推荐记录
      /*  updateRecommend(giveActivityDto, handleCouponResult,
                handleIntegralResult);

        ResponseVo<GiveActivityResult> result = handleResult(giveActivityDto,
                handleCouponResult, handleIntegralResult);*/
        return ResponseVo.getSuccessResponse(new GiveActivityResult());

    }


    /**
     * 类型为推荐注册，生成一条好友推荐记录
     *
     * @param giveActivityDto
     * @return
     */
    private ResponseVo recommendHandle(final GiveActivityDto giveActivityDto) {
        //推荐处理
        if (BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode() == giveActivityDto.getSsourceType())
                /*|| BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST.getCode() == giveActivityDto.getSsourceType()
                || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST_AGAIN.getCode() == giveActivityDto.getSsourceType()
				|| BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_OPEN_ACCOUNT.getCode() == giveActivityDto.getSsourceType())*/ {


            RecommendParam recommendParam = giveActivityDto.getRecommendParam();//推荐相关参数,为推荐记录表提供数据
            // RecommendParam recommendParam = new RecommendParam();
           /* recommendParam.setRecUserId("1");
            recommendParam.setRecUserCode("1");
            recommendParam.setRecUserRealName("yyf");*/
            String memberId = giveActivityDto.getMemberId();//被推荐人Id
            String memberCode = giveActivityDto.getMemberCode();//被推荐人Code
            String memberRealName = giveActivityDto.getMemberRealName();
            //推荐注册只给推荐人奖励那么奖励的主体是推荐人，这里将奖励的主体改为推荐人：
            giveActivityDto.setMemberId(recommendParam.getRecUserId());
            giveActivityDto.setMemberCode(recommendParam.getRecUserCode());
            giveActivityDto.setMemberBirthDay(recommendParam.getRecUserBirthDay());
            giveActivityDto.setMemberRealName(recommendParam.getRecUserRealName());
            //预留推荐人信息
            RecommendParam recommendParam2 = new RecommendParam();
            recommendParam2.setRecUserId(memberId);
            recommendParam2.setRecUserCode(memberCode);
            giveActivityDto.setRecommendParam(recommendParam2);


            //如果类型为推荐注册
            if (BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode() == giveActivityDto.getSsourceType()) {
                logger.debug("类型为推荐注册，检查是否需要生成好友推荐记录...");
                Integer recommendRecordCount = recommendRecordService.countRecommendRecordBySpresenteeId(memberId);
                //如果之前没有记录
                if (recommendRecordCount == 0) {
                    try {
                        RecommendRecord rec = new RecommendRecord();
                        rec.setSreferrerName(recommendParam.getRecUserRealName());//推荐人姓名
                        rec.setSreferrerCode(recommendParam.getRecUserCode());//推荐人编号
                        rec.setSreferrerId(recommendParam.getRecUserId());//推荐人ID
                        rec.setSpresenteeCode(memberCode);//被推荐人编号
                        rec.setSpresenteeId(memberId);//被推荐人ID
                        rec.setSpresenteeName(memberRealName);//被推荐人姓名
                        Date member = memberInfoService.selectRegisterDate(memberId);//查询注册用户的日期
                        rec.setTregDatetime(member);//注册时间
                        rec.setTaddTime(new Date());//生成时间
                        recommendRecordService.insert(rec);
                        logger.debug("生成好友推荐记录成功...");
                    } catch (Exception e) {
                        logger.error("生成好友推荐记录异常！", e);
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("生成好友推荐记录失败");
                    }

                } else {
                    logger.debug("好友推荐注册调用重复");
                    //如果有重复记录，说明重复推荐，则不继续后续逻辑
                    return ErrorCodeEnum.ERROR_ACT_REC_REPEAT.getResponseVo();
                }

            }
        }
        return ResponseVo.getSuccessResponse();

    }

    /**
     * 校验活动配置表
     *
     * @param giveActivityDto
     * @return
     */
    ResponseVo<ActivityConf> handleActivityConf(GiveActivityDto giveActivityDto) {
        ActivityConf activityConf = activityConfService
                .selectActivityConfByCode(giveActivityDto.getActiveConfCode());
        if (activityConf == null) {
            logger.debug("当前没有有效的活动配置！活动编号：{}",
                    giveActivityDto.getActiveConfCode());
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY.getResponseVo();
        }
        Date todayDate = DateUtils.getToday();
        if (todayDate.getTime() < activityConf.getTactiveStartTime().getTime()
                || todayDate.getTime() > activityConf.getTactiveEndTime().getTime()) {
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY
                    .getResponseVo("当前日期不在券规则活动日期内！");
        }
        Integer joinNum = activityConf.getIjoinNum();//活动已参与人数
        //次数限制类型 10：活动期间  30：活动期间每天
        if (10 == activityConf.getIcountType()) {
            //活动期间用户参与人数限制不为0并且参与人数已满
            if (0 != activityConf.getIallCount() && activityConf.getIallCount().compareTo(joinNum) <= 0) {
                return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                        .getResponseVo("活动期间内参与人数已满！");
            }
        }
        if (30 == activityConf.getIcountType()) {
            //每日用户参与人数限制不为0并且参与人数已满
            if (0 != activityConf.getIallCount() && activityConf.getIallCount().compareTo(joinNum) <= 0) {
                return ErrorCodeEnum.ERROR_ACT_NOT_FULFIL
                        .getResponseVo("今日参与人数已满！");
            }
        }

        return ResponseVo.getSuccessResponse(activityConf);
    }

    /**
     * 更新推荐记录表
     *
     * @param giveActivityDto,handleCouponResult,handleIntegralResult
     * @return
     */
    private void updateRecommend(GiveActivityDto giveActivityDto, ResponseVo<List<CouponUser>> handleCouponResult, ResponseVo<ChangeIntegralDto> handleIntegralResult) {
        try {
            if (BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode() == giveActivityDto.getSsourceType()
                 /*   || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST.getCode() == giveActivityDto.getSsourceType()
                    || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST_AGAIN.getCode() == giveActivityDto.getSsourceType()
                    || BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_OPEN_ACCOUNT.getCode() == giveActivityDto.getSsourceType()*/) {
                logger.debug("类型为推荐活动，且发放券或积分成功，更新推荐记录开始...");
                RecommendRecord recommendRecord = recommendRecordService.selectRecommendRecordBySpresenteeId(giveActivityDto.getRecommendParam().getRecUserId());
                //如果有推荐记录
                if (recommendRecord != null) {

                    RecommendRecord model = new RecommendRecord();
                    model.setId(recommendRecord.getId());//推荐记录ID
                    //如果为注册
                    if (BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_REGISTER.getCode() == giveActivityDto.getSsourceType()) {
                        model.setSpresenteeName(giveActivityDto.getRecommendParam().getRecUserRealName());//被推荐人姓名
                    }
                   /* if (BizTypeDefinitionEnum.SourceBizStatus.RECOMMEND_INVEST.getCode() == giveActivityDto.getSsourceType()) {
                        //之前没被奖励过才更新
                        if (recommendRecord.getSinvestmentPrjAmount() == null) {
                            InvestParam investParam = giveActivityDto.getInvestParam();
                            model.setSinvestmentPrjAmount(new BigDecimal(String.valueOf(investParam.getInvestAmount())));//首次投资项目金额
                            model.setSinvestmentPrjCode(investParam.getProjectCode());//首次投资项目编号
                            model.setSinvestmentPrjId(investParam.getProjectId());//首次投资项目ID
                            model.setSinvestmentPrjName(investParam.getProjectName());//首次投资项目名称
                            model.setTinvestmentDatetime(investParam.getInvestTime());//投资时间
                        } else {
                            logger.debug("投资推荐的推荐记录已经更新过了。");
                        }
                    }*/

                    StringBuffer sinvestmentReward = new StringBuffer();
                    if (handleCouponResult.isSuccess()) {
                        List<CouponUser> couponUserList = handleCouponResult.getData();
                        if (couponUserList.size() > 0) {
                            CouponUser couponUser = couponUserList.get(0);
                           /* if (couponUser.getIcouponType().intValue() == 40) { //如果券类型为40
                                sinvestmentReward.append(new DecimalFormat("#0.##").format(couponUser.getFexchangeRatio().multiply(new BigDecimal("100"))) + "%" + ActivityConstants.couponTypeMap.get(couponUser.getIcouponType()) + couponUserList.size() + "张；");
                            } else {*/
                            sinvestmentReward.append(new DecimalFormat("#0.0#").format(couponUser.getFcouponValue()) + "元" + ActivityConstants.couponTypeMap.get(couponUser.getIcouponType()) + couponUserList.size() + "张；");
                            // }

                        }

                    }

                    if (handleIntegralResult.isSuccess()) {
                        ChangeIntegralDto changeIntegralDto = handleIntegralResult.getData();
                        sinvestmentReward.append("积分" + changeIntegralDto.getFvalue() + "个；");
                    }

                    model.setSrewardInstruction((recommendRecord.getSrewardInstruction() == null ? "" : recommendRecord.getSrewardInstruction())
                            + sinvestmentReward.toString());//奖励说明
                    recommendRecordService.updateBySelective(model);

                    logger.debug("推荐记录更新成功！");
                } else {
                    logger.debug("没有相应的推荐记录。");
                }
            }

        } catch (Exception e) {
            logger.error("更新推荐记录异常", e);
        }
    }

    @SuppressWarnings("unchecked")
    private ResponseVo<GiveActivityResult> handleResult(GiveActivityDto giveActivityDto, ResponseVo<List<CouponUser>> handleCouponResult, ResponseVo<ChangeIntegralDto> handleIntegralResult) {
        logger.debug("处理优惠券活动结果：{}", handleCouponResult);
        logger.debug("处理积分活动结果：{}", handleIntegralResult);

        GiveActivityResult activeGiveResult = new GiveActivityResult();
/*        if (handleCouponResult.isSuccess()) {
            List<CouponUser> couponUserList = handleCouponResult.getData();
            List<CouponGiveResult> couponGiveResults = new ArrayList<CouponGiveResult>();
            if (couponUserList != null && couponUserList.size() > 0) {
                CouponUser couponUser = couponUserList.get(0);
                //返回值塞值
                CouponGiveResult couponGiveResult = new CouponGiveResult();
                couponGiveResult.setCount(couponUserList.size());//券的数量
                couponGiveResult.setActivityCode(giveActivityDto.getActiveConfCode());
                couponGiveResult.setActivityInstruction(couponUser.getSactivityInstruction());//券说明
                couponGiveResult.setCouponType(couponUser.getIcouponType());//券类型
                couponGiveResult.setCouponTypeName(ActivityConstants.couponTypeMap.get(couponUser.getIcouponType()));//券类型名称
                couponGiveResult.setCouponValue(couponUser.getFcouponValue().doubleValue());//券面值
                if (couponUser.getFexchangeRatio() != null) {
                    couponGiveResult.setExchangeRatio(couponUser.getFexchangeRatio().doubleValue());
                }
                couponGiveResult.setExpiryDate(couponUser.getDcouponExpiryDate());
                couponGiveResults.add(couponGiveResult);


            }
            activeGiveResult.setCouponGiveResultList(couponGiveResults);
        }
        if (handleIntegralResult.isSuccess()) {
            ChangeIntegralDto changeIntegralDto = handleIntegralResult.getData();
            IntegralGiveResult integralGiveResult = new IntegralGiveResult();
            integralGiveResult.setIntegralRuleName(changeIntegralDto.getSintegralRuleName());//积分活动名称
            integralGiveResult.setIntegralValue(changeIntegralDto.getFvalue());//积分值
            activeGiveResult.setIntegralGiveResult(integralGiveResult);
        }*/
        if (handleCouponResult.isSuccess() || handleIntegralResult.isSuccess()) {
            return ResponseVo.getSuccessResponse(activeGiveResult);
        } else {
            return ErrorCodeEnum.ERROR_ACT_NO_ACTIVITY.getResponseVo("没有有效的活动或者不满足活动条件");
        }

    }

}
