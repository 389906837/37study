package com.cloud.cang.tec.service;

import com.alipay.api.domain.Coupon;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.model.ac.CouponUserSend;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.tec.ac.service.CouponUserService;
import com.cloud.cang.tec.ac.vo.CouponUserVo;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.cloud.cang.tec.sm.service.StandardDetailService;
import com.cloud.cang.tec.sm.service.StockDetailService;
import com.cloud.cang.tec.sm.vo.StandardDetailVo;
import com.cloud.cang.tec.sm.vo.StockDetailVo;
import com.cloud.cang.tec.sp.service.CommodityInfoService;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.server.SessionTracker;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券定时任务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service(value = "couponService")
public class CouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);
    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private CouponUserService couponUserService;

    /**
     * 优惠券过期预警 
     */
    public void couponExpiredWarningJob() {
        logger.info("优惠券过期预警定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(10);
                } catch (Exception e) {
                    logger.error("优惠券过期预警定时任务失败", e);
                    return "优惠券过期预警定时任务失败";
                }
            }
        }, TecConstants.COUPON_EXPIRED_WARN_TASK, true);
    }

    /**
     * 优惠券过期状态变更 
     */
    public void couponExpiredJob() {
        logger.info("优惠券过期状态变更定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(20);
                } catch (Exception e) {
                    logger.error("优惠券过期状态变更定时任务失败", e);
                    return "优惠券过期状态变更定时任务失败";
                }
            }
        }, TecConstants.COUPON_EXPIRED_TASK, true);
    }

    /**
     * 商户分页操作
     *
     * @param type 定时任务类型 10 优惠券过期预警 20 优惠券过期状态变更
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
                msg = "优惠券过期预警定时任务成功";
            } else if (type.intValue() == 20) {
                msg = "优惠券过期状态变更定时任务成功";
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
                                couponExpiredWarnByMerchant(merchantInfo);
                            } else if (20 == type.intValue()) {
                                couponExpiredByMerchant(merchantInfo);
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
            msg = "优惠券过期预警定时任务成功";
        } else if (type.intValue() == 20) {
            msg = "优惠券过期状态变更定时任务成功";
        }
        return msg;
    }

    /**
     * 优惠券过期预警 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void couponExpiredWarnByMerchant(MerchantInfo merchantInfo) {
        logger.info("优惠券过期预警开始：{}", merchantInfo);
        String expireDate = BizParaUtil.get("couponuser_expired_warn");
        if (StringUtils.isBlank(expireDate)) {
            expireDate = "3";
        }
        List<CouponUserVo> couponUserVoList = couponUserService.selectExpiredCouponWarn(merchantInfo.getId(),expireDate);
        StringBuffer msgInfo = null;
        for (CouponUserVo couponUserVo : couponUserVoList) {
            MessageDto messageDto = new MessageDto();
            //商户Id
            messageDto.setSmerchantId(merchantInfo.getId());
            messageDto.setSmerchantCode(merchantInfo.getScode());

            // 模板类型
            messageDto.setTemplateType("couponuser_expired_warning");
            // 内容
            msgInfo = new StringBuffer();
            if (couponUserVo.getXj() != null && couponUserVo.getXj().intValue() > 0) {
                msgInfo.append(couponUserVo.getXj().intValue() + "张现金券，");
            }
            if (couponUserVo.getDk() != null && couponUserVo.getDk().intValue() > 0) {
                msgInfo.append(couponUserVo.getDk().intValue() + "张抵扣券，");
            }
            if (couponUserVo.getMj() != null && couponUserVo.getMj().intValue() > 0) {
                msgInfo.append(couponUserVo.getMj().intValue() + "张满减券，");
            }
            if (couponUserVo.getSp() != null && couponUserVo.getSp().intValue() > 0) {
                msgInfo.append(couponUserVo.getSp().intValue() + "张商品券，");
            }

            Map<String, Object> map = new HashMap();
            map.put("msg",  msgInfo.toString().substring(0,msgInfo.length()-1));
            map.put("expireDate",  expireDate);
            messageDto.setContentParam(map);
            messageDto.setMobile(couponUserVo.getSmemberName());
            try {
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_SINGLE_MESSAGE_SEND_SERVICE);
                invoke.setRequestObj(messageDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {});
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            } catch (Exception e) {
                logger.error("优惠券过期预警短信发送异常：{}", e);
            }
        }
    }

    /**
     * 优惠券过期状态变更 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void couponExpiredByMerchant(MerchantInfo merchantInfo) {
        logger.info("商品过期状态变更定时任务开始：{}", merchantInfo);
        List<CouponUser> couponUserList = couponUserService.selectExpiredCoupon(merchantInfo.getId());
        for (CouponUser couponUser : couponUserList) {
            CouponUser updata = new CouponUser();
            updata.setId(couponUser.getId());
            updata.setIstatus(3);
            couponUserService.updateBySelective(updata);
        }
    }
}
