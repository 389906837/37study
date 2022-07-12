<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>资质证书说明</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                    <div class="layui-upload-list">
                            <p style="text-align: left;">
                                    <img src="${dynamicSource}${merchantAttach.sattachPath}" <%--style='width: 450px; height: 450px;'--%>>
                            </p>
                    </div>
            </div>
        </div>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script type="text/javascript">
</script>
</body>
</html>

