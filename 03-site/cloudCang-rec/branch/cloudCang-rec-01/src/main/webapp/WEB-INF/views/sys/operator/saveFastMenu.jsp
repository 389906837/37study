<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>添加快捷菜单</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.css"
      rel="stylesheet"/>

</head>

<body>
<div class="ibox-content">
    <select id="roleSelect" multiple="multiple" <%--onchange="aaa()"--%>  >
        <c:forEach items="${fastMenuList}" var="item" varStatus="L">

            <option value="${item.detailId}"<c:if
                    test="${item.isSelect eq 1}"> selected</c:if>>${item.smenuName}</option>
        </c:forEach>
    </select>

    <form class="layui-form" action="${ctx}/fastMenu/saveFastMenu" method="post" id="myForm">
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>

        <input type="hidden" id="operatorRoleIds" name="operatorRoleIds" value=""/>
    </form>

</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.js"></script>

<script type="text/javascript">
    function aaa() {
        var a = $(":selected").length;
        if (a >${maxNum}) {
            alertDel("只能选择${maxNum}个快捷菜单！")
        }
        return;
    }
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
            $("#operatorRoleIds").val($("#roleSelect").val());
            var loadIndex = loading();
            $('#myForm').ajaxSubmit({
                type: "post",
                dataType: "json",
                async: true,
                error: function () {
                    alertDelAndReload("操作异常，请重新操作");
                },
                success: function (msg) {
                    //成功返回
                    closeLayer(loadIndex);//关闭加载框
                    if (msg.success) {
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
    $(function () {
        $("#roleSelect").treeMultiselect({
            onChange: function () {
                var a = $(":selected").length;
                if (a >${maxNum}) {
                    alertDel("只能选择${maxNum}个快捷菜单！")
                }
                return false;
            }
        });
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>

