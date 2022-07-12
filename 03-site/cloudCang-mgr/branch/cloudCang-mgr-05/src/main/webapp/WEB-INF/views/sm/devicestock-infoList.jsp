<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>总库存列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body class="gray-bg" style="overflow-x: scroll;">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <table class="layui-table">
                <colgroup>
                    <col width="120">
                    <col width="120">
                    <col width="120">
                    <col width="120">
                    <col width="100">
                    <col width="100">
                    <col width="120">
                    <col width="100">
                    <col width="120">
                    <col width="120">
                    <col width="110">
                    <col width="120">
                </colgroup>
                <thead>
                <tr>
                    <th><spring:message code="sm.product.name" /></th>
                    <th><spring:message code="sm.device.id" /></th>
                    <th><spring:message code="sm.device.name" /></th>
                    <th><spring:message code="sm.device.address" /></th>
                    <th><spring:message code="sm.batch.number" /></th>
                    <th><spring:message code="sm.product.status" /></th>
                    <th><spring:message code="sm.item.pricing" /></th>
                    <th><spring:message code="sm.commodity.cost.price" /></th>
                    <th><spring:message code="sm.shelf.time" /></th>
                    <th><spring:message code="sm.date.of.production" /></th>
                    <th><spring:message code="sm.shelf.life" /></th>
                    <th><spring:message code="sm.expiration.date" /></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${allList}" var="item" varStatus="L">
                    <tr>
                        <td>${item.spName}</td>
                        <td>${item.sbCode}</td>
                        <td>${item.sbName}</td>
                        <td>${item.adress}</td>
                        <td>${item.sbatchNo}</td>
                        <td>
                            <c:if test="${item.istatus == 10}"><spring:message code="sm.normal" /></c:if>
                            <c:if test="${item.istatus == 20}"><spring:message code="sm.sold" /></c:if>
                            <c:if test="${item.istatus == 30}"><spring:message code="sm.expired" /></c:if>
                            <c:if test="${item.istatus == 40}"><spring:message code="sm.expired.and.sold" /></c:if>
                        </td>
                        <td>${item.spFsaleprice}</td>
                        <td>${item.fcostAmount}</td>
                        <td>
                            <fmt:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${item.dproduceDate}" pattern="yyyy-MM-dd  HH:mm:ss"/>
                        </td>
                        <td>${item.sshelfLife}</td>
                        <td>
                            <fmt:formatDate value="${item.dexpiredDate}" pattern="yyyy-MM-dd  HH:mm:ss"/>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
</body>
</html>

