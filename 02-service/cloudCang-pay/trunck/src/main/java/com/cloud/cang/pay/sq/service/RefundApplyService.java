package com.cloud.cang.pay.sq.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.pay.RefundApplyDto;

import java.util.HashMap;

public interface RefundApplyService extends GenericService<RefundApply, String> {

    /**
     * 支付宝退款申请
     *
     * @param refundApplyDto 退款申请参数
     * @throws Exception
     */
    ResponseVo<HashMap<String, Object>> generateAliPayRefundApply(RefundApplyDto refundApplyDto) throws Exception;

    /**
     * 积分退款申请
     *
     * @param refundApplyDto 退款申请参数
     * @throws Exception
     */
    ResponseVo<HashMap<String, Object>> generatePointRefundApply(RefundApplyDto refundApplyDto) throws Exception;

    /**
     * 微信退款申请
     *
     * @param refundApplyDto 退款申请参数
     * @throws Exception
     */
    ResponseVo<HashMap<String, Object>> generateWechatRefundApply(RefundApplyDto refundApplyDto) throws Exception;

    /**
     * 生成退款申请信息
     *
     * @param refundApplyDto 退款请求参数
     * @throws Exception
     */
    ResponseVo<RefundApply> generateRefundApply(RefundApplyDto refundApplyDto) throws Exception;
}