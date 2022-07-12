<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备信息查看</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="#" method="" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.device.id" />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.sdeviceCode}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.device.name" />:</label>
                <label class="layui-form-label"style="text-align: left;">${deviceCommodityDomain.sname}</label>
            </div>
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.number" />:</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.merchantName}</label>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.name" />:</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.merchantCode}</label>
                </div>
            </c:if>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.product.number" />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.scommodityCode}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.product.name" />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.commodityName}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.state" />:</label>
                <label class="layui-form-label" style="text-align: left;">
                    <c:if test="${deviceCommodityDomain.istatus == 10 }"><spring:message code="sb.on.sale" /></c:if>
                    <c:if test="${deviceCommodityDomain.istatus == 20 }"><spring:message code="sb.lower.shelf" /></c:if>
                </label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sb.add.date" />:</label>
                <label class="layui-form-label" style="text-align: left;"><fmt:formatDate value="${deviceCommodityDomain.taddTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sb.modified.date" />:</label>
                <label class="layui-form-label" style="text-align: left;"><fmt:formatDate value="${deviceCommodityDomain.tupdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn " id="cancelBtn"><spring:message code="sp.brand.return" /></button>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var form;
    layui.use(['form', 'layedit', 'laydate'], function () {
        form = layui.form;

    });

    $(function () {
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>


