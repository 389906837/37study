<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>接口请求/返回数据</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <c:if test="${interfaceTransferLog.iinterfaceType eq 10}">
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code='tb.request.data' /></label>
            <div class="layui-input-block">
                <pre id="preReq"></pre>
            </div>
        </div>
    </c:if>
    <c:if test="${interfaceTransferLog.iinterfaceType eq 20}">
        <div class="layui-form-item">
            <label class="layui-form-label"><spring:message code='tb.return.data' /></label>
            <div class="layui-input-block">
                <pre id="preResp"></pre>
            </div>
        </div>
    </c:if>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
    $(".wflayui-form-item tr:even").addClass("odd");
</script>
<script>
    $(function () {
        var interfaceType = ${interfaceTransferLog.iinterfaceType};
        if (10 == interfaceType) {
            var req = ${interfaceTransferLog.srequestData};
            if (!isEmptyObject(req)) {
                $("#preReq").text(JSON.stringify(req, null, 2));
            }
        } else {
            var resp = ${interfaceTransferLog.sresponseData};
            if (!isEmptyObject(resp)) {
                $("#preResp").text(JSON.stringify(resp, null, 2));
            }
        }
    });

</script>
</body>
</html>

