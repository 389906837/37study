<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
    <title>导出配置</title>
    <link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
    <link href="${staticSource}/resources/css/override.css" rel="stylesheet">
</head>
<body>
<div class="layui-form">
    <div class="layui-form-item">
        <label class="layui-form-label"><spring:message code='sh.export.file.type' /></label>
        <div class="layui-input-block">
            <input type="radio" name="fileType" value="excel" title="EXCEL" checked="checked" />
            <input type="radio" name="fileType" value="csv" title="CSV" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><spring:message code='sh.export.range' /></label>
        <div class="layui-input-block">
            <input type="radio" name="exportRange" value="1" title="<spring:message code='sh.current.page' />" checked="checked" />
            <input type="radio" name="exportRange" value="2" title="<spring:message code='sh.all.data' />" />
        </div>
    </div>
    <div class="layui-form-item fixed-bottom">
        <div class="layui-input-block">
            <button class="layui-btn" id="myFormSub"><spring:message code='sh.export' /></button>
        </div>
    </div>
</div>
<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
    <script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form'], function () {
        var form = layui.form;
    });
    $(function() {
        $("#myFormSub").click(function() {
            var fileType = $(".layui-form").find("input[name='fileType']:checked").val();
            var exportRange = $(".layui-form").find("input[name='exportRange']:checked").val();
            parent.confirmExportFile1(fileType,exportRange);
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

