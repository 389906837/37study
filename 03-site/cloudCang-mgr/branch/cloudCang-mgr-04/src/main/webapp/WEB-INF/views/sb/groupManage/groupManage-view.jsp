<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ include file="/common/include/header.jsp" %>
<title>设备查看</title>
<link href="${staticSource}/resources/layui/css/layui.css" rel="stylesheet">
<link href="${staticSource}/resources/css/override.css" rel="stylesheet">

</head>

<body>
<div class="ibox-content">
    <form class="layui-form" action="#" method="" id="myForm">
        <div class="layui-form-item">
            <div class="layui-col-md6">
                <label class="layui-form-label">分组名称:</label>
                <label class="layui-form-label"style="text-align: left;">${groupManage.sgroupName}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">排序号:</label>
                <label class="layui-form-label" style="text-align: left;">${groupManage.isort}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">备注:</label>
                <label class="layui-form-label" style="text-align: left;">${groupManage.sremark}</label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">添加日期:</label>
                <label class="layui-form-label" style="text-align: left;"><fmt:formatDate value="${groupManage.taddTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
            </div>
            <div class="layui-col-md6">
                <label class="layui-form-label">修改日期:</label>
                <label class="layui-form-label" style="text-align: left;"><fmt:formatDate value="${groupManage.tupdateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></label>
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
        //监听提交
        /*form.on('submit(myFormSub)', function () {
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
         cancel: function () {
         parent.closeLayerAndRefresh(index);
         }
         }, function () {
         parent.closeLayerAndRefresh(index);
         });
         } else {
         alertDel(msg.msg);
         }
         }
         });
         return false;
         });*/
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



