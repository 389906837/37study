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
                <label class="layui-form-label">设备编号:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.sdeviceCode}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">设备名称:</label>
                <label class="layui-form-label"style="text-align: left;">${deviceCommodityDomain.sname}</label>
            </div>
            <c:if test="${SESSION_KEY_ROOT_MERCHANT eq 1}">
                <div class="layui-col-md6">
                    <label class="layui-form-label">商户编号:</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.merchantName}</label>
                </div>
                <div class="layui-col-md6">
                    <label class="layui-form-label">商户名称:</label>
                    <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.merchantCode}</label>
                </div>
            </c:if>
            <div class="layui-col-md6">
                <label class="layui-form-label">商品编号:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.scommodityCode}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">商品名称:</label>
                <label class="layui-form-label" style="text-align: left;">${deviceCommodityDomain.commodityName}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">状态:</label>
                <label class="layui-form-label" style="text-align: left;">
                    <c:if test="${deviceCommodityDomain.istatus == 10 }">在售</c:if>
                    <c:if test="${deviceCommodityDomain.istatus == 20 }">下架</c:if>
                </label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">添加日期:</label>
                <label class="layui-form-label" style="text-align: left;"><fmt:formatDate value="${deviceCommodityDomain.taddTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">修改日期:</label>
                <label class="layui-form-label" style="text-align: left;"><fmt:formatDate value="${deviceCommodityDomain.tupdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
            </div>
        </div>
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn " id="cancelBtn">返回</button>
            </div>
        </div>
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
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


