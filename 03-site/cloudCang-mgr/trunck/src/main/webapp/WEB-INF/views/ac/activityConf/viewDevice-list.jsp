<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>活动设备</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <table class="layui-table">
                <colgroup>
                    <col width="15%">
                    <col width="25%">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th>设备编号</th>
                    <th>设备名称</th>
                    <th>地址</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${deviceInfos}" var="item" varStatus="L">
                        <tr>
                            <td>${item.scode}</td>
                            <td>${item.sname}</td>
                            <td>${item.sprovinceName}${item.scityName}${item.sareaName}${item.sputAddress}</td>
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


