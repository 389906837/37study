<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>单机库存列表</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-label">设备名称:</label>
                <label class="layui-label" style="text-align: left;">${deviceInfo.sname}</label>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-label">设备地址:</label>
                <label class="layui-label" style="width: 340px">${deviceInfo.sputAddress}</label>
            </div>
        </div>

    <div class="row">
        <div class="col-sm-12">
            <table class="layui-table">
                <colgroup>
                <col width="150">
                <col width="80">
                <col width="100">
                <col width="100">
                <col width="180">
                <col width="180">
                <col width="110">
                <col width="180">
                </colgroup>
                <thead>
                <tr>
                    <th>商品名称</th>
                    <th>批次号</th>
                    <th>商品状态</th>
                    <th>商品成本价</th>
                    <th>上架时间</th>
                    <th>生产日期</th>
                    <th>保质期</th>
                    <th>过期日期</th>
                </tr>
                </thead>
                <tbody>

                    <c:forEach items="${allList}" var="item" varStatus="L">
                    <tr>
                        <td>${item.spName}</td>
                        <td>${item.sbatchNo}</td>
                        <td>
                            <c:if test="${item.istatus == 10}">正常</c:if>
                            <c:if test="${item.istatus == 20}">已售出</c:if>
                            <c:if test="${item.istatus == 30}">已过期</c:if>
                            <c:if test="${item.istatus == 40}">已过期且售出</c:if>
                        </td>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
</body>
</html>

