<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/common/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>退款订单详情</title>
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
            <c:if test="${refundAudit.iauditStatus eq 10}">待审核</c:if>
            <c:if test="${refundAudit.iauditStatus eq 30}">审核拒绝</c:if>
            <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 10}">待退款</c:if>
            <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 20}">退款失败</c:if>
            <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 30}">订单已退款</c:if>
        </div>
        <div class="order-details-main">
            <div class="order-details-list">
                <h4>原订单号：${refundAudit.sorderCode}<span></h4>
                <h4>退款订单号：${refundAudit.srefundCode}<span><f:formatDate value="${refundAudit.tapplyTime}" pattern="yyyy/MM/dd HH:mm" /></span></h4>
                <c:if test="${fn:length(commodities) > 0}">
                    <ul>
                        <c:forEach items="${commodities}" var="item" varStatus="L">
                            <li class="order-main-listli refund-inforLi-flex">
                                <div class="shop-infor-flex">
                                    <h4>${item.scommodityName}</h4>
                                    <p>￥<f:formatNumber pattern=",##0.00" value="${item.fcommodityPrice}"/></p>
                                </div>
                                <div class="check-pic-flex order-main-listli-num trg">
                                    x${item.frefundCount}
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                <div class="order-details-discounts">
                    退款总金额<span>￥<f:formatNumber pattern=",##0.00" value="${refundAudit.fapplyRefundAmount}"/></span>
                </div>
            </div>
            <div class="shop-money-total trg">实际退款金额:<span>￥<f:formatNumber pattern=",##0.00" value="${refundAudit.factualRefundAmount}"/></span></div>
        </div>
        <c:if test="${refundAudit.iauditStatus eq 30}">
            <div class="order-main-total clearfix">
                <div class="right md">
                    <a class="apply-button look-btn" onclick="jumpApplyRefund('${refundAudit.sorderCode}');" href="javascript:void(0);">重新申请</a>
                </div>
            </div>
        </c:if>
        <c:if test="${refundAudit.iauditStatus eq 30}">
            <div class="order-details-title">
                    审核驳回原因：${refundAudit.sauditRefuseReason}
            </div>
        </c:if>
        <c:if test="${refundAudit.iauditStatus eq 20 and refundAudit.irefundStatus eq 20}">
            <div class="order-details-title">
                退款失败原因：${refundAudit.srefundFailureReason}
            </div>
        </c:if>
    </div>
    <%@ include file="/common/include/service-tell.jsp"%>
    <script type="text/javascript" src="${staticSource}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${staticSource}/static/js/comm.js"></script>
    <script src="${staticSource}/static/js/jquery.form.js"  type="text/javascript"></script>
    <script type="text/javascript" src="${staticSource}/static/js/util.js"></script>
    <script type="text/javascript">

        <%@ include file="/common/include/baidu.jsp"%>
    </script>
</body>
</html>