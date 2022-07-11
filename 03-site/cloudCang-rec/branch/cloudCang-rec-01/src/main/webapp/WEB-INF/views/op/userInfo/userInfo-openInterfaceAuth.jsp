<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>选择接口权限</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">
<link href="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.css"
      rel="stylesheet"/>
</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="${ctx}/userInfo/saveInterfaceAuth" method="post" id="myForm">
        <div class="layui-form-item fixed-bottom">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary" id="cancelBtn">取消</button>
                <button class="layui-btn" lay-submit="" lay-filter="myFormSub">保存</button>
            </div>
        </div>
        <input type="hidden" id="pkIds" name="pkIds" value=""/>
        <input type="hidden" id="id" name="id" value="${userInfo.id}"/>
        <input type="hidden" id="scode" name="scode" value="${userInfo.scode}"/>
    </form>
    <br/>
    <c:choose>
        <c:when test="${userInterfaceAuthVos != null && fn:length(userInterfaceAuthVos) > 0}">
            <select id="openInterfaceAuthSelect" multiple="multiple">
                <c:forEach items="${userInterfaceAuthVos}" var="item" varStatus="L">
                    <option value="${item.appManageId}#${item.appManageScode}#${item.interfaceId}"
                            data-section="接口权限选择/${item.appManageScode}#${item.appManageName}"> ${item.interfaceName}</option>
                </c:forEach>
            </select>
        </c:when>
        <c:otherwise>
            <div style="text-align: center;margin-top: 30%;color: #000000;font-size: 18px;">
                    ${msg}
            </div>

        </c:otherwise>
    </c:choose>

</div>

<!-- layerUI-->
<script type="text/javascript" src="${staticSource}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${staticSource}/resources/js/jquery.form.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${staticSource}/resources/hplus/js/plugins/tree-multiselect/jquery.tree-multiselect.min.js"></script>

<script type="text/javascript">
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    layui.use(['form', 'layedit', 'laydate'], function () {
        var form = layui.form;
        //监听提交
        form.on('submit(myFormSub)', function () {
            $("#pkIds").val($("#openInterfaceAuthSelect").val());
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
        $("#openInterfaceAuthSelect").treeMultiselect();
        $("#cancelBtn").click(function () {
            parent.layer.close(index);
            return false;
        });
    });
</script>
</body>
</html>