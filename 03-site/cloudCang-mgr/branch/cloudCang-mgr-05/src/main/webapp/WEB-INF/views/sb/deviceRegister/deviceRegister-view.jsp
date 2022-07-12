<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备注册信息</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="#" method="" id="myForm">
        <div class="layui-form-item">
            <%--<div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='main.device' />ID:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegister.sdeviceId}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.device.id" />:</label>
                <label class="layui-form-label"style="text-align: left;">${deviceRegister.sdeviceCode}</label>
            </div>--%>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.device.id" />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.sdeviceCode}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.device.name" />:</label>
                <label class="layui-form-label"style="text-align: left;">${deviceRegisterDomain.sname}</label>
            </div>
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.number" />:</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.merchantName}</label>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.name" />:</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.merchantCode}</label>
                </div>
            </c:if>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sb.registered.ip' />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.sregIp}</label>
            </div>
            <%--<div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sb.registered.port' />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.sregPort}</label>
            </div>--%>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code='sb.security.key' />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.ssecurityKey}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.state" />:</label>
                <label class="layui-form-label" style="text-align: left;">
                    <c:if test="${deviceRegisterDomain.istatus == 10 }"><spring:message code="main.audited" /></c:if>
                    <c:if test="${deviceRegisterDomain.istatus == 20 }"><spring:message code="main.approval" /></c:if>
                    <c:if test="${deviceRegisterDomain.istatus == 30 }"><spring:message code="main.rejection" /></c:if>
                    <c:if test="${deviceRegisterDomain.istatus == 40 }"><spring:message code="main.registered" /></c:if>
                </label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.reviewer" />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegister.deviceRegisterDomain}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.remarks" />:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegister.deviceRegisterDomain}</label>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <%--<button class="layui-btn " id="cancelBtn"><spring:message code='sp.brand.return' /></button>--%>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

