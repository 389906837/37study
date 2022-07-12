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
                    <th>会员编号</th>
                    <th>会员姓名</th>
                    <th>积分赠送活动规则编号</th>
                    <th>积分赠送活动规则名称</th>
                    <th>积分值</th>
                    <th>账户积分余额</th>
                    <th>是否后台操作</th>
                    <th>来源编号</th>
                    <th>来源说明</th>
                    <th>产生时间</th>
                    <th>来源终端</th>
                    <th>备注</th>
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
                            <c:if test="${item.isbackOperate == 0}">否</c:if>
                            <c:if test="${item.isbackOperate == 1}">是</c:if>
                        </td>
                        <td>${item.ssourceNo}</td>
                        <td>${item.ssourceInstruction}</td>
                        <td>
                            <fmt:formatDate value="${item.taddTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>
                        </td>
                        <td>
                            <c:if test="${item.isourceType == 10}">平台赠送</c:if>
                            <c:if test="${item.isourceType == 20}">用户注册</c:if>
                            <c:if test="${item.isourceType == 30}">开托管账户</c:if>
                            <c:if test="${item.isourceType == 40}">首次绑银行卡</c:if>
                            <c:if test="${item.isourceType == 50}">首次充值</c:if>
                            <c:if test="${item.isourceType == 60}">参与下单</c:if>
                            <c:if test="${item.isourceType == 61}">首次订单</c:if>
                            <c:if test="${item.isourceType == 70}">邀请好友注册</c:if>
                            <c:if test="${item.isourceType == 80}">邀请好友首次订单</c:if>
                            <c:if test="${item.isourceType == 90}">开托管账户推荐</c:if>
                            <c:if test="${item.isourceType == 100}">活动所得</c:if>
                            <c:if test="${item.isourceType == 110}">签到</c:if>
                            <c:if test="${item.isourceType == 120}">券码兑换</c:if>
                        </td>
                        <td>${item.sremark}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
</body>
</html>

