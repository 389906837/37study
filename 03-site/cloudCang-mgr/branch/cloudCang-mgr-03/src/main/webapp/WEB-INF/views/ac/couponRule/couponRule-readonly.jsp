<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>送券规则</title>
<link href="${staticSource}/resources/layui/css/layui.css?4" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css?20189" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
    <table  cellspacing="0" border="0">
        <tr>
            <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.is.it.effective' />：</td>
            <td class="text-right" style="width:32%"><c:if test="${couponRule.iisValid eq 1}"><spring:message code="main.enable" /></c:if><c:if test="${(empty couponRule.iisValid ? 0 : couponRule.iisValid) eq 0}"><spring:message code='sh.prohibit' /></c:if></td>
            <td class="text-left" style="width:18%">&nbsp;</td>
            <td class="text-right" style="width:32%">&nbsp;</td>
        </tr>
        <tr>
            <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.voucher.type' />：</td>
            <td class="text-right"  style="width:32%"><c:if test="${couponRule.icouponType eq 10}"><spring:message code='ac.couponrule.cash.coupon' /></c:if>
                <c:if test="${couponRule.icouponType eq 20}"><spring:message code='ac.couponrule.full.coupon' /></c:if>
                <c:if test="${couponRule.icouponType eq 30}"><spring:message code='ac.couponrule.discount.coupon' /></c:if>
                <c:if test="${couponRule.icouponType eq 40}"><spring:message code='ac.couponrule.commodity.ticket' /></c:if></td>
            <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.way.of.sending.coupons' />：</td>
            <td class="text-right"  style="width:32%"><c:if test="${couponRule.igiveMethod eq 10}"><spring:message code='ac.couponrule.proportion' /></c:if>
                <c:if test="${couponRule.igiveMethod eq 20}"><spring:message code='ac.couponrule.fixed.amount' /></c:if></td>
        </tr>
        <c:if test="${couponRule.igiveMethod eq 10}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.minimum.coupon.value.yuan' />：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatNumber value="${couponRule.fminValue}" pattern="#0.##<spring:message code='ac.couponrule.yuan' />"/></td>
                    <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.maximum.coupon.value.yuan' />：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatNumber value="${couponRule.fmaxValue}" pattern="#0.##<spring:message code='ac.couponrule.yuan' />"/></td>
                </tr>
        </c:if>
        <tr>
            <c:if test="${couponRule.icouponType eq 40}">
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.product.information' />：</td>
                <td class="text-right"  style="width:32%">${couponRule.scommodityCode}</td>
            </c:if>
            <c:if test="${couponRule.icouponType ne 40}">
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.send.face.value.yuan.or.%' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponRule.igiveMethod eq 10}"><fmt:formatNumber value="${couponRule.fcouponValue/100}" pattern="#0.##%"/></c:if>
                    <c:if test="${couponRule.igiveMethod eq 20}"><fmt:formatNumber value="${couponRule.fcouponValue}" pattern="#0.##<spring:message code='ac.couponrule.yuan' />"/></c:if></td>
            </c:if>
            <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.number.of.coupons.piece' />：</td>
            <td class="text-right"  style="width:32%">${couponRule.isendNumber}<spring:message code='ac.couponrule.piece' /></td>
        </tr>
        <tr>
            <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.bond.effective.date' />：</td>
            <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponRule.dcouponEffectiveDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.day.of.validity.of.the.voucher' />：</td>
            <td class="text-right"  style="width:32%">${couponRule.icouponValidityValue}<spring:message code='sp.commodityInfo.day' /></td>
        </tr>
        <tr>
            <td class="text-left"  style="width:18%"><spring:message code="main.voucher.expiration.date" />：</td>
            <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponRule.dcouponExpiryDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.voucher.brief' />：</td>
            <td class="text-right" style="width:32%">${couponRule.sbriefDesc}</td>
        </tr>

        <tr>
            <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.voucher.description' />：</td>
            <td class="text-right"  style="width:32%">${couponRule.sactivityInstruction}</td>
            <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.gift.description' />：</td>
            <td class="text-right"  style="width:32%">${couponRule.sremark}</td>
        </tr>
        <c:if test="${iscenesType eq 51 or iscenesType eq 60 or iscenesType eq 70 or itype eq 20}">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.gift.order.payment.amount.yuan' />：</td>
                <td class="text-right" style="width:32%"><fmt:formatNumber value="${empty couponRule.fgiveLimitAmount ? 0 : couponRule.fgiveLimitAmount }" pattern="#0.##元"/></td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.gift.restrictions' />：</td>
                <td class="text-right"  style="width:32%">${(empty couponRule.sgiveLimitCommodity) ? '<spring:message code="main.unlimited"/>' : couponRule.sgiveLimitCommodity}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.gift.classification.restrictions' />：</td>
                <td class="text-right" style="width:32%">${(empty giveLimitCategory) ? '<spring:message code="main.unlimited"/>' : giveLimitCategory}</td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.gift.brand.restrictions' />：</td>
                <td class="text-right"  style="width:32%">${(empty giveLimitBrand) ? '<spring:message code="main.unlimited"/>' : giveLimitBrand}</td>
            </tr>
        </c:if>
        <c:if test="${couponRule.icouponType eq 40}">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.device.restrictions' />：</td>
                <td class="text-right" style="width:32%">${(empty couponRule.suseLimitDevice) ? '<spring:message code="main.unlimited"/>' : couponRule.suseLimitDevice}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </c:if>
        <c:if test="${couponRule.icouponType ne 40}">
            <c:if test="${couponRule.icouponType ne 30}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.order.payment.amount.yuan' />：</td>
                    <td class="text-right" style="width:32%"><fmt:formatNumber value="${empty couponRule.fuseLimitAmount ? 0 : couponRule.fuseLimitAmount }" pattern="#0.##<spring:message code='ac.couponrule.yuan' />"/></td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
            </c:if>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.use.product.restrictions' />：</td>
                <td class="text-right"  style="width:32%">${(empty couponRule.suseLimitCommodity) ? '<spring:message code="main.unlimited"/>' : couponRule.suseLimitCommodity}</td>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.device.restrictions' />：</td>
                <td class="text-right" style="width:32%">${(empty couponRule.suseLimitDevice) ? '<spring:message code="main.unlimited"/>' : couponRule.suseLimitDevice}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.product.classification.restrictions' />：</td>
                <td class="text-right" style="width:32%">${(empty useLimitCategory) ? '<spring:message code="main.unlimited"/>' : useLimitCategory}</td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.use.product.brand.restrictions' />：</td>
                <td class="text-right"  style="width:32%">${(empty useLimitBrand) ? '<spring:message code="main.unlimited"/>' : useLimitBrand}</td>
            </tr>
        </c:if>
    </table>
    </div>
</div>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>