<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>活动商品</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <table class="layui-table">
                <colgroup>
                    <col width="15%">
                    <col width="15%">
                    <col width="10%">
                </colgroup>
                <thead>
                <tr>
                    <th>商品编号</th>
                    <th>商品名称</th>
                    <th>商品单价</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${commodityInfos}" var="item" varStatus="L">
                        <tr>
                            <td>${item.scode}</td>
                            <td>${item.sname}</td>
                            <td>${item.fsalePrice}</td>
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


