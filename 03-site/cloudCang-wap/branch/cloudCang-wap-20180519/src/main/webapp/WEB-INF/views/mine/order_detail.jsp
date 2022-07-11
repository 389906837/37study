<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="${staticSource}/static/css/comm.css" />
<link rel="stylesheet" href="${staticSource}/static/css/style.css" />
</head>
<body>
    <%@ include file="/common/include/commonHeader.jsp"%>
    <div class="container">
        <div class="order-details-title md">
            <img src="${staticSource}/static/images/order-details-icon.png">
            <c:if test="${orderRecord.istatus eq 10}">订单已完成</c:if>
            <c:if test="${orderRecord.istatus eq 20}">付款失败</c:if>
            <c:if test="${orderRecord.istatus eq 100}">待支付</c:if>
            <c:if test="${orderRecord.istatus eq 110}">支付处理中</c:if>
        </div>
        <div class="order-details-main">
            <div class="order-details-list">
                <h4>订单号：${orderRecord.sorderCode}<span><f:formatDate value="${orderRecord.torderTime}" pattern="yyyy/MM/dd HH:mm" /></span></h4>
                <c:if test="${fn:length(commodities) > 0}">
                    <ul>
                        <c:forEach items="${commodities}" var="item" varStatus="L">
                            <li class="order-main-listli refund-inforLi-flex">
                                <div class="shop-infor-flex">
                                    <h4>${item.scommodityName}</h4>
                                    <p>￥<f:formatNumber pattern=",##0.00" value="${item.fcommodityPrice}"/></p>
                                </div>
                                <div class="check-pic-flex order-main-listli-num trg">
                                    x${item.forderCount}
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                <div class="order-details-discounts">
                    商品总价<span>+￥<f:formatNumber pattern=",##0.00" value="${orderRecord.ftotalAmount}"/></span>
                </div>
                <%--<div class="order-details-discounts">
                    优惠总额<span>-￥<f:formatNumber pattern=",##0.00" value="${orderRecord.fdiscountAmount}"/></span>
                </div>--%>
                <c:if test="${!empty orderRecord.ffirstDiscountAmount and orderRecord.ffirstDiscountAmount > 0}">
                    <div class="order-details-discounts">
                        首单优惠<span>-￥<f:formatNumber pattern=",##0.00" value="${orderRecord.ffirstDiscountAmount}"/></span>
                    </div>
                </c:if>
                <c:if test="${!empty orderRecord.fpromotionDiscountAmount and orderRecord.fpromotionDiscountAmount > 0}">
                    <div class="order-details-discounts">
                        ${promotionTitle}<span>-￥<f:formatNumber pattern=",##0.00" value="${orderRecord.fpromotionDiscountAmount}"/></span>
                    </div>
                </c:if>
                <c:if test="${!empty orderRecord.fcouponDeductionAmount and orderRecord.fcouponDeductionAmount > 0}">
                    <div class="order-details-discounts">
                        优惠券抵扣<span>-￥<f:formatNumber pattern=",##0.00" value="${orderRecord.fcouponDeductionAmount}"/></span>
                    </div>
                </c:if>
                <c:if test="${(!empty orderRecord.fpointDiscountAmount and orderRecord.fpointDiscountAmount > 0) ||
               (!empty orderRecord.fotherDiscountAmount and orderRecord.fotherDiscountAmount > 0) }">
                    <div class="order-details-discounts">
                        其他优惠<span>-￥<f:formatNumber pattern=",##0.00" value="${orderRecord.fpointDiscountAmount+orderRecord.fotherDiscountAmount}"/></span>
                    </div>
                </c:if>
            </div>
            <div class="shop-money-total trg">实付款:<span>￥<f:formatNumber pattern=",##0.00" value="${orderRecord.factualPayAmount}"/></span></div>
            <div class="order-main-total clearfix">
                <div class="right md">
                    <c:if test="${orderRecord.istatus eq 10 and orderRecord.factualPayAmount > 0 and orderRecord.factualPayAmount > orderRecord.factualRefundAmount}">
                        <a class="apply-button look-btn" onclick="jumpApplyRefund('${orderRecord.sorderCode}');" href="javascript:void(0);">申请退款</a>
                    </c:if>
                    <c:if test="${orderRecord.istatus eq 100 or orderRecord.istatus eq 20}">
                        <a class="apply-button look-btn" href="javascript:void(0);" onclick="payment('${orderRecord.sorderCode}');">付款</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/common/include/service-tell.jsp"%>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
    <script src="${staticSource}/static/js/jquery.form.js"  type="text/javascript"></script>
    <script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/order-pay.js"></script>
    <script type="text/javascript">

        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>