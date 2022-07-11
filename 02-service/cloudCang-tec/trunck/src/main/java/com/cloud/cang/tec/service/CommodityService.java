package com.cloud.cang.tec.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.cloud.cang.tec.sm.service.StandardDetailService;
import com.cloud.cang.tec.sm.service.StockDetailService;
import com.cloud.cang.tec.sm.vo.StandardDetailVo;
import com.cloud.cang.tec.sm.vo.StockDetailVo;
import com.cloud.cang.tec.sp.service.CommodityInfoService;
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
 * 商品定时任务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service(value = "commodityService")
public class CommodityService {

    private static final Logger logger = LoggerFactory.getLogger(CommodityService.class);
    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private StockDetailService stockDetailService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private StandardDetailService standardDetailService;

    /**
     * 保质期预警
     */
    public void comExpiredWarningJob() {
        logger.info("保质期预警定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(10);
                } catch (Exception e) {
                    logger.error("保质期预警定时任务失败", e);
                    return "保质期预警定时任务失败";
                }
            }
        }, TecConstants.COMMODITY_EXPIRED_WARN_TASK, true);
    }

    /**
     * 库存预警
     */
    public void stockWarningJob() {
        logger.info("库存预警定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(20);
                } catch (Exception e) {
                    logger.error("库存预警定时任务失败", e);
                    return "库存预警定时任务失败";
                }
            }
        }, TecConstants.COMMODITY_STOCK_WARN_TASK, true);
    }

    /**
     * 商品过期状态变更
     */
    public void commodityExpireJob() {
        logger.info("商品过期状态变更定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(30);
                } catch (Exception e) {
                    logger.error("商品过期状态变更定时任务失败", e);
                    return "商品过期状态变更定时任务失败";
                }
            }

        }, TecConstants.COMMODITY_EXPIRED_TASK, true);
    }

    /**
     * 今日已过期商品通知内部人员
     */
    public void commodityExpirePromptJob() {
        logger.info("今日已过期商品通知内部人员定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(40);
                } catch (Exception e) {
                    logger.error("今日已过期商品通知内部人员定时任务失败", e);
                    return "今日已过期商品通知内部人员定时任务失败";
                }
            }

        }, TecConstants.TODAY_COMMODITY_EXPIRED_PROMPT, true);
    }

    /**
     * 商户分页操作
     *
     * @param type 定时任务类型 10 保质期预警 20 库存预警  30 商品过期状态变更  40 今日已过期商品通知内部人员
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
                msg = "商品保质期预警定时任务成功";
            } else if (type.intValue() == 20) {
                msg = "库存预警定时任务成功";
            } else if (type.intValue() == 30) {
                msg = "商品过期状态变更定时任务成功";
            } else if (type.intValue() == 40) {
                msg = "今日已过期商品通知内部人员定时任务成功";
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
                                expiredWarningByMerchant(merchantInfo);
                            } else if (20 == type.intValue()) {
                                stockWarningByMerchant(merchantInfo);
                            } else if (30 == type.intValue()) {
                                commodityExpireByMerchant(merchantInfo);
                            } else if (40 == type.intValue()) {
                                commodityExpirePromptJobByMerchant(merchantInfo);
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
            msg = "商品保质期预警定时任务成功";
        } else if (type.intValue() == 20) {
            msg = "库存预警定时任务成功";
        } else if (type.intValue() == 30) {
            msg = "商品过期状态变更定时任务成功";
        } else if (type.intValue() == 40) {
            msg = "今日已过期商品通知内部人员定时任务成功";
        }
        return msg;
    }

    /**
     * 商品保质期预警 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void expiredWarningByMerchant(MerchantInfo merchantInfo) {
        logger.info("保质期预警开始：{}", merchantInfo);
        List<StockDetailVo> stockDetailVos = stockDetailService.selectExpired(merchantInfo.getId());
        if (null != stockDetailVos && stockDetailVos.size() > 0) {
            InnerMessageDto innerMessageDto = new InnerMessageDto();
            // 模板编号
            innerMessageDto.setTemplateType("com_expired_warning");
            String msg = "";
            for (StockDetailVo stockDetailVo : stockDetailVos) {
                // 内容
                msg = "您的设备" + stockDetailVo.getSdeviceName() + "中有商品" + stockDetailVo.getScommidityName() + "即将过期,";
            }
            Map<String, Object> map = new HashMap();
            map.put("msg", msg);
            innerMessageDto.setContentParam(map);
            // 权限编码
            innerMessageDto.setPurviewCode("COM_EXPIRED_WARNING");
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
                logger.error("商品保质期预警短信发送失败", e);

            }
        }
    }

    /**
     * 商品过期状态变更 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void commodityExpireByMerchant(MerchantInfo merchantInfo) {
        logger.info("商品过期状态变更定时任务开始：{}", merchantInfo);
        List<StockDetail> stockDetailList = stockDetailService.selectExpiredCommodity(merchantInfo.getId());
        for (StockDetail stockDetail : stockDetailList) {
            StockDetail updata = new StockDetail();
            updata.setId(stockDetail.getId());
            updata.setIstatus(30);
            stockDetailService.updateBySelective(updata);
        }
    }

    /**
     * 库存预警 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void stockWarningByMerchant(MerchantInfo merchantInfo) {
        logger.info("商品库存预警开始：{}", merchantInfo);
        List<StandardDetailVo> stockDetailVoList = standardDetailService.selectStockWarningByMerchant(merchantInfo.getId());
        if (null != stockDetailVoList && stockDetailVoList.size() > 0) {
            InnerMessageDto innerMessageDto = new InnerMessageDto();
            // 模板编号
            innerMessageDto.setTemplateType("com_stock_warning");
            // 内容
            String msg = "";

            for (StandardDetailVo standardDetailVo : stockDetailVoList) {
                // 内容
                msg = "您的设备" + standardDetailVo.getSdeviceName() + "中有商品" + standardDetailVo.getScommodityName() + "库存不足,";
            }

            Map<String, Object> map = new HashMap();
            map.put("msg", msg);
            innerMessageDto.setContentParam(map);
            // 权限编码
            innerMessageDto.setPurviewCode("STOCK_WARNING");
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
                logger.error("商品库存预警短信发送失败", e);
            }
        }
    }

    /**
     * 今日已过期商品通知内部人员 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void commodityExpirePromptJobByMerchant(MerchantInfo merchantInfo) {
        logger.info("今日已过期商品通知内部人员定时任务开始：{}", merchantInfo);
        List<StockDetailVo> stockDetailVos = stockDetailService.selectComExpiredPrompt(merchantInfo.getId());
        if (null != stockDetailVos && stockDetailVos.size() > 0) {
            InnerMessageDto innerMessageDto = new InnerMessageDto();
            // 模板类型
            innerMessageDto.setTemplateType("commodity_expired_prompt");
            String msg = "";
            for (StockDetailVo stockDetailVo : stockDetailVos) {
                // 内容
                msg = "您的设备" + stockDetailVo.getSdeviceName() + "中有商品" + stockDetailVo.getScommidityName() + "今日已过期,";
            }
            Map<String, Object> map = new HashMap();
            map.put("msg", msg);
            innerMessageDto.setContentParam(map);
            // 权限编码
            innerMessageDto.setPurviewCode("COMMODITY_EXPIRED_PROMPT");
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
                logger.error("今日已过期商品通知内部人员短信发送失败", e);
            }
        }
    }
}
