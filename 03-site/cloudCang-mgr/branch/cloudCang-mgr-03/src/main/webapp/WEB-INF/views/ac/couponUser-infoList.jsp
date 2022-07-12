<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>用户持有券明细</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.voucher.number' />：</td>
                <td class="text-right" style="width:32%">${couponUser.scouponCode}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.voucher.type' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponUser.icouponType == 10}"><spring:message code='ac.couponrule.cash.coupon' /></c:if>
                    <c:if test="${couponUser.icouponType == 20}"><spring:message code='ac.couponrule.full.coupon' /></c:if>
                    <c:if test="${couponUser.icouponType == 30}"><spring:message code='ac.couponrule.discount.coupon' /></c:if>
                    <c:if test="${couponUser.icouponType == 40}"><spring:message code='ac.couponrule.commodity.ticket' /></c:if>
                </td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.voucher.time' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponUser.tgetDatetime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.voucher.value.yuan' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatNumber value="${couponUser.fcouponValue}" pattern="#0.##<spring:message code='ac.couponrule.yuan' />"/></td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.voucher.status' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponUser.istatus == 1 }"><spring:message code='ac.unused' /></c:if>
                    <c:if test="${couponUser.istatus == 2 }"><spring:message code='ac.used' /></c:if>
                    <c:if test="${couponUser.istatus == 3 }"><spring:message code='sm.expired' /></c:if>
                    <c:if test="${couponUser.istatus == 4 }"><spring:message code='ac.freeze' /></c:if>
                    <c:if test="${couponUser.istatus == 5 }"><spring:message code="main.delete" /></c:if>
                </td>
            </tr>
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.bond.effective.date' />：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponUser.dcouponEffectiveDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                    <td class="text-left"  style="width:18%"><spring:message code="main.voucher.expiration.date" />：</td>
                    <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponUser.dcouponExpiryDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                </tr>
            <tr>
                    <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.day.of.validity.of.the.voucher' />：</td>
                    <td class="text-right"  style="width:32%">${couponUser.scouponValidityValue}<spring:message code='vrsku.day' /></td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.voucher.description' />：</td>
                <td class="text-right"  style="width:32%">${couponUser.scouponInstruction}</td>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.voucher.brief' />：</td>
                <td class="text-right"  style="width:32%">${couponUser.sbriefDesc}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="main.member.number" />：</td>
                <td class="text-right"  style="width:32%">${couponUser.smemberCode}</td>
                <td class="text-left" style="width:18%"><spring:message code='main.member.username' />：</td>
                <td class="text-right"  style="width:32%">${couponUser.smemberName}</td>
            </tr>
            <c:if test="${couponUser.isourceType ne 10 }">
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='ac.source' /><spring:message code="activity.code" />：</td>
                <td class="text-right"  style="width:32%">${couponUser.ssourceAcCode}</td>
                <td class="text-left" style="width:18%"><spring:message code='ac.source.activity.name' />：</td>
                <td class="text-right" style="width:32%">${couponUser.ssourceAcName}</td>
            </tr>
            </c:if>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='ac.source.number' />：</td>
                <td class="text-right"  style="width:32%">${couponUser.ssourceCode}</td>
                <td class="text-left" style="width:18%"><spring:message code='ac.source.type' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponUser.isourceType == 10 }"><spring:message code='ac.activityconf.platform.gift' /></c:if>
                    <c:if test="${couponUser.isourceType == 20 }"><spring:message code='ac.activityconf.sign.up' /></c:if>
                    <c:if test="${couponUser.isourceType == 30 }"><spring:message code="main.first.open.alipay.free" /></c:if>
                    <c:if test="${couponUser.isourceType == 40 }"><spring:message code="main.first.open.wechat.free" /></c:if>
                    <c:if test="${couponUser.isourceType == 50 }"><spring:message code='ac.activityconf.recommended.registration' /></c:if>
                    <c:if test="${couponUser.isourceType == 60 }"><spring:message code='ac.activityconf.first.order' /></c:if>
                    <c:if test="${couponUser.isourceType == 70 }"><spring:message code='ac.order.gift' /></c:if>
                    <c:if test="${couponUser.isourceType == 80 }"><spring:message code='menu.sales.promotion' /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='ac.source.description' />：</td>
                <td class="text-right"  style="width:32%">${couponUser.ssourceInstruction}</td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.use.order.number' />：</td>
                <td class="text-right"  style="width:32%">${couponUser.suseTargetCode}</td>
            </tr>
                    <tr>
                        <td class="text-left" style="width:18%"><spring:message code='ac.actual.usage.amount.yuan' />：</td>
                        <td class="text-right" style="width:32%">${couponUser.factualExchangeAmount}</td>
                        <td class="text-left" style="width:18%"><spring:message code='ac.actual.use.time' />：</td>
                        <td class="text-right" style="width:32%"><fmt:formatDate value='${couponUser.tactualUseDatetime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                    </tr>
            <c:if test="${couponUser.icouponType ne 30}">
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.order.payment.amount.yuan' />：</td>
                    <td class="text-right" style="width:32%">${couponUser.fuseLimitAmount}</td>
                    <td class="text-left" style="width:18%">&nbsp;</td>
                    <td class="text-right" style="width:32%">&nbsp;</td>
                </tr>
            </c:if>
                <tr>
                    <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.use.product.restrictions' />：</td>
                    <td class="text-right"  style="width:32%">${(empty couponUser.suseLimitCommodity) ? '<spring:message code="main.unlimited"/>' : couponUser.suseLimitCommodity}</td>
                    <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.device.restrictions' />：</td>
                    <td class="text-right" style="width:32%">${(empty couponUser.suseLimitDevice) ? '<spring:message code="main.unlimited"/>' : couponUser.suseLimitDevice}</td>
                </tr>
                <tr>
                    <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.product.classification.restrictions' />：</td>
                    <td class="text-right" style="width:32%">${(empty useLimitCategory) ? '<spring:message code="main.unlimited"/>' : useLimitCategory}</td>
                    <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.use.product.brand.restrictions' />：</td>
                    <td class="text-right"  style="width:32%">${(empty useLimitBrand) ? '<spring:message code="main.unlimited"/>' : useLimitBrand}</td>
                </tr>
        </table>
    </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

