package com.cloud.cang.mgr.om.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.om.dao.RefundAuditDao;
import com.cloud.cang.mgr.om.domain.RefundAuditDomain;
import com.cloud.cang.mgr.om.service.*;
import com.cloud.cang.mgr.om.vo.RefunAuditVo;
import com.cloud.cang.mgr.om.web.RefundAuditController;
import com.cloud.cang.mgr.sq.service.RefundApplyService;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.*;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.pay.PayServicesDefine;
import com.cloud.cang.pay.RefundApplyDto;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RefundAuditServiceImpl extends GenericServiceImpl<RefundAudit, String> implements
        RefundAuditService {

    @Autowired
    RefundAuditDao refundAuditDao;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ICached iCached;
    @Autowired
    private RefundOperlogService refundOperlogService;
    @Autowired
    private RefundCommodityService refundCommodityService;
    @Autowired
    private OrderCommodityService orderCommodityService;
    @Autowired
    private RefundApplyService refundApplyService;
    private static final Logger logger = LoggerFactory.getLogger(RefundAuditServiceImpl.class);


    @Override
    public GenericDao<RefundAudit, String> getDao() {
        return refundAuditDao;
    }


    @Override
    public Page<RefunAuditVo> selectPageByDomainWhere(Page<RefunAuditVo> page, RefundAuditDomain refundAuditDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<RefunAuditVo>) refundAuditDao.selectPageByDomainWhere(refundAuditDomain);
    }

    /**
     * 审核订单
     *
     * @param refundAuditDomain
     * @return
     */
    public ResponseVo audit(RefundAuditDomain refundAuditDomain, HttpServletRequest request) throws Exception {

        String merchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
        MerchantClientConf merchantClientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
        int isRefundAudit = merchantClientConf.getIisRefundAudit();
        RefundAudit upRefundAudit = new RefundAudit();
        //如果审核通过并且通过可直接退款
        if (20 == refundAuditDomain.getIauditStatus() && 0 == isRefundAudit) {
            RefundAudit refundAudit = refundAuditDao.selectByPrimaryKey(refundAuditDomain.getId());
            if (null == refundAudit) {
                logger.error("退款审核订单不存在！");
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("退款审核订单不存在!");
            }
            try {
                this.refund(refundAudit, request);
            } catch (Exception e) {
                logger.info("退款订单审核通过退款失败！");
            }
        } else if (20 == refundAuditDomain.getIauditStatus() && 1 == isRefundAudit) {
            upRefundAudit.setIrefundStatus(10);
        } else if (30 == refundAuditDomain.getIauditStatus()) {
            //审核拒绝
            //1.减少订单表退款总金额
            //2.减少订单明细退款金额,退款数量
            RefundAudit refundAudit = this.selectByPrimaryKey(refundAuditDomain.getId());
            //1.修改订单主表 退款总金额
            OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundAuditDomain.getSorderId());
            if (null == orderRecord) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("审核订单订单不存在！");
            }
            orderRecord.setFactualRefundAmount(orderRecord.getFactualRefundAmount().subtract(refundAudit.getFactualRefundAmount()));
            orderRecordService.updateBySelective(orderRecord);
            //2.减少订单明细退款金额,退款数量
            RefundCommodity refundCommodity = new RefundCommodity();
            refundCommodity.setSrefundId(refundAuditDomain.getId());
            List<RefundCommodity> refundCommodityList = refundCommodityService.selectByEntityWhere(refundCommodity);
            for (RefundCommodity re : refundCommodityList) {
                OrderCommodity orderCommodity = orderCommodityService.selectByOrderIdAndComId(orderRecord.getId(), re.getScommodityId());
                if (null != orderCommodity) {
                    orderCommodity.setFrefundAmount(orderCommodity.getFrefundAmount().subtract(re.getFrefundAmount()));
                    orderCommodity.setFrefundCount(orderCommodity.getFrefundCount() - re.getFrefundCount());
                    orderCommodityService.updateBySelective(orderCommodity);
                }
            }
        }
        upRefundAudit.setIauditStatus(refundAuditDomain.getIauditStatus());
        upRefundAudit.setSauditRefuseReason(refundAuditDomain.getSauditRefuseReason());
        upRefundAudit.setId(refundAuditDomain.getId());
        upRefundAudit.setTauditTime(DateUtils.getCurrentDateTime());
        upRefundAudit.setTupdateTime(DateUtils.getCurrentDateTime());
        upRefundAudit.setSauditOperId(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        upRefundAudit.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        this.updateBySelective(upRefundAudit);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 审核订单退款
     *
     * @param refundAudit
     * @return
     */
    @Override
    public ResponseVo refund(RefundAudit refundAudit, HttpServletRequest request) {
        OrderRecord orderRecord = orderRecordService.selectByPrimaryKey(refundAudit.getSorderId());
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderRecord.getSmemberId());
        //申请退款操作日志表
        RefundOperlog refundOperlog = new RefundOperlog();
        refundOperlog.setSrefundId(refundAudit.getId());
        refundOperlog.setSrefundCode(refundAudit.getSrefundCode());
        refundOperlog.setSoperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        refundOperlog.setSip(SessionUserUtils.getIpAddr(request));
        refundOperlog.setTaddTime(DateUtils.getCurrentDateTime());
        // 组装数据
        //======必填=========
        RefundApplyDto refundApplyDto = new RefundApplyDto();
        refundApplyDto.setSmerchantCode(refundAudit.getSmerchantCode());
        refundApplyDto.setSorderId(refundAudit.getSorderId());
        refundApplyDto.setSorderCode(refundAudit.getSorderCode());
        refundApplyDto.setSauditOrderId(refundAudit.getId());
        refundApplyDto.setSauditOrderCode(refundAudit.getSrefundCode());
        refundApplyDto.setStransactionId(orderRecord.getSpaySerialNumber());

        refundApplyDto.setFtotalMoney(refundAudit.getFactualPayAmount());//订单实付金额
        refundApplyDto.setFrefundMoney(refundAudit.getFapplyRefundAmount()); //申请退款金额
        refundApplyDto.setSmemberId(refundAudit.getSmemberId());
        refundApplyDto.setSmemberCode(refundAudit.getSmemberCode());
        refundApplyDto.setSmemberName(refundAudit.getSmemberName());
        //用户IP
        refundApplyDto.setSip(memberInfo.getSmemberRegip());
        refundApplyDto.setIpayType(orderRecord.getIpayType());//支付类型
        //======选填=========
        //一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号
        RefundApply refundApply = new RefundApply();
        refundApply.setSorderId(refundAudit.getSorderId());
        refundApply = refundApplyService.selectByOrderId(refundApply);
        if (null != refundApply && 20 != refundApply.getIstatus()) {
            refundApplyDto.setSrefundNo(refundAudit.getSrefundCode());//退款编号
        }
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.REFUND_APPLY);
            invoke.setRequestObj(refundApplyDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<HashMap<String, Object>>>() {
            });
            ResponseVo<HashMap<String, Object>> responseVo = (ResponseVo<HashMap<String, Object>>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess()) {
                refundAudit.setIrefundStatus(30);//退款状态
                refundAudit.setTrefundTime(DateUtils.getCurrentDateTime());//退款时间
                if (BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode() == orderRecord.getIpayType()) {
                    Long date = (Long) responseVo.getData().get("refundTime");
                    refundAudit.setTrefundCompleteTime(new Date(date));//退款完成时间
                } else if (BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode() == orderRecord.getIpayType()) {

                    String date = (String) responseVo.getData().get("refundTime");
                    refundAudit.setTrefundCompleteTime(DateUtils.parseDateByFormat(date, "yyyy-MM-dd HH:mm:ss"));//退款完成时间
                }
                String tradeNo = (String) responseVo.getData().get("tradeNo");//退款流水号
                refundAudit.setSpaySerialNumber(tradeNo);
                refundAudit.setSrefundOperId(SessionUserUtils.getSessionAttributeForUserDtl().getId());//退款操作人ID
                refundAudit.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//退款操作人姓名
                this.updateBySelective(refundAudit);
                logger.info("调用订单退款申请接口服务成功");
                //添加申请退款操作日志表
                refundOperlog.setScontent("退款审核订单退款成功！");
                refundOperlogService.insert(refundOperlog);
                return ResponseVo.getSuccessResponse();
            } else {
                refundAudit.setIrefundStatus(20);//退款状态
                refundAudit.setSrefundFailureReason(responseVo.getMsg());
                refundAudit.setTrefundTime(DateUtils.getCurrentDateTime());//退款时间
                this.updateByPrimaryKey(refundAudit);
                refundOperlog.setScontent("退款审核订单退款失败！");
                refundOperlogService.insert(refundOperlog);
                logger.info("调用订单退款申请接口服务失败{}" + JSON.toJSONString(responseVo));
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("退款失败!");
            }
        } catch (Exception e) {
            logger.error("审核订单退款失败！", e);
            throw new ServiceException("审核订单退款失败！");
        }
    }

    /**
     * 导出EXC
     *
     * @param queryCondition
     * @return
     */
    @Override
    public List<Map<String, Object>> selectDownloadExcelData(String queryCondition) {
        return refundAuditDao.selectDownloadExcelData(queryCondition);
    }

    /**
     * 退款申请列表页脚总合计
     *
     * @param refundAuditDomain
     * @return BigDecimal
     */
    @Override
    public BigDecimal queryTotalData(RefundAuditDomain refundAuditDomain) {
        return refundAuditDao.queryTotalData(refundAuditDomain);
    }
}