package com.cloud.cang.tec.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.rm.ReplenishmentRecord;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.om.service.OrderRecordService;
import com.cloud.cang.tec.rm.service.ReplenishmentRecordService;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.cloud.cang.tec.sm.service.DeviceStockService;
import com.cloud.cang.tec.sm.service.StockDetailService;
import com.cloud.cang.tec.sm.service.StockOperRecordService;
import com.cloud.cang.tec.sm.vo.ErrorCommodityVo;
import com.cloud.cang.tec.sm.vo.RealtimeInventorySynVo;
import com.cloud.cang.tec.sp.service.CommodityInfoService;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据同步定时任务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service(value = "dataSynchronizationJobService")
public class DataSynchronizationJobService {
    private static final Logger logger = LoggerFactory.getLogger(DataSynchronizationJobService.class);
    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private DeviceStockService deviceStockService;
    @Autowired
    private StockDetailService stockDetailService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private StockOperRecordService stockOperRecordService;
    @Autowired
    private ReplenishmentRecordService replenishmentRecordService;
    @Autowired
    private CommodityInfoService commodityInfoService;

    /**
     * 商品实时库存同步
     */
    public void realtimeInventorySynJob() {
        logger.info("商品实时库存同步定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(10);
                } catch (ServiceException se) {
                    return se.getMessage();
                } catch (Exception e) {
                    logger.error("商品实时库存同步定时任务失败", e);
                    return "商品实时库存同步定时任务失败";
                }
            }

        }, TecConstants.REALTIME_INVENTORY_SYN_TASK, true);
    }

    /**
     * 异常商品预警
     */
    public void errorCommodityWarnJob() {
        logger.info("异常商品预警定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(20);
                } catch (ServiceException se) {
                    return se.getMessage();
                } catch (Exception e) {
                    logger.error("异常商品预警定时任务失败", e);
                    return "异常商品预警定时任务失败";
                }
            }

        }, TecConstants.ERROR_COMMODITY_WARN_TASK, true);
    }


    /**
     * 商户昨日商品销售数,补货数与库存变化对比
     */
    public void comSalesRepContrastJob() {
        logger.info("商户商品销售数,补货数与库存变化对比定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(30);
                } catch (ServiceException se) {
                    return se.getMessage();
                } catch (Exception e) {
                    logger.error("商户商品销售数,补货数与库存变化对比定时任务失败", e);
                    return "商户商品销售数,补货数与库存变化对比定时任务失败";
                }
            }

        }, TecConstants.COMMODITY_SALES_REP_CONTRAST_TASK, true);
    }

    /**
     * 同步商品总销售数量 平均销售价 平均成本价
     */
    public void syncCommodityDataJob() {
        logger.info("商户商品总销售数量平均销售价平均成本价同步定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(40);
                } catch (ServiceException se) {
                    return se.getMessage();
                } catch (Exception e) {
                    logger.error("商户商品总销售数量平均销售价平均成本价同步定时任务失败", e);
                    return "商品总销售数量平均销售价平均成本价同步定时任务失败";
                }
            }

        }, TecConstants.SYNC_COMMODITY_DATA_TASK, false);
    }


    /**
     * 商户分页操作
     *
     * @param type 定时任务类型  10 商品实时库存同步  20 异常商品预警
     *             30 商户商品销售数,补货数与库存变化对比
     *             40 商户商品总销售数量平均销售价平均成本价同步
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
                msg = "商品实时库存同步定时任务成功";
            } else if (20 == type.intValue()) {
                msg = "异常商品预警定时任务成功";
            } else if (30 == type.intValue()) {
                msg = "商户商品销售数,补货数与库存变化对比定时任务成功";
            } else if (40 == type.intValue()) {
                msg = "商户商品总销售数量平均销售价平均成本价同步定时任务成功";
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
                                realtimeInventorySynByMerchant(merchantInfo);
                            } else if (20 == type.intValue()) {
                                errorCommodityWarnJobByMerchant(merchantInfo);
                            } else if (30 == type.intValue()) {
                                commoditySalesRepContrastJobByMerchant(merchantInfo);
                            } else if (40 == type.intValue()) {
                                syncCommodityDataJobByMerchant(merchantInfo);
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
            msg = "商品实时库存同步定时任务成功";
        } else if (20 == type.intValue()) {
            msg = "异常商品预警定时任务成功";
        } else if (30 == type.intValue()) {
            msg = "商户商品销售数,补货数与库存变化对比定时任务成功";
        } else if (40 == type.intValue()) {
            msg = "商户商品总销售数量平均销售价平均成本价同步定时任务成功";
        }
        return msg;
    }

    /***
     * 商户商品总销售数量平均销售价平均成本价同步
     * @param merchantInfo 商户编号
     */
    private void syncCommodityDataJobByMerchant(MerchantInfo merchantInfo) {
        logger.info("商户商品总销售数量平均销售价平均成本价同步开始:商户Code=={}", merchantInfo.getScode());
        try {
            int updateNum = commodityInfoService.updateDataByMerchantId(merchantInfo.getId());
            logger.info("同步商户商品总销售数量平均销售价平均成本价成功,商户编号: " + merchantInfo.getScode() + ", 影响记录条数：" + updateNum);
        } catch (Exception e) {
            logger.error("商户商品总销售数量平均销售价平均成本价同步异常{}", e);
        }
    }

    /**
     * 商品实时库存同步 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void realtimeInventorySynByMerchant(MerchantInfo merchantInfo) {
        logger.info("商品实时库存同步开始:商户Code=={}", merchantInfo.getScode());
        try {
            List<RealtimeInventorySynVo> realtimeInventorySynVoList = deviceStockService.selectRealtimeInventorySyn(merchantInfo.getId());
            for (RealtimeInventorySynVo rea : realtimeInventorySynVoList) {
                if (rea.getIstock() != rea.getRealTsimeNum()) {
                    //修改单设备商品库存
                    DeviceStock deviceStock = new DeviceStock();
                    deviceStock.setId(rea.getId());
                    deviceStock.setIstock(rea.getRealTsimeNum());
                    deviceStock.setTlastUpdateTime(DateUtils.getCurrentDateTime());
                    deviceStockService.updateBySelective(deviceStock);
                    logger.info("同步实时商品库存数成功,商户编号=" + merchantInfo.getScode() + ":设备id=" + rea.getDeviceId() + ":商品id=" + rea.getCommodityId());
                }
            }
        } catch (Exception e) {
            logger.error("商户单设备实施库存对比更新异常", e);
        }
    }

    /**
     * 异常商品预警 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void errorCommodityWarnJobByMerchant(MerchantInfo merchantInfo) {
        logger.info("异常商品预警开始:商户Code=={}", merchantInfo.getScode());
        try {
            List<ErrorCommodityVo> errorCommodityVos = stockDetailService.selectErrorCommodity(merchantInfo.getId());
            logger.info("商户异常商品数量为:" + errorCommodityVos.size());
            //有异常商品发送短信提醒
            if (null != errorCommodityVos && errorCommodityVos.size() > 0) {
                InnerMessageDto innerMessageDto = new InnerMessageDto();
                // 模板编号
                innerMessageDto.setTemplateType("error_commodity_warn");
                // 内容
                String deviceName = "";
                for (int i = 0; i < errorCommodityVos.size(); i++) {
                    if (i == errorCommodityVos.size() - 1) {
                        deviceName += errorCommodityVos.get(i).getDeviceName();
                    } else {
                        deviceName += errorCommodityVos.get(i).getDeviceName() + ",";
                    }
                }
                Map<String, Object> map = new HashMap();
                map.put("deviceName", deviceName);
                innerMessageDto.setContentParam(map);
                // 权限编码
                innerMessageDto.setPurviewCode("ERROR_COMMODITY_WARN");
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
            logger.error("异常商品预警异常:{}", e);
        }
    }

    /**
     * 商品销售数,补货数与库存对比 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void commoditySalesRepContrastJobByMerchant(MerchantInfo merchantInfo) {
        //1.查询昨日订单销售商品数
        //2.查询昨日库存变化(销售数和补货数)
        //3.查询补货数
        //4.不相同发送短信
        logger.info("商户商品销售数,补货数与库存变化对比定时任务开始：{}", merchantInfo);
        try {
            Integer yesterdaySalesNum = orderRecordService.selectYesterdaySalesNum(merchantInfo.getId());
            Map map = stockOperRecordService.selectYesterdayStockChangeAndReplenishmentNum(merchantInfo.getId());
            Integer yesterdayReplenishmentNum = replenishmentRecordService.selectYesterdayReplenishmentNum(merchantInfo.getId());
            Long salesNum = (Long) map.get("SALES_NUM");
            Long repNum = (Long) map.get("REPLENISHMENT_NUM");

            if (yesterdaySalesNum.compareTo(salesNum.intValue()) != 0 || yesterdayReplenishmentNum.compareTo(repNum.intValue()) != 0) {
                InnerMessageDto innerMessageDto = new InnerMessageDto();
                // 模板编号
                innerMessageDto.setTemplateType("com_sales_rep_contrast");
                // 内容
                String message = "";
                if (yesterdaySalesNum.compareTo(salesNum.intValue()) != 0) {
                    message += "您的商品昨日总销售数与库存销售数不符,";
                }
                if (yesterdayReplenishmentNum.compareTo(repNum.intValue()) != 0) {
                    message += "您的商品昨日总补货数与库存补货数不符,";
                }
                Map<String, Object> messageMap = new HashMap();
                messageMap.put("message", message);
                innerMessageDto.setContentParam(messageMap);
                // 权限编码
                innerMessageDto.setPurviewCode("COM_SALES_REP_CONTRAST");
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
            logger.error("商品销售数,补货数与库存对比定时任务异常:{}", e);
        }
    }
}
