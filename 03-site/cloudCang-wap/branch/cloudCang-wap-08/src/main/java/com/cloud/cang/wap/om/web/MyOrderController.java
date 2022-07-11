package com.cloud.cang.wap.om.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.model.om.*;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.wap.ac.service.DiscountRecordService;
import com.cloud.cang.wap.ac.vo.CouponDomain;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.ParamPage;
import com.cloud.cang.wap.index.service.IndexService;
import com.cloud.cang.wap.om.service.*;
import com.cloud.cang.wap.om.vo.CommodityVo;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.cloud.cang.wap.om.vo.RefundCommodityVo;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 我的订单
 * @Author: zhouhong
 * @Date: 2018/4/17 16:28
 */
@Controller
@RequestMapping("/personal")
public class MyOrderController {

    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private RefundAuditService refundAuditService;
    @Autowired
    private OrderCommodityService orderCommodityService;
    @Autowired
    private DiscountRecordService discountRecordService;
    @Autowired
    private RefundCommodityService refundCommodityService;
    @Autowired
    private RefundImgDescService refundImgDescService;
    @Autowired
    private IndexService indexService;

    private static final Logger logger = LoggerFactory.getLogger(MyOrderController.class);

    /***
     * 我的订单列表
     * @param type 订单类型 All 全部 Fail 待付款支付失败  Success 已完成 Refund 退款订单
     * @return
     */
    @RequestMapping("/order{type}")
    public String orderList(@PathVariable String type, ModelMap modelMap) {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        if (StringUtil.isBlank(type)) {
            type = "All";
        }
        modelMap.put("pageSize", WapConstants.PAGE_SIZE);
        modelMap.put("type", type);
        //查询订单数量
        Map<String, Object> mapValue = orderRecordService.statisticalOrderNumByMemberId(authVo.getId());
        modelMap.put("allCount", (Long) mapValue.get("ALLCOUNT"));//全部
        modelMap.put("payCount", (Long) mapValue.get("PAYCOUNT"));//待支付
        modelMap.put("failureCount", (Long) mapValue.get("FAILURECOUNT"));//支付失败
        modelMap.put("successCount", (Long) mapValue.get("SUCCESSCOUNT"));//支付成功

        //退款订单数量
        Long refundNum = refundAuditService.selectRefundNum(authVo.getId());
        modelMap.put("refundNum", refundNum);

        if(type.equals("All") || type.equals("Fail") || type.equals("Success") || type.equals("Refund")){
            return "mine/order_list_new";
        }
        return "error/404";
    }

    /**
     * 我的订单列表
     * @param type 订单类型 All 全部 Fail 待付款支付失败  Success 已完成 Refund 退款订单
     * @return
     * @author zhouhong
     */
    @RequestMapping("/myOrderList")
    @ResponseBody
    ResponseVo<List<OrderDomian>> myOrderList(ParamPage paramPage, String type) {
        logger.debug("获取我的订单记录{}", type);
        if (StringUtil.isBlank(type)) {
            type = "All";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (!type.equals("All") && !type.equals("Fail") && !type.equals("Success") && !type.equals("Refund")) {
            ResponseVo<List<OrderDomian>> responseVo = ResponseVo.getSuccessResponse();
            responseVo.setSuccess(false);
            return responseVo;
        }
        Page<OrderDomian> page = new Page<OrderDomian>();
        page.setPageNum(paramPage.pageNo() > 0 ? paramPage.pageNo() : 1);
        page.setPageSize(paramPage.getLimit() > 0 ? paramPage.getLimit() : WapConstants.PAGE_SIZE);

        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        map.put("memberId", authVo.getId());
        if (type.equals("Fail")) {
            map.put("itype", 20);
        } else if (type.equals("Success")) {
            map.put("itype", 30);
        }
        if (type.equals("Refund")) {
            page = refundAuditService.selectOrderListByPage(page, map);
        } else {
            page = orderRecordService.selectOrderListByPage(page, map);
        }
        return ResponseVo.getSuccessResponse(page.getTotal(), page.getResult());
    }

    /***
     * 订单详情
     * @param orderCode 订单编号
     * @return
     */
    @RequestMapping("/orderDetail/{orderCode}")
    public ModelAndView orderDetail(@PathVariable String orderCode, ModelMap modelMap) {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据

        OrderRecord orderRecord = orderRecordService.selectByCode(orderCode);
        if (null == orderRecord || !orderRecord.getSmemberId().equals(authVo.getId())) {
            modelMap.put("resVo", new SiteResponseVo(false, -1000, "订单状态异常", -1));
            return new ModelAndView("error/error");
        }
        modelMap.put("orderRecord", orderRecord);
        //订单商品明细
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sorderId", orderRecord.getId());
        List<CommodityVo> commodities = orderCommodityService.selectByMap(params);
        modelMap.put("commodities", commodities);

        String promotionTitle = "促销优惠";
        //促销优惠金额 是否大于0  获取促销活动明细
        if (null != orderRecord.getFpromotionDiscountAmount() && orderRecord.getFpromotionDiscountAmount().doubleValue() > 0) {
            //查询活动优惠
            String sourceCode = orderRecord.getSorderCode();
            if (null != orderRecord.getIisDismantling() && orderRecord.getIisDismantling().intValue() == 1) {//拆单
                sourceCode = orderRecord.getSdismantlingCode();
            }
            params = new HashMap<String, Object>();
            params.put("sourceCode", sourceCode);
            params.put("sourceType", BizTypeDefinitionEnum.SourceBizStatus.SALESPROMOTION.getCode());
            DiscountRecord discountRecord = discountRecordService.selectDiscountRecordByMap(params);

            if (null != discountRecord) {
                promotionTitle = discountRecord.getSacName();
            }
        }
        modelMap.put("promotionTitle", promotionTitle);

        return new ModelAndView("mine/order_detail_new");
    }

    /***
     * 退款订单详情
     * @param refundCode 退款订单编号
     * @return
     */
    @RequestMapping("/refundOrderDetail/{refundCode}")
    public ModelAndView refundOrderDetail(@PathVariable String refundCode, ModelMap modelMap, HttpServletRequest request) {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据

        RefundAudit refundAudit = refundAuditService.selectByCode(refundCode);
        if (null == refundAudit || !refundAudit.getSmemberId().equals(authVo.getId())) {
            modelMap.put("resVo", new SiteResponseVo(false, -1000, "退款订单状态异常", -1));
            return new ModelAndView("error/error");
        }
        modelMap.put("refundAudit", refundAudit);
        //订单商品明细
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("srefundId", refundAudit.getId());
        List<RefundCommodityVo> commodities = refundCommodityService.selectByMap(params);
        modelMap.put("commodities", commodities);


        OrderRecord orderRecord = orderRecordService.selectByCode(refundAudit.getSorderCode());
        if (null == orderRecord || !orderRecord.getSmemberId().equals(authVo.getId())) {
            modelMap.put("resVo", new SiteResponseVo(false, -1000, "退款原订单数据异常", -1));
            return new ModelAndView("error/error");
        }


        String promotionTitle = "促销优惠";
        //促销优惠金额 是否大于0  获取促销活动明细
        if (null != orderRecord.getFpromotionDiscountAmount() && orderRecord.getFpromotionDiscountAmount().doubleValue() > 0) {
            //查询活动优惠
            String sourceCode = orderRecord.getSorderCode();
            if (null != orderRecord.getIisDismantling() && orderRecord.getIisDismantling().intValue() == 1) {//拆单
                sourceCode = orderRecord.getSdismantlingCode();
            }
            params = new HashMap<String, Object>();
            params.put("sourceCode", sourceCode);
            params.put("sourceType", BizTypeDefinitionEnum.SourceBizStatus.SALESPROMOTION.getCode());
            DiscountRecord discountRecord = discountRecordService.selectDiscountRecordByMap(params);

            if (null != discountRecord) {
                promotionTitle = discountRecord.getSacName();
            }
        }
        modelMap.put("promotionTitle", promotionTitle);

        modelMap = indexService.getPayConfig(modelMap, request);

        params = new HashMap<String, Object>();
        params.put("srefundId", refundAudit.getId());
        params.put("orderCondition", "ISORT ASC");
        //退款图片
        List<RefundImgDesc> imgDescs = refundImgDescService.selectByMapWhere(params);
        if (null != imgDescs && imgDescs.size() > 0) {
            modelMap.put("imgDescs", imgDescs);
        }


        return new ModelAndView("mine/refund_order_detail_new");
    }
}
