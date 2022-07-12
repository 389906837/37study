package com.cloud.cang.mgr.om.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.service.OrderCommodityService;
import com.cloud.cang.mgr.om.vo.OrderCommodityEditVo;
import com.cloud.cang.mgr.om.vo.OrderRecordMoneyStaVo;
import com.cloud.cang.mgr.om.vo.OrderRecordVo;
import com.cloud.cang.mgr.om.web.OrderRecordController;
import com.cloud.cang.mgr.sh.vo.DomainConfVo;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.pay.CancelSmartretailOrderResult;
import com.cloud.cang.pay.CancelSmartretailOrdersDto;
import com.cloud.cang.pay.FreeServicesDefine;
import com.cloud.cang.pay.PayServicesDefine;
import com.cloud.cang.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.om.dao.OrderRecordDao;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.mgr.om.service.OrderRecordService;

import javax.servlet.http.HttpServletRequest;

@Service
public class OrderRecordServiceImpl extends GenericServiceImpl<OrderRecord, String> implements
        OrderRecordService {
    private static final Logger logger = LoggerFactory.getLogger(OrderRecordServiceImpl.class);

    @Autowired
    OrderRecordDao orderRecordDao;
    @Autowired
    OrderCommodityService orderCommodityService;
    @Autowired
    private CommodityInfoService commodityInfoService;

    @Override
    public GenericDao<OrderRecord, String> getDao() {
        return orderRecordDao;
    }

    @Override
    public Page<OrderRecordVo> selectPageByDomainWhere(Page<OrderRecordVo> page, OrderRecordDomain orderRecordDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return orderRecordDao.selectPageByDomainWhere(orderRecordDomain);
    }

    @Override
    public ResponseVo saveOrder(OrderRecord orderRecord, HttpServletRequest request, String commodityIds) throws UnsupportedEncodingException {

        //删除原有的订单从表
        orderCommodityService.delectByOrderId(orderRecord.getId());
        //重新计算订单商品总成本
        BigDecimal ftotalCostAmount = BigDecimal.ZERO;
        //添加新的订单从表
        String[] arr = commodityIds.substring(0, commodityIds.length() - 1).split(",");
        for (int i = 0; i < arr.length; i++) {
            OrderCommodityEditVo orderCommodityEditVo = commodityInfoService.selectExisCommodity(arr[i], orderRecord.getSdeviceId());
            if (null == orderCommodityEditVo) {
                throw new ServiceException("设备里不存在该商品，请重新选择！");
            }
            OrderCommodity orderCommodity = new OrderCommodity();
            orderCommodity.setSorderId(orderRecord.getId());
            orderCommodity.setSorderCode(orderRecord.getSorderCode());
            orderCommodity.setScommodityId(request.getParameter("scommodityId_" + arr[i]));
            orderCommodity.setScommodityCode(request.getParameter("scommodityCode_" + arr[i]));
            orderCommodity.setScommodityName(orderCommodityEditVo.getSname());
            orderCommodity.setFcostAmount(orderCommodityEditVo.getFcostAmount());
            orderCommodity.setFcommodityPrice(orderCommodityEditVo.getFsalePrice());
            orderCommodity.setFtaxPoint(orderCommodityEditVo.getFtaxPoint());
           /* orderCommodity.setScommodityName(new String(request.getParameter("scommodityName_" + arr[i]).getBytes("iso-8859-1"), "utf-8"));*/
        /*    orderCommodity.setFcommodityPrice(new BigDecimal(request.getParameter("fcommodityPrice_" + arr[i])));*/
            /*orderCommodity.setFcostAmount(new BigDecimal(request.getParameter("fcostAmount_" + arr[i])));*/
            orderCommodity.setForderCount(Integer.valueOf(request.getParameter("forderCount_" + arr[i])));
            orderCommodity.setFcommodityAmount(new BigDecimal(request.getParameter("fcommodityAmount_" + arr[i])));
            orderCommodity.setFdiscountAmount(new BigDecimal(request.getParameter("fdiscountAmount_" + arr[i])));
            orderCommodity.setFfirstDiscountAmount(new BigDecimal(request.getParameter("ffirstDiscountAmount_" + arr[i])));
            orderCommodity.setFpromotionDiscountAmount(new BigDecimal(request.getParameter("fpromotionDiscountAmount_" + arr[i])));
            orderCommodity.setFcouponDeductionAmount(new BigDecimal(request.getParameter("fcouponDeductionAmount_" + arr[i])));
            orderCommodity.setFpointDiscountAmount(BigDecimal.ZERO);
            orderCommodity.setFotherDiscountAmount(BigDecimal.ZERO);
            orderCommodity.setIpoint(BigDecimal.ZERO);
            orderCommodity.setFactualAmount(new BigDecimal(request.getParameter("factualAmount_" + arr[i])));
/*            orderCommodity.setFrefundCount(Integer.valueOf(request.getParameter("frefundCount_" + arr[i])));
            orderCommodity.setFrefundAmount(new BigDecimal(request.getParameter("frefundAmount_" + arr[i])));*/
            orderCommodity.setFrefundCount(0);
            orderCommodity.setFrefundAmount(BigDecimal.ZERO);
            orderCommodity.setTaddTime(DateUtils.getCurrentDateTime());
            orderCommodity.setTupdateTime(DateUtils.getCurrentDateTime());
            orderCommodityService.insert(orderCommodity);
            ftotalCostAmount = ftotalCostAmount.add(orderCommodityEditVo.getFcostAmount().multiply(new BigDecimal(request.getParameter("forderCount_" + arr[i]))));
        }
        orderRecord.setFtotalCostAmount(ftotalCostAmount);
        this.updateBySelective(orderRecord);
        logger.info("编辑订单成功：{}", orderRecord.getSorderCode());
        String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.eidt.order.success"), orderRecord.getSorderCode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 查询导出的Excel数据
     *
     * @param orderRecordDomain
     * @return List<OrderRecordVo>
     */
    @Override
    public List<Map<String, Object>> selectDownloadExcelData(OrderRecordDomain orderRecordDomain) {
        return orderRecordDao.selectDownloadExcelData(orderRecordDomain);
    }

    /**
     * 订单列表页脚总统计
     *
     * @param orderRecordDomain
     * @return OrderRecordMoneyStaVo
     */
    @Override
    public OrderRecordMoneyStaVo queryTotalData(OrderRecordDomain orderRecordDomain) {
        return orderRecordDao.queryTotalData(orderRecordDomain);
    }

    /**
     * 撤销订单
     *
     * @param orderId
     * @return
     */
    @Override
    public ResponseVo cancelOrder(String orderId) throws Exception {
        //创建Rest服务客户端
        OrderRecord orderRecord = orderRecordDao.selectByPrimaryKey(orderId);
        CancelSmartretailOrdersDto cancelSmartretailOrdersDto = new CancelSmartretailOrdersDto();
        cancelSmartretailOrdersDto.setReason("撤销支付分订单");
        cancelSmartretailOrdersDto.setSmerchantCode(orderRecord.getSmerchantCode());
        cancelSmartretailOrdersDto.setOrderCode(orderRecord.getSorderCode());
        cancelSmartretailOrdersDto.setOrderId(orderRecord.getId());
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder()
                .newInvoker(FreeServicesDefine.WECHAT_POINT_CANCEL_ORDER);
        invoke.setRequestObj(cancelSmartretailOrdersDto);
        //返回对象中含有泛型，则需要设置返回类型，否则无需设置
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<CancelSmartretailOrderResult>>() {
        });
        ResponseVo<CancelSmartretailOrderResult> responseVo = (ResponseVo<CancelSmartretailOrderResult>) invoke.invoke();
        if (null != responseVo && responseVo.isSuccess()) {
            logger.info("微信支付分撤销订单完成：{}", orderRecord.getSorderCode());
            return ResponseVo.getSuccessResponse();
        }
        logger.error("撤销订单失败,订单编号:{}", orderRecord.getSorderCode());
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("撤销订单失败！");
    }
}