<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>第三方订单详细信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <table class="layui-table">
        <tr>
            <th><spring:message code="main.product.name" /></th>
            <th><spring:message code='tb.third.party.sku.number' /></th>
            <th><spring:message code='tb.local.visual.identity' /></th>
            <th><spring:message code='tb.product.weight' /></th>
            <th><spring:message code='tb.reduce.in.quantity' /></th>
            <th><spring:message code='tb.increase.the.number.of' /></th>
            <th><spring:message code='tb.total.quantity.of.goods' /></th>
        </tr>
        <tbody>
        <c:forEach items="${thirdOrderCommodityDomainList}" var="domain">
            <tr>
                <td>${domain.svrName}</td>
                <td>${domain.sthirdPartSkuCode}</td>
                <td>${domain.svrCode}</td>
                <td>${domain.iweight}</td>
                <td>${domain.svrDecrease}</td>
                <td>${domain.svrIncrement}</td>
                <td>${domain.svrTotalAmount}</td>
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

