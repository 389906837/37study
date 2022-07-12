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
                <label class="layui-form-label">设备ID:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegister.sdeviceId}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">设备编号:</label>
                <label class="layui-form-label"style="text-align: left;">${deviceRegister.sdeviceCode}</label>
            </div>--%>
            <div class="layui-col-md6">
                <label class="layui-form-label">设备编号:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.sdeviceCode}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">设备名称:</label>
                <label class="layui-form-label"style="text-align: left;">${deviceRegisterDomain.sname}</label>
            </div>
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商户编号:</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.merchantName}</label>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">商户名称:</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.merchantCode}</label>
                </div>
            </c:if>
            <div class="layui-col-md6">
                <label class="layui-form-label">注册IP:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.sregIp}</label>
            </div>
            <%--<div class="layui-col-md6">
                <label class="layui-form-label">注册端口:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.sregPort}</label>
            </div>--%>
            <div class="layui-col-md6">
                <label class="layui-form-label">安全密钥:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegisterDomain.ssecurityKey}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">状态:</label>
                <label class="layui-form-label" style="text-align: left;">
                    <c:if test="${deviceRegisterDomain.istatus == 10 }">待审核</c:if>
                    <c:if test="${deviceRegisterDomain.istatus == 20 }">审核通过</c:if>
                    <c:if test="${deviceRegisterDomain.istatus == 30 }">审核拒绝</c:if>
                    <c:if test="${deviceRegisterDomain.istatus == 40 }">已注册</c:if>
                </label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">审核人:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegister.deviceRegisterDomain}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">备注:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceRegister.deviceRegisterDomain}</label>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <%--<button class="layui-btn " id="cancelBtn">返回</button>--%>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
</body>
</html>

