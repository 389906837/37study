package com.cloud.cang.act.ws;

import com.cloud.cang.act.*;
import com.cloud.cang.act.ac.ext.CouponServiceProxy;
import com.cloud.cang.act.ac.service.CouponBatchService;
import com.cloud.cang.act.ac.service.CouponUserService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 券服务
 * 查券 验券 用券  更新优惠券状态
 *
 * @author yanlingfeng
 * @version 1.0
 */
@RestController
@RequestMapping("/couponService")
@RegisterRestResource
public class CouponService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CouponService.class);

    @Autowired
    private CouponServiceProxy couponServiceProxy;

    @Autowired
    private CouponUserService couponUserService;

    @Autowired
    private CouponBatchService couponBatchService;

    /**
     * cloudCang查券
     *
     * @param couponQueryDto 券查询参数
     * @return ResponseVo<List<CouponQueryResult>> 券查询返回
     */
    @RequestMapping(value = "/queryCoupon", method = RequestMethod.POST)
    public ResponseVo<List<CouponQueryResult>> queryCoupon(@RequestBody CouponQueryDto couponQueryDto) {

        LOGGER.debug("################查券开始！参数为：{}################",
                couponQueryDto);
        try {
            // 1.校验参数
            ResponseVo valicateResult = valicateQueryCouponParams(
                    couponQueryDto, null);
            if (!valicateResult.isSuccess()) {
                LOGGER.debug("查券校验参数失败：{}", valicateResult.getMsg());
                return valicateResult;
            }
          /*  if (Arrays.asList(couponQueryDto.getSuseLimitCategory().split(",")).size() == Arrays.asList(couponQueryDto.getSuseLimitBrand().split(",")).size()
                    && Arrays.asList(couponQueryDto.getSuseLimitCategory().split(",")).size() == Arrays.asList(couponQueryDto.getSuseLimitCommodity().split(",")).size()) {

                return couponServiceProxy.queryCoupon(couponQueryDto);
            } else {
                LOGGER.debug("限制参数数目不一致：", Arrays.asList(couponQueryDto.getSuseLimitCategory().split(",")).size());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("限制参数不一致");
            }*/
            return couponServiceProxy.queryCoupon(couponQueryDto);
        } catch (Exception e) {
            LOGGER.error("查券异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
    }

    /**
     * cloudCang验券
     *
     * @param validateCouponDto
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/validateCoupon", method = RequestMethod.POST)
    public ResponseVo<CouponValidateResult> validateCoupon(
            @RequestBody CouponValidateDto validateCouponDto) {
        LOGGER.debug("###验券开始！validateCouponDto:{}###", validateCouponDto);
        try {
            // 1.校验参数
            if (StringUtils.isBlank(validateCouponDto.getCouponUserId())) {
                LOGGER.debug("验券校验参数失败：{}", "券ID不能为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("券ID不能为空！");
            }
            // 根据couponId查券
            CouponUser couponUser = couponUserService
                    .selectByPrimaryKey(validateCouponDto.getCouponUserId());


            if (couponUser == null) {
                LOGGER.debug("没有查询到券ID对应的券！");
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("没有查询到券ID对应的券！");
            }
            //订单实付金额
            if (couponUser.getFactualExchangeAmount() == null) couponUser.setFactualExchangeAmount(BigDecimal.ZERO);
            //券面值
            if (couponUser.getFcouponValue() == null) couponUser.setFcouponValue(BigDecimal.ZERO);

            ResponseVo valicateResult = valicateQueryCouponParams(
                    validateCouponDto, couponUser.getIcouponType());

            if (!valicateResult.isSuccess()) {
                LOGGER.debug("验券校验参数失败：{}", valicateResult.getMsg());
                return valicateResult;
            }
            return couponServiceProxy.validateCoupon(couponUser,
                    validateCouponDto);
        } catch (Exception e) {
            LOGGER.error("验券异常", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("验券异常");
        }
    }

    /**
     * cloudCang使用券
     *
     * @param couponUseDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/useCoupon", method = RequestMethod.POST)
    public ResponseVo<CouponValidateResult> useCoupon(
            @RequestBody CouponUseDto couponUseDto) {

        LOGGER.debug("################用券开始couponUseDto:{}################",
                couponUseDto);

        try {
            // 验券
            LOGGER.debug("用券前先验券。。。");
            ResponseVo<CouponValidateResult> validateCouponResult = this
                    .validateCoupon(couponUseDto);
            if (!validateCouponResult.isSuccess()) {
                LOGGER.debug("验券失败：{}", validateCouponResult.getMsg());
                return validateCouponResult;
            }

            CouponValidateResult couponValidateResult = validateCouponResult.getData();

            ResponseVo<CouponValidateResult> result = couponServiceProxy.useCoupon(couponUseDto, couponValidateResult);
            LOGGER.debug("用券成功：{}", validateCouponResult.getData());
            return result;
        } catch (Exception e) {
            LOGGER.error("用券异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }

    }

    /**
     * cloudCang更新券状态
     *
     * @param upCouponDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/upCoupon", method = RequestMethod.POST)
    public ResponseVo upCoupon(
            @RequestBody UpCouponDto upCouponDto) {
        LOGGER.debug("################更新优惠券状态开始upCouponDto:{}################",
                upCouponDto);
        try {
            // 验券
            LOGGER.debug("更新券前先验券。。。");
            ResponseVo<CouponValidateResult> validateCouponResult = this
                    .valicateUpCouponParams(upCouponDto);
            if (!validateCouponResult.isSuccess()) {
                LOGGER.debug("验券失败：{}", validateCouponResult.getMsg());
                return validateCouponResult;
            }
            ResponseVo result = couponServiceProxy.upCoupon(upCouponDto);
            LOGGER.debug("更新券成功：{}");
            return result;
        } catch (Exception e) {
            LOGGER.error("更新券异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
    }

    /**
     * cloudCang更新券参数校验
     *
     * @param upCouponDto
     * @return
     */
    private ResponseVo valicateUpCouponParams(UpCouponDto upCouponDto) {
        LOGGER.debug("参数校验开始...");
        if (null == upCouponDto) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("更新券参数对象不能为null！");
        }
       /* if (StringUtils.isBlank(upCouponDto.getMemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员ID不能为空！");
        }*/
        if (StringUtils.isBlank(upCouponDto.getCouponUserId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("券使用ID不能为空！");
        }
        CouponUser couponUser = couponUserService.selectByPrimaryKey(upCouponDto.getCouponUserId());
        if (null == couponUser) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("没有找到该优惠券！");
        }
        if (StringUtils.isBlank(upCouponDto.getTargetCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("使用订单编号不能为空！");
        }
        if (null == upCouponDto.getCouponDeductionAmount()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("优惠券抵扣金额不能为空！");
        }
/*        if (StringUtils.isBlank(upCouponDto.getSupateUser())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("修改人不能为空！");
        }*/
        LOGGER.debug("参数校验成功...");
        return ResponseVo.getSuccessResponse();
    }

    /**
     * cloudCang查券参数校验
     *
     * @param couponType
     * @return
     */

    private ResponseVo valicateQueryCouponParams(CouponQueryDto couponQueryDto,
                                                 Integer couponType) {
        LOGGER.debug("参数校验开始...");
        if (couponQueryDto == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("券查询参数对象不能为null！");
        }
        if (StringUtils.isBlank(couponQueryDto.getMemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员ID不能为空！");
        }
        if (null == couponQueryDto.getUseOrderAmount()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单金额不能为空！");
        }
     /*   if (StringUtils.isBlank(couponQueryDto.getSuseLimitBrand())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单商品品牌不能为空！");
        }
        if (StringUtils.isBlank(couponQueryDto.getSuseLimitCategory())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单商品分类不能为空！");
        }*/
        if (StringUtils.isBlank(couponQueryDto.getSuseLimitCommodity())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单商品不能为空！");
        }
        if (StringUtils.isBlank(couponQueryDto.getSuseLimitDevice())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单设备不能为空！");
        }
        //满减券

        LOGGER.debug("参数校验成功...");
        return ResponseVo.getSuccessResponse();
    }

    /**
     * cloudCang优惠券下发
     *
     * @param sendSingleCouponDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sendSingleCoupon", method = RequestMethod.POST)
    public ResponseVo<CouponQueryResult> sendSingleCoupon(
            SendSingleCouponDto sendSingleCouponDto) {
        LOGGER.debug("#################优惠券下发###参数：{}###################",
                sendSingleCouponDto);
        try {
            if (StringUtils.isBlank(sendSingleCouponDto.getMemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("会员ID不能为空！");
            }
            if (StringUtils.isBlank(sendSingleCouponDto.getSourceCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("来源单据编号不能为空！");
            }
            if (StringUtils.isBlank(sendSingleCouponDto.getCouponBatchId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("批量下发券配置id不能为空！");
            }
            if (sendSingleCouponDto.getSourceType() == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("来源类型不能为空！");
            }
            if (StringUtils.isBlank(sendSingleCouponDto.getSourceTypeDesc())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("来源单据类型说明不能为空！");
            }

            ResponseVo<CouponQueryResult> result = couponServiceProxy
                    .sendSingleCoupon(sendSingleCouponDto);
            if (!result.isSuccess()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo();
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("优惠券下发异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }

    }

    /**
     * cloudCang多张优惠券下发
     *
     * @param sendSingleCouponDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sendMutipleCoupon", method = RequestMethod.POST)
    public ResponseVo<List<CouponQueryResult>> sendMutipleCoupon(
            SendSingleCouponDto sendSingleCouponDto) {
        LOGGER.debug("#################多张优惠券下发###参数：{}###################",
                sendSingleCouponDto);
        try {
            if (StringUtils.isBlank(sendSingleCouponDto.getMemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("会员ID不能为空！");
            }
            if (StringUtils.isBlank(sendSingleCouponDto.getSourceCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("来源单据编号不能为空！");
            }
            if (StringUtils.isBlank(sendSingleCouponDto.getCouponBatchId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("批量下发券配置id不能为空！");
            }
            if (sendSingleCouponDto.getSendCount() == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("下发数量不能为空！");
            }
            if (sendSingleCouponDto.getSourceType() == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("来源类型不能为空！");
            }
            if (StringUtils.isBlank(sendSingleCouponDto.getSourceTypeDesc())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("来源单据类型说明不能为空！");
            }
            ResponseVo<List<CouponQueryResult>> result = couponServiceProxy
                    .sendMutipleCoupon(sendSingleCouponDto);
            if (!result.isSuccess()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo();
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("多张优惠券下发异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
    }

    /**
     * cloudCang优惠券批量下发
     *
     * @param batchCouponDto
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/batchCoupon", method = RequestMethod.POST)
    public ResponseVo<String> batchCoupon(
            @RequestBody BatchCouponDto batchCouponDto) {
        try {
            LOGGER.debug("#################优惠券批量下发###参数：{}###################",
                    batchCouponDto);
            if (StringUtils.isBlank(batchCouponDto.getBatchId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("优惠券批量下发Id不能为空！");
            }
            CouponBatch couponBatch = couponBatchService
                    .selectByPrimaryKey(batchCouponDto.getBatchId());
            if (null == couponBatch) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("优惠券批量下发数据不存在！");
            }
            if (1 == couponBatch.getIdelFlag()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("优惠券批量下发已删除");
            }
            if (20 != couponBatch.getIstatus()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("优惠券批量下发审核未通过！");
            }
            if (null != couponBatch.getDcouponExpiryDate() && couponBatch.getDcouponExpiryDate().getTime() < DateUtils.getCurrentDateTime().getTime()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("优惠券批量下发已失效！");
            }
            return couponServiceProxy.batchCoupon(batchCouponDto);
        } catch (ServiceException se) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            LOGGER.error("批量发券异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("批量发券异常");
        }
    }

    /**
     * cloudCang券码兑换
     *
     * @param exchangeCouponDto
     * @return
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exchangeCoupon", method = RequestMethod.POST)
    public ResponseVo<CouponQueryResult> exchangeCoupon(
            ExchangeCouponDto exchangeCouponDto) {
        LOGGER.debug("#################券码兑换开始###参数：{}###################",
                exchangeCouponDto);
        try {
            if (StringUtils.isBlank(exchangeCouponDto.getExCouponCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("兑换券码不能为空！");
            }

            if (StringUtils.isBlank(exchangeCouponDto.getMemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("会员ID不能为空！");
            }

            if (StringUtils.isBlank(exchangeCouponDto.getMemberCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("会员编号不能为空！");
            }

            if (exchangeCouponDto.getClientType() == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM
                        .getResponseVo("来源客户端不能为空！");
            }

            ResponseVo<CouponQueryResult> result = couponServiceProxy
                    .exchangeCoupon(exchangeCouponDto);
            return result;
        } catch (Exception e) {
            LOGGER.error("券码兑换异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }

    }

}
