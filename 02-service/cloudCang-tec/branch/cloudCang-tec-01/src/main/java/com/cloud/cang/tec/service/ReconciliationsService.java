package com.cloud.cang.tec.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sl.CheckLog;
import com.cloud.cang.pay.DownloadBillDto;
import com.cloud.cang.pay.PayServicesDefine;
import com.cloud.cang.tec.common.ReadFileUtil;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.om.service.OrderRecordService;
import com.cloud.cang.tec.om.service.RefundAuditService;
import com.cloud.cang.tec.om.vo.AlipayReconciliationsVo;
import com.cloud.cang.tec.om.vo.OfBillAlipayVo;
import com.cloud.cang.tec.om.vo.OfBillWechantVo;
import com.cloud.cang.tec.om.vo.WechantReconciliationsVo;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.cloud.cang.tec.sl.service.CheckLogService;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付宝/微信 对账定时任务
 * Created by yan on 2018/7/3.
 */

@Service(value = "reconciliationsService")
public class ReconciliationsService {

    private static final Logger logger = LoggerFactory.getLogger(ReconciliationsService.class);

    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private RefundAuditService refundAuditService;
    @Autowired
    private CheckLogService checkLogService;

    /**
     * 支付宝对账
     */
    public void alipayReconciliationsJob() {
        logger.info("支付宝对账定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(10);
                } catch (Exception e) {
                    logger.error("支付宝对账定时任务失败", e);
                    return "支付宝对账定时任务失败";
                }
            }
        }, TecConstants.SCANNING_ORDER_TASK, true);
    }

    /**
     * 微信对账
     */
    public void wechantReconciliationsJob() {
        logger.info("微信对账定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(20);
                } catch (Exception e) {
                    logger.error("微信对账定时任务失败", e);
                    return "微信对账定时任务失败";
                }
            }
        }, TecConstants.SCANNING_ORDER_TASK, true);
    }

    /**
     * 商户分页操作
     *
     * @param type 定时任务类型: 10 支付宝对账 20 微信对账
     * @return
     * @throws Exception
     */


    private String pageOperByMerchant(final Integer type) throws Exception {
        String msg = "定时任务执行成功";
        Page<MerchantInfo> p = new Page<MerchantInfo>();
        p.setPageSize(10);
        p.setPageNum(1);
        final MerchantInfo param = new MerchantInfo();
        param.setIstatus(BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode());
        param.setIdelFlag(0);
        p = (Page<MerchantInfo>) merchantInfoService.queryPage(p, param);
        if (null == p || 0 == p.getPages()) {
            if (type.intValue() == 10) {
                msg = "支付宝对账定时任务成功";
            } else if (type.intValue() == 20) {
                msg = "微信对账定时任务成功";
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
            TaskAction<List<MerchantInfo>>[] taskActions = new TaskAction[endPage - startPage];
            for (int page = (i - 1) * pageSize + 1; page <= endPage; page++) {
                //根据页数循环创建任务，一页一个任务
                final int _page = page;
                TaskAction<List<MerchantInfo>> taskAction = new TaskAction<List<MerchantInfo>>() {
                    @Override
                    public List<MerchantInfo> doInAction() {
                        Page<MerchantInfo> pl = new Page<MerchantInfo>();
                        pl.setPageSize(10);
                        pl.setPageNum(_page);
                        pl = (Page<MerchantInfo>) merchantInfoService.queryPage(pl, param);
                        List<MerchantInfo> merchantInfoList = pl.getResult();
                        for (MerchantInfo merchantInfo : merchantInfoList) {
                            //执行定时任务方法
                            if (10 == type.intValue()) {
                                alipayReconciliationsByMerchant(merchantInfo);
                            } else if (20 == type.intValue()) {
                                wechantReconciliationsByMerchant(merchantInfo);
                            }
                        }
                        return merchantInfoList;
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
            msg = "支付宝对账定时任务成功";
        } else if (type.intValue() == 20) {
            msg = "微信对账定时任务成功";
        }
        return msg;
    }


    /**
     * 微信对账 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void wechantReconciliationsByMerchant(MerchantInfo merchantInfo) {
        //查询商户昨日有没有订单
        //有：调用pay得到支付宝对账txt进行对账 结果入数据库  双边对账结果不一致发送短信
        //无：结束
        try {
            logger.info("微信对账定时任务开始:{}", merchantInfo.getScode());
            CheckLog checkLog = new CheckLog();
            checkLog.setDcheckDate(DateUtils.getCurrentDateTime());//对账日期
            checkLog.setItype(10);// 10 微信支付 20 支付宝
            checkLog.setTbeginDatetime(DateUtils.getCurrentDateTime());
         /*   Map map = orderRecordService.selectOrderNum(merchantInfo.getId(), BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());
            Long orderNum = (Long) map.get("ORDERNUM");
            if (null != orderNum && orderNum > 0) {
            }*/
            String date = DateUtils.dateToString(DateUtils.getDateBefore(1), "yyyyMMdd");
            //调用pay查询微信对账单ftp地址
            DownloadBillDto downloadBillDto = new DownloadBillDto();
            downloadBillDto.setSmerchantCode(merchantInfo.getScode());
            downloadBillDto.setBillType("ALL");//账单类型
            downloadBillDto.setBillDate(date);//账单日期  微信格式：20140603 支付宝格式 日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
            downloadBillDto.setItype(BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());//第三方类型 30 微信 40 支付宝
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.DOWNLOAD_BILL);
            invoke.setRequestObj(downloadBillDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {
                logger.info("微信账单查询成功，{}", JSON.toJSONString(responseVo));
                //得到ftp地址,下载文件
                 /*   String[] fileNameSplit = responseVo.getData().split("\\.");
                    String exp = fileNameSplit[fileNameSplit.length - 1];*///获取后缀名====>txt
                String path = System.getProperty("catalina.home") + File.separator + "wechant" + File.separator + merchantInfo.getScode();
                File file = new File(path);
                String name = path + File.separator + DateUtils.getCurrentDTimeNumber() + ".txt";
                File file2 = new File(name);
                if (!file.exists()) {//不存在则创建路径
                    file.mkdirs();
                }
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                OutputStream outputStream = new FileOutputStream(file2);
                FtpUtils.downloadFormFtp(outputStream, EvnUtils.getValue("dynamic.http.domain") + responseVo.getData(), FtpParamUtil.getFtpUser());
                ResponseVo<WechantReconciliationsVo> responseVo2 = ReadFileUtil.resolveBillFile(name);
                if (null != responseVo2 && responseVo2.isSuccess() && null != responseVo2.getData()) {
                    Map<String, OrderRecord> resultMap = orderRecordService.selectOrderMap(merchantInfo.getId(), BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());
                    BigDecimal refundTotalMoney = BigDecimal.ZERO;
                    //开始微信对账
                    String errorOrderMsg = "";
                    String totalMoneyErrOrderMsg = "";
                    List<OfBillWechantVo> ofBillWechantVos = responseVo2.getData().getOfBillWechantVoList();
                    for (OfBillWechantVo ofBillWechantVo : ofBillWechantVos) {
                        if (ofBillWechantVo.getCommodityName().contains(merchantInfo.getSname())) {
                            //交易成功
                            String orderCode = ofBillWechantVo.getMerchantOrderNumber();
                            if ("SUCCESS".equals(ofBillWechantVo.getTransactionStatus())) {
                                OrderRecord orderRecord = resultMap.get(orderCode);
                                if (null == orderRecord) {
                                    orderRecord = orderRecordService.selectByOrderCode(merchantInfo.getId(), orderCode);
                                    if (null != orderRecord) {
                                        errorOrderMsg = orderRecord.getTorderTime() + "--" + orderCode + "--" + orderRecord.getFactualPayAmount() + ",";
                                    }
                                } else {
                                    if (ofBillWechantVo.getTotalMoney().compareTo(orderRecord.getFactualPayAmount()) != 0) {
                                        totalMoneyErrOrderMsg = orderRecord.getTorderTime() + "--" + orderCode + "--" + orderRecord.getFactualPayAmount() + ",";
                                    }
                                }
                            } else if ("REFUND".equals(ofBillWechantVo.getTransactionStatus()) && "SUCCESS".equals(ofBillWechantVo.getRefundStatus())) {
                                refundTotalMoney = refundTotalMoney.add(ofBillWechantVo.getRefundMoney());
                            }
                        }
                    }
                    BigDecimal refundMoney = refundAuditService.selectYesTodayRefundMoney(merchantInfo.getId(), BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());
                    logger.info("####对账昨日退款总金额####：{}", refundTotalMoney);
                    logger.info("####数据库昨日退款总金额####：{}", refundMoney);
                    if (StringUtils.isNotBlank(errorOrderMsg)) {
                        sendMessage("wechant_reconciliations_error_order", errorOrderMsg, "WECHANT_RECONCILIATIONS_ERROR_ORDER", merchantInfo);
                    }
                    if (StringUtils.isNotBlank(totalMoneyErrOrderMsg)) {
                        sendMessage("wechant_reconciliations_money_error", totalMoneyErrOrderMsg, "WECHANT_RECONCILIATIONS_MONEY_ERROR", merchantInfo);
                    }
                    if (refundTotalMoney.compareTo(refundMoney) != 0) {
                        String refundMsg = " 退款订单金额为" + refundMoney + "，实际微信退款金额为" + refundTotalMoney;
                        sendMessage("wechant_reconciliations_refundmoney_error", refundMsg, "WECHANT_RECONCILIATIONS_REFUNDMONEY_ERROR", merchantInfo);
                    }
                    //对账结果入库

                    checkLog.setSresult(1);//对账结果 1=成功  2=失败    3=异常
                    checkLog.setStitle(date + "-" + merchantInfo.getSname() + "-微信对账");
                    checkLog.setTendDatetime(DateUtils.getCurrentDateTime());
                    checkLog.setSremark("微信对账完成");//对账描述

                    checkLogService.insert(checkLog);

                    ReadFileUtil.deleteDirectory(path);
                }
            }
        } catch (Exception e) {
            logger.error("微信对账异常：{}", e);
        }
    }


    /**
     * 支付宝对账 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void alipayReconciliationsByMerchant(MerchantInfo merchantInfo) {
        try {
            //查询商户昨日有没有订单
            //有：调用pay得到支付宝对账zip包解析 进行对账 结果入数据库  双边对账结果不一致发送短信
            //无：结束
         /*   Map map = orderRecordService.selectOrderNum(merchantInfo.getId(), BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());
            Long orderNum = (Long) map.get("ORDERNUM");*/
            /*if (!merchantInfo.getId().equals("f6befd4fff6e11e7be44000c2937a246")) {
                return;
            }*/
            /*if (null != orderNum && orderNum > 0) {}*/
            logger.info("支付宝对账定时任务开始:{}", merchantInfo.getScode());
            CheckLog checkLog = new CheckLog();
            checkLog.setDcheckDate(DateUtils.getCurrentDateTime());//对账日期
            checkLog.setItype(20);// 10 微信支付 20 支付宝
            checkLog.setTbeginDatetime(DateUtils.getCurrentDateTime());
            String date = DateUtils.dateToString(DateUtils.getDateBefore(1), "yyyy-MM-dd");
            //调用pay查询支付宝对账单ftp地址
            DownloadBillDto downloadBillDto = new DownloadBillDto();
            downloadBillDto.setSmerchantCode(merchantInfo.getScode());
            downloadBillDto.setBillType("trade");//账单类型
            downloadBillDto.setBillDate(date);//账单日期  微信格式：20140603 支付宝格式 日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
            downloadBillDto.setItype(BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());//第三方类型 30 微信 40 支付宝
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.DOWNLOAD_BILL);
            invoke.setRequestObj(downloadBillDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {
                logger.info("支付宝账单查询成功，{}", JSON.toJSONString(responseVo));
              /*  String[] fileNameSplit = responseVo.getData().split("\\.");
                String exp = fileNameSplit[fileNameSplit.length - 1];*///获取后缀名====>zip
                String path = System.getProperty("catalina.home") + File.separator + "alipay" + File.separator + merchantInfo.getScode();
                File file = new File(path);
                String name = path + File.separator + DateUtils.getCurrentDTimeNumber() + ".zip";
                File file2 = new File(name);
                if (!file.exists()) {//不存在则创建路径
                    file.mkdirs();
                }
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                OutputStream outputStream = new FileOutputStream(file2);
                FtpUtils.downloadFormFtp(outputStream, EvnUtils.getValue("dynamic.http.domain") + responseVo.getData(), FtpParamUtil.getFtpUser());
                List<String> list = new ArrayList();
                String b = path + File.separator;
                //解压文件到b目录下
                ResponseVo responseVo1 = ReadFileUtil.unZip(file2, b, list);
                if (null != responseVo1 && responseVo1.isSuccess() && null != responseVo1.getData()) {
                    list = (List<String>) responseVo1.getData();
                    String detailName = "";
                    for (String str : list) {
                        if (!str.contains("业务明细(汇总)")) {
                            detailName = str;
                            break;
                        }
                    }
                    if (StringUtils.isNotBlank(detailName)) {
                        ResponseVo<AlipayReconciliationsVo> reconciliationsVoResponseVo = ReadFileUtil.alipayResolveBillFile(detailName);
                        if (null != reconciliationsVoResponseVo && reconciliationsVoResponseVo.isSuccess()
                                && null != reconciliationsVoResponseVo.getData()) {
                            Map<String, OrderRecord> resultMap = orderRecordService.selectOrderMap(merchantInfo.getId(), BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());
                            //开始对账
                            //支付宝对账订单不存在
                            String errorOrderMsg = "";
                            //支付宝对账订单金额不一致
                            String moneyErrorMsg = "";
                            BigDecimal totalMoney = BigDecimal.ZERO;
                            //退款总额 
                            BigDecimal refundTotalMoney = BigDecimal.ZERO;
                            List<OfBillAlipayVo> ofBillAlipayVos = reconciliationsVoResponseVo.getData().getOfBillAlipayVoList();
                            for (OfBillAlipayVo ofBillAlipayVo : ofBillAlipayVos) {
                                if (ofBillAlipayVo.getCommodityName().contains(merchantInfo.getSname())) {
                                    String orderCode = ofBillAlipayVo.getMerchantOrderNum();
                                    if ("交易".equals(ofBillAlipayVo.getBusinessType())) {
                                        OrderRecord orderRecord = resultMap.get(orderCode);
                                        if (null == orderRecord) {
                                            orderRecord = orderRecordService.selectByOrderCode(merchantInfo.getId(), orderCode);
                                            if (null != orderRecord)
                                                errorOrderMsg = orderRecord.getTorderTime() + "--" + orderCode + "--" + orderRecord.getFactualPayAmount() + ",";
                                        } else {
                                            if (ofBillAlipayVo.getMerchantCollection().compareTo(orderRecord.getFactualPayAmount()) != 0) {
                                                moneyErrorMsg = orderRecord.getTorderTime() + "--" + orderCode + "--" + orderRecord.getFactualPayAmount() + ",";
                                            }
                                        }
                                    } else if ("退款".equals(ofBillAlipayVo.getBusinessType())) {
                                        refundTotalMoney = refundTotalMoney.add(ofBillAlipayVo.getMerchantCollection());
                                    }
                                }
                            }
                            if (StringUtils.isNotBlank(errorOrderMsg)) {
                                sendMessage("alipay_reconciliations_error_order", errorOrderMsg, "ALIPAY_RECONCILIATIONS_ERROR_ORDER", merchantInfo);
                            }
                            if (StringUtils.isNotBlank(moneyErrorMsg)) {
                                sendMessage("alipay_reconciliations_money_error", moneyErrorMsg, "ALIPAY_RECONCILIATIONS_MONEY_ERROR", merchantInfo);
                            }
                            BigDecimal refundMoney = refundAuditService.selectYesTodayRefundMoney(merchantInfo.getId(), BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());
                            //对比退款金额
                            logger.info(date + "支付宝对账完成，总退款金额：{}", refundTotalMoney);
                            logger.info(date + "本地数据库对账，总退款金额：{}", refundMoney);
                            //不相等发送短信
                            if (refundTotalMoney.compareTo(refundTotalMoney) != 0) {
                                String refundMsg = " 退款订单金额为" + refundMoney + "，实际支付宝退款金额为" + refundTotalMoney;
                                sendMessage("alipay_reconciliations_refundmoney_error", refundMsg, "ALIPAY_RECONCILIATIONS_REFUNDMONEY_ERROR", merchantInfo);
                            }
                            //对账结果入库
                            checkLog.setSresult(1);//对账结果 1=成功  2=失败    3=异常
                            checkLog.setStitle(date + "-" + merchantInfo.getSname() + "-支付宝对账");
                            checkLog.setTendDatetime(DateUtils.getCurrentDateTime());
                            checkLog.setSremark("支付宝对账完成");//对账描述
                            checkLogService.insert(checkLog);
                            ReadFileUtil.deleteDirectory(path);
                        } else {
                            logger.info("支付宝对账解析文件失败，{}", JSON.toJSONString(reconciliationsVoResponseVo));
                        }
                    }
                } else {
                    logger.info("支付宝对账解压文件失败，{}", JSON.toJSONString(responseVo1));
                }
            } else {
                logger.info("支付宝对账下载第三方支付账单失败，{}", JSON.toJSONString(responseVo));
            }
        } catch (Exception e) {
            logger.error("支付宝对账异常：{}", e);
        }
    }

    private ResponseVo sendMessage(String templateType, String msg, String purviewCode, MerchantInfo merchantInfo) throws Exception {
        InnerMessageDto innerMessageDto = new InnerMessageDto();
        // 模板类型
        innerMessageDto.setTemplateType(templateType);
        // 内容
        Map<String, Object> contentParamMap = new HashMap<>();
        contentParamMap.put("msg", msg);
        innerMessageDto.setContentParam(contentParamMap);
        // 权限编码
        innerMessageDto.setPurviewCode(purviewCode);
        //商户Id
        innerMessageDto.setSmerchantId(merchantInfo.getId());
        innerMessageDto.setSmerchantCode(merchantInfo.getScode());
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
        invoke.setRequestObj(innerMessageDto); // post 参数
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
        });
        ResponseVo<String> responseVo2 = (ResponseVo<String>) invoke.invoke();
        return responseVo2;
    }

}
