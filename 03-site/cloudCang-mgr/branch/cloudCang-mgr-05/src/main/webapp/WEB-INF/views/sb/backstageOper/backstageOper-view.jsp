<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>操作内容/对象</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code="sb.operational.content" /></label>
            <div class="layui-input-block">
                <pre id="soperContent"></pre>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code="sb.operating.object" /></label>
            <div class="layui-input-block">
                <pre id="soperObject"></pre>
            </div>
        </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
<script>
    $(function () {
        var req = ${backstageOper.soperContent};
        if (!isEmptyObject(req)) {
            $("#soperContent").text(JSON.stringify(req, null, 2));
        }
        var resp = ${backstageOper.soperObject};
        if (!isEmptyObject(resp)) {
            $("#soperObject").text(JSON.stringify(resp, null, 2));
        }
    });

</script>
</body>
</html>

