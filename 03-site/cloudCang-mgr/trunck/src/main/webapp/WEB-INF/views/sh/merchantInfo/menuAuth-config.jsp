<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/header.jsp"%>
<title>商户菜单权限分配</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.css" rel="stylesheet" />
</head>

<body>
<div class="ibox-content">
    <select id="purviewSelect" multiple="multiple">
        <c:forEach items="${purviewList}" var="item" varStatus="L">
            <c:if test="${item.bisroot eq '1'}">
                <%--<option value="${item.id}" data-section="${item.menuName}/${item.text}" <c:if test="${item.isSelect eq 1}"> selected</c:if>>${item.text}</option>--%>
                <c:forEach varStatus="K" var="purItem" items="${item.nodes}">
                    <option value="${item.id}#//#${purItem.id}" data-section="菜单权限配置/${item.menuName}/${purItem.menuName}" <c:if test="${purItem.isSelect eq 1}"> selected</c:if>>${purItem.text}</option>
                </c:forEach>
            </c:if>
            <c:if test="${item.bisroot ne '1'}">
                <option value="${item.id}" data-section="${item.sparentName}/${item.text}" <c:if test="${item.isSelect eq 1}"> selected</c:if>>${item.text}</option>
            </c:if>
        </c:forEach>
    </select>
    <form class="layui-form" action="${ctx}/merchantInfo/updateMenuAuth" method="post" id="myForm">
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="merchantId" name="merchantId" value="${merchantInfo.id}" />
        <input type="hidden" id="purviewIds" name="purviewIds" value="" />
    </form>
</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.js"></script>
<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function(){
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function(){
            $("#purviewIds").val($("#purviewSelect").val());
            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type : "post",
                dataType : "json",
                async : true,
                error:function() {
                    alertDelAndReload("操作异常，请重新操作");
                },
                success : function(msg) {
                    //成功返回
                    closeLayer(loadIndex);//关闭加载框
                    if(msg.success) {
                        alertSuccess("操作成功", {
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
        $("#purviewSelect").treeMultiselect();
        $("#cancelBtn").click(function() {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

