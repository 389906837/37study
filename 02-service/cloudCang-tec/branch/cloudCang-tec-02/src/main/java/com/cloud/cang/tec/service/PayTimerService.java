package com.cloud.cang.tec.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.*;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.hy.service.MemberInfoService;
import com.cloud.cang.tec.om.service.OrderRecordService;
import com.cloud.cang.tec.om.vo.OrderVo;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 订单支付定时任务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service(value = "payTimerService")
public class PayTimerService {
    private static final Logger logger = LoggerFactory.getLogger(PayTimerService.class);

    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ICached iCached;

    /**
     * 订单支付失败代支付
     */
    public void failedOrderPayJob() {
        logger.info("异常订单支付定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByOrder(10);
                } catch (Exception e) {
                    logger.error("订单支付失败代支付定时任务失败", e);
                    return "订单支付失败代支付定时任务失败";
                }
            }
        }, TecConstants.FAILED_ORDER_PAY_TASK, true);
    }

    /**
     * 付款处理中订单补处理
     */
    public void inPaymentOrderTreatmentJob() {
        logger.info("付款处理中订单补处理定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByOrder(20);
                } catch (Exception e) {
                    logger.error("付款处理中订单补处理定时任务失败", e);
                    return "付款处理中订单补处理定时任务失败";
                }
            }
        }, TecConstants.IN_PAYMENT_ORDER_TREATMENT_TASK, true);
    }


    /**
     * 支付失败订单分页操作
     *
     * @param type 定时任务类型 10 订单支付失败代支付  20 付款处理中订单补处理
     * @return
     * @throws Exception
     */
    private String pageOperByOrder(final Integer type) throws Exception {
        String msg = "定时任务执行成功";
        Page<OrderRecord> p = new Page<OrderRecord>();
        p.setPageSize(10);
        p.setPageNum(1);
       /* final OrderRecord param = new OrderRecord();*/
        final OrderVo orderVo = new OrderVo();
        if (10 == type) {
            String orderTimeCondition = BizParaUtil.get("error_order_pay_time_threshold");
            if (StringUtil.isBlank(orderTimeCondition)) {
                orderTimeCondition = "10";
            }
            orderVo.setOrderTimeCondition(orderTimeCondition);
            orderVo.setCondition(BizTypeDefinitionEnum.OrderStatus.PAYMENT_FAIL.getCode() + "," + BizTypeDefinitionEnum.OrderStatus.PENDING_PAYMENT.getCode());
           /* param.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_FAIL.getCode());*/
        } else if (20 == type) {
            orderVo.setIstatus(BizTypeDefinitionEnum.OrderStatus.IN_PAYMENT.getCode());
        }
        orderVo.setIdelFlag(0);
        p = (Page<OrderRecord>) orderRecordService.queryPage(p, orderVo);
        if (null == p || 0 == p.getPages()) {
            if (type.intValue() == 10) {
                msg = "订单支付失败代支付定时任务成功";
            } else if (type.intValue() == 20) {
                msg = "付款中订单补处理定时任务成功";
            }
            return msg;
        }
        //总页数
        int totalPage = p.getPages();
        //每个线程组执行10页
        final int pageSize = 1;
        //循环次数
        int loopPage = totalPage % pageSize == 0 ? totalPage / pageSize : totalPage / pageSize + 1;
        for (int i = 1; i <= loopPage; i++) {
            int endPage = i * pageSize;
            if (endPage > totalPage) endPage = totalPage;
            int startPage = (i - 1) * pageSize;
            int count = endPage - startPage;
            TaskAction<List<OrderRecord>>[] taskActions = new TaskAction[endPage - startPage];
            for (int page = (i - 1) * pageSize + 1; page <= endPage; page++) {
                //根据页数循环创建任务，一页一个任务
                final int _page = page;
                TaskAction<List<OrderRecord>> taskAction = new TaskAction<List<OrderRecord>>() {
                    @Override
                    public List<OrderRecord> doInAction() {
                        Page<OrderRecord> pl = new Page<OrderRecord>();
                        pl.setPageSize(10);
                        pl.setPageNum(_page);
                        pl = (Page<OrderRecord>) orderRecordService.queryPage(pl, orderVo);
                        List<OrderRecord> orderRecordList = pl.getResult();
                        for (OrderRecord orderRecord : orderRecordList) {
                            //执行定时任务方法
                            if (10 == type.intValue()) {
                                errorOrderPayByOrderRecord(orderRecord);
                            } else if (20 == type.intValue()) {
                                inPaymentOrderTreatment(orderRecord);
                            }
                        }
                        return orderRecordList;
                    }
                };
                //加到任务数组里面
                taskActions[count - 1] = taskAction;
                count--;
            }
            //执行线程
            ExecutorManager.getInstance().executeTask(taskActions);
        }

        if (type.intValue() == 10) {
            msg = "订单支付失败代支付定时任务成功";
        } else if (type.intValue() == 20) {
            msg = "付款中订单补处理定时任务成功";
        }
        return msg;
    }

    /**
     * 付款处理中订单补处理 根据订单
     *
     * @param orderRecord 付款处理中订单信息
     */
    private void inPaymentOrderTreatment(OrderRecord orderRecord) {
        logger.info("付款处理中订单补处理定时任务开始：{}", orderRecord);
        // 组装数据
        RepairProcessDto repairProcessDto = new RepairProcessDto();
        repairProcessDto.setSmerchantCode(orderRecord.getSmerchantCode());
        repairProcessDto.setSorderId(orderRecord.getId());
        repairProcessDto.setSordercode(orderRecord.getSorderCode());
        repairProcessDto.setSmemberId(orderRecord.getSmemberId());
        repairProcessDto.setItype(10);//补处理类型  10 支付 20 退款
        if (StringUtil.isNotBlank(orderRecord.getSpaySerialNumber())) {//支付交易流水号  如果有就传
            repairProcessDto.setSpaySerialNumber(repairProcessDto.getSpaySerialNumber());
        }
        repairProcessDto.setIpayType(orderRecord.getIpayType());//订单支付方式 30 微信 40 支付宝
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.REPAIR_PROCESS);
            invoke.setRequestObj(repairProcessDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<HashMap<String, Object>>>() {
            });
            ResponseVo<HashMap<String, Object>> responseVo = (ResponseVo<HashMap<String, Object>>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess()) {
                //补处理成功,订单状态修改为已支付
                logger.info("定时任务调用订单支付补处理接口服务成功");
            } else {
                logger.error("定时任务调用订单支付补处理接口服务失败{}", JSON.toJSONString(responseVo));
            }
        } catch (Exception e) {
            logger.error("定时任务调用订单支付补处理接口服务异常", e);
        }
    }

    /**
     * 异常订单支付 根据订单
     *
     * @param orderRecord 支付失败订单信息
     */
    private void errorOrderPayByOrderRecord(OrderRecord orderRecord) {
        logger.info("订单支付失败代支付定时任务开始：{}", orderRecord);
      /*  Long day = (new Date().getTime() - orderRecord.getTaddTime().getTime()) / (1000 * 3600 * 24);
        if (0 >= day.compareTo(Long.valueOf(BizParaUtil.get("error_order_pay_time_threshold")))) {*/
        //查看会员是否开通免密
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderRecord.getSmemberId());
        //支付宝支付失败订单
        if (BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode() == orderRecord.getIpayType()) {
            //会员开通支付宝免密支付则代付
            if (1 == memberInfo.getIaipayOpen()) {
                //支付宝免密支付查询
                PaymentDto paymentDto = new PaymentDto();
                paymentDto.setSmerchantCode(orderRecord.getSmerchantCode());//收款商户
                paymentDto.setOutTradeNo(orderRecord.getSorderCode());//商户订单编号 必填
                try {
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_QUERY_PAYMENT);
                    invoke.setRequestObj(paymentDto); // post 参数
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<QueryPaymentResult>>() {
                    });
                    ResponseVo<QueryPaymentResult> responseVo = (ResponseVo<QueryPaymentResult>) invoke.invoke();
                    if (null != responseVo && ((responseVo.isSuccess() && null != responseVo.getData() && ("WAIT_BUYER_PAY".equals(responseVo.getData().getTradeStatus()) || "TRADE_CLOSED".equals(responseVo.getData().getTradeStatus()))) ||
                            (!responseVo.isSuccess() && -1000 == responseVo.getErrorCode()))) {
                      /*      if ("WAIT_BUYER_PAY".equals(responseVo.getData().getTradeStatus()) || "TRADE_CLOSED".equals(responseVo.getData().getTradeStatus())) {*/
                        FreePaymentDto freePaymentDto = new FreePaymentDto();
                        freePaymentDto.setSmerchantCode(orderRecord.getSmerchantCode());
                        freePaymentDto.setSmemberId(orderRecord.getSmemberId());
                        freePaymentDto.setSmemberCode(orderRecord.getSmemberCode());
                        freePaymentDto.setSmemberName(orderRecord.getSmemberName());
                        freePaymentDto.setSorderId(orderRecord.getId());
                        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.MERCHANT_INFO_DETAILS + orderRecord.getSmerchantCode());
                        if (StringUtils.isBlank(json)) {
                            logger.info("异常订单支付,从redis取商户信息,商户不存在:{}", orderRecord);
                            return;
                        }
                        MerchantInfo merchantInfo = JSONObject.parseObject(json, MerchantInfo.class);
                        freePaymentDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getId());
                        freePaymentDto.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());
                        freePaymentDto.setIisIgnoreStatus(1);
                        invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_PAYMENT);
                        invoke.setRequestObj(freePaymentDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                        });
                        ResponseVo<FreePaymentResult> responseVo2 = (ResponseVo<FreePaymentResult>) invoke.invoke();
                        if (null != responseVo2 && responseVo2.isSuccess()) {
                            logger.info("定时任务异常订单支付成功,订单编号：{}", orderRecord.getSorderCode());
                        }
                    }
                       /* }*/
                } catch (Exception e) {
                    logger.error("定时任务支付宝免密支付查询异常", e);
                }
            }
        }
        //微信支付失败订单
        if (BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode() == orderRecord.getIpayType()) {
            //会员开通微信免密支付则代付
            if (1 == memberInfo.getIwechatOpen()) {
                //微信免密支付查询
                PaymentDto paymentDto = new PaymentDto();
                paymentDto.setSmerchantCode(orderRecord.getSmerchantCode());//收款商户
                paymentDto.setOutTradeNo(orderRecord.getSorderCode());//商户订单编号 必填
                try {
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_QUERY_PAYMENT);
                    invoke.setRequestObj(paymentDto); // post 参数
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<QueryWechatFreePayResult>>() {
                    });
                    ResponseVo<QueryWechatFreePayResult> responseVo = (ResponseVo<QueryWechatFreePayResult>) invoke.invoke();
                   /* if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {*/
                    /*    String state = responseVo.getData().getTrade_state();
                        if ("CLOSED".equals(state) || "PAY_FAIL".equals(state)) {*/
                    if (null != responseVo && ((responseVo.isSuccess() && null != responseVo.getData() && ("CLOSED".equals(responseVo.getData().getTrade_state()) || "PAY_FAIL".equals(responseVo.getData().getTrade_state()))) ||
                            (!responseVo.isSuccess() && -1000 == responseVo.getErrorCode()))) {
                        FreePaymentDto freePaymentDto = new FreePaymentDto();
                        freePaymentDto.setSmerchantCode(orderRecord.getSmerchantCode());
                        freePaymentDto.setSmemberId(orderRecord.getSmemberId());
                        freePaymentDto.setSmemberCode(orderRecord.getSmemberCode());
                        freePaymentDto.setSmemberName(orderRecord.getSmemberName());
                        freePaymentDto.setSorderId(orderRecord.getId());
                        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.MERCHANT_INFO_DETAILS + orderRecord.getSmerchantCode());
                        if (StringUtils.isBlank(json)) {
                            logger.info("商户不存在！");
                            return;
                        }
                        MerchantInfo merchantInfo = JSONObject.parseObject(json, MerchantInfo.class);
                        freePaymentDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getSorderCode());//商户明细+订单编号
                        freePaymentDto.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());
                        freePaymentDto.setIisIgnoreStatus(1);
                       /*     try {*/
                        invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_PAYMENT);
                        invoke.setRequestObj(freePaymentDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                        });
                        ResponseVo<FreePaymentResult> responseVo2 = (ResponseVo<FreePaymentResult>) invoke.invoke();
                        if (null != responseVo2 && responseVo2.isSuccess()) {
                            logger.info("定时任务异常订单支付成功,订单编号：{}", orderRecord.getSorderCode());
                        }
                           /* } catch (Exception e) {
                                logger.error("定时任务微信免密支付异常", e);
                            }*/
                    }
               /*     }*/
                } catch (Exception e) {
                    logger.error("定时任务微信免密支付查询异常", e);
                }
               /* }*/
            }
        }
    }
}
