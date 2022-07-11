package com.cloud.cang.act.ac.ext;

import com.cloud.cang.act.*;
import com.cloud.cang.act.ac.service.CouponBatchService;
import com.cloud.cang.act.ac.service.CouponIntegralActivityService;
import com.cloud.cang.act.ac.service.CouponUserSendService;
import com.cloud.cang.act.ac.service.CouponUserService;
import com.cloud.cang.act.common.ActivityConstants;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.model.ac.CouponUserSend;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 优惠券服务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service
public class CouponServiceProxy {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CouponServiceProxy.class);

    @Autowired
    private CouponUserService couponUserService;

    @Autowired
    private CouponIntegralActivityService couponIntegralActivityService;

    @Autowired
    private CouponBatchService couponBatchService;
    @Autowired
    private CouponUserSendService couponUserSendService;

    /**
     * cloudCang查券
     *
     * @param couponQueryDto
     * @return
     */
    public ResponseVo<List<CouponQueryResult>> queryCoupon(
            CouponQueryDto couponQueryDto) {

        List<CouponUser> couponUserList = couponUserService
                .selectCouponUserByMemberId(couponQueryDto);
        if (null == couponUserList) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户持有券为空！");
        }

        LOGGER.debug("查询到券的总数为：{}，开始过滤符合条件的券..", couponUserList.size());

        List<CouponQueryResult> resultList = new ArrayList<CouponQueryResult>();
        // 3.使用条件
        Iterator<CouponUser> it = couponUserList.iterator();
        while (it.hasNext()) {
            CouponUser couponUser = it.next();
            ResponseVo limitResult = valicateLimit(couponUser, couponQueryDto);
            if (!limitResult.isSuccess()) {
                LOGGER.debug("券不满足限制条件：{}", limitResult.getMsg());
                continue;
            }
            CouponQueryResult couponQueryResult = new CouponQueryResult();
            couponQueryResult.setActivityInstruction(couponUser
                    .getScouponInstruction());
            couponQueryResult.setCouponCode(couponUser.getScouponCode());
            couponQueryResult.setCouponId(couponUser.getId());
            couponQueryResult.setCouponEffectiveDate(couponUser
                    .getDcouponEffectiveDate());
            couponQueryResult.setCouponExpiryDate(couponUser
                    .getDcouponExpiryDate());
            couponQueryResult.setCouponType(couponUser.getIcouponType());
            couponQueryResult.setCouponTypeName(ActivityConstants.couponTypeMap
                    .get(couponUser.getIcouponType()));
            couponQueryResult.setCouponValue(couponUser.getFcouponValue());
            couponQueryResult.setSourceInstruction(couponUser
                    .getSsourceInstruction());
            //券的使用限制
            couponQueryResult.setFuseAmount(couponUser.getFuseLimitAmount());
            couponQueryResult.setSuseLimitBrand(couponUser.getSuseLimitBrand());
            couponQueryResult.setSuseLimitCategory(couponUser.getSuseLimitCategory());
            couponQueryResult.setSuseLimitCommodity(couponUser.getSuseLimitCommodity());
            couponQueryResult.setSuseLimitDevice(couponUser.getSuseLimitDevice());
            resultList.add(couponQueryResult);
        }

        LOGGER.debug("用户ID：" + couponQueryDto.getMemberId() + "，持有有效券的数量为："
                + resultList.size());
        return ResponseVo.getSuccessResponse(resultList);
    }

    /**
     * cloud验券
     *
     * @param validateCouponDto
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ResponseVo<CouponValidateResult> validateCoupon(CouponUser couponUser,
                                                           CouponValidateDto validateCouponDto) {

        // 3.使用条件
        ResponseVo limitResult = valicateLimit(couponUser, validateCouponDto);

        if (!limitResult.isSuccess()) {
            LOGGER.debug("使用条件校验失败：{}", limitResult.getMsg());
            return limitResult;
        }

        //返回结果
        CouponValidateResult couponValidateResult = new CouponValidateResult();

        // 如果购买金额小于使用要求额度(满减）
        if (ActivityConstants.ICOUPON_TYPE_MANJIAN.equals(couponUser
                .getIcouponType())) {

            if (validateCouponDto.getUseOrderAmount()
                    .compareTo(couponUser.getFuseLimitAmount()) < 0) {
                LOGGER.debug("使用条件校验失败：使用金额不满足使用要求！");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("购"
                        + getAccountantMoney(couponUser.getFuseLimitAmount())
                        + "元以上才能使用该券");
            }
            //满减 券的起投金额
            couponValidateResult.setFuseInvestmentAmount(couponUser.getFuseLimitAmount());
            //券的抵扣金额
            couponValidateResult.setFdeductibleAmount(couponUser.getFcouponValue());
            //券面值
            // couponValidateResult.setCouponValue(BigDecimal.ZERO);
        }
        //抵扣券
        if (ActivityConstants.ICOUPON_TYPE_DIKOU.equals(couponUser
                .getIcouponType())) {
            //couponValidateResult.setFdeductibleAmount（抵扣金额）
            //券面值
            BigDecimal couponValue = couponUser.getFcouponValue();
            //订单金额
            BigDecimal value = validateCouponDto.getUseOrderAmount();
            //设置券的抵扣金额
            if (couponValue.compareTo(value) < 0) {
                couponValidateResult.setFdeductibleAmount(couponValue);
            } else {
                couponValidateResult.setFdeductibleAmount(value);
            }
            //券面值
            // couponValidateResult.setCouponValue(BigDecimal.ZERO);
        }
        //现金券
        if (ActivityConstants.ICOUPON_TYPE_XIANJIN.equals(couponUser
                .getIcouponType())) {
            //券面值
            BigDecimal couponValue = couponUser.getFcouponValue();
            //订单金额
            BigDecimal value = validateCouponDto.getUseOrderAmount();
            //设置券的抵扣金额
            if (couponValue.compareTo(value) <= 0) {
                couponValidateResult.setFdeductibleAmount(couponValue);
                //券面值
                // couponValidateResult.setCouponValue(BigDecimal.ZERO);
            } else {
                couponValidateResult.setFdeductibleAmount(value);
                //券面值
                // couponValidateResult.setCouponValue(couponValue.subtract(value));
            }
        }

        //商品券
        if (ActivityConstants.ICOUPON_TYPE_SHANGPIN.equals(couponUser
                .getIcouponType())) {
            if (StringUtils.isNotBlank(couponUser.getSuseLimitCommodity()) && couponUser.getSuseLimitCommodity().equals(validateCouponDto.getSuseLimitCommodity())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("该订单商品不能使用对应的券！");
            }
            //抵扣金额
            couponValidateResult.setFdeductibleAmount(validateCouponDto.getUseOrderAmount());
            //券面值
            //couponValidateResult.setCouponValue(BigDecimal.ZERO);
        }

        couponValidateResult.setActivityInstruction(couponUser.getScouponInstruction()
        );
        couponValidateResult.setCouponCode(couponUser.getScouponCode());
        couponValidateResult.setCouponId(couponUser.getId());
        couponValidateResult.setCouponEffectiveDate(couponUser
                .getDcouponEffectiveDate());
        couponValidateResult
                .setCouponExpiryDate(couponUser.getDcouponExpiryDate());
        couponValidateResult.setCouponType(couponUser.getIcouponType());
        couponValidateResult.setCouponTypeName(ActivityConstants.couponTypeMap
                .get(couponUser.getIcouponType()));
        couponValidateResult.setSourceInstruction(couponUser
                .getSsourceInstruction());
        //券面值
        couponValidateResult.setCouponValue(couponUser
                .getFcouponValue());
        //订单金额
        couponValidateResult.setOrderAmount(validateCouponDto.getUseOrderAmount());
        couponValidateResult.setFuseInvestmentAmount(couponUser
                .getFuseLimitAmount());

        ResponseVo<CouponValidateResult> result = ResponseVo
                .getSuccessResponse(couponValidateResult);

        LOGGER.debug("验券结果：{}", result);
        return result;
    }

    /**
     * cloudCang使用券
     *
     * @param couponUseDto
     * @param couponValidateResult
     * @return
     */
    public ResponseVo<CouponValidateResult> useCoupon(CouponUseDto couponUseDto,
                                                      CouponValidateResult couponValidateResult) {

        return couponIntegralActivityService.useCoupon(couponUseDto, couponValidateResult);

    }

    /**
     * cloudCang更新券
     *
     * @param upCouponDto
     * @return
     */
    public ResponseVo upCoupon(UpCouponDto upCouponDto) {

        return couponIntegralActivityService.upCoupon(upCouponDto);

    }

    /**
     * @param money 待处理的人民币
     * @return
     * @title 添加会计标识：','
     */
    public static String getAccountantMoney(BigDecimal money) {
        String disposeMoneyStr = money.setScale(2, RoundingMode.HALF_UP)
                .toString();
        // 小数点处理
        int dotPosition = disposeMoneyStr.indexOf(".");
        String exceptDotMoeny = null;// 小数点之前的字符串
        String dotMeony = null;// 小数点之后的字符串
        if (dotPosition > 0) {
            exceptDotMoeny = disposeMoneyStr.substring(0, dotPosition);
            dotMeony = disposeMoneyStr.substring(dotPosition);
        } else {
            exceptDotMoeny = disposeMoneyStr;
        }
        // 负数处理
        int negativePosition = exceptDotMoeny.indexOf("-");
        if (negativePosition == 0) {
            exceptDotMoeny = exceptDotMoeny.substring(1);
        }
        StringBuffer reverseExceptDotMoney = new StringBuffer(exceptDotMoeny);
        reverseExceptDotMoney.reverse();// 字符串倒转
        char[] moneyChar = reverseExceptDotMoney.toString().toCharArray();
        StringBuffer returnMeony = new StringBuffer();// 返回值
        for (int i = 0; i < moneyChar.length; i++) {
            if (i != 0 && i % 3 == 0) {
                returnMeony.append(",");// 每隔3位加','
            }
            returnMeony.append(moneyChar[i]);
        }
        returnMeony.reverse();// 字符串倒转
        if (dotPosition > 0) {
            returnMeony.append(dotMeony);
        }
        if (negativePosition == 0) {
            return "-" + returnMeony.toString();
        } else {
            return returnMeony.toString();
        }
    }

    /**
     * cloudCang使用要求校验
     *
     * @param couponUser,couponQueryDto
     * @return ResponseVo
     */

    @SuppressWarnings("rawtypes")
    private ResponseVo valicateLimit(CouponUser couponUser, CouponQueryDto couponQueryDto) {
        if (null == couponUser) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户持有券为空！");
        }

        // 状态是否有效
        if (!couponUser.getIstatus().equals(ActivityConstants.COUPON_STATUS_NOTUSE)) {
            LOGGER.debug("券状态不是未使用状态,券ID：{}", couponUser.getId());
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券不是未使用状态！");
        }

        // 如果失效日期为空，则表示永不失效
        Date nowDate = new Date();
        if (couponUser.getDcouponExpiryDate() != null
                && couponUser.getDcouponEffectiveDate() != null) {
            if (nowDate.getTime() > couponUser.getDcouponExpiryDate().getTime()
                    || nowDate.getTime() < couponUser.getDcouponEffectiveDate()
                    .getTime()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券不在有效期");
            }
        }
        // 券的用户ID和参数的用户ID不一致
        if (!couponUser.getSmemberId().equals(couponQueryDto.getMemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券与当前用户不匹配！");
        }
        //订单金额使用限制
        if (ActivityConstants.ICOUPON_TYPE_MANJIAN.equals(couponUser.getIcouponType())) {
            if (couponUser.getFuseLimitAmount().compareTo(BigDecimal.ZERO) != 0 && couponQueryDto.getUseOrderAmount().compareTo(couponUser.getFuseLimitAmount()) < 0) {
                LOGGER.debug("该订单金额不能使用对应的券！");
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("该订单金额不能使用对应的券！");
            }
        }
        //订单设备使用限制
        if (StringUtils.isNotBlank(couponUser.getSuseLimitDevice()) && !Arrays.asList(couponUser.getSuseLimitDevice().split(",")).contains(couponQueryDto.getSuseLimitDevice())) {
            LOGGER.debug("该订单设备不能使用对应的券！");
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("该订单设备不能使用对应的券！");
        }
        //商品分类限制
        List fuseLimitCategoryList = Arrays.asList(couponQueryDto.getSuseLimitCategory().split(","));
        //商品品牌限制
        List fuseLimitBrandList = Arrays.asList(couponQueryDto.getSuseLimitBrand().split(","));
        //订单商品使用限制
        List fuseLimitCommodityList = Arrays.asList(couponQueryDto.getSuseLimitCommodity().split(","));

        List<Integer> list = new ArrayList();
        for (int x = 0; x < fuseLimitCategoryList.size(); x++) {
            if (StringUtils.isBlank(couponUser.getSuseLimitCategory()) || Arrays.asList(couponUser.getSuseLimitCategory().split(",")).contains(fuseLimitCategoryList.get(x))) {
                list.add(x);
            }
        }
        if (0 == list.size()) {
            LOGGER.debug("该订单分类不能使用对应的券！");
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("该订单分类不能使用对应的券！");
        }
        List<Integer> list2 = new ArrayList();
        for (Integer y : list) {
            if (StringUtils.isBlank(couponUser.getSuseLimitBrand()) || Arrays.asList(couponUser.getSuseLimitBrand().split(",")).contains(fuseLimitBrandList.get(y))) {
                list2.add(y);
            }
        }
        if (0 == list2.size()) {
            LOGGER.debug("该订单品牌不能使用对应的券！");
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("该订单品牌不能使用对应的券！");
        }
        List<Integer> list3 = new ArrayList<>();
        for (Integer z : list2) {
            if (StringUtils.isBlank(couponUser.getSuseLimitCommodity()) || Arrays.asList(couponUser.getSuseLimitCommodity().split(",")).contains(fuseLimitCommodityList.get(z))) {
                list3.add(z);
            }
        }
        if (0 == list3.size()) {
            LOGGER.debug("该订单商品不能使用对应的券！");
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("该订单商品不能使用对应的券！");
        }
        return ResponseVo.getSuccessResponse();
 /*       List<Integer> list = new ArrayList();
        if (StringUtils.isNotBlank(couponUser.getSuseLimitBrand())) {
            List suseLimitCommodity = Arrays.asList(couponUser.getSuseLimitCommodity().split(","));
            for (int x = 0; x < fuseLimitCommodityList.size(); x++) {
                if (suseLimitCommodity.contains(fuseLimitCommodityList.get(x))) {
                    list.add(x);
                }
            }
            if (list.size() == 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("该订单商品不能使用对应的券！");
            }
        } else {
            for (int x = 0; x <= fuseLimitCommodityList.size(); x++) {
                list.add(x);
            }
        }
        //商品品牌限制
        List<Integer> list2 = new ArrayList();
        if (StringUtils.isNotBlank(couponUser.getSuseLimitBrand())) {
            List suseLimitBrand = Arrays.asList(couponUser.getSuseLimitBrand().split(","));
            for (int x = 0; x < list.size(); x++) {
                if (list.get(x) > suseLimitBrand.size()) {
                    break;
                } else {
                    if (suseLimitBrand.contains(fuseLimitBrandList.get(list.get(x)))) {
                        list2.add(list.get(x));
                    }
                }
            }
            if (list2.size() == 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("该订单商品品牌不能使用对应的券！");
            }
        } else {
            for (int x = 0; x < fuseLimitBrandList.size(); x++) {
                list2.add(x);
            }
        }
        //商品分类限制
        List list3 = new ArrayList();
        if (StringUtils.isNotBlank(couponUser.getSuseLimitCategory())) {
            List suseLimitCategory = Arrays.asList(couponUser.getSuseLimitCategory().split(","));
            for (int x = 0; x < list2.size(); x++) {
                if (list2.get(x) > fuseLimitCategoryList.size()) {
                    break;
                } else {
                    if (suseLimitCategory.contains(fuseLimitCategoryList.get(list2.get(x)))) {
                        list3.add(list2.get(x));
                    }
                }
            }
            if (list3.size() == 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("该订单分类不能使用对应的券！");
            }
        }
        */


    }


    /**
     * 优惠券下发
     *
     * @param sendSingleCouponDto
     * @return
     */
    public ResponseVo<CouponQueryResult> sendSingleCoupon(SendSingleCouponDto sendSingleCouponDto) {

        return couponIntegralActivityService.sendSingleCoupon(sendSingleCouponDto);
    }


    /**
     * 多张优惠券下发
     *
     * @param sendSingleCouponDto
     * @return
     */
    public ResponseVo<List<CouponQueryResult>> sendMutipleCoupon(SendSingleCouponDto sendSingleCouponDto) {

        return couponIntegralActivityService.sendMutipleCoupon(sendSingleCouponDto);
    }

    /**
     * 优惠券批量下发
     *
     * @param batchCouponDto
     */

    public ResponseVo batchCoupon(BatchCouponDto batchCouponDto) {
        CouponBatch couponBatch = couponBatchService
                .selectByPrimaryKey(batchCouponDto.getBatchId());
        //取下发用户数据
        List<CouponUserSend> userSendList = couponUserSendService.selectCouponUserSendByBatchId(batchCouponDto.getBatchId());
        if (userSendList == null || userSendList.isEmpty()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo();
        }
        couponIntegralActivityService.batchCoupon(batchCouponDto.getSaddUser(),couponBatch, userSendList);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 券码兑换
     *
     * @param exchangeCouponDto
     */
    public ResponseVo<CouponQueryResult> exchangeCoupon(ExchangeCouponDto exchangeCouponDto) {

        return couponIntegralActivityService.exchangeCoupon(exchangeCouponDto);
    }
}
