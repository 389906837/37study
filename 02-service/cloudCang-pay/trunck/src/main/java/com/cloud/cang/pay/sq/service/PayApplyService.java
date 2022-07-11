package com.cloud.cang.pay.sq.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sq.PayApply;
import com.cloud.cang.pay.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface PayApplyService extends GenericService<PayApply, String> {


    /***
     * 微信手动支付
     * @param payApplyDto 支付参数
     * @throws Exception
     */
    ResponseVo<PayBackDto> generateWechatPayApply(PayApplyDto payApplyDto) throws Exception;

    /***
     * 抵扣积分
     * @param deductionPointDto 支付参数
     * @throws Exception
     */
    ResponseVo deductionPoint(DeductionPointDto deductionPointDto) throws Exception;

    /**
     * 支付宝手动支付
     * @param payApplyDto 支付参数
     * @throws Exception
     */
    ResponseVo<PayBackDto> generateAliPayApply(PayApplyDto payApplyDto) throws Exception;

    /**
     * 支付宝补处理操作
     * @param repairProcessDto
     * @throws Exception
     */
    ResponseVo<HashMap<String,Object>> payAliPayRepairProcess(RepairProcessDto repairProcessDto) throws Exception;

    /**
     * 微信补处理操作
     * @param repairProcessDto
     * @throws Exception
     */
    ResponseVo<HashMap<String,Object>> payWechatRepairProcess(RepairProcessDto repairProcessDto) throws Exception;

    /**
     * 更新付款申请信息
     * @param pmap
     */
    void updateStatusById(Map<String, Object> pmap);

    /**
     * 微信下载账单
     * @param downloadBillDto 查询参数
     * @param request
     * @return
     */
    ResponseVo<String> wechatDownloadBill(DownloadBillDto downloadBillDto, HttpServletRequest request) throws Exception;
    /**
     * 支付宝下载账单
     * @param downloadBillDto 查询参数
     * @param request
     * @return
     */
    ResponseVo<String> alipayDownloadBill(DownloadBillDto downloadBillDto, HttpServletRequest request) throws Exception;
}