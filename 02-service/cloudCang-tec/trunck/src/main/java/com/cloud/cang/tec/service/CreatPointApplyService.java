package com.cloud.cang.tec.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.jy.GeneratingOrderResults;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sq.CreatApply;
import com.cloud.cang.pay.EndSmartretailOrderResult;
import com.cloud.cang.pay.FreeServicesDefine;
import com.cloud.cang.pay.QueryAndEndSmartretailOrderDto;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.hy.service.MemberInfoService;
import com.cloud.cang.tec.sb.service.DeviceInfoService;
import com.cloud.cang.tec.sq.service.CreatApplyService;
import com.cloud.cang.tec.sq.vo.CreatApplyVo;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 取消创建微信支付分订单定时任务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service(value = "creatPointApplyService")
public class CreatPointApplyService {
    private static final Logger logger = LoggerFactory.getLogger(CreatPointApplyService.class);
    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private CreatApplyService creatApplyService;
    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 取消创建微信支付分订单
     */
    public void endWechatPointCreateApplyJob() {
        logger.info("取消创建微信支付分订单定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOper(10);
                } catch (Exception e) {
                    logger.error("取消创建微信支付分订单定时任务失败", e);
                    return "取消创建微信支付分订单定时任务失败";
                }
            }
        }, TecConstants.END_WECHAT_POINT_CREATE_APPLY_TASK, true);
    }

    /**
     * @param type 定时任务类型 10 取消创建微信支付分订单
     * @return
     * @throws Exception
     */
    private String pageOper(final Integer type) throws Exception {
        String msg = "定时任务执行成功";
        Page<CreatApply> p = new Page<CreatApply>();
        p.setPageSize(10);
        p.setPageNum(1);
        final CreatApplyVo creatApplyVo = new CreatApplyVo();
        if (10 == type) {
            creatApplyVo.setIstatus(10);
            creatApplyVo.setDateCondition("2");
        }
        p = (Page<CreatApply>) creatApplyService.queryPage(p, creatApplyVo);
        if (null == p || 0 == p.getPages()) {
            if (type.intValue() == 10) {
                msg = "取消创建微信支付分订单定时任务成功";
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
            TaskAction<List<CreatApply>>[] taskActions = new TaskAction[endPage - startPage];
            for (int page = (i - 1) * pageSize + 1; page <= endPage; page++) {
                //根据页数循环创建任务，一页一个任务
                final int _page = page;
                TaskAction<List<CreatApply>> taskAction = new TaskAction<List<CreatApply>>() {
                    @Override
                    public List<CreatApply> doInAction() {
                        Page<CreatApply> pl = new Page<CreatApply>();
                        pl.setPageSize(10);
                        pl.setPageNum(_page);
                        pl = (Page<CreatApply>) creatApplyService.queryPage(pl, creatApplyVo);
                        List<CreatApply> creatApplyList = pl.getResult();
                        for (CreatApply creatApply1 : creatApplyList) {
                            //执行定时任务方法
                            if (10 == type.intValue()) {
                                endWechatPointCreateApply(creatApply1);
                            }
                        }
                        return creatApplyList;
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
            msg = "取消创建微信支付分订单定时任务成功";
        }
        return msg;
    }

    /**
     * @param creatApply
     */
    private void endWechatPointCreateApply(CreatApply creatApply) {
        try {
            //取消订单
            QueryAndEndSmartretailOrderDto queryAndEndSmartretailOrderDto = new QueryAndEndSmartretailOrderDto();
            MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(creatApply.getSmemberId());
            queryAndEndSmartretailOrderDto.setSmerchantCode(memberInfo.getSmerchantCode());
            queryAndEndSmartretailOrderDto.setSmemberId(creatApply.getSmemberId());
            queryAndEndSmartretailOrderDto.setDeviceId(creatApply.getSdeviceId());
            queryAndEndSmartretailOrderDto.setFinish_type(1);
            queryAndEndSmartretailOrderDto.setCancel_reason("取消创建过期的微信支付分订单");
            queryAndEndSmartretailOrderDto.setProfit_sharing(false);
            queryAndEndSmartretailOrderDto.setCreatApplyId(creatApply.getId());
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_POINT_QUERY_AND_END_ORDER);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置

            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<EndSmartretailOrderResult>>() {
            });
            invoke.setRequestObj(queryAndEndSmartretailOrderDto); // post 参数
            ResponseVo<EndSmartretailOrderResult> responseVo = (ResponseVo<EndSmartretailOrderResult>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess()) {
                //补处理成功,订单状态修改为已支付
                logger.info("定时任务调用取消创建微信支付分订单服务成功");
            } else {
                logger.error("定时任务调用取消创建微信支付分订单服务失败{}", JSON.toJSONString(responseVo));
            }
        } catch (Exception e) {
            logger.error("定时任务调用取消创建微信支付分订单请求服务异常", e);
        }
    }
}
