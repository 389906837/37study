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
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.number" />:</label>
                    <label class="layui-form-label" style="text-align: left;">${labelInfo.smerchantCode}</label>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label"><spring:message code="main.merchant.name" />:</label>
                    <label class="layui-form-label" style="text-align: left;">${merchantInfo.sname}</label>
                </div>
            </c:if>
            <div class="layui-col-md6">
                <label class="layui-form-label" ><spring:message code="sp.commodityInfo.label.name" />:</label>
                <label class="layui-form-label"style="text-align: left;">${labelInfo.sname}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.label.whether.the.parent.tag" />:</label>
                <label class="layui-form-label"style="text-align: left;">${labelInfo.iisParent}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="sp.label.parent.tag.id" />:</label>
                <label class="layui-form-label" style="text-align: left;">${labelInfo.sparentId}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label"><spring:message code="main.sort" />:</label>
                <label class="layui-form-label" style="text-align: left;">${labelInfo.isort}</label>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="sp.brand.return" /></button>
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
        //监听提交
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

