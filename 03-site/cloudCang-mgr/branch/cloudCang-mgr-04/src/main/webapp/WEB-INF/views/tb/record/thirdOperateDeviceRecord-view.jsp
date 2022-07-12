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
            <th>商品名称</th>
            <th>第三方SKU编号</th>
            <th>本地视觉识别码</th>
            <th>商品重量（g）</th>
            <th>减少数量</th>
            <th>增加数量</th>
            <th>商品总数量</th>
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
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

