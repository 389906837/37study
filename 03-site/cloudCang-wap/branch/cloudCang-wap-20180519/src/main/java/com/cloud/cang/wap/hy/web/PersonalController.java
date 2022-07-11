package com.cloud.cang.wap.hy.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.om.RefundAudit;
import com.cloud.cang.model.om.RefundCommodity;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.wap.ac.service.DiscountRecordService;
import com.cloud.cang.wap.common.SiteResponseVo;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.ParamPage;
import com.cloud.cang.wap.hy.service.MemberInfoService;
import com.cloud.cang.wap.om.service.OrderCommodityService;
import com.cloud.cang.wap.om.service.OrderRecordService;
import com.cloud.cang.wap.om.service.RefundAuditService;
import com.cloud.cang.wap.om.service.RefundCommodityService;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Description: 我的
 * @Author: zhouhong
 * @Date: 2018/4/17 16:28
 */
@Controller
@RequestMapping("/personal")
public class PersonalController {
    @Autowired
    private MemberInfoService memberInfoService;
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
    /**
     * 我的
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    public String getMineInfo(ModelMap modelMap) {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(authVo.getId());
        modelMap.put("user", memberInfo);
        if (authVo.isReplenishment()) {//是否补货员
            modelMap.put("isReplenishment", 1);
        }
        return "mine/mine";
    }


    /***
     * 我的订单列表
     * @param itype 订单类型 10 全部 20 待付款 30 支付失败 40 已完成
     * @return
     */
    @RequestMapping("/orderList")
    public String orderList(Integer itype, ModelMap modelMap) {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        if(null == itype) {
            itype = 10;
        }
        modelMap.put("pageSize", WapConstants.PAGE_SIZE);
        modelMap.put("itype",itype);
        //查询订单数量
        Map<String, Object> mapValue = orderRecordService.statisticalOrderNumByMemberId(authVo.getId());
        modelMap.put("allCount", (Long) mapValue.get("ALLCOUNT"));//全部
        modelMap.put("payCount", (Long) mapValue.get("PAYCOUNT"));//待支付
        modelMap.put("failureCount", (Long) mapValue.get("FAILURECOUNT"));//支付失败
        modelMap.put("successCount", (Long) mapValue.get("SUCCESSCOUNT"));//支付成功

        //退款订单数量
        Long refundNum = refundAuditService.selectRefundNum(authVo.getId());
        modelMap.put("refundNum",refundNum);
        return "mine/order_list";
    }


    /**
     * 我的订单列表
     * @author zhouhong
     * @param itype 订单类型 10 全部 20 待付款 30 已完成 40 退款订单
     * @return
     */
    @RequestMapping("/myOrderList")
    @ResponseBody
    ResponseVo<List<OrderDomian>> myOrderList(ParamPage paramPage, Integer itype) {
        if(itype == null) {
            itype = 10;
        }
        if(itype.intValue() == 10 || itype.intValue() == 20 || itype.intValue() == 30 || itype.intValue() == 40) {
        } else {
            ResponseVo<List<OrderDomian>> responseVo = new ResponseVo<List<OrderDomian>>();
            responseVo.setSuccess(false);
            return responseVo;
        }
        Page<OrderDomian> page = new Page<OrderDomian>();
        page.setPageNum(paramPage.pageNo() > 0 ? paramPage.pageNo() : 1);
        page.setPageSize(paramPage.getLimit() > 0 ? paramPage.getLimit() : WapConstants.PAGE_SIZE);
        Map<String,Object> map = new HashMap<String, Object>();
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        map.put("memberId", authVo.getId());
        map.put("itype", itype);
        if (itype.intValue() == 40) {//退款订单数据
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
        params.put("sorderId",orderRecord.getId());
        List<OrderCommodity> commodities = orderCommodityService.selectByMapWhere(params);
        modelMap.put("commodities",commodities);

        String promotionTitle = "促销优惠";
        //促销优惠金额 是否大于0  获取促销活动明细
        if (null != orderRecord.getFpromotionDiscountAmount() && orderRecord.getFpromotionDiscountAmount().doubleValue() > 0) {
            //查询活动优惠
            String sourceCode = orderRecord.getSorderCode();
            if (null != orderRecord.getIisDismantling() && orderRecord.getIisDismantling().intValue() == 1) {//拆单
                sourceCode = orderRecord.getSdismantlingCode();
            }
            params = new HashMap<String, Object>();
            params.put("sourceCode",sourceCode);
            params.put("sourceType", BizTypeDefinitionEnum.SourceBizStatus.SALESPROMOTION.getCode());
            DiscountRecord discountRecord = discountRecordService.selectDiscountRecordByMap(params);

            if (null != discountRecord) {
                promotionTitle = discountRecord.getSacName();
            }
        }
        modelMap.put("promotionTitle", promotionTitle);

        return new ModelAndView("mine/order_detail");
    }

    /***
     * 退款订单详情
     * @param refundCode 退款订单编号
     * @return
     */
    @RequestMapping("/refundOrderDetail/{refundCode}")
    public ModelAndView refundOrderDetail(@PathVariable String refundCode, ModelMap modelMap) {
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
        List<RefundCommodity> commodities = refundCommodityService.selectByMapWhere(params);
        modelMap.put("commodities", commodities);

        return new ModelAndView("mine/refund_order_detail");
    }
}
