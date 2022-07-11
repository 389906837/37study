/*
 * Copyright (C) 2016 hurbao All rights reserved
 * Author: zhouhong
 * Date: 2016年4月5日
 * Description:StatisticsJobService.java 
 */
package com.cloud.cang.tec.service;

import com.alibaba.fastjson.JSON;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.model.tj.SummarizationToday;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.cloud.cang.tec.tj.service.SummarizationDataPlfService;
import com.cloud.cang.tec.tj.service.SummarizationTodayService;
import com.cloud.cang.tj.StatisticsVo;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhouhong
 * @version 1.0
 */
@Service(value = "statisticsJobService")
public class StatisticsJobService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsJobService.class);

    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private SummarizationDataPlfService summarizationDataPlfService;
    @Autowired
    private SummarizationTodayService summarizationTodayService;
    @Autowired
    private ICached iCached;
    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * 统计参数配置
     */
    private static final String BASE_STATISTICS_PARAM_CODE = "SP000135";


    /**
     * 商户平台实时数据统计 2分钟一次
     */
    public void platformStatisticsJob() {
        logger.info("商户平台实时数据统计开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(10);
                } catch (Exception e) {
                    logger.error("平台实时数据统计失败", e);
                    return "平台实时数据统计失败";
                }
            }

        }, TecConstants.PLATFORM_TJ_DATA_TASK, false);
    }

    /**
     * 商户分页操作
     *
     * @param type 定时任务类型 10 平台实时数据统计 20 商户更新到平台数据汇总 30 商户更新到平台每日数据汇总
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
                msg = "平台实时数据统计成功";
            } else if (type.intValue() == 20) {
                msg = "商户更新到平台数据汇总表成功";
            } else if (type.intValue() == 30) {
                msg = "商户更新到平台每日数据汇总表成功";
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
                            if (type.intValue() == 10) {
                                platformStatisticsByMerchant(merchantInfo);
                            } else if (type.intValue() == 20) {
                                updateplatformStatisticsByMerchant(merchantInfo);
                            } else if (type.intValue() == 30) {
                                updateplatformStatisticsByDay(merchantInfo);
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
            msg = "平台实时数据统计成功";
        } else if (type.intValue() == 20) {
            msg = "商户更新到平台数据汇总表成功";
        } else if (type.intValue() == 30) {
            msg = "商户更新到平台每日数据汇总表成功";
        }
        return msg;
    }

    /**
     * 商户平台实时数据统计 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void platformStatisticsByMerchant(MerchantInfo merchantInfo) {
        logger.info("商户平台实时数据统计开始：{}", merchantInfo);
        SummarizationDataPlf summarizationDataPlf = null;
        //1.从缓存中取得缓存的平台数据
        try {
            Object yesterdayPlatformData = iCached.get(RedisConst.YESTERDAY_PLATFORM_DATA_INFO + merchantInfo.getScode());
            if (yesterdayPlatformData == null) {
                //获取商户最近一条平台汇总统计数据
                summarizationDataPlf = summarizationDataPlfService.selectLatelySummarizationDataPlf(merchantInfo.getId());
                if (summarizationDataPlf != null) {
                    //放入缓存
                    iCached.put(RedisConst.YESTERDAY_PLATFORM_DATA_INFO + merchantInfo.getScode(), JSON.toJSONString(summarizationDataPlf));
                }
            } else {
                summarizationDataPlf = JSON.parseObject(String.valueOf(yesterdayPlatformData), SummarizationDataPlf.class);
            }
        } catch (Exception e) {
            logger.error("商户平台实时数据统计异常", e);
        }

        try {
            //2.当日统计数据
            Date currentDate = DateUtils.getCurrentDateTime();
            StatisticsVo statisticsVo = summarizationDataPlfService.selectTodayStatisticsByDate(currentDate, merchantInfo.getId());
            if (statisticsVo.getOrderTodayAmount() == null || statisticsVo.getOrderTodayAmount().doubleValue() == 0) {
                String yesterdayKey = RedisConst.YESTERDAY_ORDER_AMOUNT_PREDIX + new SimpleDateFormat("yyyyMMdd").format(new Date());
                if (iCached.get(yesterdayKey) == null) {
                    StatisticsVo yesterdayStatisticsVo = summarizationDataPlfService.selectTodayStatisticsByDate(DateUtils.addDays(DateUtils.getCurrentDateTime(), -1), merchantInfo.getId());
                    iCached.put(yesterdayKey, JSON.toJSONString(yesterdayStatisticsVo), 24 * 60 * 60);
                }
            }
            if (summarizationDataPlf != null) {
                statisticsVo.setOrderManTotalNum(statisticsVo.getOrderManTodayNum() + summarizationDataPlf.getIorderManNum());//总订单人数
                statisticsVo.setRefundTotalNum(statisticsVo.getRefundTodayNum() + summarizationDataPlf.getIrefundNum());//总退款笔数
                statisticsVo.setRefundTotalAmount(statisticsVo.getRefundTodayAmount().add(summarizationDataPlf.getFapplyRefundAmount()));//总退款金额
                statisticsVo.setRefundSuccessTotalAmount(statisticsVo.getRefundSuccessTodayAmount().add(summarizationDataPlf.getForderRefundAmount()));//总订单成功退款额

                statisticsVo.setRefundAuditTotalNum(statisticsVo.getRefundAuditTodayNum() + summarizationDataPlf.getIrefundAuditNum());//总退款审核笔数
                statisticsVo.setRefundAuditSuccessTotalNum(statisticsVo.getRefundAuditSuccessTodayNum() + summarizationDataPlf.getIrefundAuditSuccessNum());//总退款审核成功笔数
                statisticsVo.setRefundAuditFailTotalNum(statisticsVo.getRefundAuditFailTodayNum() + summarizationDataPlf.getIrefundAuditFailNum());//总退款审核拒绝笔数

                statisticsVo.setAbnormalTotalNum(statisticsVo.getAbnormalTodayNum() + summarizationDataPlf.getIabnormalNum());//总异常订单笔数
                statisticsVo.setAbnormalDealwithNum(statisticsVo.getAbnormalDealwithTodayNum() + summarizationDataPlf.getIabnormalDealwithNum());//总异常订单处理笔数
                statisticsVo.setAbnormalIgnoreNum(statisticsVo.getAbnormalIgnoreTodayNum() + summarizationDataPlf.getIabnormalIgnoreNum());//总异常订单忽略笔数
                statisticsVo.setAbnormalChargebackNum(statisticsVo.getAbnormalChargebackTodayNum() + summarizationDataPlf.getIabnormalChargebackNum());//总异常订单扣款笔数
                statisticsVo.setMemberTotalNum(statisticsVo.getMemberTodayNum() + summarizationDataPlf.getImemberNum());//总访客次数（包含未购物）
                statisticsVo.setVisitorsNum(statisticsVo.getVisitorsTodayNum() + summarizationDataPlf.getIvisitorsNum());//总未购物次数
                statisticsVo.setReplenishmentNum(statisticsVo.getReplenishmentTodayNum() + summarizationDataPlf.getIreplenishmentNum());//总补货次数
                statisticsVo.setNotReplenishmentTotalNum(statisticsVo.getNotReplenishmentTodayNum() + summarizationDataPlf.getInotReplenishmentNum());//总未补货次数

                statisticsVo.setOrderTotalAmount(statisticsVo.getOrderTodayAmount().add(summarizationDataPlf.getForderAmount()));//总订单额

                statisticsVo.setDiscountTotalAmount(statisticsVo.getDiscountTodayAmount().add(summarizationDataPlf.getFdiscountAmount()));//总订单优惠总额
                statisticsVo.setFirstDiscountTotalAmount(statisticsVo.getFirstDiscountTodayAmount().add(summarizationDataPlf.getFfirstDiscountAmount()));//总订单首单优惠金额
                statisticsVo.setPromotionDiscountTotalAmount(statisticsVo.getPromotionDiscountTodayAmount().add(summarizationDataPlf.getFpromotionDiscountAmount()));//总订单促销优惠金额
                statisticsVo.setCouponDeductionTotalAmount(statisticsVo.getCouponDeductionTodayAmount().add(summarizationDataPlf.getFcouponDeductionAmount()));//总订单优惠券抵扣金额
                statisticsVo.setPointDiscountTotalAmount(statisticsVo.getPointDiscountTodayAmount().add(summarizationDataPlf.getFpointDiscountAmount()));//总订单积分优惠金额
                statisticsVo.setOtherDiscountTotalAmount(statisticsVo.getOtherDiscountTodayAmount().add(summarizationDataPlf.getFotherDiscountAmount()));//总订单其他优惠金额
                statisticsVo.setActualPayTotalAmount(statisticsVo.getActualPayTodayAmount().add(summarizationDataPlf.getFactualPayAmount()));//总订单实付金额

                statisticsVo.setOrderSuccessTotalAmount(statisticsVo.getOrderSuccessTodayAmount().add(summarizationDataPlf.getForderSuccessAmount()));//总订单成功支付金额
                statisticsVo.setOrderFailTotalAmount(statisticsVo.getActualPayTodayAmount().subtract(statisticsVo.getOrderSuccessTotalAmount()));//总订单成功支付失败金额或未付款金额

                statisticsVo.setOrderTotalNum(statisticsVo.getOrderTodayNum() + summarizationDataPlf.getIorderNum());//总订单笔数
                statisticsVo.setRegTotalNum(statisticsVo.getRegTodayNum() + summarizationDataPlf.getIregisteNum());//总注册人数
                statisticsVo.setDeviceTotalNum(statisticsVo.getDeviceTodayNum() + summarizationDataPlf.getIdeviceNum());//总设备数量
            } else {
                statisticsVo.setOrderManTotalNum(statisticsVo.getOrderManTodayNum());//总订单人数
                statisticsVo.setRefundTotalNum(statisticsVo.getRefundTodayNum());//总退款笔数
                statisticsVo.setRefundTotalAmount(statisticsVo.getRefundTodayAmount());//总退款金额
                statisticsVo.setRefundSuccessTotalAmount(statisticsVo.getRefundSuccessTodayAmount());//总订单成功退款额

                statisticsVo.setRefundAuditTotalNum(statisticsVo.getRefundAuditTodayNum());//总退款审核笔数
                statisticsVo.setRefundAuditSuccessTotalNum(statisticsVo.getRefundAuditSuccessTodayNum());//总退款审核成功笔数
                statisticsVo.setRefundAuditFailTotalNum(statisticsVo.getRefundAuditFailTodayNum());//总退款审核拒绝笔数

                statisticsVo.setAbnormalTotalNum(statisticsVo.getAbnormalTodayNum());//总异常订单笔数
                statisticsVo.setAbnormalDealwithNum(statisticsVo.getAbnormalDealwithTodayNum());//总异常订单处理笔数
                statisticsVo.setAbnormalIgnoreNum(statisticsVo.getAbnormalIgnoreTodayNum());//总异常订单忽略笔数
                statisticsVo.setAbnormalChargebackNum(statisticsVo.getAbnormalChargebackTodayNum());//总异常订单扣款笔数
                statisticsVo.setMemberTotalNum(statisticsVo.getMemberTodayNum());//总访客次数（包含未购物）
                statisticsVo.setVisitorsNum(statisticsVo.getVisitorsTodayNum());//总未购物次数
                statisticsVo.setReplenishmentNum(statisticsVo.getReplenishmentTodayNum());//总补货次数
                statisticsVo.setNotReplenishmentTotalNum(statisticsVo.getNotReplenishmentTodayNum());//总未补货次数

                statisticsVo.setOrderTotalAmount(statisticsVo.getOrderTodayAmount());//总订单额

                statisticsVo.setDiscountTotalAmount(statisticsVo.getDiscountTodayAmount());//总订单优惠总额
                statisticsVo.setFirstDiscountTotalAmount(statisticsVo.getFirstDiscountTodayAmount());//总订单首单优惠金额
                statisticsVo.setPromotionDiscountTotalAmount(statisticsVo.getPromotionDiscountTodayAmount());//总订单促销优惠金额
                statisticsVo.setCouponDeductionTotalAmount(statisticsVo.getCouponDeductionTodayAmount());//总订单优惠券抵扣金额
                statisticsVo.setPointDiscountTotalAmount(statisticsVo.getPointDiscountTodayAmount());//总订单积分优惠金额
                statisticsVo.setOtherDiscountTotalAmount(statisticsVo.getOtherDiscountTodayAmount());//总订单其他优惠金额
                statisticsVo.setActualPayTotalAmount(statisticsVo.getActualPayTodayAmount());//总订单实付金额
                statisticsVo.setOrderSuccessTotalAmount(statisticsVo.getOrderSuccessTodayAmount());//总订单成功支付金额
                statisticsVo.setOrderFailTotalAmount(statisticsVo.getActualPayTodayAmount().subtract(statisticsVo.getOrderSuccessTotalAmount()));//总订单成功支付失败金额或未付款金额

                statisticsVo.setOrderTotalNum(statisticsVo.getOrderTodayNum());//总订单笔数
                statisticsVo.setRegTotalNum(statisticsVo.getRegTodayNum());//总注册人数
                statisticsVo.setDeviceTotalNum(statisticsVo.getDeviceTodayNum());//总设备数量
            }
            Integer orderNum = 0; //订单笔数
            Integer registerNum = 0; //注册人数
            Integer deviceNum = 0; //设备数量
            BigDecimal orderAmount = BigDecimal.ZERO; //订单金额
            try {
                orderNum = Integer.parseInt(GrpParaUtil.getDetailForName(BASE_STATISTICS_PARAM_CODE, "order_num").getSvalue());
                deviceNum = Integer.parseInt(GrpParaUtil.getDetailForName(BASE_STATISTICS_PARAM_CODE, "device_num").getSvalue());
                registerNum = Integer.parseInt(GrpParaUtil.getDetailForName(BASE_STATISTICS_PARAM_CODE, "register_num").getSvalue());
                orderAmount = new BigDecimal(GrpParaUtil.getDetailForName(BASE_STATISTICS_PARAM_CODE, "order_amount").getSvalue());
            } catch (Exception e) {
                logger.error("商户平台实时数据统计", e);
            }
            statisticsVo.setOrderTotalAmount(statisticsVo.getOrderTotalAmount().add(orderAmount));//总订单额
            statisticsVo.setOrderTotalNum(statisticsVo.getOrderTotalNum() + orderNum);//总订单笔数
            statisticsVo.setRegTotalNum(statisticsVo.getRegTotalNum() + registerNum);//总注册人数
            statisticsVo.setDeviceTotalNum(statisticsVo.getDeviceTotalNum() + deviceNum);//总设备数量

            statisticsVo.setStatisticDate(currentDate);
            iCached.put(RedisConst.CURRENT_PLATFORM_DATA_INFO + merchantInfo.getScode(), JSON.toJSONString(statisticsVo));
        } catch (Exception e) {
            logger.error("商户平台实时数据统计失败：{}", e);
        }
    }

    /**
     * 商户平台数据汇总表更新 定时任务  1天一次
     */
    public void updatePlatformStatisticsJob() {
        logger.info("更新到平台数据汇总表...");
        jobTemplate.excuteJob(new JobCallBack() {

            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(20);
                } catch (Exception e) {
                    logger.error("商户更新到平台数据汇总表异常", e);
                    return "商户更新到平台数据汇总表异常";
                }
            }
        }, TecConstants.PLATFORM_TJ_UPDATE_TASK, true);
    }

    private void updateplatformStatisticsByMerchant(MerchantInfo merchantInfo) {
        logger.info("商户平台数据统计开始：{}", merchantInfo);
        try {
            SummarizationDataPlf summarizationDataPlf = summarizationDataPlfService.selectLatelySummarizationDataPlf(merchantInfo.getId());
            Date summerEndTime = DateUtils.addDays(DateUtils.getDayEnd(DateUtils.getCurrentDateTime()), -1);//统计截止时间
            //Date summerEndTime = DateUtils.getCurrentDateTime();//统计截止时间
            if (null == summarizationDataPlf) {
                summarizationDataPlf = new SummarizationDataPlf();
                //查询商户统计数据
                StatisticsVo statisticsVo = summarizationDataPlfService.selectStatisticsByDate(DateUtils.parseDateByFormat(BizParaUtil.get("platform_online_date"),"yyyy-MM-dd"), summerEndTime, merchantInfo.getId());
                summarizationDataPlf.setIorderManNum(statisticsVo.getOrderManTodayNum());//总订单人数
                summarizationDataPlf.setIrefundNum(statisticsVo.getRefundTodayNum());//总退款笔数
                summarizationDataPlf.setFapplyRefundAmount(statisticsVo.getRefundTodayAmount());//总退款金额
                summarizationDataPlf.setForderRefundAmount(statisticsVo.getRefundSuccessTodayAmount());//总订单成功退款额

                summarizationDataPlf.setIrefundAuditNum(statisticsVo.getRefundAuditTodayNum());//总处理退款审核笔数
                summarizationDataPlf.setIrefundAuditSuccessNum(statisticsVo.getRefundAuditSuccessTodayNum());//总退款审核成功笔数
                summarizationDataPlf.setIrefundAuditFailNum(statisticsVo.getRefundAuditFailTodayNum());//总退款审核拒绝笔数

                summarizationDataPlf.setIabnormalNum(statisticsVo.getAbnormalTodayNum());//总异常订单笔数
                summarizationDataPlf.setIabnormalDealwithNum(statisticsVo.getAbnormalDealwithTodayNum());//总异常订单处理笔数
                summarizationDataPlf.setIabnormalIgnoreNum(statisticsVo.getAbnormalIgnoreTodayNum());//总异常订单忽略笔数
                summarizationDataPlf.setIabnormalChargebackNum(statisticsVo.getAbnormalChargebackTodayNum());//总异常订单扣款笔数
                summarizationDataPlf.setImemberNum(statisticsVo.getMemberTodayNum());//总访客次数（包含未购物）
                summarizationDataPlf.setIvisitorsNum(statisticsVo.getVisitorsTodayNum());//总未购物次数
                summarizationDataPlf.setIreplenishmentNum(statisticsVo.getReplenishmentTodayNum());//总补货次数
                summarizationDataPlf.setInotReplenishmentNum(statisticsVo.getNotReplenishmentTodayNum());//总未补货次数

                summarizationDataPlf.setForderAmount(statisticsVo.getOrderTodayAmount());//总订单额

                summarizationDataPlf.setFdiscountAmount(statisticsVo.getDiscountTodayAmount());//总订单优惠总额
                summarizationDataPlf.setFfirstDiscountAmount(statisticsVo.getFirstDiscountTodayAmount());//总订单首单优惠金额
                summarizationDataPlf.setFpromotionDiscountAmount(statisticsVo.getPromotionDiscountTodayAmount());//总订单促销优惠金额
                summarizationDataPlf.setFcouponDeductionAmount(statisticsVo.getCouponDeductionTodayAmount());//总订单优惠券抵扣金额
                summarizationDataPlf.setFpointDiscountAmount(statisticsVo.getPointDiscountTodayAmount());//总订单积分优惠金额
                summarizationDataPlf.setFotherDiscountAmount(statisticsVo.getOtherDiscountTodayAmount());//总订单其他优惠金额
                summarizationDataPlf.setFactualPayAmount(statisticsVo.getActualPayTodayAmount());//总订单实付金额

                summarizationDataPlf.setForderSuccessAmount(statisticsVo.getOrderSuccessTodayAmount());//总订单成功支付金额
                summarizationDataPlf.setForderFailAmount(summarizationDataPlf.getFactualPayAmount().subtract(summarizationDataPlf.getForderSuccessAmount()));//总订单成功支付失败金额或未付款金额

                summarizationDataPlf.setIorderNum(statisticsVo.getOrderTodayNum());//总订单笔数
                summarizationDataPlf.setIregisteNum(statisticsVo.getRegTodayNum());//总注册人数
                summarizationDataPlf.setIdeviceNum(statisticsVo.getDeviceTodayNum());//总设备数量

                summarizationDataPlf.setDsummerEndTime(summerEndTime);
                summarizationDataPlf.setTaddTime(DateUtils.getCurrentDateTime());
                summarizationDataPlf.setSmerchantCode(merchantInfo.getScode());
                summarizationDataPlf.setSmerchantId(merchantInfo.getId());
                summarizationDataPlfService.insert(summarizationDataPlf);
            } else {
                //查询商户统计数据
                StatisticsVo statisticsVo = summarizationDataPlfService.selectStatisticsByDate(summarizationDataPlf.getDsummerEndTime(), summerEndTime, merchantInfo.getId());
                summarizationDataPlf.setId(null);
                summarizationDataPlf.setIorderManNum(statisticsVo.getOrderManTodayNum() + summarizationDataPlf.getIorderManNum());//总订单人数
                summarizationDataPlf.setIrefundNum(statisticsVo.getRefundTodayNum() + summarizationDataPlf.getIrefundNum());//总退款笔数
                summarizationDataPlf.setFapplyRefundAmount(statisticsVo.getRefundTodayAmount().add(summarizationDataPlf.getFapplyRefundAmount()));//总退款金额
                summarizationDataPlf.setForderRefundAmount(statisticsVo.getRefundSuccessTodayAmount().add(summarizationDataPlf.getForderRefundAmount()));//总订单成功退款额

                summarizationDataPlf.setIrefundAuditNum(statisticsVo.getRefundAuditTodayNum() + summarizationDataPlf.getIrefundAuditNum());//总处理退款审核笔数
                summarizationDataPlf.setIrefundAuditSuccessNum(statisticsVo.getRefundAuditSuccessTodayNum() + summarizationDataPlf.getIrefundAuditSuccessNum());//总退款审核成功笔数
                summarizationDataPlf.setIrefundAuditFailNum(statisticsVo.getRefundAuditFailTodayNum() + summarizationDataPlf.getIrefundAuditFailNum());//总退款审核拒绝笔数

                summarizationDataPlf.setIabnormalNum(statisticsVo.getAbnormalTodayNum() + summarizationDataPlf.getIabnormalNum());//总异常订单笔数
                summarizationDataPlf.setIabnormalDealwithNum(statisticsVo.getAbnormalDealwithTodayNum() + summarizationDataPlf.getIabnormalDealwithNum());//总异常订单处理笔数
                summarizationDataPlf.setIabnormalIgnoreNum(statisticsVo.getAbnormalIgnoreTodayNum() + summarizationDataPlf.getIabnormalIgnoreNum());//总异常订单忽略笔数
                summarizationDataPlf.setIabnormalChargebackNum(statisticsVo.getAbnormalChargebackTodayNum() + summarizationDataPlf.getIabnormalChargebackNum());//总异常订单扣款笔数
                summarizationDataPlf.setImemberNum(statisticsVo.getMemberTodayNum() + summarizationDataPlf.getImemberNum());//总访客次数（包含未购物）
                summarizationDataPlf.setIvisitorsNum(statisticsVo.getVisitorsTodayNum() + summarizationDataPlf.getIvisitorsNum());//总未购物次数
                summarizationDataPlf.setIreplenishmentNum(statisticsVo.getReplenishmentTodayNum() + summarizationDataPlf.getIreplenishmentNum());//总补货次数
                summarizationDataPlf.setInotReplenishmentNum(statisticsVo.getNotReplenishmentTodayNum() + summarizationDataPlf.getInotReplenishmentNum());//总未补货次数

                summarizationDataPlf.setForderAmount(statisticsVo.getOrderTodayAmount().add(summarizationDataPlf.getForderAmount()));//总订单额

                summarizationDataPlf.setFdiscountAmount(statisticsVo.getDiscountTodayAmount().add(summarizationDataPlf.getFdiscountAmount()));//总订单优惠总额
                summarizationDataPlf.setFfirstDiscountAmount(statisticsVo.getFirstDiscountTodayAmount().add(summarizationDataPlf.getFfirstDiscountAmount()));//总订单首单优惠金额
                summarizationDataPlf.setFpromotionDiscountAmount(statisticsVo.getPromotionDiscountTodayAmount().add(summarizationDataPlf.getFpromotionDiscountAmount()));//总订单促销优惠金额
                summarizationDataPlf.setFcouponDeductionAmount(statisticsVo.getCouponDeductionTodayAmount().add(summarizationDataPlf.getFcouponDeductionAmount()));//总订单优惠券抵扣金额
                summarizationDataPlf.setFpointDiscountAmount(statisticsVo.getPointDiscountTodayAmount().add(summarizationDataPlf.getFpointDiscountAmount()));//总订单积分优惠金额
                summarizationDataPlf.setFotherDiscountAmount(statisticsVo.getOtherDiscountTodayAmount().add(summarizationDataPlf.getFotherDiscountAmount()));//总订单其他优惠金额
                summarizationDataPlf.setFactualPayAmount(statisticsVo.getActualPayTodayAmount().add(summarizationDataPlf.getFactualPayAmount()));//总订单实付金额

                summarizationDataPlf.setForderSuccessAmount(statisticsVo.getOrderSuccessTodayAmount().add(summarizationDataPlf.getForderSuccessAmount()));//总订单成功支付金额
                summarizationDataPlf.setForderFailAmount(summarizationDataPlf.getFactualPayAmount().subtract(summarizationDataPlf.getForderSuccessAmount()));//总订单成功支付失败金额或未付款金额

                summarizationDataPlf.setIorderNum(statisticsVo.getOrderTodayNum() + summarizationDataPlf.getIorderNum());//总订单笔数
                summarizationDataPlf.setIregisteNum(statisticsVo.getRegTodayNum() + summarizationDataPlf.getIregisteNum());//总注册人数
                summarizationDataPlf.setIdeviceNum(statisticsVo.getDeviceTodayNum() + summarizationDataPlf.getIdeviceNum());//总设备数量

                summarizationDataPlf.setDsummerEndTime(summerEndTime);
                summarizationDataPlf.setTaddTime(DateUtils.getCurrentDateTime());
                summarizationDataPlf.setSmerchantCode(merchantInfo.getScode());
                summarizationDataPlf.setSmerchantId(merchantInfo.getId());
                summarizationDataPlfService.insert(summarizationDataPlf);
            }
            //放入缓存
            iCached.put(RedisConst.YESTERDAY_PLATFORM_DATA_INFO + merchantInfo.getScode(), JSON.toJSONString(summarizationDataPlf));

        } catch (Exception e) {
            logger.error("商户平台数据统计更新异常：{}", e);
        }
    }

    /**
     * 商户平台每天数据汇总表更新 定时任务  1天一次
     */
    public void updatePlatformStatisticsByDayJob() {
        logger.info("更新到平台每日数据汇总表...");
        jobTemplate.excuteJob(new JobCallBack() {

            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(30);
                } catch (Exception e) {
                    logger.error("商户更新到平台每日数据汇总表异常", e);
                    return "商户更新到平台每日数据汇总表异常";
                }
            }
        }, TecConstants.PLATFORM_DAY_UPDATE_TASK, true);
    }

    private void updateplatformStatisticsByDay(MerchantInfo merchantInfo) {
        try {
            //Date currentDate = DateUtils.getCurrentDateTime();
            Date currentDate = DateUtils.convertToDate(DateUtils.dateParseString(DateUtils.addDays(DateUtils.getCurrentDateTime(), -1)));
            SummarizationToday summarizationToday = summarizationTodayService.selectSummarizationTodayByDate(currentDate, merchantInfo.getId());
            SummarizationToday temp = summarizationTodayService.selectTodayStatisticsByDate(currentDate, merchantInfo.getId());
            if (null != summarizationToday) {
                //更新数据
                temp.setForderFailAmount(temp.getFactualPayAmount().subtract(temp.getForderSuccessAmount()));
                temp.setDsummerEndTime(currentDate);
                temp.setTupdateTime(new Date());
                temp.setId(summarizationToday.getId());
                summarizationTodayService.updateBySelective(temp);
            } else {
                //新增数据
                temp.setForderFailAmount(temp.getFactualPayAmount().subtract(temp.getForderSuccessAmount()));
                temp.setDsummerEndTime(currentDate);
                temp.setSmerchantCode(merchantInfo.getScode());
                temp.setSmerchantId(merchantInfo.getId());
                temp.setTaddTime(new Date());
                temp.setTupdateTime(temp.getTaddTime());
                summarizationTodayService.insert(temp);
            }
            //放入缓存
            iCached.put(RedisConst.YESTERDAY_TODAY_PLATFORM_DATA_INFO + merchantInfo.getScode(), JSON.toJSONString(temp));
        } catch (Exception e) {
            logger.error("商户更新到平台每日数据汇总表异常", e);
        }
    }
}
