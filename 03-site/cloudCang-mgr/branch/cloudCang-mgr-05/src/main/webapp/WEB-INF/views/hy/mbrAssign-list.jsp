<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>会员权限分配</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.css" rel="stylesheet" />

</head>

<body>
<div class="ibox-content">
    <select id="roleSelect" multiple="multiple">
        <c:forEach items="${mbrRoleList}" var="item" varStatus="L">
            <option value="${item.id}"<c:if test="${item.isSelect eq 1}"> selected</c:if>>${item.spurName}</option>
        </c:forEach>
    </select>

    <form class="layui-form" action="${ctx}/mbrRole/saveMbrRole" method="post" id="myForm">
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn"><spring:message code="main.cancel" /></button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub"><spring:message code="main.save" /></button>
            </div>
        </div>
        <input type="hidden" id="mid" name="mid" value="${mbrRole.id}" />
        <input type="hidden" id="roleIds" name="roleIds" value="" />
    </form>

</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui-${currentLanguage}.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.js"></script>

<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function(){
            $("#roleIds").val($("#roleSelect").val());
            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type : "post",
                dataType : "json",
                async : true,
                error:function() {
                    alertDelAndReload("<spring:message code='main.error.try.again' />");
                },
                success : function(msg) {
                    //成功返回
                    closeLayer(loadIndex);//关闭加载框
                    if(msg.success) {
                        alertSuccess("<spring:message code='main.success' />", {
                            'index': index
                        }, function () {
                            parent.closeLayerAndRefresh(index);
                        });
                    } else {
                        alertDel(msg.msg);
                    }
                }
            });
            return false;
        });
    });
    $(function() {
        $("#roleSelect").treeMultiselect();
        $("#cancelBtn").click(function() {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>
