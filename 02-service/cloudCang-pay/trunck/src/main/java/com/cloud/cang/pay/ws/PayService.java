package com.cloud.cang.pay.ws;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.jy.CreatOrderResult;
import com.cloud.cang.pay.*;
import com.cloud.cang.pay.sq.service.PayApplyService;
import com.cloud.cang.pay.sq.service.RefundApplyService;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 付款申请 创建付款订单 退款申请
 *
 * @author zhouhong
 */
@RestController
@RequestMapping("/payService")
@RegisterRestResource
public class PayService {

    @Autowired
    private RefundApplyService refundApplyService;
    @Autowired
    private PayApplyService payApplyService;

    private static final Logger logger = LoggerFactory.getLogger(PayService.class);

    /**
     * 创建手动支付
     *
     * @param payApplyDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createPayApply", method = RequestMethod.POST)
    public ResponseVo<PayBackDto> createPayApply(@RequestBody PayApplyDto payApplyDto) {
        logger.info("创建付款申请服务开始...{}", payApplyDto);
        ResponseVo<PayBackDto> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            ResponseVo<String> validateResult = validateCreateParam(payApplyDto);
            if (!validateResult.isSuccess()) {
                logger.info("创建订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }
            //判断选择支付方式
            if (payApplyDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()) {
                //微信支付
                if (null == payApplyDto.getIpayWay()) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款方式不能为空");
                } else if (payApplyDto.getIpayWay().intValue() == BizTypeDefinitionEnum.PayWay.PUBLIC_NUMBER.getCode() && StringUtils.isBlank(payApplyDto.getSmemberOpenId())) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员微信授权信息不能为空");
                }
                return payApplyService.generateWechatPayApply(payApplyDto);
                //支付宝支付
            } else if (payApplyDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {
                return payApplyService.generateAliPayApply(payApplyDto);
            }

        } catch (Exception e) {
            logger.error("创建付款申请服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("付款申请失败，订单异常");
        return responseVo;
    }

    /**
     * 积分抵扣
     *
     * @param deductionPointDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/deductionPoint", method = RequestMethod.POST)
    public ResponseVo<CreatOrderResult> deductionPoint(@RequestBody DeductionPointDto deductionPointDto) {
        logger.info("创建积分抵扣申请服务开始...{}", JSON.toJSONString(deductionPointDto));
        ResponseVo<CreatOrderResult>  responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
         /*   ResponseVo<String> validateResult = validateCreateParam(payApplyDto);
            if (!validateResult.isSuccess()) {
                logger.info("积分抵扣参数校验失败：{}",validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }*/
            if (StringUtil.isBlank(deductionPointDto.getOrderId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单号不能为空");
            }
            if (StringUtil.isBlank(deductionPointDto.getMobile())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("手机号不能为空");
            }
            return payApplyService.deductionPoint(deductionPointDto);
        } catch (Exception e) {
            logger.error("调用给第三方扣除积分异常：{}", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("积分抵扣失败，订单异常");
        return responseVo;
    }

    /**
     * 退款申请
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/refundApply", method = RequestMethod.POST)
    public ResponseVo<HashMap<String, Object>> refundApply(@RequestBody RefundApplyDto refundApplyDto) {
        logger.info("退款申请服务开始...{}", refundApplyDto);
        ResponseVo<HashMap<String, Object>> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            if (StringUtils.isBlank(refundApplyDto.getSmemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员不能为空");
            } else if (StringUtils.isBlank(refundApplyDto.getSorderId())
                    || StringUtils.isBlank(refundApplyDto.getSorderCode())
                    || ((refundApplyDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()
                    || refundApplyDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode())
                    && StringUtils.isBlank(refundApplyDto.getStransactionId()))) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("退款订单信息不能为空");
            } else if (refundApplyDto.getIpayType().intValue() != BizTypeDefinitionEnum.PayType.PAY_POINT.getCode() &&
                    (refundApplyDto.getFrefundMoney() == null || refundApplyDto.getFrefundMoney().doubleValue() <= 0)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("退款金额不能为空");
            } else if (refundApplyDto.getIpayType() == null) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("退款方式不能为空");
            }
            //判断选择支付方式
            if (refundApplyDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()) {
                //微信支付
                return refundApplyService.generateWechatRefundApply(refundApplyDto);
            } else if (refundApplyDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {
                //支付宝支付
                return refundApplyService.generateAliPayRefundApply(refundApplyDto);
            } else if (refundApplyDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_POINT.getCode()) {
                //积分支付
                return refundApplyService.generatePointRefundApply(refundApplyDto);
            }
        } catch (Exception e) {
            logger.error("退款申请服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("退款申请失败，订单异常");
        return responseVo;
    }

    /**
     * 补处理接口
     *
     * @param repairProcessDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/payRepairProcess", method = RequestMethod.POST)
    public ResponseVo<HashMap<String, Object>> payRepairProcess(@RequestBody RepairProcessDto repairProcessDto) {
        logger.info("补处理服务开始...{}", repairProcessDto);
        ResponseVo<HashMap<String, Object>> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            if (StringUtils.isBlank(repairProcessDto.getSmemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员不能为空");
            } else if (StringUtils.isBlank(repairProcessDto.getSorderId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("操作订单号不能为空");
            } else if (repairProcessDto.getItype() == null || (repairProcessDto.getItype().intValue() != 10
                    && repairProcessDto.getItype().intValue() != 20)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补处理操作类型异常");
            }
            //判断选择支付方式
            if (repairProcessDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()) {
                //微信支付
                return payApplyService.payWechatRepairProcess(repairProcessDto);
            } else if (repairProcessDto.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {
                //支付宝支付
                return payApplyService.payAliPayRepairProcess(repairProcessDto);
            }
        } catch (Exception e) {
            logger.error("补处理服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("补处理失败，订单异常");
        return responseVo;
    }


    /**
     * 下载第三方支付账单
     * w
     *
     * @param downloadBillDto 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/downloadBill", method = RequestMethod.POST)
    public ResponseVo<String> downloadBill(@RequestBody DownloadBillDto downloadBillDto, HttpServletRequest request) {
        logger.info("下载第三方支付账单服务开始...{}", downloadBillDto);
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            if (StringUtils.isBlank(downloadBillDto.getSmerchantCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("操作商户不能为空");
            } else if (StringUtils.isBlank(downloadBillDto.getBillType())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("账单类型不能为空");
            } else if (StringUtils.isBlank(downloadBillDto.getBillDate())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("下载账单日期不能为空");
            } else if (null == downloadBillDto.getItype()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("第三方支付类型不能为空");
            }
            //判断选择支付方式
            if (downloadBillDto.getItype().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()) {
                //微信支付
                return payApplyService.wechatDownloadBill(downloadBillDto, request);
            } else if (downloadBillDto.getItype().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {
                //支付宝支付
                return payApplyService.alipayDownloadBill(downloadBillDto, request);
            }
        } catch (Exception e) {
            logger.error("下载第三方支付账单服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("下载第三方支付账单失败");
        return responseVo;
    }

    /**
     * 校验基础参数
     *
     * @param payApplyDto
     * @return
     */
    @SuppressWarnings("unchecked")
    private ResponseVo<String> validateCreateParam(PayApplyDto payApplyDto) {
        logger.info("创建付款申请校验参数开始.....参数：{}", payApplyDto);
        if (StringUtil.isBlank(payApplyDto.getSmemberCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户编号不能为空");
        } else if (StringUtils.isBlank(payApplyDto.getSmemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员不能为空");
        } else if (StringUtils.isBlank(payApplyDto.getSorderId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款订单不能为空");
        } else if (StringUtil.isBlank(payApplyDto.getSsubject())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款订单标题不能为空");
        } else if (payApplyDto.getIpayType() == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款类型不能为空");
        }
        logger.debug("创建付款申请校验参数成功.....");
        return ResponseVo.getSuccessResponse();
    }
}
