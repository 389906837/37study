<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>优惠券批量下发详情</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="ibox-content">
    <div class="layui-form-item wflayui-form-item">
        <table  cellspacing="0" border="0">
            <tr>
                <td class="text-left" style="width:18%"><spring:message code="activity.issuance.batch.number" />：</td>
                <td class="text-right" style="width:32%">${couponBatch.sbatchCode}</td>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.voucher.type' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponBatch.icouponType == 10}"><spring:message code='ac.couponrule.cash.coupon' /></c:if>
                    <c:if test="${couponBatch.icouponType == 20}"><spring:message code='ac.couponrule.full.coupon' /></c:if>
                    <c:if test="${couponBatch.icouponType == 30}"><spring:message code='ac.couponrule.discount.coupon' /></c:if>
                    <c:if test="${couponBatch.icouponType == 40}"><spring:message code='ac.couponrule.commodity.ticket' /></c:if>
                </td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.voucher.value.yuan' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatNumber value="${couponBatch.fcouponValue}" pattern="#0.##<spring:message code='ac.couponrule.yuan' />"/></td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.day.of.validity.of.the.voucher' />：</td>
                <td class="text-right"  style="width:32%">${couponBatch.scouponValidityValue}<spring:message code='vrsku.day' /></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.voucher.effective.time' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponBatch.dcouponEffectiveDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td class="text-left"  style="width:18%"><spring:message code="main.voucher.expiration.date" />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponBatch.dcouponExpiryDate}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.delivery.type' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponBatch.itype == 0 }"><spring:message code='ac.coupon' /></c:if>
                    <c:if test="${couponBatch.itype == 1 }"><spring:message code='ac.coupon.code' /></c:if>
                    <c:if test="${couponBatch.itype == 2 }"><spring:message code='ac.marketing.event.voucher' /></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='ac.number.of.issues' />：</td>
                <td class="text-right"  style="width:32%">${couponBatch.ibatchNumber}<spring:message code='ac.couponrule.piece' /></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='sb.audit.status' />：</td>
                <td class="text-right"  style="width:32%">
                    <c:if test="${couponBatch.istatus == 10 }"><spring:message code="main.draft"/></c:if>
                    <c:if test="${couponBatch.istatus == 11 }"><spring:message code="main.audited"/></c:if>
                    <c:if test="${couponBatch.istatus == 20 }"><spring:message code="main.approval"/></c:if>
                    <c:if test="${couponBatch.istatus == 21 }"><spring:message code="main.rejection"/></c:if>
                </td>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.order.payment.amount.yuan' />：</td>
                <td class="text-right" style="width:32%">${couponBatch.fuseLimitAmount}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code="main.reviewer" />：</td>
                <td class="text-right"  style="width:32%">${couponBatch.sauditOperatorName}</td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.date.of.review' />：</td>
                <td class="text-right"  style="width:32%"><fmt:formatDate value='${couponBatch.tauditDatetime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.voucher.description' />：</td>
                <td class="text-right"  style="width:32%">${couponBatch.scouponInstruction}</td>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.voucher.brief' />：</td>
                <td class="text-right"  style="width:32%">${couponBatch.sbriefDesc}</td>
            </tr>
            <tr>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.use.product.restrictions' />：</td>
                <td class="text-right"  style="width:32%">${(empty couponBatch.suseLimitCommodity) ? '<spring:message code="main.unlimited"/>' : couponBatch.suseLimitCommodity}</td>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.device.restrictions' />：</td>
                <td class="text-right" style="width:32%">${(empty couponBatch.suseLimitDevice) ? '<spring:message code="main.unlimited"/>' : couponBatch.suseLimitDevice}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='ac.couponrule.use.product.classification.restrictions' />：</td>
                <td class="text-right" style="width:32%">${(empty useLimitCategory) ? '<spring:message code="main.unlimited"/>' : useLimitCategory}</td>
                <td class="text-left"  style="width:18%"><spring:message code='ac.couponrule.use.product.brand.restrictions' />：</td>
                <td class="text-right"  style="width:32%">${(empty useLimitBrand) ? '<spring:message code="main.unlimited"/>' : useLimitBrand}</td>
            </tr>
            <tr>
                <td class="text-left" style="width:18%"><spring:message code='sh.audit.opinion' />:</td>
                <td class="text-right" style="width:32%">${couponBatch.sauditOpinion}</td>
                <td class="text-left" style="width:18%">&nbsp;</td>
                <td class="text-right" style="width:32%">&nbsp;</td>
            </tr>
        </table>
    </div>
    <table class="layui-table">
        <colgroup>
            <col width="65">
            <col width="166">
            <col width="165">
            <col width="65">
            <col>
        </colgroup>
        <div class="wfsplitt" style="margin-top: 20px">
            <p><spring:message code='ac.coupons.are.distributed.to.users.in.batches' /></p>
        </div>
        <thead>
        <tr>
            <th><spring:message code='main.serial.number' /></th>
            <th><spring:message code="main.member.number" /></th>
            <th><spring:message code='main.member.username' /></th>
            <th><spring:message code='ac.number.of.issues' /></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${couponUserSendList}" var="item" varStatus="L">
            <tr>
                <td>${L.index+1}</td>
                <td>${item.smemberCode}</td>
                <td>${item.smemberName}</td>
                <td>${item.inumber}</td>
            </tr>
        </c:forEach>
        </tbody>

    </table>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>
