package com.cloud.cang.pay;


import com.cloud.cang.common.ResponseVo;

import java.util.HashMap;

/**
 * 收到支付服务
 */
public class PayServicesDefine {

    /***
     * 付款申请
     * 请求参数：{@link PayApplyDto}
     * 返回参数：{@link ResponseVo<PayBackDto>}
     */
    public static final String PAY_APPLY = "com.cloud.cang.pay.ws.PayService.createPayApply";

    /***
     * 抵扣积分申请
     * 请求参数：{@link DeductionPointDto}
     * 返回参数：{@link ResponseVo<>}
     */
    public static final String DEDUCTION_POINT = "com.cloud.cang.pay.ws.PayService.deductionPoint";

    /***
     * 退款申请
     * 请求参数：{@link RefundApplyDto}
     * 返回参数：{@link ResponseVo<HashMap<String, Object>>}
     */
    public static final String REFUND_APPLY = "com.cloud.cang.pay.ws.PayService.refundApply";

    /**
     * 补处理操作
     * 请求参数：{@link RepairProcessDto}
     * 返回参数：{@link ResponseVo< HashMap <String, Object>>}
     */
    public static final String REPAIR_PROCESS = "com.cloud.cang.pay.ws.PayService.payRepairProcess";

    /**
     * 下载第三方支付账单
     * 请求参数：{@link DownloadBillDto}
     * 返回参数：{@link ResponseVo<String>}
     */
    public static final String DOWNLOAD_BILL = "com.cloud.cang.pay.ws.PayService.downloadBill";


}
