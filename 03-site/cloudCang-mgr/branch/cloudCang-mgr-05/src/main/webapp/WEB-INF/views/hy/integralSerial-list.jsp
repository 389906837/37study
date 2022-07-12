<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>会员积分明细</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <table class="layui-table">
                <colgroup>
                    <col width="150">
                    <col width="150">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                </colgroup>
                <thead>
                <tr>
                    <th><spring:message code="main.member.number" /></th>
                    <th><spring:message code='main.member.username' /></th>
                    <th><spring:message code='hy.bonus.gift.rule.number' /></th>
                    <th><spring:message code='hy.credit.giving.event.rule.name' /></th>
                    <th><spring:message code='hy.integral.value' /></th>
                    <th><spring:message code='hy.account.credit.balance' /></th>
                    <th><spring:message code='hy.whether.it.is.background.operation' /></th>
                    <th><spring:message code='ac.source.number' /></th>
                    <th><spring:message code='ac.source.description' /></th>
                    <th><spring:message code='hy.time.of.production' /></th>
                    <th><spring:message code='hy.source.terminal' /></th>
                    <th><spring:message code="main.remarks" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${integralSerial}" var="item" varStatus="L">
                    <tr>
                        <td>${item.smemberNo}</td>
                        <td>${item.smemberName}</td>
                        <td>${item.sintegralRuleNo}</td>
                        <td>${item.sintegralRuleName}</td>
                        <td>${item.fvalue}</td>
                        <td>${item.fbalanceValue}</td>
                        <td>
                            <c:if test="${item.isbackOperate == 0}"><spring:message code="main.no" /></c:if>
                            <c:if test="${item.isbackOperate == 1}"><spring:message code="main.yes" /></c:if>
                        </td>
                        <td>${item.ssourceNo}</td>
                        <td>${item.ssourceInstruction}</td>
                        <td>
                            <fmt:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>
                        </td>
                        <td>
                            <c:if test="${item.isourceType == 10}"><spring:message code='ac.activityconf.platform.gift' /></c:if>
                            <c:if test="${item.isourceType == 20}"><spring:message code='hy.user.registration' /></c:if>
                            <c:if test="${item.isourceType == 30}"><spring:message code='hy.open.escrow.account' /></c:if>
                            <c:if test="${item.isourceType == 40}"><spring:message code='hy.first.tied.bank.card' /></c:if>
                            <c:if test="${item.isourceType == 50}"><spring:message code='hy.first.recharge' /></c:if>
                            <c:if test="${item.isourceType == 60}"><spring:message code='hy.participate.in.the.order' /></c:if>
                            <c:if test="${item.isourceType == 61}"><spring:message code='hy.first.order' /></c:if>
                            <c:if test="${item.isourceType == 70}"><spring:message code='hy.invite.friends.to.register' /></c:if>
                            <c:if test="${item.isourceType == 80}"><spring:message code='hy.invite.friends.for.the.first.order' /></c:if>
                            <c:if test="${item.isourceType == 90}"><spring:message code='hy.open.hosting.account.recommendation' /></c:if>
                            <c:if test="${item.isourceType == 100}"><spring:message code='hy.activity.income' /></c:if>
                            <c:if test="${item.isourceType == 110}"><spring:message code='hy.check.in' /></c:if>
                            <c:if test="${item.isourceType == 120}"><spring:message code='hy.coupon.code.exchange' /></c:if>
                        </td>
                        <td>${item.sremark}</td>
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

