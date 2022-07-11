package com.cloud.cang.tec.service;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageCodeDefine;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sl.CheckLog;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.pay.DownloadBillDto;
import com.cloud.cang.pay.PayServicesDefine;
import com.cloud.cang.tec.ac.service.ActivityConfService;
import com.cloud.cang.tec.common.ReadFileUtil;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.om.service.OrderRecordService;
import com.cloud.cang.tec.om.vo.*;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.cloud.cang.tec.tec.service.ScheduleLogService;
import com.cloud.cang.tec.ac.vo.ActivityConfVo;
import com.cloud.cang.tec.xx.service.SupplierInfoService;
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

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 *
 */
@Service(value = "jobService")
public class JobService {

    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private ScheduleLogService scheduleLogService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private SupplierInfoService supplierInfoService;
    private static final Logger logger = LoggerFactory.getLogger(JobService.class);


    /**
     * 总任务结果预警
     */
    public void scheduleWarnJob() {
        logger.info("总任务结果预警job_启动");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                int i = scheduleLogService.getScheduleCountToday();//今日实际执行的job数
                Map<String, Object> contentParam = new HashMap<String, Object>();
                contentParam.put("taskNumber", EvnUtils.getValue("task.number"));
                contentParam.put("excuteNumber", i);
                //发送告警短信
                jobTemplate.sendWarnMsg(MessageCodeDefine.TASK_NUMBER_MSG, "WARN_MESSAGE", contentParam);
                return "总任务结果预警job_执行的job数:" + i;
            }
        }, "scheduleWarnJob", false);
        logger.info("总任务结果预警job_结束");

    }

    /**
     * 短信余额预警
     */
    public void messageBalanceWarnJob() {
        logger.info("短信余额预警定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(10);
                } catch (Exception e) {
                    logger.error("短信余额预警定时任务失败", e);
                    return "短信余额预警定时任务失败";
                }
            }
        }, TecConstants.MESSAGE_WARN_TASK, true);
    }

    /**
     * 商户合约到期日预警
     */
    public void expireDateWarnJob() {
        logger.info("商户合约到期日预警定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(20);
                } catch (Exception e) {
                    logger.error("商户合约到期日预警定时任务失败", e);
                    return "商户合约到期日预警定时任务失败";
                }
            }
        }, TecConstants.EXPIRE_DATE_WARN_TASK, true);
    }

    /**
     * 活动过期状态变更
     */
    public void activityStatusChangeJob() {
        logger.info("活动过期状态变更定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(30);
                } catch (Exception e) {
                    logger.error("活动过期状态变更定时任务失败", e);
                    return "活动过期状态变更定时任务失败";
                }
            }
        }, TecConstants.ACTIVITY_STATUS_CHANGE_TASK_, true);
    }

    /**
     * 活动过期提醒
     */
    public void sceneActivityExpireWarnJob() {
        logger.info("场景活动过期提醒定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(40);
                } catch (Exception e) {
                    logger.error("场景活动过期提醒定时任务失败", e);
                    return "场景活动过期提醒定时任务失败";
                }
            }
        }, TecConstants.SCENEACTIVITY_EXPIRE_WARN_TASK, true);
    }

    /**
     * 订单扫描（未支付、支付失败、支付处理中）
     * 异常订单扫描通知内部人员
     */
    public void scanningOrderJob() {
        logger.info("订单扫描定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(50);
                } catch (Exception e) {
                    logger.error("订单扫描定时任务失败", e);
                    return "订单扫描定时任务失败";
                }
            }
        }, TecConstants.SCANNING_ORDER_TASK, true);
    }

    /**
     * 商户合约到期状态变更
     */
    public void merchantExpireJob() {
        logger.info("商户合约到期状态变更定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(60);
                } catch (Exception e) {
                    logger.error("商户合约到期状态变更定时任务失败", e);
                    return "商户合约到期状态变更定时任务失败";
                }
            }
        }, TecConstants.MERCHANT_EXPIRE_TASK, true);
    }

    /**
     * 商户分页操作
     *
     * @param type 定时任务类型: 10 短信余额预警 20 商户合约到期日 30 活动过期状态变更 40 场景活动过期预警 50 扫描订单定时任务  60 商户合约过期状态变更
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
                msg = "短信余额预警时任务成功";
            } else if (type.intValue() == 20) {
                msg = "商户合约到期日定时任务成功";
            } else if (type.intValue() == 30) {
                msg = "活动过期状态变更定时任务成功";
            } else if (type.intValue() == 40) {
                msg = "活动过期提醒定时任务成功";
            } else if (type.intValue() == 50) {
                msg = "订单扫描定时任务成功";
            } else if (type.intValue() == 60) {
                msg = "商户合约到期状态变更定时任务成功";
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
                                messageBalanceWarnByMerchant(merchantInfo);
                            } else if (20 == type.intValue()) {
                                dexpireDateWarnByMerchant(merchantInfo);
                            } else if (30 == type.intValue()) {
                                activityStatusChangeByMerchant(merchantInfo);
                            } else if (40 == type.intValue()) {
                                sceneActivityExpireWarnByMerchant(merchantInfo);
                            } else if (50 == type.intValue()) {
                                scanningOrderByMerchant(merchantInfo);
                            } else if (60 == type.intValue()) {
                                merchantExpireByMerchant(merchantInfo);
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
            msg = "短信余额预警时任务成功";
        } else if (type.intValue() == 20) {
            msg = "商户合约到期日定时任务成功";
        } else if (type.intValue() == 30) {
            msg = "活动过期状态变更定时任务成功";
        } else if (type.intValue() == 40) {
            msg = "活动过期提醒定时任务成功";
        } else if (type.intValue() == 50) {
            msg = "订单扫描定时任务成功";
        } else if (type.intValue() == 60) {
            msg = "商户合约到期状态变更定时任务成功";
        }
        return msg;
    }

    /**
     * 短信余额预警 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void messageBalanceWarnByMerchant(MerchantInfo merchantInfo) {
        logger.info("短信余额预警开始：{}", merchantInfo);
        ResponseVo<String> responseVo = new ResponseVo<>();

        List<SupplierInfo> supplierInfoList = supplierInfoService.selectByMerchantId(merchantInfo.getId());
        for (SupplierInfo supplierInfo : supplierInfoList) {
            try {
                // 创建Rest服务客户端
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_QUERY_BALANCE_SERVICE);
                Map param = new HashMap<String, String>();
                param.put("supplierCode", supplierInfo.getScode()); //运营商编号
                invoke.setUrlVariables(param); //get URL 参数
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                logger.debug("开始调用短信余额接口" + MessageServicesDefine.SMS_MESSAGE_QUERY_BALANCE_SERVICE);
                responseVo = (ResponseVo<String>) invoke.invoke();
                if (null != responseVo && responseVo.isSuccess()) {
                    logger.debug("调用短信余额查询服务接口结束,余额：{}", responseVo.getData());
                  /*  1：营销
                    2：非营销*/
                    if (null != supplierInfo.getIintention() && 1 == supplierInfo.getIintention()) {
                        if (StringUtils.isNotBlank(responseVo.getData()) && !responseVo.getData().equals("-1") &&
                                Integer.valueOf(BizParaUtil.get("marketing_message_balance_warn")).compareTo(Integer.valueOf(responseVo.getData()) / 100) >= 0) {
                            //开始预警
                            InnerMessageDto innerMessageDto = new InnerMessageDto();
                            // 模板类型
                            innerMessageDto.setTemplateType("message_balance_warn");
                            // 内容
                            Map<String, Object> contentParamMap = new HashMap<>();
                            contentParamMap.put("messageBalance", Integer.valueOf(responseVo.getData()) / 100);
                            innerMessageDto.setContentParam(contentParamMap);
                            // 权限编码
                            innerMessageDto.setPurviewCode("MESSAGE_BALANCE_WARN");
                            //商户Id
                            innerMessageDto.setSmerchantId(merchantInfo.getId());
                            innerMessageDto.setSmerchantCode(merchantInfo.getScode());
                            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                            invoke.setRequestObj(innerMessageDto); // post 参数
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                            });
                            ResponseVo<String> responseVo2 = (ResponseVo<String>) invoke.invoke();
                        }
                    } else if (null != supplierInfo.getIintention() && 2 == supplierInfo.getIintention()) {
                        if (StringUtils.isNotBlank(responseVo.getData()) && !responseVo.getData().equals("-1") && Integer.valueOf(BizParaUtil.get("message_balance_warn")).compareTo(Integer.valueOf(responseVo.getData()) / 100) >= 0) {
                            InnerMessageDto innerMessageDto = new InnerMessageDto();
                            // 模板类型
                            innerMessageDto.setTemplateType("message_balance_warn");
                            // 内容
                            Map<String, Object> contentParamMap = new HashMap<>();
                            contentParamMap.put("messageBalance", Integer.valueOf(responseVo.getData()) / 100);
                            innerMessageDto.setContentParam(contentParamMap);
                            // 权限编码
                            innerMessageDto.setPurviewCode("MESSAGE_BALANCE_WARN");
                            //商户Id
                            innerMessageDto.setSmerchantId(merchantInfo.getId());
                            innerMessageDto.setSmerchantCode(merchantInfo.getScode());
                            invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                            invoke.setRequestObj(innerMessageDto); // post 参数
                            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                            });
                            ResponseVo<String> responseVo2 = (ResponseVo<String>) invoke.invoke();
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("供应商短信余额预警异常：{}", e);
            }
        }
    }

    /**
     * 商户合约到期日预警 根据商户
     *
     * @param merchantInfo 商户信息
     */

    private void dexpireDateWarnByMerchant(MerchantInfo merchantInfo) {
        logger.info("商户合约到期日预警定时任务开始：{}", merchantInfo);
        int day = DateUtils.sub(new Date(), merchantInfo.getDexpireDate());
        if (Integer.valueOf(BizParaUtil.get("expire_date_warn")) == day) {
            InnerMessageDto innerMessageDto = new InnerMessageDto();
            // 模板编号
            innerMessageDto.setTemplateType("merchant_expire_date_warn");

            // 内容
            Map<String, Object> contentParamMap = new HashMap<>();
            contentParamMap.put("merchantName", merchantInfo.getSname());
            innerMessageDto.setContentParam(contentParamMap);
            // 权限编码
            innerMessageDto.setPurviewCode("EXPIRE_DATE_WARN");
            //商户Id
            innerMessageDto.setSmerchantId(merchantInfo.getId());
            innerMessageDto.setSmerchantCode(merchantInfo.getScode());
            try {
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                invoke.setRequestObj(innerMessageDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            } catch (Exception e) {
                logger.error("商户合约到期日预警短信发送失败", e);
            }
        }
    }

    /**
     * 商户合约到期状态变更 根据商户
     *
     * @param merchantInfo 商户信息
     */

    private void merchantExpireByMerchant(MerchantInfo merchantInfo) {
        logger.info("商户合约到期状态变更定时任务开始：{}", merchantInfo);
        if (null != merchantInfo.getDexpireDate() && merchantInfo.getDexpireDate().getTime() >= new Date().getTime()) {
            MerchantInfo updata = new MerchantInfo();
            updata.setId(merchantInfo.getId());
            updata.setIstatus(BizTypeDefinitionEnum.IcompanyStatus.CONTRACTEXPIRATION.getCode());
            merchantInfoService.updateBySelective(updata);
            //清除缓存
        }
    }

    /**
     * 活动过期状态变更 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void activityStatusChangeByMerchant(MerchantInfo merchantInfo) {
        logger.info("活动过期状态变更定时任务开始：{}", merchantInfo);
        List<ActivityConf> activityConfList = activityConfService.selectExpireActivity(merchantInfo.getId());
        for (ActivityConf activityConf : activityConfList) {
            ActivityConf upActivityConf = new ActivityConf();
            upActivityConf.setId(activityConf.getId());
            upActivityConf.setIisAvailable(-1);
            activityConfService.updateBySelective(upActivityConf);
        }
    }

    /**
     * 活动过期提醒 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void sceneActivityExpireWarnByMerchant(MerchantInfo merchantInfo) {
        logger.info("活动过期提醒定时任务开始：{}", merchantInfo);
        try {
            ActivityConfVo activityConfVo = activityConfService.selectExpireSceneActivity(merchantInfo.getId());
            if (null != activityConfVo) {
                InnerMessageDto innerMessageDto = new InnerMessageDto();
                // 模板编号
                innerMessageDto.setTemplateType("scene_activity_expire_warn");
                // 内容

                Map<String, Object> contentParamMap = new HashMap<>();
                contentParamMap.put("confNames", activityConfVo.getSconfNameList());
                innerMessageDto.setContentParam(contentParamMap);
                // 权限编码
                innerMessageDto.setPurviewCode("SCENE_ACTIVITY_EXPIRE_WARN");
                //商户Id
                innerMessageDto.setSmerchantId(merchantInfo.getId());
                innerMessageDto.setSmerchantCode(merchantInfo.getScode());

                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                invoke.setRequestObj(innerMessageDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            }
        } catch (Exception e) {
            logger.error("活动过期提醒短信发送异常：{}", e);
        }
    }

    /**
     * 扫描订单定时任务 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void scanningOrderByMerchant(MerchantInfo merchantInfo) {
        logger.info("扫描订单定时任务开始：{}", merchantInfo);
        try {
            ScanningOrderVo scanningOrderVo = orderRecordService.scanningOrder(merchantInfo.getId());
            if (null != scanningOrderVo) {
                StringBuffer stringBuffer = new StringBuffer();
                Integer failureNum = scanningOrderVo.getFailureNum();
                if (null != failureNum && failureNum.intValue() > 0) {
                    stringBuffer.append(failureNum.intValue() + "笔订单付款失败,");
                }
                Integer waitNum = scanningOrderVo.getWaitNum();
                if (null != waitNum && waitNum.intValue() > 0) {
                    stringBuffer.append(waitNum.intValue() + "笔订单待支付,");
                }
                Integer processingNum = scanningOrderVo.getProcessingNum();
                if (null != processingNum && processingNum.intValue() > 0) {
                    stringBuffer.append(processingNum.intValue() + "笔订单付款处理中,");
                }
                if (stringBuffer.length() > 0) {
                    InnerMessageDto innerMessageDto = new InnerMessageDto();
                    // 模板类型
                    innerMessageDto.setTemplateType("scanning_order_warn");
                    // 内容
                    Map<String, Object> contentParamMap = new HashMap<>();

                    contentParamMap.put("msg", stringBuffer);
                    innerMessageDto.setContentParam(contentParamMap);
                    // 权限编码
                    innerMessageDto.setPurviewCode("SCANNING_ORDER_WARN");
                    //商户Id
                    innerMessageDto.setSmerchantId(merchantInfo.getId());
                    innerMessageDto.setSmerchantCode(merchantInfo.getScode());
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                    invoke.setRequestObj(innerMessageDto); // post 参数
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                    });
                    ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
              /*  if (responseVo.isSuccess()) {
                    logger.info("异常订单扫描短信发送成功，商户编号：{}", merchantInfo.getScode());
                    return;
                }*/
                }
            }
            /*logger.info("异常订单扫描短信发送失败，商户编号：{}", merchantInfo.getScode());*/
        } catch (Exception e) {
            logger.error("扫描订单定时任务异常：{}", e);
        }
    }
}
