<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/common/include/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>Order details</title>
    <link rel="stylesheet" href="${staticSource}/static/css/comm.css"/>
    <link rel="stylesheet" href="${staticSource}/static/css/index.css">
</head>
<body class="refdetails_bg">
<%@ include file="/common/include/commonHeader.jsp" %>
<div class="refdetails_container">
    <div class="refdetails_head md">
        <c:if test="${orderRecord.istatus eq 10}">Completed</c:if>
        <c:if test="${orderRecord.istatus eq 20}">Payment Fail</c:if>
        <c:if test="${orderRecord.istatus eq 100}">To be paid</c:if>
        <c:if test="${orderRecord.istatus eq 110}">Payment processing</c:if>
    </div>

    <div class="refdetails_main m-b-07">
        <c:if test="${fn:length(commodities) > 0}">
            <ul class="refdetails_list">
                <c:forEach items="${commodities}" var="item" varStatus="L">
                    <li class="refdetails_flex">
                        <img src="${dynamicSource}${item.scommodityImg}">
                        <div class="refdetails_info">
                            <h4>${item.scommodityName}</h4>
                            <p>
                                <span>￥<f:formatNumber pattern=",##0.00" value="${item.favgActualAmount}"/> </span>
                                <span>￥<i><f:formatNumber pattern=",##0.00" value="${item.fcommodityPrice}"/></i></span>
                            </p>

                        </div>
                        <div class="refdetails_num">
                            x${item.forderCount}
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <div class="refdetails_priceinfo">
            <div class="refdetails_discounts">
                Original commodity price<span>￥<f:formatNumber pattern=",##0.00" value="${orderRecord.ftotalAmount}"/></span>
            </div>
            <c:if test="${!empty orderRecord.ffirstDiscountAmount and orderRecord.ffirstDiscountAmount > 0}">
                <div class="refdetails_discounts">
                    The first single preferential<span>-￥<f:formatNumber pattern=",##0.00" value="${orderRecord.ffirstDiscountAmount}"/></span>
                </div>
            </c:if>
            <c:if test="${!empty orderRecord.fpromotionDiscountAmount and orderRecord.fpromotionDiscountAmount > 0}">
                <div class="refdetails_discounts">
                        ${promotionTitle}<span>-￥<f:formatNumber pattern=",##0.00"
                                                                 value="${orderRecord.fpromotionDiscountAmount}"/></span>
                </div>
            </c:if>
            <c:if test="${!empty orderRecord.fcouponDeductionAmount and orderRecord.fcouponDeductionAmount > 0}">
                <div class="refdetails_discounts">
                    Coupon deduction<span>-￥<f:formatNumber pattern=",##0.00"
                                                 value="${orderRecord.fcouponDeductionAmount}"/></span>
                </div>
            </c:if>
            <c:if test="${(!empty orderRecord.fpointDiscountAmount and orderRecord.fpointDiscountAmount > 0) ||
                       (!empty orderRecord.fotherDiscountAmount and orderRecord.fotherDiscountAmount > 0) }">
                <div class="refdetails_discounts">
                    Other offers<span>-￥<f:formatNumber pattern=",##0.00"
                                                value="${orderRecord.fpointDiscountAmount+orderRecord.fotherDiscountAmount}"/></span>
                </div>
            </c:if>
            <c:if test="${!empty orderRecord.factualRefundAmount and orderRecord.factualRefundAmount > 0}">
                     <div class="shop_money">
                         Actual refund<span>￥<i class="totalprice">${orderRecord.factualRefundAmount}</i></span>
                     </div>
            </c:if>
        </div>
    </div>
    <div class="refdetails_data m-b-07">
        <p>Order number：${orderRecord.sorderCode}</p>
        <p>Order time：<f:formatDate value="${orderRecord.torderTime}" pattern="yyyy/MM/dd HH:mm"/></p>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/comm.js?236"></script>
<script src="${staticSource}/static/js/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
<script type="text/javascript" src="${staticSource}/static/js/order-pay.js?v=156"></script>
<script type="text/javascript">
    <%@ include file="/common/include/baidu.jsp"%>
</script>
</body>
</html>